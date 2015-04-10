package com.aora.sales.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.common.bean.Permission;
import com.aora.sales.common.bean.Role;
import com.aora.sales.common.bean.User;
import com.aora.sales.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * 用户登陆
     * @param model
     * @param userName
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value="/login", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String login(Model model, @RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) {
        
        User user = getUserService().getUserByUserAndPassword(userName, password);
        
        if (null != user){
            //放入session
            session.setAttribute("user", user);
            return "{success:true, userId: '" + user.getUserId() +"'}";
        }
        else{
            return "{success:false, msg: '您输入的帐号或密码不正确，请重新输入。'}";
        }
    }
    
    /**
     * 用于表单提交
     * @param user
     * @return
     */
    @RequestMapping(value="/create", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addUser(User user){
        
        String result = "";
        
        if (null != user){
            getUserService().addUser(user);
            result = "{success:true}";
        }else{
            result = "{success:false}";
        }
        
        return result;
    }
    
    @RequestMapping(value="/update", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updateUser(User user){
        
        String result = "";
        
        if (null != user){
            getUserService().updateUser(user);
            result = "{success:true}";
        }else{
            result = "{success:false}";
        }
        
        return result;
    }
    
    /**
     * 用于表单提交
     * @param user
     * @return
     */
    @RequestMapping(value="/delete", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String deleteUser(@RequestParam("userId") int userId){
                
        getUserService().deleteUser(userId);
        
        return "{success:true}";
    }
    
    /**
     * 用于表单提交
     * @param user
     * @return
     */
    @RequestMapping(value="/getUser", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getUser(@RequestParam("userId") int userId){
                
        User user = getUserService().getUser(userId);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", user);
        
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }
    
    /**
     * 获取所有的user, 用于grid
     * @return
     */
    @RequestMapping(value="/getAllUsers",  method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllUsers(@RequestParam("name") String name, 
                              @RequestParam("deptId") String deptId, 
                              @RequestParam("roleId") String roleId, 
                              @RequestParam("page") int page, 
                              @RequestParam("start") int start, 
                              @RequestParam("limit") int limit){
        
        User user = new User();
        user.setName(null == name || "".equals(name.trim()) ? null : name.trim());
        user.setDeptId(null == deptId || "".equals(deptId.trim()) ? 0: Integer.valueOf(deptId));
        user.setRoleId(null == roleId || "".equals(roleId.trim())? 0 : Integer.valueOf(roleId));
        
        Page p = new Page();
        p.setCurrentRecord(start);
        p.setPageSize(limit);
        p.setTotalRecord(0);
        
        List<User> users = getUserService().getAllUsers(user, p);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("root", users);
        
        p.setTotalRecord(1);
        List<User> userList = getUserService().getAllUsers(user, p);
        data.put("totalCount", userList.size());
        
        
        JSONObject jsonObject = JSONObject.fromObject(data);
        return jsonObject.toString();
    }
    
    @RequestMapping(value="/redirect2MainPage", method={RequestMethod.GET})
    public String redirect2MainPage(Model model, @CookieValue(value = "userId", required = false) String userId,  HttpSession session ){
        String redirectPage = null;
        
        //判断session里面是否存在该对象
        if (null != session.getAttribute("user")){
            redirectPage = "redirect:/index.jsp";
        }else{
            redirectPage = "redirect:/login.jsp";
        }
        return redirectPage;
    }
    
    
    /**
     * 获取所有的部门, 用于combox
     * @return
     */
    @RequestMapping(value="/getAllDepts", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllDepts(){
        
        List<Dict> dicts = getUserService().getAllDepts();
        
        List<Map<String, Object>> depts = new ArrayList<Map<String, Object>>();
        for (Dict dict : dicts){
            Map<String, Object> deptMap = new HashMap<String, Object>();
            deptMap.put("deptId", dict.getDictId());
            deptMap.put("deptName", dict.getDictName());
            depts.add(deptMap);
        }
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("root", depts);
        data.put("tocalCount", depts.size());
        
        JSONObject jsonArray = JSONObject.fromObject(data);
        return jsonArray.toString();
    }
    
    /**
     * 获取所有的角色, 用于combox
     * @return
     */
    @RequestMapping(value="/getAllRoles", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllRoles(){
        List<Role> roles = getUserService().getAllRoles();
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("root", roles);
        data.put("tocalCount", roles.size());
        
        JSONObject jsonArray = JSONObject.fromObject(data);
        return jsonArray.toString();
    }
    
    @RequestMapping(value="/getCurrentUser", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getCurrentUser(HttpSession session){
        User user = (User)session.getAttribute("user");
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (null == user){
            map.put("success", false);
            map.put("user", "");
        }else{
            int roleId = user.getRoleId();
            List<Permission> ps = getUserService().getPermissionByRoleId(roleId);
            
            map.put("success", true);
            map.put("user", user);
            map.put("permissions", ps);
        }
        
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }
    
    @RequestMapping(value="/checkUserExistByUserName", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkUserExistByUserName(@RequestParam("userName") String userName){
        
        User user = getUserService().getUserByUserName(userName);
        
        String result = null;
        
        if (null == user){
            result = "{success:true, hasExist: false}";
        }else{
            result = "{success:true, hasExist: true}";
        }
        
        return result;
    }
}
