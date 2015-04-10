package com.aora.sales.user.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.common.bean.Permission;
import com.aora.sales.common.bean.Role;
import com.aora.sales.common.bean.User;

public interface UserService {

    User getUserByUserAndPassword(String userName, String password);
	
	void addUser(User user);
   
	void deleteUser(int userId);
	
	User getUser(int userId);
	
	List<User> getAllUsers(User filter, Page page);
	
	/**
	 * 获取所有的部门
	 * @return
	 */
	List<Dict> getAllDepts();
	
	/**
	 * 获取所有的角色
	 * @return
	 */
	List<Role> getAllRoles();
	
	void updateUser(User user);
	
	/**
	 * 根据角色Id获取其所拥有的所有的权限
	 * 
	 */
	List<Permission> getPermissionByRoleId(int roleId);
	
	/**
	 * 根据userName获取user对象
	 * @param userName
	 * @return
	 */
	User getUserByUserName(@RequestParam("userName") String userName);
}
