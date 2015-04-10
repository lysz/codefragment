package com.aora.sales.receivable.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aora.sales.common.bean.Audit;
import com.aora.sales.common.bean.Bill;
import com.aora.sales.common.bean.BillDetail;
import com.aora.sales.common.bean.CPASpreadDate;
import com.aora.sales.common.bean.Page;
import com.aora.sales.receivable.service.SettlementApplyService;

@Controller
@RequestMapping("/settlementApply")
public class SettlementApplyController {

    @Autowired
    private SettlementApplyService settlementApplyService;

    public SettlementApplyService getSettlementApplyService() {
        return settlementApplyService;
    }

    public void setSettlementApplyService(
            SettlementApplyService settlementApplyService) {
        this.settlementApplyService = settlementApplyService;
    }
    
    @RequestMapping(value="/showSettlementApply", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String showSettlementApply(
            @RequestParam("companyId") String companyIdStr,  
            @RequestParam("companyCategoryId") String companyCategoryIdStr, 
            @RequestParam("settlementstatus") String settlementstatusStr,
            @RequestParam("invoiceCode") String invoiceCodeStr, 
            @RequestParam("invoiceCategory") String invoiceCategoryStr, 
            @RequestParam("billMonth") String billMonthStr,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam("from") String from,
            @RequestParam("page") int page, 
            @RequestParam("start") int start, 
            @RequestParam("limit") int limit){
        
        Bill bill = new Bill();
        bill.setCompanyId(StringUtils.isEmpty(companyIdStr)? 0 : Integer.valueOf(companyIdStr));
        bill.setCompanyCategoryId(StringUtils.isEmpty(companyCategoryIdStr) ? 0 : Integer.valueOf(companyCategoryIdStr));
        bill.setStatus(StringUtils.isEmpty(settlementstatusStr) ? 0 : Integer.valueOf(settlementstatusStr));
        bill.setInvoiceCode(StringUtils.isEmpty(invoiceCodeStr) ? null : invoiceCodeStr.trim());
        bill.setInvoiceCategory(StringUtils.isEmpty(invoiceCategoryStr) ? 0 : Integer.valueOf(invoiceCategoryStr));
        bill.setBillMonth(StringUtils.isEmpty(billMonthStr) ? null : billMonthStr.trim());
        bill.setStartDate(startDate);
        bill.setEndDate(endDate);
        
        Page p = new Page();
        p.setCurrentRecord(start);
        p.setPageSize(limit);
        p.setTotalRecord(0);
        
        List<Bill> bills = getSettlementApplyService().getBillBy(bill, from, p);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("root", bills);
        
        p.setTotalRecord(1);
        List<Bill> allBills = getSettlementApplyService().getBillBy(bill, from, p);
        data.put("totalCount", allBills.size());
        
        JSONObject jsonObject = JSONObject.fromObject(data);
        return jsonObject.toString();
    }
    
    @RequestMapping(value="/getAllBillMonth", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllBillMonth(){
        
        List<Bill> billList = getSettlementApplyService().getAllBillMonth();
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("root", billList);
        data.put("tocalCount", billList.size());
        
        JSONObject jsonArray = JSONObject.fromObject(data);
        return jsonArray.toString();
    }
    
    @RequestMapping(value="/showDetails", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String showDetails(
            @RequestParam("billId") int billId,
            @RequestParam("appId") String appIdStr,
            @RequestParam("dividedMode") String dividedModeStr,
            @RequestParam("isMainCategory") String isMainCategoryStr,
            @RequestParam("appstatus") String appstatusStr,
            @RequestParam("invoiceCategory") String invoiceCategoryStr,
            @RequestParam("year") String yearStr,
            @RequestParam("month") String monthStr,
            @RequestParam("page") int page,
            @RequestParam("start") int start,
            @RequestParam("limit") int limit
            ){
        
        BillDetail detail = new BillDetail();
        detail.setBillId(billId);
        
        Page p = new Page();
        p.setCurrentRecord(start);
        p.setPageSize(limit);
        p.setTotalRecord(0);
        
        List<BillDetail> list = getSettlementApplyService().getBillDetails(detail, p);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("root", list);
        
        p.setTotalRecord(1);
        List<BillDetail> all = getSettlementApplyService().getBillDetails(detail, p);
        data.put("totalCount", all.size());
        
        JSONObject jsonArray = JSONObject.fromObject(data);
        return jsonArray.toString();
    }
    
    @RequestMapping(value="/getCPASpread", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getCPASpread(@RequestParam("appId") int appId, @RequestParam("month") String month){
        
        month = month.replace(".", "-");
        
        List<CPASpreadDate>  list = getSettlementApplyService().getCPASpread(appId, month);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", list);
        
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }
    
    @RequestMapping(value="/addCPASpread", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addCPASpread(
            @RequestParam("appId") int appId,
            @RequestParam("spread") String spread
            ){
        
        if (StringUtils.isEmpty(spread)){
            return "{success:false}";
        }
        
        getSettlementApplyService().addCPASpread(appId, spread);
        
        return "{success:false}";
    }
    
    @RequestMapping(value="/saveExpression", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String saveExpression(
            @RequestParam("billDetaillId") int billDetaillId,
            @RequestParam("expression") String expression
            ){
        
        getSettlementApplyService().saveExpression(billDetaillId, expression);
        return "{success:true}";
    }
    
    @RequestMapping(value="/auditSettlementApply", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String auditSettlementApply(
            @RequestParam("billId") int billId, 
            @RequestParam(value = "actualMoney", required = false) String actualMoneyStr,
            @RequestParam(value = "invoiceCategory", required = false) String invoiceCategory, 
            @RequestParam("from") String from,
            @RequestParam(value="auditResult", required = false) String auditResultStr,
            @RequestParam("description") String description){
        
        //封装结算申请的数据
        Bill bill = new Bill();
        bill.setBillId(billId);
        bill.setInvoiceCategory(StringUtils.isEmpty(invoiceCategory) ? 0 : Integer.valueOf(invoiceCategory));
        bill.setDescription(description);
        try{
            BigDecimal bd = new BigDecimal(StringUtils.isEmpty(actualMoneyStr) ? "0" : actualMoneyStr);
            bill.setActualMoney(bd);
        }catch(NumberFormatException e){
            return "{success: false, msg: '结算金额格式有误！'}";
        }
        
        //封装审核的数据
        Audit audit = new Audit();
        audit.setAuditResultStr(auditResultStr);
        audit.setAuditTargetId(billId);
        audit.setAuditorName(from);
        audit.setDescription(StringUtils.isEmpty(description) ? "" : description);
        
        getSettlementApplyService().auditSettlementApply(bill, audit);        
        return "{success:true}";
    }
    
    
    
}
