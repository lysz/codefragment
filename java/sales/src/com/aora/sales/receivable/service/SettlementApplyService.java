package com.aora.sales.receivable.service;

import java.util.List;

import com.aora.sales.common.bean.Audit;
import com.aora.sales.common.bean.Bill;
import com.aora.sales.common.bean.BillDetail;
import com.aora.sales.common.bean.CPASpreadDate;
import com.aora.sales.common.bean.Page;

public interface SettlementApplyService {

    void auditSettlementApply(Bill bill, Audit audit);
    
    List<Bill> getBillBy(Bill bill, String from, Page p);
    
    List<Bill> getAllBillMonth();
    
    List<BillDetail> getBillDetails(BillDetail detail, Page page);
    
    List<CPASpreadDate> getCPASpread(int appId, String month);
    
    void addCPASpread(int appId, String spread);
    
    void saveExpression(int billDetailId, String expression);
}
