package com.aora.sales.uploadapply.dao.impl;

import static com.aora.sales.uploadapply.constants.Constants.APP_ONLINE_STATUS_NEW;
import static com.aora.sales.uploadapply.constants.Constants.APP_ONLINE_STATUS_OFFLINED;
import static com.aora.sales.uploadapply.constants.Constants.APP_ONLINE_STATUS_ONLINED;
import static com.aora.sales.uploadapply.constants.Constants.APP_ONLINE_STATUS_WAIT_OFFLINE_OPERATION_AUDIT;
import static com.aora.sales.uploadapply.constants.Constants.APP_ONLINE_STATUS_WAIT_TEST;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.aora.sales.common.bean.App;
import com.aora.sales.common.bean.CPASpreadDate;
import com.aora.sales.common.bean.CPTSpreadDate;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.uploadapply.dao.AppUploadApplyDao;

@Repository("appUploadApplyDao")
public class AppUploadApplyDaoImpl implements AppUploadApplyDao {

    @Autowired
    private SqlSession sqlSession;
    
    public SqlSession getSqlSession() {
        return sqlSession;
    }

    
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    public List<Dict> getDictByType(String dictType) {
        
        List<Dict> dicts = getSqlSession().selectList("appupload.getDictByType", dictType);
        
        if (null == dicts){
            dicts = new ArrayList<Dict>();
        }
        
        return dicts;
    }

    public Dict getDict(Dict param){        
        
        return getSqlSession().selectOne("appupload.getDict", param);
    }

    public int addUploadApply(App app) {
        getSqlSession().insert("appupload.addAppUploadApply", app);
        
        return app.getAppId();
    }
    
    public List<App> getAllApps(App app, String from, String type, Page page){
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", app.getCompanyId());
        map.put("mainCategory", app.getMainCategory());
        map.put("appId", app.getAppId());
        map.put("statusId", app.getStatusId());
        map.put("appName", app.getAppName());
        map.put("dividedMode", app.getDividedMode());
        
        map.put("from", from);
        map.put("type", type);
        
        map.put("start", page.getCurrentRecord());
        map.put("pageSize", page.getPageSize());
        map.put("getTotalSize", page.getTotalRecord());
        
        List<App> apps = getSqlSession().selectList("appupload.getApps", map);
        
        if (null == apps){
            apps = new ArrayList<App>();
        }
        
        return apps;
    }


    public void saveCPTSpreadDate(List<CPTSpreadDate> spreadDateList) {
        
        for (CPTSpreadDate cptSpreadDate : spreadDateList){
            getSqlSession().insert("appupload.addCPTSpreadDate", cptSpreadDate);
        }
    }


    public App getAppById(int appId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", 0);
        map.put("mainCategory", 0);
        map.put("appId",  appId);
        map.put("statusId", 0);
        map.put("appName", null);
        map.put("dividedMode", 0);
        

        map.put("getTotalSize", 1);
        
        App app = getSqlSession().selectOne("appupload.getApps", map);
        
        return app;
    }


    public String getMinSpreadMonthByAppId(int appId) {
        String month = getSqlSession().selectOne("appupload.getMinSpreadMonth", appId);
        return month;
    }


    public List<CPTSpreadDate> getSpreadDate(int appId, String month) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appId", appId);
        map.put("spreadMonth", month);
        
        List<CPTSpreadDate> list = getSqlSession().selectList("appupload.getSpreadDate", map);
        
        if (null == list){
            list = new ArrayList<CPTSpreadDate>();
        }
        
