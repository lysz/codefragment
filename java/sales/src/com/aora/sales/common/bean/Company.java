package com.aora.sales.common.bean;

import java.util.Date;

/**
 * 合作方, 公司
 * @author Administrator
 *
 */
public class Company implements java.io.Serializable{

	
	private static final long serialVersionUID = 1816519866323876095L;

	
	/**
	 * Id
	 */
	private int companyId;
	
	/**
	 * 公司名称 
	 */
	private String companyName;
	
	/**
	 * 公司类型
	 */
	private int categoryId; 
	
	/**
	 * 类别名称
	 */
	private String categoryName;
	
	/**
	 * 结算周期, 
	 */
	private int billingCycleId;
	
	/**
	 * 联系人
	 */
	private String contact;
	
	/**
	 * 联系电话
	 */
	private String telephone;
	
	/**
	 * 手机号码
	 */
	private String mobilephone;
	
	/**
	 * email
	 */
	private String email;

	/**
	 * 备注
	 */
	private String description;
	
	/**
	 * 创建者Id
	 */
	private int creatorId;

	/**
	 * 创建时间
	 */
    private Date createDate;
    
    /**
     * 最后修改时间
     */
    private Date lastChangeDate;
    
    /**
     * 最后修改时间字符串
     */
    private String lastChangeDateStr;
    
    

	public String getLastChangeDateStr() {
        return lastChangeDateStr;
    }

    public void setLastChangeDateStr(String lastChangeDateStr) {
        this.lastChangeDateStr = lastChangeDateStr;
    }

    public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	

	public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBillingCycleId() {
		return billingCycleId;
	}

	public void setBillingCycleId(int billingCycleId) {
		this.billingCycleId = billingCycleId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
	
	
}
