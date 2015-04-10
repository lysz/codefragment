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

import com.aora.wifi.action.ChongZhiAction;
import com.aora.wifi.bean.Ref;

public class HuafeiQCoinChongZhi {
    
    private String prevdatestr;
    private String todaystr;
    
    private static final String GET_DATA_SQL = "select a.id, a.out_trade_id, a.used_login_id, a.category, a.price, a.target, a.ip_addr, b.status from tbl_chongzhi a left join tbl_reg_account b  on a.used_login_id = b.account where b.status = 0 and a.createtime > ? and a.createtime < ?  and a.status  = 2 and b.status = 0";
    private static final String ADD_SCORE_2_LOCKUSER = "update tbl_reg_account set score = score + ? where account=?";
    
    private static final String SQL = "UPDATE tbl_chongzhi set status = 1 where used_login_id in (select account from tbl_reg_account where status != 0)";
    
    public static void main(String[] args) {
        new HuafeiQCoinChongZhi().run();
    }
    
    public void run(){
        
        try {
            List data = getWaiteProcessData();
            System.out.println("size of data:" + data.size());
            payment(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            executedSql(SQL, new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void payment(List data) {
        
        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> map = (Map)data.get(i);
            int id = (Integer)map.get("id");
            String out_trade_id = map.get("out_trade_id").toString();
            String reg_account = map.get("used_login_id").toString();
            int category = (Integer)map.get("category");
            int price = (Integer)map.get("price");
            String target = map.get("target").toString();
            String ip_addr = map.get("ip_addr").toString();
            int status = (Integer)map.get("status");
            if (0 != status){
                addScore2LockUser(reg_account, price);
            }else{
                chongzhi(reg_account, out_trade_id, id, target, price, category, ip_addr);
            }
        }
    }
    
    private void chongzhi(String reg_account, String out_trade_id, int id, String target, int price, int category, String ip_addr){
        if (1 == category){
            
            String account = target;
            String account_info = "";
            int quantity = Integer.valueOf(price) / 100;
            String product_id= "10316";
            String client_ip = ip_addr;
            int expired_mini = 5;
            
            Ref<String> msg = new Ref<String>();
            Map<String, String> resultMap = new HashMap<String, String>();
            
            ChongZhiAction cz = new ChongZhiAction();
            int result = cz.Direct(out_trade_id, product_id, quantity, account, account_info, client_ip, expired_mini, msg, resultMap);
            int status = 1; // 状态1表示充值失败,状态0表示充值成功
            if (result == 0) {
                status = 2; // 状态2表示充值中
            }
            String sql = "update tbl_chongzhi set paymenttime = now(), msg = ?, code = ?, jsondata = ?, status = ? where id = ?";
            
            System.out.print("account:" + reg_account + " to " + target + " QCoin:" + quantity + ".id:" + id + ",result:" + result + ", msg:" + msg + ",json:" + resultMap.toString());
            try {
                executedSql(sql, new String[]{msg.toString(), result +"", resultMap.toString(), status + "", id + ""});
                System.out.println("success.");
            } catch (Exception e) {
                System.out.println("failed.");
                e.printStackTrace();
            }
        } else if (2 == category){
            
            ChongZhiAction cz = new ChongZhiAction();
            String account = target;
            String account_info = "";
            int quantity = 1;
            String value = Integer.valueOf(price) / 100 + "";
            String client_ip = ip_addr;
            int expired_mini = 5;
            Ref<String> msg = new Ref<String>();
            Map<String, String> resultMap = new HashMap<String, String>();
            
            int result = cz.Huafei(out_trade_id, account, account_info, quantity, value, client_ip, expired_mini, msg, resultMap);
            int status = 1; // 状态1表示充值失败,状态0表示充值成功
            if (result == 0) {
                status = 2; // 状态2表示充值中
            }
            
            System.out.print("account:" + reg_account + " to " + target + " money:" + value + " success.  id:" + id +",result:" + result + ",msg:" + msg + ",json:" + resultMap.toString());
            String sql = "update tbl_chongzhi set paymenttime = now(), msg = ?, code = ?, jsondata = ?, status = ? where id = ?";
            
            try {
                executedSql(sql, new String[]{msg.toString(), result +"", resultMap.toString(), status + "", id + ""});
                System.out.println("success.");
            } catch (Exception e) {
                System.out.println("failed");
                e.printStackTrace();
            }
        }
    }
    
    private void addScore2LockUser(String reg_account, int price){
        try {
            System.out.println("add score:" + price + " to account:" + reg_account);
            executedSql(ADD_SCORE_2_LOCKUSER, new String[]{price + "", reg_account});
        } catch (Exception e) {
            System.out.println("add score:" + price + " to account:" + reg_account + " failed.");
            e.printStackTrace();
        }
    }
    
    private List getWaiteProcessData() throws Exception{
        Date today = new Date();
        Date prevdate =  DateUtils.addDays(today, -1);
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        todaystr = format.format(today);
        prevdatestr = format.format(prevdate);
        
        List list = getData(GET_DATA_SQL, new String[]{prevdatestr, todaystr});
        
        return list;
    }
    
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String dbpath = "jdbc:mysql://192.168.0.36:3306/wifiapp2?useUnicode=true&characterEncoding=utf-8";
        Connection conn = DriverManager.getConnection(dbpath, "wifi", "wifiapp");
        
        return conn;
    }
    
    private List getData(String sql,Object[] paras) throws Exception{
        Connection conn=null;
        PreparedStatement state=null;
        CallableStatement cstate = null;
        ResultSet rs=null;
        List ls=null;
        try{
            conn=getConnection();
            if(sql.toLowerCase().indexOf("call ")>=0){
                cstate = conn.prepareCall(sql);
                for(int i=0;i<paras.length;i++){
                    Object obj=paras[i];
                    if(obj instanceof String){
                        cstate.setString(i+1, obj.toString());
                    }else if (obj instanceof Integer){
                        cstate.setString(i+1, obj.toString());
                    }else if (obj instanceof Date){
                        cstate.setDate(i+1, (java.sql.Date)obj);
                    }else{
                        cstate.setObject(i+1, obj);
                    }
                }
                rs = cstate.executeQuery();
            }else{
                state=conn.prepareStatement(sql);
                for(int i=0;i<paras.length;i++){
                    Object obj=paras[i];
                    if(obj instanceof String){
                        state.setString(i+1, obj.toString());
                    }else if (obj instanceof Integer){
                        state.setString(i+1, obj.toString());
                    }else if (obj instanceof Date){
                        state.setDate(i+1, (java.sql.Date)obj);
                    }else{
                        state.setObject(i+1, obj);
                    }
                }
                rs=state.executeQuery();
            }
            ls=convertList(rs);
            
        }
        finally{
            try{if (rs != null){ rs.close();rs=null;}}catch(Exception e){}
            try{if(state!=null){state.close();state=null;}}catch(Exception e){}
            try{if(cstate!=null){cstate.close();cstate=null;}}catch(Exception e){}
            try{if (conn!=null){conn.close();conn=null;}}catch(Exception e){}
        }
        
        if (ls==null){
            ls = new ArrayList();
        }
        
      return ls;
  }
    
    private  List convertList(ResultSet rs) throws SQLException {    
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
    
    public boolean executedSql(String sql,Object[] paras) throws Exception{
        Connection conn=null;
        PreparedStatement state=null;
        boolean ls=false;
        try{
            conn=getConnection();
            state=conn.prepareStatement(sql);
            for(int i=0;i<paras.length;i++){
                Object obj=paras[i];
                if(obj instanceof String){
                    state.setString(i+1, obj.toString());
                }else if (obj instanceof Integer){
                    state.setString(i+1, obj.toString());
                }else if (obj instanceof Date){
                    state.setDate(i+1, (java.sql.Date)obj);
                }else{
                    state.setObject(i+1, obj);
                }
            }
            
            ls=state.execute();
            
            
        }
        finally{
            try{state.close();state=null;}catch(Exception e){}
            try{conn.close();conn=null;}catch(Exception e){}
        }
      return ls;
  }
}
