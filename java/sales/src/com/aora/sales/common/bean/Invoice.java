package com.aora.sales.common.bean;

/**
 * 发票
 * @author Administrator
 *
 */
public class Invoice implements java.io.Serializable{

	
	private static final long serialVersionUID = -7821482232979814065L;

	/**
	 * 发票Id
	 */
	private int invoiceId;
	
	/**
	 * 发票类型
	 */
	private int type;
	
	/**
	 * 发票编号
	 */
	private String invoiceCode;
	
	/**
	 * 帐单Id
	 */
	private int billId;
	
	/**
	 * 备注
	 */
	private String desc;
	
	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}
	
	
}
