package com.aora.sales.common.bean;

/**
 * 用户
 * @author Administrator
 *
 */
public class User implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7933599994632817496L;

	/**
	 * 用户Id
	 */
	private int userId;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 部门Id
	 */
	private int deptId;
	
	/**
	 * 部门名称
	 */
	private String deptName;
	
	/**
	 * 座机号码
	 */
	private String telephone;
	
	/**
	 * 手机号码
	 */
	private String mobilephone;
	
	/**
	 * email地址
	 */
	private String email;
	
	/**
	 * 角色Id
	 */
	private int roleId;
	
	/**
	 * 角色名
	 */
	private String roleName;
	
	/**
	 * 描述
	 */
	private String description;
	
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

	public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
	
	
	
}
