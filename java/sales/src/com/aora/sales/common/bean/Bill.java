package com.aora.sales.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Administrator
 *
 */
public class Bill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7463531442167697693L;

	
	/**
	 * 帐单ID
	 */
	private int billId;
	
	/**
	 * 推广合作商Id
	 */
	private int companyId;
	
	/**
	 * 推广合用商名称
	 */
	private String companyName;
	
	/**
	 * 公司所属类型Id
	 */
	private int companyCategoryId;
	
	/**
	 * 公司类型
	 */
	private String companyCategoryName;
	
	/**
	 * 帐单日期
	 */
	private Date billDate;
	
	private String billMonth;
	
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
	 * 帐单提交审批者Id
	 */
	private int createrId;
	
	/**
	 * 帐单提交日期
	 */
	private Date createDate;
	
	/**
	 * 帐单状态
	 */
	private int status;
	
	private String statusName;
	
	
	/**
	 * 发票编号
	 */
	private String invoiceCode;
	
	/**
	 * 发票分类
	 */
	private int invoiceCategory;
	
	/**
	 * 发票Id
	 */
	private int invoiceId;
	
	/**
	 * 最后修改时间
	 */
	private Date lastChangeDate;
	
	private String lastChangeDateStr;
	
	private Date startDate;
	
	private Date endDate;
	
	/**
	 * 备注
	 */
	private String description;

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public int getInvoiceCategory() {
        return invoiceCategory;
    }

    public void setInvoiceCategory(int invoiceCategory) {
        this.invoiceCategory = invoiceCategory;
    }

    public int getCompanyCategoryId() {
        return companyCategoryId;
    }

    public void setCompanyCategoryId(int companyCategoryId) {
        this.companyCategoryId = companyCategoryId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCategoryName() {
        return companyCategoryName;
    }

    public void setCompanyCategoryName(String companyCategoryName) {
        this.companyCategoryName = companyCategoryName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public String getLastChangeDateStr() {
        return lastChangeDateStr;
    }

    public void setLastChangeDateStr(String lastChangeDateStr) {
        this.lastChangeDateStr = lastChangeDateStr;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
        
}
