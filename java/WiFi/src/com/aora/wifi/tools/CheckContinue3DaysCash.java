package com.aora.wifi.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

/**
 * 已经作废
 * @author Administrator
 *
 */
public class CheckContinue3DaysCash {

    private static final int CONTINUE_CASH_DAYS = 3;
    private static final int TOTAL_CASH_DAYS = 4;
    
    private static final int MINUTE_3 = 3 * 60 * 1000;
    
    public static void main(String[] args) throws Exception{
        
        List<CashUser> users = new ArrayList<CashUser>();
        
        CashUser user = new CashUser();
        user.setId(1);
        user.setAccount("aa");
        user.setAlipay_account("aa");
        user.setIndate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse("2015-02-13 12:01:09"));
        users.add(user);
        
        user = new CashUser();
        user.setId(5);
        user.setAccount("ee");
        user.setAlipay_account("ee");
        user.setIndate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse("2015-02-08 14:13:20"));
        users.add(user);
        
        user = new CashUser();
        user.setId(2);
        user.setAccount("bb");
        user.setAlipay_account("bb");
        user.setIndate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse("2015-02-11 14:21:20"));
        users.add(user);
        
        user = new CashUser();
        user.setId(3);
        user.setAccount("cc");
        user.setAlipay_account("cc");
        user.setIndate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse("2015-02-10 19:01:20"));
        users.add(user);
        
        user = new CashUser();
        user.setId(4);
        user.setAccount("dd");
        user.setAlipay_account("dd");
        user.setIndate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse("2015-02-09 11:01:25"));
        users.add(user);
        
        Collections.sort(users, new Comparator<CashUser>(){

            public int compare(CashUser o1, CashUser o2) {
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
        
        System.out.println(users);
        
//        CheckContinue3DaysCash obj = new CheckContinue3DaysCash();
//        int count = obj.getContinueCashCount(users);
//        System.out.println(count);
        //new CheckContinue3DaysCash().parseData();
    }
    
    private void parseData(){
        //获取所有待审核的用户
        Map<String, List<CashUser>> data = getAllUsers();
        //审核所有连续提现的支付宝账户
        List<String> alipays = parseEachUserContinueCash(data);
        //根审核其占领的热点的MAC地址是否作弊, 并返回作弊的帐号
        List<CheatMsg> cheatAccounts= checkHotspotByAlipay(alipays);
        
        //冻结用户
        lockAccount(cheatAccounts);
        
    }
    
    private List<CheatMsg> checkHotspotByAlipay(List<String> alipays){
        List<CheatMsg> cheatAccount = new ArrayList<CheatMsg>();
        
        //遍历用户帐户,取出每个用户帐户所分享的热点数据，进行分析
        for (String alipay : alipays){
            List<Hotspot> list = getHotspotByUserAccount(alipay);
            
            //分析热点mac地址前六位并是否在3分钟之内
            CheatMsg result = analysisHotspot(list);
            
            if (result.isCheat()){
                result.setAccount(alipay);
                cheatAccount.add(result);
            }
        }
        
        return cheatAccount;
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
            
            boolean isIn3Minute = isIn3Minute(hots);
            if (isIn3Minute){
                isCheat = true;
                result.setRemark(entry.getKey());
                break;
            }
        }
        
        
        result.setCheat(isCheat);
        
        return result;
    }
    
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
                
                System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(previousDate));
                System.out.println("\t" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(nextDate));
                
                isIn3Minute = true;
                break;
            }
        }
        
        return isIn3Minute;
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
    private List<Hotspot> getHotspotByUserAccount(String acc){
        String sql = "select account, mac_address, indate from tbl_wifi_share_account_logs where reg_account in (select account from tbl_reg_account where alipay_account = ?)";
        
        List<Hotspot> list = new ArrayList<Hotspot>();
        
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            
            conn = getConnection();
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, acc);
            rs = pstat.executeQuery();
            
            while (rs.next()) {
                String account = rs.getString("account");
                String mac = rs.getString("mac_address");
                Date indate = rs.getTimestamp("indate");
                
                Hotspot hotspot = new Hotspot();
                hotspot.setAccount(account);
                hotspot.setMac_address(mac);
                hotspot.setIndate(indate);
                
                list.add(hotspot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (null != rs) rs.close(); } catch (SQLException e) {  }
            try { if (null != pstat)  pstat.close(); } catch (SQLException e) { }
            try { if (null != conn)  conn.close(); } catch (SQLException e) {}
        }
        
        return list;
    }
    
    private List<String> parseEachUserContinueCash(Map<String, List<CashUser>> data){
        List<String> alipays = new ArrayList<String>();
        
        for (Map.Entry<String, List<CashUser>> entry : data.entrySet()){
            String alipayAccount = entry.getKey();
            List<CashUser> users = entry.getValue();
            
            //获取最近连续提现的天数
            int count = parseIsContinues(users, alipayAccount);
            //目前需求是3的倍数才审核热点
            if (count % CONTINUE_CASH_DAYS == 0){
                alipays.add(alipayAccount);
            }
        }
        
        return alipays;
    }
    
    private int parseIsContinues(List<CashUser> users, String alipayAccount){
        //获取连续提现的天数 
        int count = getContinueCashCount(users);
        
        List<CashUser> cashs = null;
        //如果连续提现超过3天,则提取过去所有提现历史记录
        if (count >= CONTINUE_CASH_DAYS){            
            cashs = getCashLogsByAlipayAccount(alipayAccount); 
        }
        
        //再次针对过去所有提现历史记录获取连续提现的天数
        if (null != cashs){
            count = getContinueCashCount(cashs);
        }
        
        return count;
    }
    
    private List<CashUser> getCashLogsByAlipayAccount(String alipay){
        
        List<CashUser> cashs = new ArrayList<CashUser>();
        
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        
        try {                
            conn = getConnection();
            pstat =  conn.prepareStatement("SELECT id, account, alipay_account, indate as dt FROM tbl_cash_logs WHERE alipay_account = ?");
            pstat.setString(1, alipay);
            rs = pstat.executeQuery();
            
            while (rs.next()){
                int id = rs.getInt("id");
                String account = rs.getString("account");
                String alipayAccount = rs.getString("alipay_account");
                Date indate = rs.getDate("dt");
                
                CashUser user = new CashUser();
                user.setId(id);
                user.setAccount(account);
                user.setAlipay_account(alipayAccount);
                user.setIndate(indate);
                
                cashs.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
               try { if (null != rs) rs.close();  } catch (SQLException e) {}
               try { if (null != pstat) pstat.close();  } catch (SQLException e) {}
               try { if (null != conn) conn.close();  } catch (SQLException e) {}
        }
        
        return cashs;
    }
    
    private Map<String, List<CashUser>> getAllUsers(){
        
        String sql = "select id, account, money, alipay_account, str_to_date(indate,'%Y-%m-%d') as dt from tbl_continue_cash_logs order by alipay_account, indate";
        
        Map<String, List<CashUser>> data = new HashMap<String, List<CashUser>>();
        
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        
        try {                
            conn = getConnection();
            pstat =  conn.prepareStatement(sql);
            rs = pstat.executeQuery();
            
            while (rs.next()){
                int id = rs.getInt("id");
                String account = rs.getString("account");
                String alipayAccount = rs.getString("alipay_account");
                Date indate = rs.getDate("dt");
                double money = rs.getDouble("money");
                
                CashUser user = new CashUser();
                user.setId(id);
                user.setAccount(account);
                user.setAlipay_account(alipayAccount);
                user.setIndate(indate);
                user.setMoney(money);
                
                if (data.containsKey(alipayAccount)){
                    List<CashUser> list = data.get(alipayAccount);
                    if (null == list){
                        list =  new ArrayList<CashUser>();
                        data.put(alipayAccount, list);
                    }
                    
                    list.add(user);
                }else{
                    List<CashUser> list = new ArrayList<CashUser>(5);
                    list.add(user);
                    data.put(alipayAccount, list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
               try { if (null != rs) rs.close();  } catch (SQLException e) {}
               try { if (null != pstat) pstat.close();  } catch (SQLException e) {}
               try { if (null != conn) conn.close();  } catch (SQLException e) {}
        }
        
        return data;
    }
    
    private void updateContinuesCashStatus(List<String> alipays){
        
        Connection conn = null;
        Statement state = null;
        
        String sql = "UPDATE tbl_cash_logs SET suspect = 1 where alipay_account in (?)";
        
        String statement = "";
        for (String alipay : alipays){
            statement += "'" + alipay + "',";
        }
        
        statement = statement.replaceAll(",$", "");
        
        sql = sql.replace("?", statement);
        
        try {                
                conn = getConnection();
                state = conn.createStatement();
                state.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
               try { if (null != state) state.close();  } catch (SQLException e) {}
               try { if (null != conn) conn.close();  } catch (SQLException e) {}
        }
    }
    
    
    private void lockAccount(List<CheatMsg> cheatAccounts){
        
        Connection conn = null;
        Statement state = null;
        
        try {                
            conn = getConnection();
            state = conn.createStatement();
            for (CheatMsg msg : cheatAccounts){   
                String sql = "UPDATE tbl_reg_account SET status = 1 , remark = '" + msg.getRemark() + "' where alipay_account = '" + msg.getAccount() + "'";
                state.executeUpdate(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
               try { if (null != state) state.close();  } catch (SQLException e) {}
               try { if (null != conn) conn.close();  } catch (SQLException e) {}
        }
    }
    
    private static Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String dbpath = "jdbc:mysql://192.168.0.36:3306/wifiapp2?useUnicode=true&characterEncoding=utf-8";
        Connection conn = DriverManager.getConnection(dbpath, "wifi", "wifiapp");

        return conn;
    }
    
    
    /**
     * 获取连续提现的天数 
     * @param users
     * @return
     */
    private int getContinueCashCount(List<CashUser> users){
        int count = 0;
        
        sortCashDate(users);
        
        System.out.println(users);
        for (int i = 0; i < users.size() - 1; i++){
            
            Date firstDate = users.get(i).getIndate();
            Date nextDate =  DateUtils.addDays(users.get(i + 1).getIndate(), 1);
            
            System.out.print("continue:" + i + "\t" + new SimpleDateFormat("yyyy-MM-dd").format(firstDate));
            System.out.print("\t" + new SimpleDateFormat("yyyy-MM-dd").format(users.get(i + 1).getIndate()));
            boolean bool = DateUtils.isSameDay(firstDate, nextDate);
            
            if (bool){
                if (count == 0){                    
                    count += 2;
                }else{
                    count += 1;
                }
            }else{
                System.out.println("\t" + bool + "\tcount:" + count);
                break;
            }
            
            System.out.println("\t" + bool + "\tcount:" + count);
        }
        
        return count;
    }
    
    private void sortCashDate(List<CashUser> users){
        Collections.sort(users, new Comparator<CashUser>(){

            public int compare(CashUser o1, CashUser o2) {
                Date d1 = o1.getIndate();
                Date d2 = o2.getIndate();
                
                if(d1.after(d2)){  
                    return -1;  
                }  
                else{  
                    return 1;  
                }  
            }
        });
    }
    
    
    public static class CashUser{
        
        @Override
        public String toString() {
            return new SimpleDateFormat("yyyy-MM-dd").format(indate);
        }

        private int    id;

        private String account;
        
        private double money;

        private String alipay_account;

        private Date indate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAlipay_account() {
            return alipay_account;
        }

        public void setAlipay_account(String alipayAccount) {
            alipay_account = alipayAccount;
        }

        public Date getIndate() {
            return indate;
        }

        public void setIndate(Date indate) {
            this.indate = indate;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
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
    
    public static class CheatMsg{
        
        private String account;
        
        private boolean isCheat;
        
        private String remark;

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
    
}
