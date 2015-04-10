package com.aora.sales.common.bean;

import java.io.Serializable;

/**
 * 审核
 * @author Administrator
 *
 */
public class Audit implements Serializable {

	private static final long serialVersionUID = 6689601791325161555L;

	/**
	 * 审核Id
	 */
	private int auditId;
	
	/**
	 * 审核者角色Id
	 */
	private int auditorId;
	
	/**
	 * 审核者角色
	 */
	private String auditorName;

	/**
	 * 审核类型 (CP上架审核, CP下架审核, 结算申请审核, 打款申请审核)
	 */
	private int auditTypeId;
	
	/**
	 * 审核目标 
	 */
	private int auditTargetId;
	
	/**
	 * 审核结果
	 */
	private int auditResult;
	
	private String auditResultStr;
	
	/**
	 * 审核状态 (己审, 待审)
	 */
	private int auditStatus;
	
	/**
	 * 备注
	 */
	private String description;

	public int getAuditId() {
		return auditId;
	}

	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}

	public int getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(int auditorId) {
		this.auditorId = auditorId;
	}

	public int getAuditTypeId() {
		return auditTypeId;
	}

	public void setAuditTypeId(int auditTypeId) {
		this.auditTypeId = auditTypeId;
	}

	public int getAuditTargetId() {
		return auditTargetId;
	}

	public void setAuditTargetId(int auditTargetId) {
		this.auditTargetId = auditTargetId;
	}

	public int getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(int auditResult) {
		this.auditResult = auditResult;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public String getAuditResultStr() {
        return auditResultStr;
    }

    public void setAuditResultStr(String auditResultStr) {
        this.auditResultStr = auditResultStr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
	
	
}
