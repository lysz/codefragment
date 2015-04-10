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
 * 相同gps地址超过80个，或相同gps地址连续超过20个
 * @author Administrator
 *
 */
public class CheckHotspotMac4LockingUserV1 {
    
    private String get_user_sql = "select reg_account from tbl_wifi_share_account_logs where indate > ? and indate < ? and length(reg_account) > 1 group by reg_account";
    
    private String hotspotSQL = "select mac_address, wifi_gps_address, indate from tbl_wifi_share_account_logs where reg_account = ? and  indate > ? and indate < ?";
    
    private String hotspot_address_group_sql = "select wifi_gps_address, count(*) from tbl_wifi_share_account_logs where reg_account = ? and length(wifi_gps_address) > 2 group by wifi_gps_address having count(*) >= 80";
    
    private String lockUserAccountSQL = "update tbl_reg_account set status = 1, remark=? where account = ?";
    
    private String get_user_info_sql = "select status from tbl_reg_account where account = ?";
    
    private String todaystr = null;
    private String prevdatestr = null;
    
    public static void main(String[] args) throws Exception{
        new CheckHotspotMac4LockingUserV1().run();
    }
    
    public void run() throws Exception{
        
//        Date today = new Date();
//        
//        for (int i = 32; i < 89; i++){
//            Date newDate = DateUtils.addDays(today, -i);
//            Date prevdate =  DateUtils.addDays(newDate, -1);
//            
//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            todaystr = format.format(newDate);
//            prevdatestr = format.format(prevdate);
//            System.out.println("today:" + todaystr + "\tprevdate:"
//                    + prevdatestr);
//
//            System.out.println("----------------------------");
//            List userAccountlist = getCheatUserAccount();
//            List<CheatMsg> cheatList = checkUserHotspot(userAccountlist);
//            System.out.println(cheatList);
//            lockUserAccount(cheatList);
//        }
        
        
        
        List userAccountlist = getCheatUserAccount();
        System.out.println("number of user to share hotspots is:" + userAccountlist.size());
        List<CheatMsg> cheatList = checkUserHotspot(userAccountlist);
        System.out.println("V1:" + cheatList);
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
    
    private List getCheatUserAccount() throws Exception {
        
        Date today = new Date();
        Date prevdate =  DateUtils.addDays(today, -1);
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        todaystr = format.format(today);
        prevdatestr = format.format(prevdate);
        
        List list = getData(get_user_sql, new String[]{prevdatestr, todaystr});
        return list;
    }
    
    private List<CheatMsg> checkUserHotspot(List list) throws Exception {
        List<CheatMsg> cheatAccount = new ArrayList<CheatMsg>();
        
        String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        
        //遍历每个帐号
        for (Object obj : list){
            Map map = (Map)obj;
            String account = map.get("reg_account").toString();
            
            List userData = getData(get_user_info_sql, new String[]{account});
            if (userData.isEmpty()){
                continue;
            }
            //检查用户是否已经被冻结
            Map usermap = (Map)userData.get(0);
            if (!usermap.get("status").toString().equals("0")){
                continue;
            }
            
            CheatMsg result = checkHotspotByUserAccount(account, timeStr);
            
            if (result.isCheat()){
                System.out.println("V1," + timeStr + ">>>" + account);
                cheatAccount.add(result);
            }
        }
        
        return cheatAccount;
    }
    
    private CheatMsg checkHotspotByUserAccount(String account, String timeStr) throws Exception {
        CheatMsg result = new CheatMsg();
        result.setAccount(account);
        
        boolean bool = hasSameHotsotpAddressMoreThan80(account);
        if (bool){
            result.setCheat(true);
            result.setRemark(timeStr + ":it has same hot spot gps address more than 80");
            System.out.println("account[" + account + "] has same hot spot gps address more than 80");
            return result;
        }
        
        bool = hasHotspotAddressContinuousMoreThan20(account);
        if (bool){
            result.setCheat(true);
            result.setRemark(timeStr+ ":it has same hot spot gps address continuous more than 20");
            System.out.println("account[" + account + "] has same hot spot gps address continuous more than 20");
            return result;
        }
        
        return result;
    }
    
    private boolean hasHotspotAddressContinuousMoreThan20(String account) throws Exception {
        
        List list = getData(hotspotSQL, new String[]{account, prevdatestr, todaystr});
        
        if (list.size() < 20){
            return false;
        }
        
        List<Hotspot> hotspots = convert2HotSpot(list);
        
        //按从小到大排序
        Collections.sort(hotspots, new Comparator<Hotspot>(){
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
        
        boolean hasHotspotAddressContinuousMoreThan20 = false;
        
        for (int i = 0; i <= hotspots.size() - 20; i++){
            String address = hotspots.get(i).getWifi_gps_address();
            if (isEmpty(address) ){
                continue;
            }
            
            hasHotspotAddressContinuousMoreThan20 = true;
            
            for (int j = i + 1; j < i + 20; j++){
                String other_address = hotspots.get(j).getWifi_gps_address();
                
                if (isEmpty(other_address)){
                    continue;
                }
                
                if (!address.equals(other_address)){
                    hasHotspotAddressContinuousMoreThan20 = false;
                    break;
                }
            }
            
            if (hasHotspotAddressContinuousMoreThan20){
                break;
            }
            
        }
        
        return hasHotspotAddressContinuousMoreThan20;
    }
    
    private boolean hasSameHotsotpAddressMoreThan80(String account) throws Exception {
        boolean result = false;
        
        List list = getData(hotspot_address_group_sql, new String[]{account});
        if (!list.isEmpty()){
            result = true;
        }
        
        return result;
    }
    
    private List<Hotspot> convert2HotSpot(List list){
        List<Hotspot> hotspots = new ArrayList<Hotspot>();
        
        for (Object obj : list){
            Map map = (Map)obj;
            
            String mac_address = map.get("mac_address").toString();
            Date indate = (Date)map.get("indate");
            String wifi_gps_address = map.get("wifi_gps_address") == null ? "" : map.get("wifi_gps_address").toString();
            
            Hotspot hotspot = new Hotspot();
            hotspot.setMac_address(mac_address);
            hotspot.setIndate(indate);
            hotspot.setWifi_gps_address(wifi_gps_address);
            
            hotspots.add(hotspot);
        }
        
        return hotspots;
    }
    
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String dbpath = "jdbc:mysql://192.168.0.36:3306/wifiapp2?useUnicode=true&characterEncoding=utf-8";
        Connection conn = DriverManager.getConnection(dbpath, "wifi", "wifiapp");
        
//      String dbpath = "jdbc:mysql://120.31.131.166:3336/wifiapp2?useUnicode=true&characterEncoding=utf-8";
//      Connection conn = DriverManager.getConnection(dbpath, "wifi", "wifiapp");
        
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
    
    private boolean isEmpty(String cs) {
        return cs == null || cs.trim().length() == 0;
    }
    
    public static class CheatMsg{
        
        @Override
        public String toString() {
            return account+",";
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
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(indate) + "\t" + wifi_gps_address;
        }

        private String account;
        
        private String wifi_gps_address;
        
        private String mac_address;

        private Date   indate;
        
        public String getWifi_gps_address() {
            return wifi_gps_address;
        }

        public void setWifi_gps_address(String wifiGpsAddress) {
            wifi_gps_address = wifiGpsAddress;
        }

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
