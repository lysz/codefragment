package com.aora.sales.common.bean;

import java.util.Date;

/**
 * 应用
 * @author Administrator
 *
 */
public class App implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 应用Id
	 */
	private int appId;
	
	/**
	 * 应用名
	 */
	private String appName;
	
	/**
	 * 包名
	 */
	private String packageName;
	
	/**
	 * icon路径
	 */
	private String iconPath;
	
	/**
	 * apk存放路径
	 */
	private String apkPath;
	
	/**
	 * 后台查询路径
	 */
	private String platformURL;
	
	/**
	 * 后台查询密码
	 */
	private String platformPwd;
	
	/**
	 * 分成模式
	 */
	private int dividedMode;
	
	private String dividedModeName;
	
	/**
	 * 单价
	 */
	private String price;
	
	/**
	 * 是否大类
	 */
	private int mainCategory;
	
	private String mainCategoryName;
	
	/**
	 * 所属公司Id
	 */
	private int companyId;
	
	/**
	 * 所属公司名称
	 */
	private String companyName;
	
	/**
	 * 推广的月份
	 */
	private String spreadMonth;
	
	/**
	 * 推广日期
	 */
	private String cptSpreadDate;
	
	
	/**
	 * 应用申请者
	 */
	private int createrId;
	
	/**
	 * 申请时间
	 */
	private Date createDate;
	
	/**
	 * 合同Id
	 */
	private int contractId;
	
		
	/**
	 * 状态
	 */
	private int statusId;
	
	private String status;
		
	private String testResult;
	
	/**
	 * 备注
	 */
	private String description;
	
	/**
	 * 上架时间
	 */
	private Date onlineDate;
	
	private String onlineDateStr;
	
	/**
	 * 下架时间
	 */
	private Date offlineDate;
	
	private String offlineDateStr;

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

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

	public String getApkPath() {
		return apkPath;
	}

	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}

	public String getPlatformURL() {
		return platformURL;
	}

	public void setPlatformURL(String platformURL) {
		this.platformURL = platformURL;
	}

	public String getPlatformPwd() {
		return platformPwd;
	}

	public void setPlatformPwd(String platformPwd) {
		this.platformPwd = platformPwd;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	

	public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
    
	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

    public int getDividedMode() {
        return dividedMode;
    }

    public void setDividedMode(int dividedMode) {
        this.dividedMode = dividedMode;
    }

    public int getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(int mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getDividedModeName() {
        return dividedModeName;
    }

    public void setDividedModeName(String dividedModeName) {
        this.dividedModeName = dividedModeName;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public Date getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }

    public Date getOfflineDate() {
        return offlineDate;
    }

    public void setOfflineDate(Date offlineDate) {
        this.offlineDate = offlineDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSpreadMonth() {
        return spreadMonth;
    }

    public void setSpreadMonth(String spreadMonth) {
        this.spreadMonth = spreadMonth;
    }

    public String getOnlineDateStr() {
        return onlineDateStr;
    }

    public void setOnlineDateStr(String onlineDateStr) {
        this.onlineDateStr = onlineDateStr;
    }

    public String getOfflineDateStr() {
        return offlineDateStr;
    }

    public void setOfflineDateStr(String offlineDateStr) {
        this.offlineDateStr = offlineDateStr;
    }

    public String getCptSpreadDate() {
        return cptSpreadDate;
    }

    public void setCptSpreadDate(String cptSpreadDate) {
        this.cptSpreadDate = cptSpreadDate;
    }
    
    
}
