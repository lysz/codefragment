package com.aora.sales.uploadapply.service;

import java.util.List;

import com.aora.sales.common.bean.App;
import com.aora.sales.common.bean.CPTSpreadDate;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;

public interface AppUploadApplyService {

    List<Dict> getDictByType(String dictType);
    
    void addUploadApply(App app, String spreadDateStr);
    
    /**
     * 查询app
     * @param app
     * @param from  请求来自哪种角色(business, test, operation)
     * @param type  类型, 上架/下架  (因为不管是上架推广还是下架, 都会调用该方法, 只是参数不同而己)
     * @param page  封装分页信息
     * @return
     */
    List<App> getAllApps(App app, String from, String type, Page page);
    
    App getAppById(int appId);
    
    /**
     * 获取指定appId最小推广的月份
     * @param appId
     * @return
     */
    String getMinSpreadMonthByAppId(int appId);
    
    List<CPTSpreadDate> getSpreadDate(int appId, String month);
    
    void updateUploadApply(App app, String spreadDateStr);
    
    /**
     * 商务审核app上架
     * @param appId
     */
    void auditUploadApplyForBusiness(int appId);
    
    void auditUploadApplyForTest(int appId, int testResultId, String description);
    
    void auditUploadApplyForOperation(int appId);
    
    /**
     * 
     * @param app 待上架的app
     * @param isOnline  是否上/下架
     */
    void auditUploadApplyForOnline(App app, boolean isOnline);
    
    /**
     * 商务审核app下架
     * @param appId
     */
    void auditAppOfflineForBusiness(int appId);
    
    void auditAppOfflineForOperation(int appI);
    
    List<App> getAllOnlineAppsByCompanyId(int companyId);
}
