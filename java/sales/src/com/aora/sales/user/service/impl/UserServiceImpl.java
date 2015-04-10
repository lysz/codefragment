package com.aora.sales.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.common.bean.Permission;
import com.aora.sales.common.bean.Role;
import com.aora.sales.common.bean.User;
import com.aora.sales.user.dao.UserDao;
import com.aora.sales.user.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

    public void addUser(User user) {
        getUserDao().addUser(user);
    }

    public void deleteUser(int userId) {
        getUserDao().deleteUser(userId);
    }

    public User getUser(int userId) {
        return getUserDao().getUser(userId);
    }

    public User getUserByUserAndPassword(String userName, String password) {
        return getUserDao().getUserByUserAndPassword(userName, password);
    }

    public List<User> getAllUsers(User user, Page page) {
        
        return getUserDao().getAllUsers(user, page);
    }

    public List<Dict> getAllDepts() {
        return getUserDao().getAllDepts();
    }

    public List<Role> getAllRoles() {
        return getUserDao().getAllRoles();
    }

    public void updateUser(User user) {
        getUserDao().updateUser(user);
    }

    public List<Permission> getPermissionByRoleId(int roleId) {
        
        return getUserDao().getPermissionByRoleId(roleId);
    }

    public User getUserByUserName(String userName) {
        return getUserDao().getUserByUserName(userName);
    }	
}
