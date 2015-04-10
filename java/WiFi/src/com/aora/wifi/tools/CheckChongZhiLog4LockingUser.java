package com.aora.wifi.tools;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 同一个QQ或手机号被多个(目前暂定为大于3个)不同的 wifi畅游账号充值, 则这些 wifi畅游账号充值将被冻结
 * @author Administrator
 *
 */
public class CheckChongZhiLog4LockingUser {

    private static final String GET_DATA_SQL = "select target, count(*) 'num',  group_concat(used_login_id) 'accounts' from (select distinct target,used_login_id from tbl_chongzhi where createtime > ? and createtime < ?) a group by target having count(*)>3"; 
    
    private String lockUserAccountSQL = "update tbl_reg_account set status = 1, remark= ? where account in ($accounts)";
    
    private String get_status_sql = "select status from tbl_reg_account where account = ?";
    
    public static void main(String[] args) {
        new CheckChongZhiLog4LockingUser().run();
    }

    private void run(){
        
        try {
            List data = getAccounts();
            lockUserAccounts(data);
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    private void lockUserAccounts(List data){
        
        for (int i = 0; i < data.size(); i++){
            Map map = (Map)data.get(i);
            String accounts = map.get("accounts").toString();
            accounts = parseAccounts(accounts);
            
            lockUser(accounts);
        }
    }
    
    private void lockUser(String accounts){
        
        if (StringUtils.isEmpty(accounts)){
            return;
        }
        
        System.out.println("V2:" + accounts);
        String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        
        try {
            String sql = lockUserAccountSQL.replace("$accounts", accounts);
            
            executedSql(sql, new String[]{timeStr + ":it add money to the same account more than 4 once"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String parseAccounts(String accounts){
        StringBuilder build = new StringBuilder();
        
        String[] accArr = accounts.split(",");
        
        for (String acc : accArr){
            
            int status = getUserStatus(acc);
            if (status != 0){
                continue;
            }
            
            build = build.append("'").append(acc).append("'").append(",");
        }
        
        String res = build.toString().replaceAll(",$", "");
        
        return res;
    }
    
    private int getUserStatus(String account){
        int status = 1;
        
        try {
            List data = getData(get_status_sql, new String[]{account});
            if (!data.isEmpty()){
                Map map = (Map)data.get(0);
                status = (Integer)map.get("status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       return status; 
    }
    
    private List getAccounts() throws Exception{
        Date today = new Date();
        Date prevdate =  DateUtils.addDays(today, -1);
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todaystr = format.format(today);
        String prevdatestr = format.format(prevdate);
        
        List data = getData(GET_DATA_SQL, new String[]{prevdatestr, todaystr});
        
        return data;
    }
    
    private List getData(String sql, Object[] paras) throws Exception {
        Connection conn = null;
        PreparedStatement state = null;
        CallableStatement cstate = null;
        ResultSet rs = null;
        List ls = null;
        try {
            conn = getConnection();
            if (sql.toLowerCase().indexOf("call ") >= 0) {
                cstate = conn.prepareCall(sql);
                for (int i = 0; i < paras.length; i++) {
                    Object obj = paras[i];
                    if (obj instanceof String) {
                        cstate.setString(i + 1, obj.toString());
                    } else if (obj instanceof Integer) {
                        cstate.setString(i + 1, obj.toString());
                    } else if (obj instanceof Date) {
                        cstate.setDate(i + 1, (java.sql.Date) obj);
                    } else {
                        cstate.setObject(i + 1, obj);
                    }
                }
                rs = cstate.executeQuery();
            } else {
                state = conn.prepareStatement(sql);
                for (int i = 0; i < paras.length; i++) {
                    Object obj = paras[i];
                    if (obj instanceof String) {
                        state.setString(i + 1, obj.toString());
                    } else if (obj instanceof Integer) {
                        state.setString(i + 1, obj.toString());
                    } else if (obj instanceof Date) {
                        state.setDate(i + 1, (java.sql.Date) obj);
                    } else {
                        state.setObject(i + 1, obj);
                    }
                }
                rs = state.executeQuery();
            }
            ls = convertList(rs);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (Exception e) {
            }
            try {
                if (state != null) {
                    state.close();
                    state = null;
                }
            } catch (Exception e) {
            }
            try {
                if (cstate != null) {
                    cstate.close();
                    cstate = null;
                }
            } catch (Exception e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception e) {
            }
        }

        if (ls == null) {
            ls = new ArrayList();
        }

        return ls;
    }
    
    private List convertList(ResultSet rs) throws SQLException {
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map rowData = new HashMap();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnLabel(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }
    
    public boolean executedSql(String sql, Object[] paras) throws Exception {
        Connection conn = null;
        PreparedStatement state = null;
        boolean ls = false;
        try {
            conn = getConnection();
            state = conn.prepareStatement(sql);
            for (int i = 0; i < paras.length; i++) {
                Object obj = paras[i];
                if (obj instanceof String) {
                    state.setString(i + 1, obj.toString());
                } else if (obj instanceof Integer) {
                    state.setString(i + 1, obj.toString());
                } else if (obj instanceof Date) {
                    state.setDate(i + 1, (java.sql.Date) obj);
                } else {
                    state.setObject(i + 1, obj);
                }
            }

            ls = state.execute();
        } finally {
            try {
                state.close();
                state = null;
            } catch (Exception e) {
            }
            try {
                conn.close();
                conn = null;
            } catch (Exception e) {
            }
        }
        return ls;
    }
    
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String dbpath = "jdbc:mysql://192.168.0.36:3306/wifiapp2?useUnicode=true&characterEncoding=utf-8";
        Connection conn = DriverManager.getConnection(dbpath, "wifi", "wifiapp");
        
//      String dbpath = "jdbc:mysql://120.31.131.166:3336/wifiapp2?useUnicode=true&characterEncoding=utf-8";
//      Connection conn = DriverManager.getConnection(dbpath, "wifi", "wifiapp");
        
        return conn;
    }
}
