package com.aora.sales.common.bean;

/**
 * 银行账户表
 * 
 * @author Administrator
 * 
 */
public class Account implements java.io.Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1301586602982633101L;

    /**
     * 账户Id
     */
    private int accountId;

    /**
     * 帐号
     */
    private String accountNumber;

    /**
     * 户头
     */
    private String userName;

    /**
     * 开户行
     */
    private int bank;
    
    
    /**
     * 开户行名称 
     */
    private String bankName;

    /**
     * 合同Id
     */
    private int contractId;

    /**
     * 所属公司Id
     */
    private int companyId;
    
    /**
     * 帐号类型
     */
    private int accountType;
    
    /**
     * 帐号类型名称 
     */
    private String accounttypeName;
    
    

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccounttypeName() {
        return accounttypeName;
    }

    public void setAccounttypeName(String accounttypeName) {
        this.accounttypeName = accounttypeName;
    }

}
