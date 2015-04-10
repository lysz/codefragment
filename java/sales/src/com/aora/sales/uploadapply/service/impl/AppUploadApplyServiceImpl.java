package com.aora.sales.uploadapply.service.impl;

import static com.aora.sales.uploadapply.constants.Constants.APP_ONLINE_STATUS_NEW;
import static com.aora.sales.uploadapply.constants.Constants.APP_ONLINE_STATUS_TEST_RESULT_AGREE;
import static com.aora.sales.uploadapply.constants.Constants.APP_ONLINE_STATUS_TEST_RESULT_DISAGREE;
import static com.aora.sales.uploadapply.constants.Constants.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.aora.sales.common.bean.App;
import com.aora.sales.common.bean.CPTSpreadDate;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.uploadapply.dao.AppUploadApplyDao;
import com.aora.sales.uploadapply.service.AppUploadApplyService;


@Service
@Transactional
public class AppUploadApplyServiceImpl implements AppUploadApplyService {

    @Autowired
    private AppUploadApplyDao appUploadApplyDao;

    public AppUploadApplyDao getAppUploadApplyDao() {
        return appUploadApplyDao;
    }

    public void setAppUploadApplyDao(AppUploadApplyDao appUploadApplyDao) {
        this.appUploadApplyDao = appUploadApplyDao;
    }
    
    @Transactional(readOnly=true)
    public List<Dict> getDictByType(String dictType){
        return getAppUploadApplyDao().getDictByType(dictType);
    }

