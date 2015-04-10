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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

/**
 * 根据用户分享热点的MAC地址来冻结帐号:
 * 分享超过5个的用户帐号，分析热点的mac地址前6位, 并且是否在15分钟之内
 * 
 * @author Administrator
 * 
 */
public class CheckHotspotMac4LockingUser {

    private static final int MINUTE_3 = 3 * 60 * 1000;
    
    private static final int MINUTE_15 = 15 * 60 * 1000;
    
    private String sql = "select reg_account, count(*) as count from tbl_wifi_share_account_logs where indate > ? and indate < ? and length(reg_account) > 1  group by reg_account having count(*) >= 5";
    
    private String hotspotSQL = "select mac_address, indate from tbl_wifi_share_account_logs where reg_account = ? and indate > ? and indate < ?";
    
    private String lockUserAccountSQL = "update tbl_reg_account set status = 1, remark=? where account = ?";
    
    private String todaystr = null;
    private String prevdatestr = null;
    
    public static void main(String[] args) throws Exception{
        new CheckHotspotMac4LockingUser().run();
    }
    
    public void run() throws Exception{
        //取出分享超过5个的用户帐号
        List userAccountlist = getCheatUserAccount();
        System.out.println("size of share more than 5:" + userAccountlist.size());
        List<CheatMsg> cheatList = checkUserHotspot(userAccountlist);
        System.out.println("cheat:" + cheatList);
        lockUserAccount(cheatList);
    }
    
