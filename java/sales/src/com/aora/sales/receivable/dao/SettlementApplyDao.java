package com.aora.sales.receivable.dao;

import java.util.List;

import com.aora.sales.common.bean.App;
import com.aora.sales.common.bean.Bill;
import com.aora.sales.common.bean.BillDetail;
import com.aora.sales.common.bean.CPASpreadDate;
import com.aora.sales.common.bean.CPTSpreadDate;
import com.aora.sales.common.bean.Page;

public interface SettlementApplyDao {

    /**
     * 查询所有己上架的App
     * @return
     */
    List<App> getAppBySpreadMonth(String month);
    
    List<Bill> getBillBy(Bill bill, String from, Page p);
    
    /**
     * 更新结算申请
     * @param bill
     */
    void updateBill(List<Bill> bill);
    
    /**
     * 新增一个结算申请
     * @param bill
     * @return
     */
    int addBill(Bill bill);
    
    void addBillDetail(BillDetail detail);
    
    void updateBillDetail(BillDetail detail);
    
    List<Bill> getAllBillMonth();
    
    List<BillDetail> getBillDetails(BillDetail detail, Page page);
    
    List<CPASpreadDate> getCPASpread(int appId, String month);
    
    List<CPTSpreadDate> getCPTSpread(int appId, String month);
    
    void addCPASpread(List<CPASpreadDate> list);
    
    BillDetail getBillDetail(BillDetail detail);
    
    void auditForBusiness(Bill bill);
}
