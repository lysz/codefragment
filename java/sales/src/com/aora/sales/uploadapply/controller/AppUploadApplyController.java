package com.aora.sales.uploadapply.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aora.sales.common.bean.App;
import com.aora.sales.common.bean.CPTSpreadDate;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.uploadapply.service.AppUploadApplyService;

@Controller
@RequestMapping("/appupload")
public class AppUploadApplyController {

    private static Logger logger = Logger.getLogger(AppUploadApplyController.class); 
    
    @Autowired
    private AppUploadApplyService appUploadApplyService;

    public AppUploadApplyService getAppUploadApplyService() {
        return appUploadApplyService;
    }

    public void setAppUploadApplyService(AppUploadApplyService appUploadApplyService) {
        this.appUploadApplyService = appUploadApplyService;
    }
    
    private int getCurrentMonth(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        
        return month;
    }
    
    private int getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        
        return year;
    }
    
    @RequestMapping(value="/getDictByType", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getDictByType(@RequestParam("dictType") String dictType){
        
        List<Dict> dicts = getAppUploadApplyService().getDictByType(dictType);
        
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        
        for (Dict d : dicts){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dictId", d.getDictId());
            map.put("dictName", d.getDictName());
            data.add(map);
        }
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("root", data);
        result.put("tocalCount", data.size());
        
        JSONObject jsonObj = JSONObject.fromObject(result);
        return jsonObj.toString();
    }
    
    @RequestMapping(value="/getAllOnlineAppsByCompanyId", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllOnlineAppsByCompanyId(@RequestParam(value = "companyId") int companyId){
        
        List<App> data = getAppUploadApplyService().getAllOnlineAppsByCompanyId(companyId);
        
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("root", data);
        result.put("tocalCount", data.size());
        
        JSONObject jsonObj = JSONObject.fromObject(result);
        return jsonObj.toString();
    }
    
    @RequestMapping(value="/getSpreadMonth", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getSpreadMonth(){
        
        int year = getCurrentYear();
        int month = getCurrentMonth();
        
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        
        for (int i = month; i <= 12; i++){
            Map<String, String> ele = new HashMap<String, String>(); 
            String val = year + "." + i;
            ele.put("dictId", val);
            ele.put("dictName", val);
            data.add(ele);
        }
        
        if (month >= 6){
            year += 1;
            
            for (int i = 1; i <= 6; i++){
                Map<String, String> ele = new HashMap<String, String>(); 
                String val = year + "." + i;
                ele.put("dictId", val);
                ele.put("dictName", val);
                data.add(ele);
            }
        }
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("root", data);
        result.put("tocalCount", data.size());
        
        JSONObject jsonObj = JSONObject.fromObject(result);
        return jsonObj.toString();
    }
    
    @RequestMapping(value="/addAppUploadApply", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addAppUploadApply(App app, @RequestParam(value = "spreadDates", required = false) String spreadDates){
        
        logger.info("spreadDate:" + spreadDates);
        
        String result = null;
        
        if (null != app){            
            getAppUploadApplyService().addUploadApply(app, spreadDates);            
            result = "{success:true}";            
        }else{
            result = "{success:false}";
        }
        
        return result;
    }
    
    @RequestMapping(value="/getApps", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getApps(
            @RequestParam(value = "companyId") String companyIdStr,
            @RequestParam(value = "isMainCategory") String isMainCategoryStr,
            @RequestParam(value = "appId") String appIdStr,
            @RequestParam(value = "appstatus") String appstatusStr,
            @RequestParam(value = "appName") String appName,
            @RequestParam(value = "dividedMode") String dividedModeStr,
            @RequestParam(value = "from") String from,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "start") int start,
            @RequestParam(value = "limit") int limit){
        
        
        App app = new App();
        app.setCompanyId(StringUtils.isEmpty(companyIdStr) ? 0 : Integer.valueOf(companyIdStr.trim()));
        app.setAppId(StringUtils.isEmpty(appIdStr) ? 0 : Integer.valueOf(appIdStr.trim()));
        app.setMainCategory(StringUtils.isEmpty(isMainCategoryStr) ? 0 : Integer.valueOf(isMainCategoryStr.trim()));
        app.setStatusId(StringUtils.isEmpty(appstatusStr) ? 0 : Integer.valueOf(appstatusStr.trim()));
        app.setAppName(StringUtils.isEmpty(appName) ? null : appName.trim());
        app.setDividedMode(StringUtils.isEmpty(dividedModeStr) ? 0 : Integer.valueOf(dividedModeStr.trim()));
        
        
        Page p = new Page();
        p.setCurrentRecord(start);
        p.setPageSize(limit);
        p.setTotalRecord(0);
        
        //默认情况下, 商务只能查询新增的app, 测试只能查看待测试的app, 运营只能查看待运营审核的app
        //当用户在界面上根据状态太查询时,就根据该状态查询所有符合条件的app, 默认的查询失效
        if (app.getStatusId() != 0){
            from  = null;
        }
        
        List<App> apps = getAppUploadApplyService().getAllApps(app, from, type,  p);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("root", apps);
        
        p.setTotalRecord(1);
        List<App> allApps = getAppUploadApplyService().getAllApps(app, from, type, p);
        data.put("totalCount", allApps.size());
        
        JSONObject jsonObject = JSONObject.fromObject(data);
        return jsonObject.toString();
    }
    
    @RequestMapping(value="/getById", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAppById(@RequestParam(value = "appId") int appId){
        
        App app = getAppUploadApplyService().getAppById(appId);
        
        if (app.getDividedModeName().equalsIgnoreCase("CPT")){
            String spreadMonth = getAppUploadApplyService().getMinSpreadMonthByAppId(appId);
            app.setSpreadMonth(spreadMonth);
        }
                
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", app);
        
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
        
    }
    
    @RequestMapping(value="/getSpreadDate", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getSpreadDate(@RequestParam(value = "appId") int appId, @RequestParam(value = "month") String month){
        
        month = month.replace(".", "-");
        List<CPTSpreadDate>  list = getAppUploadApplyService().getSpreadDate(appId, month);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", list);
        
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }
    
    @RequestMapping(value="/updateUploadApply", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updateUploadApply(App app, @RequestParam(value = "spreadDates", required = false) String spreadDateStr){
        
        logger.info("spreadDate:" + spreadDateStr);
        
        String result = "";
        
        if (null != app){
            getAppUploadApplyService().updateUploadApply(app, spreadDateStr);
            result = "{success:true}";
        }else{
            result = "{success:false}";
        }
        
        return result;
    }
    
    @RequestMapping(value="/auditUploadApplyForBusiness", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String auditUploadApplyForBusiness(@RequestParam(value = "appId") int appId){
        getAppUploadApplyService().auditUploadApplyForBusiness(appId);
        return "{success:true}";
    }
    
    @RequestMapping(value="/auditUploadApplyForTest", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String auditUploadApplyForTest(
            @RequestParam(value = "appId") int appId,
            @RequestParam(value = "testResultId") int testResultId,
            @RequestParam(value = "description") String description){
        
        getAppUploadApplyService().auditUploadApplyForTest(appId, testResultId, description);
        
        return "{success:true}";
    }
    
    @RequestMapping(value="/auditUploadApplyForOperation", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String auditUploadApplyForOperation(
            @RequestParam(value = "appId") int appId,
            @RequestParam(value = "packageName") String packageName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "isOnline") boolean isOnline
            ){
        
        App app = new App();
        app.setAppId(appId);
        app.setPackageName(packageName);
        app.setDescription(description);
        
        getAppUploadApplyService().auditUploadApplyForOnline(app, isOnline);
        
        return "{success:true}";
    }
    
    @RequestMapping(value="/auditAppOfflineForBusiness", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String auditAppOfflineForBusiness(@RequestParam(value = "appId") int appId){
        getAppUploadApplyService().auditAppOfflineForBusiness(appId);
        return "{success:true}";
    }
    
    @RequestMapping(value="/auditAppOfflineForOperation", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String auditAppOfflineForOperation(@RequestParam(value = "appId") int appId){
        getAppUploadApplyService().auditAppOfflineForOperation(appId);
        return "{success:true}";
    }
}
