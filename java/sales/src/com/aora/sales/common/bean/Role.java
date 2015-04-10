package com.aora.sales.common.bean;

import java.io.Serializable;

/**
 * 角色
 * @author Administrator
 *
 */
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5166796018328601398L;

	/**
	 * 角色Id
	 */
	private int roleId;
	
	/**
	 * 角色名
	 */
	private String roleName;
	
	/**
	 * 权限Id,  多个Id之间用,分隔
	 */
	private String permissionId;
	
	/**
	 * 备注
	 */
	private String description;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	
	
	
}
