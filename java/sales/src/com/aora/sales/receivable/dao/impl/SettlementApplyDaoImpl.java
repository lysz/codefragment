package com.aora.sales.receivable.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.aora.sales.common.bean.App;
import com.aora.sales.common.bean.Bill;
import com.aora.sales.common.bean.BillDetail;
import com.aora.sales.common.bean.CPASpreadDate;
import com.aora.sales.common.bean.CPTSpreadDate;
import com.aora.sales.common.bean.Page;
import com.aora.sales.receivable.dao.SettlementApplyDao;

@Repository
public class SettlementApplyDaoImpl implements SettlementApplyDao {

    private SqlSession sqlSession;
    
    public SqlSession getSqlSession() {
        return sqlSession;
    }

    @Autowired
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<App> getAppBySpreadMonth(String month) {
        
        List<App> apps = getSqlSession().selectList("settlement.getAppBySpreadMonth", month);
        
        if (null == apps){
            apps = new ArrayList<App>();
        }
        
        return apps;
    }

    public List<Bill> getBillBy(Bill bill, String from, Page page) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("companyId", bill.getCompanyId());
        map.put("companyCategoryId", bill.getCompanyCategoryId());
        map.put("status", bill.getStatus());
        map.put("invoiceCode", bill.getInvoiceCode());
        map.put("invoiceCategory", bill.getInvoiceCategory());
        map.put("billMonth", bill.getBillMonth());
        map.put("from", from);
        
        if (null == page){
            ////getTotalSize等于1, 则取所有符合条件的数据
            map.put("getTotalSize", 1);
        }else{
            map.put("start", page.getCurrentRecord());
            map.put("pageSize", page.getPageSize());
            map.put("getTotalSize", page.getTotalRecord());
        }
        
        List<Bill> bills = getSqlSession().selectList("settlement.getBill", map);
        
        if (null == bills){
            bills = new ArrayList<Bill>();
        }
        
        return bills;
    }

    public void updateBill(List<Bill> bills) {
        for (Bill bill : bills){
            getSqlSession().update("settlement.updateBill", bill);
        }
    }

    public int addBill(Bill bill) {
        getSqlSession().insert("settlement.insertBill", bill);
        
        int primaryKey = bill.getBillId();
        
        return primaryKey;
    }

    public List<Bill> getAllBillMonth() {
        
        List<Bill> billList = getSqlSession().selectList("settlement.getAllBillMonth");
        
        if (null == billList){
            billList = new ArrayList<Bill>();
        }
        
        return billList;
    }

    public void addBillDetail(BillDetail detail) {
        getSqlSession().insert("settlement.insertBillDetail", detail);
    }

    public void updateBillDetail(BillDetail detail) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("billDetailId", detail.getBillDetailId());
        map.put("billId", detail.getBillId());
        map.put("appId", detail.getAppId());
        map.put("spreadDate", detail.getSpreadDate());
        map.put("billMonth", detail.getBillMonth());
        map.put("getTotalSize", 1);
        
        BillDetail d = getSqlSession().selectOne("settlement.getBillDetail", map);
        
        if (null != d){
            map.put("downloadCount", detail.getDownloadCount());
            map.put("totalMoney", detail.getTotalMoney());
            map.put("requiredMoney", detail.getRequiredMoney());
            map.put("expression", detail.getExpression());
            getSqlSession().update("settlement.updateBillDetail", map);
        }else{
            getSqlSession().insert("settlement.insertBillDetail", detail);
        }
        
    }

    public List<BillDetail> getBillDetails(BillDetail detail, Page page) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("billDetailId", 0);
        map.put("billId", detail.getBillId());
        map.put("appId", 0);
        map.put("billMonth", null);
        
        
        if (null == page){
            ////getTotalSize等于1, 则取所有符合条件的数据
            map.put("getTotalSize", 1);
        }else{
            map.put("start", page.getCurrentRecord());
            map.put("pageSize", page.getPageSize());
            map.put("getTotalSize", page.getTotalRecord());
        }
        
        List<BillDetail> list = getSqlSession().selectList("settlement.getBillDetail", map);
        
        if (null == list){
            list = new ArrayList<BillDetail>();
        }
        
        return list;
    }

    public List<CPASpreadDate> getCPASpread(int appId, String month) {
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appId", appId);
        params.put("billMonth", month);
        
        List<CPASpreadDate> list = getSqlSession().selectList("settlement.getCPASpreaddate", params);
        
        if (null == list){
            list = new ArrayList<CPASpreadDate>();
        }
        
        return list;
    }
    
    public List<CPTSpreadDate> getCPTSpread(int appId, String month) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appId", appId);
        map.put("billMonth", month);
        
        List<CPTSpreadDate> list = getSqlSession().selectList("settlement.getCPTSpreadDate", map);
        
        if (null == list){
            list = new ArrayList<CPTSpreadDate>();
        }
        
        return list;
    }

    public void addCPASpread(List<CPASpreadDate> list) {
        for (CPASpreadDate one : list){
            
            if (one.getCpasId() == 0){
                getSqlSession().insert("settlement.addCPASpread", one);                
            }else if (one.getCpasId() != 0 && !StringUtils.isEmpty(one.getPrice())){
                getSqlSession().update("settlement.updateCPASpreaddate", one);
            }else if (one.getCpasId() != 0 && StringUtils.isEmpty(one.getPrice())){
                getSqlSession().delete("settlement.deleteCPASpreaddate", one.getCpasId());
            }
        }
    }

    public BillDetail getBillDetail(BillDetail detail) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("billDetailId", detail.getBillDetailId());
        map.put("billId", detail.getBillId());
        map.put("appId", detail.getAppId());
        map.put("billMonth", detail.getBillMonth());
        map.put("getTotalSize", 1);
        
        return getSqlSession().selectOne("settlement.getBillDetail", map);
    }

    public void auditForBusiness(Bill bill) {
        getSqlSession().update("settlement.updateBill", bill);
    }
}