        return list;
    }


    public void updateAppSpread(List<CPTSpreadDate> spreadDateList) {
        for (CPTSpreadDate cptSpreadDate : spreadDateList){
            //如果SpreadId不等于0(说明数据库有对应的记录), 而且price不为空, 则直接根据spreadId更新price
            if (cptSpreadDate.getCptSpreadId() != 0 && !StringUtils.isEmpty(cptSpreadDate.getPrice())){                
                getSqlSession().update("appupload.updateCPTSpreadDate", cptSpreadDate);
            }
            //如果SpreadId不等于0(说明数据库有对应的记录), 而且price为空,则说明客户在界面删除了对应的price
            else if (cptSpreadDate.getCptSpreadId() != 0 && StringUtils.isEmpty(cptSpreadDate.getPrice())){
                getSqlSession().delete("appupload.deleteCPTSpreadDate", cptSpreadDate.getCptSpreadId());
            }
            //如果SpreadId等于0, 则说明数据库没有对应的记录, 是新录入的, 直接入库
            else if(cptSpreadDate.getCptSpreadId() == 0){
                getSqlSession().insert("appupload.addCPTSpreadDate", cptSpreadDate); 
            }
        }
    }


    public void updateUploadApply(App app) {
        getSqlSession().update("appupload.updateApp", app);
    }


    public void auditUploadApplyForBusiness(int appId) {
        
        Dict param = new Dict();
        param.setDictType("appstatus");
        param.setKeyName(APP_ONLINE_STATUS_WAIT_TEST);
        
        //查询出待测试状态的字典Id
        Dict dict = getDict(param); 
        int dictId = dict.getDictId();
        
        App app = new App();
        app.setAppId(appId);
        app.setStatusId(dictId);
        
        //根据appId, 修改app的状态
        getSqlSession().update("appupload.updateStatus", app);
    }


    public void auditUploadApplyForTest(App app) {
        
        getSqlSession().update("appupload.updateStatus", app);
    }


    public void auditUploadApplyForOperation(int appId) {
        Dict param = new Dict();
        param.setDictType("appstatus");
        param.setKeyName(APP_ONLINE_STATUS_ONLINED);
        
        //查询出待测试状态的字典Id
        Dict dict = getDict(param);        
        int dictId = dict.getDictId();
        
        App app = new App();
        app.setAppId(appId);
        app.setStatusId(dictId);
        
        //根据appId, 修改app的状态
        getSqlSession().update("appupload.updateStatus", app);
    }


    public void auditUploadApplyForOnline(App app, boolean isOnline) {
        
        Dict param = new Dict();
        param.setDictType("appstatus");
        
       if (isOnline){
           param.setKeyName(APP_ONLINE_STATUS_ONLINED);           
       }else{
           param.setKeyName(APP_ONLINE_STATUS_NEW); 
       }
       
       //查询出待测试状态的字典Id
       Dict dict = getDict(param);        
       app.setStatusId(dict.getDictId());
       app.setOnlineDate(new Date());
       
       
       //根据appId, 修改app的状态
       getSqlSession().update("appupload.updateStatus", app);
    }


    public void auditAppOfflineForBusiness(int appId) {
        Dict param = new Dict();
        param.setDictType("appstatus");
        param.setKeyName(APP_ONLINE_STATUS_WAIT_OFFLINE_OPERATION_AUDIT); 
        
        //查询出待测试状态的字典Id
        Dict dict = getDict(param);        
        
        App app = new App();
        app.setAppId(appId);
        app.setStatusId(dict.getDictId());
        
        //根据appId, 修改app的状态
        getSqlSession().update("appupload.updateStatus", app);
    }
    

    public void auditAppOfflineForOperation(int appId) {
        
       Dict param = new Dict();
       param.setDictType("appstatus");
       param.setKeyName(APP_ONLINE_STATUS_OFFLINED); 
       
       //查询出待测试状态的字典Id
       Dict dict = getDict(param);
       
       App app = new App();
       app.setAppId(appId);
       app.setStatusId(dict.getDictId());
       app.setOfflineDate(new Date());
       
       
       //根据appId, 修改app的状态
       getSqlSession().update("appupload.updateStatus", app);
    }


    public List<CPTSpreadDate> getCPTSpreadDateByAppId(int appId) {
                
        List<CPTSpreadDate> list = getSqlSession().selectList("appupload.getSpreadDateByAppId", appId);
        
        if (null == list){
            list = new ArrayList<CPTSpreadDate>();
        }
        
        return list;
    }


    public List<App> getAllOnlineAppsByCompanyId(App app) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", app.getCompanyId());
        map.put("mainCategory", 0);
        map.put("appId",  0);
        map.put("statusId", app.getStatusId());
        map.put("appName", null);
        map.put("dividedMode", 0);
        
        map.put("getTotalSize", 1);
        
        List<App> apps = getSqlSession().selectList("appupload.getApps", map);
        
        if (null == apps){
            apps = new ArrayList<App>();
        }
        
        return apps;
    }


    public List<CPASpreadDate> getCPASpreadDate(int appId, String month) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appId", appId);
        map.put("spreadMonth", month);
        
        List<CPASpreadDate> list = getSqlSession().selectList("appupload.getCPASpreadDate", map);
        
        if (null == list){
            list = new ArrayList<CPASpreadDate>();
        }
        
        return list;
    }
}
