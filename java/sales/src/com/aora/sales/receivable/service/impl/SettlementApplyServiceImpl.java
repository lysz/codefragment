package com.aora.sales.receivable.service.impl;

import static com.aora.sales.receivable.constants.Constants.*;
import static com.aora.sales.receivable.constants.Constants.APP_SPREAD_TYPE_CPS;
import static com.aora.sales.receivable.constants.Constants.APP_SPREAD_TYPE_CPT;
import static com.aora.sales.receivable.constants.Constants.SETTLEMENT_APPLY_BUSINESS_UNCOMMIT;
import static com.aora.sales.receivable.constants.Constants.SETTLEMENT_APPLY_STATUS_WAIT_OPERATION;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aora.sales.common.bean.App;
import com.aora.sales.common.bean.Audit;
import com.aora.sales.common.bean.Bill;
import com.aora.sales.common.bean.BillDetail;
import com.aora.sales.common.bean.CPASpreadDate;
import com.aora.sales.common.bean.CPTSpreadDate;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.common.util.DateUtils;
import com.aora.sales.receivable.dao.SettlementApplyDao;
import com.aora.sales.receivable.service.SettlementApplyService;
import com.aora.sales.uploadapply.dao.AppUploadApplyDao;


@Service(value = "settlementApplyService")
public class SettlementApplyServiceImpl implements SettlementApplyService {

    private static Logger logger = Logger.getLogger(SettlementApplyServiceImpl.class); 
    
    @Autowired
    private SettlementApplyDao settlementApplyDao;
    
    @Autowired
    private AppUploadApplyDao appUploadApplyDao;

    public AppUploadApplyDao getAppUploadApplyDao() {
        return appUploadApplyDao;
    }

    public void setAppUploadApplyDao(AppUploadApplyDao appUploadApplyDao) {
        this.appUploadApplyDao = appUploadApplyDao;
    }

    public SettlementApplyDao getSettlementApplyDao() {
        return settlementApplyDao;
    }

    public void setSettlementApplyDao(SettlementApplyDao settlementApplyDao) {
        this.settlementApplyDao = settlementApplyDao;
    }
    
    // Quartz 
    public void job(){
        try{            
            createSettlementApply();
        }catch(Exception e){
            logger.error(e);
        }
    }

    //@Transactional(isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public void createSettlementApply() {
        
        //获取当前月产生费用的app, 由companyId作为key
        Map<Integer, List<App>> onlineApps = getAppBySpreadMonth(getCurrentMonth("."));
        
        //获取结算状态:商务未提交        
        Dict dict = getSettlementStatusBusinessUncommit();
        
        //通过计算己上架的app的推广模式与推广金额去更新商务未提交的结算申请的流水金额等数据
        updateUncommitBillByOnlineApp(dict, onlineApps);
        