    private void lockUserAccount(List<CheatMsg> cheatList){
        
        
        for (CheatMsg msg : cheatList){
            
            String account = msg.getAccount();
            String remark = msg.getRemark();
            
            try {
                executedSql(lockUserAccountSQL, new String[]{remark, account});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private List<CheatMsg> checkUserHotspot(List list) throws Exception {
        List<CheatMsg> cheatAccount = new ArrayList<CheatMsg>();
        
        //遍历每个帐号
        for (Object obj : list){
            Map map = (Map)obj;
            
            String account = map.get("reg_account").toString();
            //根据帐号查询所分享的热点
            List ls = getData(hotspotSQL, new String[]{account, prevdatestr, todaystr});
            //将map转换成Hotspot对象
            List<Hotspot> hotspots = wrap2HotSpot(ls);
            //分析热点的mac地址前6位, 并且是否在15分钟之内
            CheatMsg result = analysisHotspot(hotspots);
            
            if (result.isCheat()){
                result.setAccount(account);
                cheatAccount.add(result);
            }
        }
        
        return cheatAccount;
    }
    
    private List<Hotspot> wrap2HotSpot(List list){
        List<Hotspot> hotspots = new ArrayList<Hotspot>();
        
        for (Object obj : list){
            Map map = (Map)obj;
            
            String mac_address = map.get("mac_address").toString();
            Date indate = (Date)map.get("indate");
            
            Hotspot hotspot = new Hotspot();
            hotspot.setMac_address(mac_address);
            hotspot.setIndate(indate);
            
            hotspots.add(hotspot);
        }
        
        return hotspots;
    }
    
    private CheatMsg analysisHotspot(List<Hotspot> list){     
        Map<String, List<Hotspot>> data = put2ListByPrefix(list);
        
        CheatMsg result = new CheatMsg();
        boolean isCheat = false;
        
        for (Map.Entry<String, List<Hotspot>> entry : data.entrySet()){
            List<Hotspot> hots = entry.getValue();
            
            if (hots.size() < 5){
                continue;
            }
            
            //10条之内的检查第1个热点分享的时间和第5个热点分享的时间相隔在15分钟之内
            if (hots.size() < 10){
                CheatMsg msg = isIn15Minute(hots);
                if (msg.isCheat()){
                    isCheat = true;
                    result.setRemark(todaystr + ",mac:" + entry.getKey() + ",time:" + msg.getCheatTime());
                    break;
                } 
            }
            
            if (hots.size() >= 10){
                isCheat = true;
                result.setRemark(todaystr + ", it share hot spot greater than 10 in one day.");
                break;
            }
        }
        
        result.setCheat(isCheat);
        return result;
    }
    
    /**
     * 通过mac地址前缀分类汇总
     * @param list
     * @return
     */
    private Map<String, List<Hotspot>> put2ListByPrefix(List<Hotspot> list){
        
        Map<String, List<Hotspot>> data = new HashMap<String, List<Hotspot>>();
        
        for (Hotspot hotspot : list){
            
            String mac_address = hotspot.getMac_address();
            String prefix = mac_address.substring(0, 8);
            
            if (data.containsKey(prefix)){
                List<Hotspot> hotspots = data.get(prefix);
                if (null == hotspots){
                    hotspots = new ArrayList<Hotspot>();
                    data.put(prefix, hotspots);
                }
                
                hotspots.add(hotspot);
            }else{
                List<Hotspot> hotspots = new ArrayList<Hotspot>();
                hotspots.add(hotspot);
                data.put(prefix, hotspots);
            }
        }
        
        return data;
    }
    
    /**
     * 当天分享的热点，最早和最晚间隔15分钟之内
     * @return
     */
    private CheatMsg isIn15Minute(List<Hotspot> list){
        boolean isIn15Minute = false;
        
        CheatMsg msg = new CheatMsg();
        msg.setCheat(isIn15Minute);
        
        //按从小到大排序
        Collections.sort(list, new Comparator<Hotspot>(){

            public int compare(Hotspot o1, Hotspot o2) {
                Date d1 = o1.getIndate();
                Date d2 = o2.getIndate();
                
                if(d1.after(d2)){  
                    return 1;  
                }  
                else{  
                    return -1;  
                }  
            }
        });
        
        //取最早的一个分享的时间
        Hotspot first = list.get(0);
        //取第5个热点分享的时间
        Hotspot last = list.get(4);
        
        long firstTime = first.getIndate().getTime();
        long lastTime =  last.getIndate().getTime();
        
        //比较两个时间是否在15分钟之内
        if ((lastTime - firstTime) <= MINUTE_15){            
            isIn15Minute = true;
            msg.setCheat(isIn15Minute);
            //记录作弊的开始时间
            msg.setCheatTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(first.getIndate()));
        }
        
        return msg;
    }
    
    /**
     * 任意两个热点在3分钟之内
     * @param list
     * @return
     */
    private boolean isIn3Minute(List<Hotspot> list){        
        
        boolean isIn3Minute = false;
        
        Collections.sort(list, new Comparator<Hotspot>(){

            public int compare(Hotspot o1, Hotspot o2) {
                Date d1 = o1.getIndate();
                Date d2 = o2.getIndate();
                
                if(d1.after(d2)){  
                    return 1;  
                }  
                else{  
                    return -1;  
                }  
            }
        });
        
        for (int i = 0; i < list.size() - 1; i++){
            
            long previousDate = list.get(i).getIndate().getTime();
            long nextDate =  list.get(i + 1).getIndate().getTime();
            
            if ((nextDate - previousDate) <= MINUTE_3){
                
                isIn3Minute = true;
                break;
            }
        }
        
        return isIn3Minute;
    }
    
    private List getCheatUserAccount() throws Exception {
        
        Date today = new Date();
        Date prevdate =  DateUtils.addDays(today, -1);
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        todaystr = format.format(today);
        prevdatestr = format.format(prevdate);
        
        List list = getData(sql, new String[]{prevdatestr, todaystr});
        return list;
    }
    
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String dbpath = "jdbc:mysql://192.168.0.36:3306/wifiapp2?useUnicode=true&characterEncoding=utf-8";
        Connection conn = DriverManager.getConnection(dbpath, "wifi", "wifiapp");
//      String dbpath = "jdbc:mysql://localhost:3306/wifiapp2?useUnicode=true&characterEncoding=utf-8";
//      Connection conn = DriverManager.getConnection(dbpath, "root", "123456");
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
    
    public static class CheatMsg{
        
        @Override
        public String toString() {
            return account;
        }

        private String account;
        
        private boolean isCheat;
        
        private String remark;
        
        private String cheatTime;
        
        public String getCheatTime() {
            return cheatTime;
        }

        public void setCheatTime(String cheatTime) {
            this.cheatTime = cheatTime;
        }

        public boolean isCheat() {
            return isCheat;
        }

        public void setCheat(boolean isCheat) {
            this.isCheat = isCheat;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
        
    }
    
    public static class Hotspot {
        
        @Override
        public String toString() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(indate);
        }

        private String account;
        
        private String mac_address;

        private Date   indate;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getMac_address() {
            return mac_address;
        }

        public void setMac_address(String macAddress) {
            mac_address = macAddress;
        }

        public Date getIndate() {
            return indate;
        }

        public void setIndate(Date indate) {
            this.indate = indate;
        }
    }
}
