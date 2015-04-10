package com.aora.sales.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.common.bean.Permission;
import com.aora.sales.common.bean.Role;
import com.aora.sales.common.bean.User;
import com.aora.sales.user.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

	private SqlSession sqlSession;
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

    public void addUser(User user) {
        sqlSession.insert("user.createUser", user);
    }
    

    public void deleteUser(int userId) {
        sqlSession.delete("user.deleteUser", userId);
    }

    public User getUser(int userId) {
        
        User user = new User();
        user.setUserId(userId);
        
        return sqlSession.selectOne("user.getUser", user);
    }

    public User getUserByUserAndPassword(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        
        return sqlSession.selectOne("user.getUser", user);
    }

    public List<User> getAllUsers(User user, Page page) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getName());
        map.put("deptId", user.getDeptId());
        map.put("roleId", user.getRoleId());
        map.put("start", page.getCurrentRecord());
        map.put("pageSize", page.getPageSize());
        map.put("getTotalSize", page.getTotalRecord());
        
        
        List<User> users = sqlSession.selectList("user.getAllUsers", map);
        
        if (null == users){
            users = new ArrayList<User>();
        }
        
        return users;
    }

    public List<Dict> getAllDepts() {
        
        List<Dict> dicts = sqlSession.selectList("user.getAllDepts");
        
        if (null == dicts){
            dicts = new ArrayList<Dict>();
        }
        
        return dicts;
    }

    public List<Role> getAllRoles() {
        List<Role> roles = sqlSession.selectList("user.getAllRoles");
        
        if (null == roles){
            roles = new ArrayList<Role>();
        }
        
        return roles;
    }

    public void updateUser(User user) {
        sqlSession.update("user.updateUser", user);
    }

    public List<Permission> getPermissionByRoleId(int roleId) {
        
        String permissionStr = getSqlSession().selectOne("user.getPermissionByRoleId", roleId);
        
        List<Permission> list = null;
        
        if (StringUtils.isEmpty(permissionStr)){
            list = new ArrayList<Permission>();
            return list;
        }
        
        String[] ps = permissionStr.split(",");
        
        list = getSqlSession().selectList("user.getPermissionById", ps);
        
        if (null == list){
            list = new ArrayList<Permission>();
        }
        
        return list;
    }

    public User getUserByUserName(String userName) {
        
        User user = new User();
        user.setUserName(userName);
        
        return sqlSession.selectOne("user.getUser", user);
    }	
}