    @Transactional(isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public void addUploadApply(App app, String spreadDateStr) {
        
        setAppStatusToNew(app);
        
        //保存app对象
        int appId = getAppUploadApplyDao().addUploadApply(app);
                
        //将客户端提交的cpt推广日期转换为 CPTSpreadDate 对象
        List<CPTSpreadDate> spreadDateList = convertStringToList(spreadDateStr, appId);
        
        getAppUploadApplyDao().saveCPTSpreadDate(spreadDateList);
    
    }
    
    
    private List<CPTSpreadDate> convertStringToList(String spreadDateStr, int appId){
        
        List<CPTSpreadDate> cptDatas = new ArrayList<CPTSpreadDate>();
        
        if (StringUtils.isEmpty(spreadDateStr)){
            return cptDatas;
        }
        
        
        String[] monthSpreadData = spreadDateStr.split("@");
                
        for (String monthData : monthSpreadData){
            String[] monthDataArray = monthData.split(":");
            String yearMonth = monthDataArray[0];
            String monthDatas = monthDataArray[1];
            
            String year = yearMonth.split("\\.")[0];
            //每个推广日期之间用(,)分割,如果没有(,)说明当月只有一个推广日期 
            if (!monthDatas.contains(",")){
                CPTSpreadDate cptData = createCPTSpreadDate(year, monthDatas, appId);
                
                if (null != cptData){
                    cptDatas.add(cptData);
                }
                
            }else{
                String[] allDatas = monthDatas.split(",");
                
                for (String oneData : allDatas){
                    CPTSpreadDate cptData = createCPTSpreadDate(year, oneData, appId);
                    
                    if (null != cptData){
                        cptDatas.add(cptData);
                    }
                }
            }
        }
        
        return cptDatas;
    }
    
    private CPTSpreadDate createCPTSpreadDate(String year, String oneData, int appId){
        String[] data = oneData.split("#");
        
        CPTSpreadDate instance = null;
        
        if (data.length == 2) {
            String date = data[0];
            String money = data[1];

            if (null != money && !money.trim().equals("")) {

                instance = new CPTSpreadDate();
                instance.setPrice(money);
                date = year + "-" + date;
                date = date.replaceAll("\\.", "-");
                instance.setSpreadDate(date);
                instance.setAppId(appId);
            }
            
        }else if (data.length == 3){
            
            String date = data[0];
            String money = data[1];
            String cptSpreadId = data[2];
            
            instance = new CPTSpreadDate();
            date = year + "-" + date;
            date = date.replaceAll("\\.", "-");
            instance.setSpreadDate(date);
            instance.setAppId(appId);
            instance.setPrice(money.trim());
            instance.setCptSpreadId(Integer.valueOf(cptSpreadId));
        }
        
        return instance;
    }
    
    
    /**
     * 设置app的状态
     * @param app
     */
    private void setAppStatusToNew(App app){
        
        Dict param = new Dict();
        param.setDictType("appstatus");
        param.setKeyName(APP_ONLINE_STATUS_NEW);
        
        Dict dict = getAppUploadApplyDao().getDict(param);
        app.setStatusId(dict.getDictId());
    }

    @Transactional(readOnly=true)
    public List<App> getAllApps(App app, String from, String type, Page page) {
        
        List<App> list = getAppUploadApplyDao().getAllApps(app, from, type, page);
        
        if (list.isEmpty()){
            return list;
        }
        
        //遍历所有的App, 如果分成模式是CPT, 则将所有的推广日期查找出来
        for (App one : list){
            if ("CPT".equalsIgnoreCase(one.getDividedModeName())){
                 List<CPTSpreadDate> dateList = getAppUploadApplyDao().getCPTSpreadDateByAppId(one.getAppId());
                 String spreadDate  = convertSpreadDateListToString(dateList);
                 one.setCptSpreadDate(spreadDate);
            }
        }
        
        return list;
    }
    
    /**
     * 将List转换成String
     * @param dateList
     * @return
     */
    private String convertSpreadDateListToString(List<CPTSpreadDate> dateList){
        StringBuilder build = new StringBuilder();
        
        for (CPTSpreadDate spreadDate :  dateList){
            build.append(spreadDate.getSpreadDate()).append("<br/>");
        }
        
        String result = build.toString();
        //result = result.replaceAll(",$", "");
        
        return result;
    }

    @Transactional(readOnly=true)
    public App getAppById(int appId) {
        return getAppUploadApplyDao().getAppById(appId);
    }

    @Transactional(readOnly=true)
    public String getMinSpreadMonthByAppId(int appId) {
        
        return getAppUploadApplyDao().getMinSpreadMonthByAppId(appId);
    }

    @Transactional(readOnly=true)
    public List<CPTSpreadDate> getSpreadDate(int appId, String month) {
        
        return getAppUploadApplyDao().getSpreadDate(appId, month);
    }

    @Transactional(isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public void updateUploadApply(App app, String spreadDateStr) {
        //先修改app信息
        getAppUploadApplyDao().updateUploadApply(app);
        //再解析推广日期
        List<CPTSpreadDate> data = convertStringToList(spreadDateStr, app.getAppId());
        //更新推广日期
        getAppUploadApplyDao().updateAppSpread(data);
    }

    @Transactional(isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public void auditUploadApplyForBusiness(int appId) {
        getAppUploadApplyDao().auditUploadApplyForBusiness(appId);
    }

    @Transactional(isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public void auditUploadApplyForTest(int appId, int testResultId, String description) {
                
        //通过testResultId得出dictName
        Dict dict = getDictById(testResultId);
        String result = dict.getDictName();
        
        //组装testResult属性
        String testResult = result;
        if (!StringUtils.isEmpty(description)){
            testResult += ":" + description;
        }
                
        Dict param = new Dict();
        param.setDictType("appstatus");
        
        //设置查询参数
        if (dict.getKeyName().equalsIgnoreCase(APP_ONLINE_STATUS_TEST_RESULT_AGREE)){
            param.setKeyName(APP_ONLINE_STATUS_WAIT_ONLINE_OPERATION_AUDIT);
        }else if (dict.getKeyName().equalsIgnoreCase(APP_ONLINE_STATUS_TEST_RESULT_DISAGREE)){
            param.setKeyName(APP_ONLINE_STATUS_NEW);
        }
        
        //根据查询参数查询出字典Id
        dict = getAppUploadApplyDao().getDict(param);        
        int dictId = dict.getDictId();
        
        App app = new App();
        app.setTestResult(testResult);
        app.setAppId(appId);
        app.setStatusId(dictId);
        
        getAppUploadApplyDao().auditUploadApplyForTest(app);
    }
    
    private Dict getDictById(int dictId){
        Dict param = new Dict();
        param.setDictId(dictId);
        
        //通过testResultId得出dictName
        Dict dict = getAppUploadApplyDao().getDict(param);
        
        return dict;
    }

    @Transactional(isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public void auditUploadApplyForOperation(int appId) {
        getAppUploadApplyDao().auditUploadApplyForOperation(appId);
    }

    @Transactional(isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public void auditUploadApplyForOnline(App app, boolean isOnline) {
        getAppUploadApplyDao().auditUploadApplyForOnline(app, isOnline);
    }

    @Transactional(isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public void auditAppOfflineForBusiness(int appId) {
        getAppUploadApplyDao().auditAppOfflineForBusiness(appId);
    }

    @Transactional(isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public void auditAppOfflineForOperation(int appI) {
        getAppUploadApplyDao().auditAppOfflineForOperation(appI);
    }

    public List<App> getAllOnlineAppsByCompanyId(int companyId) {
        
        Dict param = new Dict();
        param.setDictType("appstatus");
        param.setKeyName(APP_ONLINE_STATUS_ONLINED);
        
        //根据查询参数查询出字典Id
        Dict dict = getAppUploadApplyDao().getDict(param);        
        int dictId = dict.getDictId();
        
        App app = new App();
        app.setCompanyId(companyId);
        app.setStatusId(dictId);
        
        return getAppUploadApplyDao().getAllOnlineAppsByCompanyId(app);
    }
}
