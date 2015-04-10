package com.aora.sales.common.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 收入
 * @author Administrator
 *
 */
public class Income implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7591474872248408725L;

	/**
	 * 收入Id
	 */
	private int incomeId;
	
	/**
	 * 收入金额
	 */
	private BigDecimal money;
	
	/**
	 * 应用Id
	 */
	private int appId;
	
	/**
	 * 产生收入的时间
	 */
	private Date billDate;
	
	/**
	 * 计算公式
	 */
	private String expression;

	/**
	 * 下载量
	 */
	private String downloadCount;
	
	/**
	 * 备注
	 */
	private String desc;

	public int getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(int incomeId) {
		this.incomeId = incomeId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(String downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
}
