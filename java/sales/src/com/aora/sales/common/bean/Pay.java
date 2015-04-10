package com.aora.sales.common.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Pay implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 197280172930456681L;

	/**
	 * 付款Id
	 */
	private int payId;
	
	/**
	 * 产品Id
	 */
	private int appId;
	
	/**
	 * 结算年月
	 */
	private int payDate;
	
	/**
	 * 付款金额
	 */
	private BigDecimal payMoney;
	
	/**
	 * 审批状态
	 */
	private int status;
	
	
	/**
	 * 支付申请创建时间
	 */
	private Date createDate;
	
	/**
	 * 商务提交审批时间
	 */
	private Date submitDate;
	
	/**
	 * 最后修改时间
	 */
	private Date lastChangeDate;
	
	/**
	 * 备注
	 */
	private String desc;

	public int getPayId() {
		return payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getPayDate() {
		return payDate;
	}

	public void setPayDate(int payDate) {
		this.payDate = payDate;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getLastChangeDate() {
		return lastChangeDate;
	}

	public void setLastChangeDate(Date lastChangeDate) {
		this.lastChangeDate = lastChangeDate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
