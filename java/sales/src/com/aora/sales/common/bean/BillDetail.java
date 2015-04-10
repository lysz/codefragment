package com.aora.sales.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 帐单明细
 * @author Administrator
 *
 */
public class BillDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4892732602963176379L;

	/**
	 * 帐单明细Id
	 */
	private int billDetailId;
	
	/**
	 * 帐单Id
	 */
	private int billId;
	
	/**
	 * 应用Id
	 */
	private int appId;
	
    /**
     * 分成模式
     */
    private int dividedMode;
	
	/**
	 * 推广月分
	 */
	private String billMonth;
	
	private String price;
	
    /**
     * 帐单流水汇总
     */
    private BigDecimal totalMoney;
    /**
     * 应收金额
     */
    private BigDecimal requiredMoney;
    
    /**
     * 结算金额
     */
    private BigDecimal actualMoney;
    
    /**
     * 计算工式
     */
    private String expression;
	
	/**
	 * 本月单次下载收益
	 */
	private BigDecimal benefit;
	
	/**
	 * 本月下载量
	 */
	private int downloadCount;
	
	private String appName;
	
	private String packageName;
	
	private String iconPath;
	
	private String mainCategoryName;
	
	private String dividedModeName;
	
	private String spreadDate;
	/**
	 * 备注
	 */
	private String description;
	
	

	public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getDividedModeName() {
        return dividedModeName;
    }

    public void setDividedModeName(String dividedModeName) {
        this.dividedModeName = dividedModeName;
    }

    public int getBillDetailId() {
		return billDetailId;
	}

	public void setBillDetailId(int billDetailId) {
		this.billDetailId = billDetailId;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public BigDecimal getBenefit() {
		return benefit;
	}

	public void setBenefit(BigDecimal benefit) {
		this.benefit = benefit;
	}

    public int getDividedMode() {
        return dividedMode;
    }

    public void setDividedMode(int dividedMode) {
        this.dividedMode = dividedMode;
    }
    

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getRequiredMoney() {
        return requiredMoney;
    }

    public void setRequiredMoney(BigDecimal requiredMoney) {
        this.requiredMoney = requiredMoney;
    }

    public BigDecimal getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(BigDecimal actualMoney) {
        this.actualMoney = actualMoney;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpreadDate() {
        return spreadDate;
    }

    public void setSpreadDate(String spreadDate) {
        this.spreadDate = spreadDate;
    }
	
	
}
