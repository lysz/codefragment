package com.aora.sales.user.dao;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.common.bean.Permission;
import com.aora.sales.common.bean.Role;
import com.aora.sales.common.bean.User;

public interface UserDao {
    
    void addUser(User user);
   
    void deleteUser(int userId);
    
    User getUser(int userId);
    
    User getUserByUserAndPassword(String userName, String password);
    
    List<User> getAllUsers(User user, Page page);
    
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
    
    List<Permission> getPermissionByRoleId(int roleId);
    
    User getUserByUserName(@RequestParam("userName") String userName);
}
