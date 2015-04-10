package com.aora.sales.common.bean;

import java.io.Serializable;

/**
 * 审核流程
 * @author Administrator
 *
 */
public class AuditSeq implements Serializable {

	private static final long serialVersionUID = -7137666574955340693L;

	/**
	 * 审核流程Id
	 */
	private int suditSeqId;
	
	/**
	 * 审核类别Id
	 */
	private int auditTypeId;
	
	/**
	 * 角色
	 */
	private int roleId;
	
	/**
	 * 顺序
	 */
	private int order;
	
	/**
	 * 备注
	 */
	private String desc;

	public int getSuditSeqId() {
		return suditSeqId;
	}

	public void setSuditSeqId(int suditSeqId) {
		this.suditSeqId = suditSeqId;
	}

	public int getAuditTypeId() {
		return auditTypeId;
	}

	public void setAuditTypeId(int auditTypeId) {
		this.auditTypeId = auditTypeId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