        //新增结算款项
        addNewBill(dict, onlineApps);
    }
    
    /**
     * 修改结算申请
     * @param dict
     * @param onlineApps
     */
    private void updateUncommitBillByOnlineApp(Dict dict, Map<Integer, List<App>> onlineApps){
        //获取所有未提交的结算申请
        List<Bill> bills = getAllUncommitBill(dict.getDictId());
        
        List<Bill> waitToUpdate = new ArrayList<Bill>();
        
        for (Bill bill : bills){
            
            //如果现有的结算申请已经包含了companyId, 则直接更新现有的bill
            if (onlineApps.containsKey(bill.getCompanyId())){
                List<App> apps = onlineApps.get(bill.getCompanyId());
                                
                waitToUpdate.add(bill);

                //根据companyId移除己上架的app, 
                onlineApps.remove(bill.getCompanyId());
                
                //更新帐单明细表
                updateBillDetail(bill, apps);
            }
        }
        
        //更新现有的bill
        getSettlementApplyDao().updateBill(waitToUpdate);
    }
    
    private void updateBillDetail(Bill bill, List<App> apps){
        
        String billMonth = getCurrentMonth("-");
        
        BigDecimal billTotalMoney = new BigDecimal(0);
        BigDecimal billRequiredMoney = new BigDecimal(0);
        
        for (App app : apps){
            
            BillDetail detail = new BillDetail();
            detail.setAppId(app.getAppId());
            detail.setBillId(bill.getBillId());
            detail.setBillMonth(getCurrentMonth("."));
            
            String divideName = app.getDividedModeName();
            if (divideName.equalsIgnoreCase(APP_SPREAD_TYPE_CPT)){
                setCPTSpreadAndTotalMoney(billMonth, detail);
            }else if(divideName.equalsIgnoreCase(APP_SPREAD_TYPE_CPA) || divideName.equalsIgnoreCase(APP_SPREAD_TYPE_CPS)){
                setCPASpreadAndTotalMoney(billMonth, detail);                
            }
            
            if (detail.getTotalMoney() != null){
                billTotalMoney    = billTotalMoney.add(detail.getTotalMoney());
            }
            
            if (detail.getRequiredMoney() != null){                
                billRequiredMoney = billRequiredMoney.add(detail.getRequiredMoney());
            }
                        
            getSettlementApplyDao().updateBillDetail(detail);
        }
        
        bill.setTotalMoney(billTotalMoney);
        bill.setRequiredMoney(billRequiredMoney);
    }
    
    private void setCPASpreadAndTotalMoney(String month, BillDetail detail){
        
        //在tbl_billdetail表获取计算公式字段
        BillDetail param = new BillDetail();
        param.setAppId(detail.getAppId());
        param.setBillId(detail.getBillId());
        param.setBillMonth(getCurrentMonth("."));
        BillDetail dtl = getSettlementApplyDao().getBillDetail(param);
        String expression = null;
        if (null != dtl){
            expression = dtl.getExpression();
        }
        
        //获取cpa spread date
        List<CPASpreadDate> cpaSpread = getSettlementApplyDao().getCPASpread(detail.getAppId(), month);
        
        StringBuilder build = new StringBuilder();
        BigDecimal totalMoney = new BigDecimal(0);
        BigDecimal requiredMoney = new BigDecimal(0);
        
        BigDecimal expNum = null;
        if (null != expression){
            expNum = new BigDecimal(expression);
        }
                
        for (CPASpreadDate cpa : cpaSpread){
            build.append(cpa.getSpreadDate()).append(",");
            
            //计算流水汇总
            BigDecimal price = new BigDecimal(cpa.getPrice());
            
            //计算应收金额
            //应收金额 = (每笔流水 * 公式 ) 之后的累加和
            if (null != expNum){
                BigDecimal requiredPrice = price.multiply(expNum);
                requiredMoney = requiredMoney.add(requiredPrice);;
            }
            
            //累加流水汇总
            totalMoney = totalMoney.add(price);
        }
        
        String spreadDate = build.toString().replaceAll(",$", "");
        detail.setSpreadDate(spreadDate);
        detail.setTotalMoney(totalMoney);
        detail.setRequiredMoney(requiredMoney);        
    }
    
    private void setCPTSpreadAndTotalMoney(String month, BillDetail detail){
        
        List<CPTSpreadDate> cptSpread = getSettlementApplyDao().getCPTSpread(detail.getAppId(), month);
        
        BigDecimal total = new BigDecimal(0);
        StringBuilder build = new StringBuilder();
        
        for (CPTSpreadDate cpt : cptSpread){
            
            //计算流水汇总
            BigDecimal tmp = new BigDecimal(cpt.getPrice());
            total = total.add(tmp);
            
            //计算推广日期
            build.append(cpt.getSpreadDate()).append(",");
        }
        
        String spreadDates = build.toString().replaceAll(",$", "");
        
        detail.setSpreadDate(spreadDates);
        detail.setTotalMoney(total);
        detail.setRequiredMoney(total);
    }
    
    /**
     * 新增结算款项
     * @param dict
     * @param onlineApps
     */
    private void addNewBill(Dict dict, Map<Integer, List<App>> onlineApps){
        
        List<Bill> waitToUpdate = new ArrayList<Bill>();
        
        for (Map.Entry<Integer, List<App>> entry: onlineApps.entrySet()){
            
            Bill bill = new Bill();
            bill.setCompanyId(entry.getKey());
            bill.setStatus(dict.getDictId());
            bill.setLastChangeDate(new Date());
                        
            //新增结算申请款项 持久化,入库,  
            int pk = getSettlementApplyDao().addBill(bill);
            
            //新增结算申请明细, 并修改结算申请的流水汇总, 应收汇总
            createBillDetail(pk, bill, entry.getValue());
            
            waitToUpdate.add(bill);
        }
        
        //更新现有的bill
        getSettlementApplyDao().updateBill(waitToUpdate);
    }
    
    private void createBillDetail(int billId, Bill bill, List<App> appList){
        String billDetailMonth = getCurrentMonth(".");
        String month = billDetailMonth.replace("\\.", "-");
        
        BigDecimal billTotalMoney = new BigDecimal(0);
        BigDecimal billRequiredMoney = new BigDecimal(0);
        
        for (App app : appList){
            
            BillDetail detail = new BillDetail();
            detail.setAppId(app.getAppId());
            detail.setBillId(billId);
            detail.setBillMonth(billDetailMonth);
            detail.setDividedMode(app.getDividedMode());
            
            String divideName = app.getDividedModeName();
            if (divideName.equalsIgnoreCase(APP_SPREAD_TYPE_CPT)){
                
               setCPTSpreadAndTotalMoney(month, detail);
            
            }else if(divideName.equalsIgnoreCase(APP_SPREAD_TYPE_CPA) || divideName.equalsIgnoreCase(APP_SPREAD_TYPE_CPS)){
                
                setCPASpreadAndTotalMoney(month, detail);
            }
            
            billTotalMoney = billTotalMoney.add(detail.getTotalMoney());
            billRequiredMoney = billRequiredMoney.add(detail.getRequiredMoney());
            
            getSettlementApplyDao().addBillDetail(detail);
        }
        
        bill.setTotalMoney(billTotalMoney);
        bill.setRequiredMoney(billRequiredMoney);
    }
    
    
    /**
     * 获取商务未提交的结算状态的字典        
     * @return
     */
    private Dict getSettlementStatusBusinessUncommit(){
        Dict param = new Dict();
        param.setDictType("settlementstatus");
        param.setDictName(SETTLEMENT_APPLY_BUSINESS_UNCOMMIT);
        
        Dict dict = getAppUploadApplyDao().getDict(param);
        
        return dict;
    }
    
    private String getCurrentMonth(String sep){
        
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        String month = year + sep + m;
        
        return month;
    }
    
    /**
     * 获取所有己上架或下架的app,返回Map, 该Map以companyId为key
     * @return
     */
    private Map<Integer, List<App>> getAppBySpreadMonth(String month){
        
        Map<Integer, List<App>> map = new HashMap<Integer, List<App>>();
        
        //获取所有已上架或下架的App
        List<App> apps = getSettlementApplyDao().getAppBySpreadMonth(month);

        if (apps.isEmpty()) {
            return map;
        }

        for (App app : apps) {
            int companyId = app.getCompanyId();

            if (map.containsKey(companyId)) {
                List<App> list = map.get(companyId);
                list.add(app);
            } else {
                List<App> list = new ArrayList<App>();
                list.add(app);

                map.put(companyId, list);
            }
        }
        
        return map;
    }
    
    /**
     * 查询所有未提交的结算申请
     * @return
     */
    private List<Bill> getAllUncommitBill(int status){
                        
        Bill bill = new Bill();
        bill.setStatus(status);
        //设置当前月份
        bill.setBillMonth(DateUtils.formatToMonth(new Date()));
        
        List<Bill> bills = getSettlementApplyDao().getBillBy(bill, null, null);
        
        return bills;
    }

    
    public List<Bill> getBillBy(Bill bill, String from, Page page) {        
        return getSettlementApplyDao().getBillBy(bill, from, page);
    }

    public List<Bill> getAllBillMonth() {
        
        return getSettlementApplyDao().getAllBillMonth();
    }

    public List<BillDetail> getBillDetails(BillDetail detail, Page page) {
        
        List<BillDetail> details = getSettlementApplyDao().getBillDetails(detail, page);
        
        
        for (BillDetail d : details){
            if (StringUtils.isEmpty(d.getSpreadDate())){
                continue;
            }
            
            d.setSpreadDate(d.getSpreadDate().replaceAll(",", "<br/>"));
        }
        
        return details;
    }

    public List<CPASpreadDate> getCPASpread(int appId, String month) {
        return getSettlementApplyDao().getCPASpread(appId, month);
    }

    public void addCPASpread(int appId, String spread) {
        String[] data = spread.split(":");
        
        String yearMonth = data[0];
        String year = yearMonth.split("\\.")[0];
        String money = data[1];
        
        if (StringUtils.isEmpty(money)){
            return ;
        }

        List<CPASpreadDate> list = new ArrayList<CPASpreadDate>();
        
        for (String onday : money.split(",")){
            CPASpreadDate instance = createCPASpreadDate(onday, year, appId);
            
            if (null == instance){
                continue;
            }
            
            list.add(instance);
        }
        
        getSettlementApplyDao().addCPASpread(list);
    }
    
    private CPASpreadDate createCPASpreadDate(String onday, String year, int appId){
        if (StringUtils.isEmpty(onday)){
            return null;
        }
        
        String[] strArr = onday.split("#");
        
        CPASpreadDate instance = null;
        
        if(strArr.length == 2){
            String date = strArr[0];
            String money = strArr[1];
            
            if (!StringUtils.isEmpty(money)){
                
                instance = new CPASpreadDate();
                instance.setPrice(money.trim());
                date = year + "-" + date;
                date = date.replaceAll("\\.", "-");
                instance.setSpreadDate(date);
                instance.setAppId(appId);
            }
            
        }
        else if (strArr.length == 3){
            String date = strArr[0];
            String money = strArr[1];
            String cpaspreadId = strArr[2];
            
            instance = new CPASpreadDate();
            date = year + "-" + date;
            date = date.replaceAll("\\.", "-");
            instance.setSpreadDate(date);
            instance.setAppId(appId);
            instance.setPrice(StringUtils.isEmpty(money) ? "" : money.trim());
            instance.setCpasId(Integer.valueOf(cpaspreadId));
        }
        
        return instance;
    }

    public void saveExpression(int billDetailId, String expression) {
        BillDetail detail = new BillDetail();
        detail.setBillDetailId(billDetailId);
        detail.setExpression(expression);
        
        getSettlementApplyDao().updateBillDetail(detail);
    }

    
    public void auditSettlementApply(Bill bill, Audit audit) {
        
        bill.setStatus(getStatus(audit));
        getSettlementApplyDao().auditForBusiness(bill);
    }
    
    
    private int getStatus(Audit audit){
        Dict param = setSettlementStatusByAuditor(audit);
        Dict dict = getAppUploadApplyDao().getDict(param);
        int dictId = dict.getDictId();
        
        return dictId;
    }
    
    /**
     * 通过审核者的角色来设置结算申请的状态
     * @param dict
     * @param from
     */
    private Dict setSettlementStatusByAuditor(Audit audit){
        //审核者的角色
        String from = audit.getAuditorName();
        int auditResult = audit.getAuditResult();
        
        Dict dict = new Dict();
        dict.setDictType("settlementstatus");
        
        //只要审核不通过, 统一回退到结算申请
        if (AUDIT_RESULT_NO_PASS.equals(auditResult)){
            dict.setKeyName(SETTLEMENT_APPLY_BUSINESS_UNCOMMIT);
            return dict;
        }
        
        if (from.equals(ROLE_NAME_BUSINESS)){
            dict.setKeyName(SETTLEMENT_APPLY_STATUS_WAIT_OPERATION);
        }else if (from.equals(ROLE_NAME_OPERATION)){
            dict.setKeyName(SETTLEMENT_APPLY_STATUS_WAIT_OPERATIONDIRECTOR);
        }else if (from.equals(ROLE_NAME_OPERATIONDIRECTOR)){
            dict.setKeyName(SETTLEMENT_APPLY_STATUS_WAIT_FINANCEAUDIT);
        }else if (from.equals(ROLE_NAME_FINANCEAUDIT)){
            dict.setKeyName(SETTLEMENT_APPLY_STATUS_WAIT_FINANCECONFIRM);
        }else if (from.equals(ROLE_NAME_FINANCECONFIRM)){
            dict.setKeyName(SETTLEMENT_APPLY_STATUS_RECEIVED_COMPLETION);
        }
        
        return dict;
    }
}
