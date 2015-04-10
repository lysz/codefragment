package com.aora.sales.receivable.constants;

public interface Constants {
    
    public static String APP_SPREAD_TYPE_CPT = "CPT";
    
    public static String APP_SPREAD_TYPE_CPS = "CPS";
    
    public static String APP_SPREAD_TYPE_CPA = "CPA";
    
    //商务审核
    public static String ROLE_NAME_BUSINESS = "business";
    //运营审核
    public static String ROLE_NAME_OPERATION = "operation";
    //运营总监审核
    public static String ROLE_NAME_OPERATIONDIRECTOR = "operationDirector";
    //财务开票审核
    public static String ROLE_NAME_FINANCEAUDIT = "financeAudit";
    //财务回款确认审核
    public static String ROLE_NAME_FINANCECONFIRM = "financeConfirm";
    
    
    //申请待提交
    public static String SETTLEMENT_APPLY_BUSINESS_UNCOMMIT = "settlement-wait-submit";
    //待运营审核
    public static String SETTLEMENT_APPLY_STATUS_WAIT_OPERATION = "settlement-wait-operation";
    //待运营总监审核
    public static String SETTLEMENT_APPLY_STATUS_WAIT_OPERATIONDIRECTOR = "settlement-wait-operationDirector";
    //待财务审核开票
    public static String SETTLEMENT_APPLY_STATUS_WAIT_FINANCEAUDIT = "settlement-wait-financeaudit";
    //待财务审核确认
    public static String SETTLEMENT_APPLY_STATUS_WAIT_FINANCECONFIRM = "settlement-wait-financeconfirm";
    //己回款
    public static String SETTLEMENT_APPLY_STATUS_RECEIVED_COMPLETION = "settlement-received-completion";
    
    //审核通过
    public static String AUDIT_RESULT_PASS = "通过";
    //审核不通过
    public static String AUDIT_RESULT_NO_PASS = "不通过";
}
