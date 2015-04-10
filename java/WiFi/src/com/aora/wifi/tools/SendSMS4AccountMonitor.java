package com.aora.wifi.tools;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendSMS4AccountMonitor {

    private static final String QUERY_SQL = "select count(*) as num from tbl_wifi_account where category = '${category}' and status = ?";
    
    public static void main(String[] args) {
        new SendSMS4AccountMonitor().start();
    }
    
    private void start(){
        int cmcc_status1_Num = getUsableNum("CMCC", 1);
        int chinaNet_status1_Num = getUsableNum("CHINANET", 1);
        
        String datestr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        
        System.out.println("date:" + datestr +"\t CMCC:" + cmcc_status1_Num + ",chinaNet:" + chinaNet_status1_Num);
        
        if (cmcc_status1_Num <= 50 || chinaNet_status1_Num <= 2000){
            
            int cmcc_status2_Num = getUsableNum("CMCC", 2);
            int cmcc_status3_Num = getUsableNum("CMCC", 3);
            
            int chinaNet_status2_Num = getUsableNum("CHINANET", 2);
            int chinaNet_status3_Num = getUsableNum("CHINANET", 3);
            
            if (hasMoreThan30Minute()){
                int status = sendSMS(cmcc_status1_Num, cmcc_status2_Num,  cmcc_status3_Num, chinaNet_status1_Num, chinaNet_status2_Num, chinaNet_status3_Num);
                
                saveToDB(cmcc_status1_Num, cmcc_status2_Num,  cmcc_status3_Num, chinaNet_status1_Num, chinaNet_status2_Num, chinaNet_status3_Num, status);
            }
        }
    }
    
    private boolean hasMoreThan30Minute(){
        System.out.println("hasMoreThan30Minute...");
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        
        boolean result = false;
        String sql = "select   time_to_sec( timediff(now(), max(sendTime)) ) as sec from tbl_save_usableNum_sendsms";
        try {                
                conn = getConnection();
                pstat =  conn.prepareStatement(sql);
                rs = pstat.executeQuery();
                
                int num = 0;
                if (rs.next()){
                    num = rs.getObject("sec") == null ? 0 : Integer.valueOf(rs.getObject("sec").toString());
                }
                
                double minute = (num / 60.0);
                System.out.println("minute:" + minute);
                result = (minute == 0.0 || minute >= 30);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
               try { rs.close();  } catch (SQLException e) {}
               try { pstat.close();  } catch (SQLException e) {}
               try { conn.close();  } catch (SQLException e) {}
        }
        
        System.out.println("hasMoreThan30Minute result:" + result);
        return result;
    }
    
    private void saveToDB(int cmcc_status1_Num, int cmcc_status2_Num, int  cmcc_status3_Num, int chinaNet_status1_Num, int chinaNet_status2_Num, int chinaNet_status3_Num, int status){
        System.out.println("save status to DB.");
        Connection conn = null;
        PreparedStatement state = null;
        try {                
                conn = getConnection();
                String SQL = "INSERT INTO tbl_save_usableNum_sendsms(sendTime, cmcc_status1, cmcc_status2, cmcc_status3, chinaNet_status1, chinaNet_status2, chinaNet_status3, sendStatus) VALUES(now(), ?,?,?,?,?,?,?)";
                state = conn.prepareStatement(SQL);
                
                state.setInt(1, cmcc_status1_Num);
                state.setInt(2, cmcc_status2_Num);
                state.setInt(3, cmcc_status3_Num);
                
                state.setInt(4, chinaNet_status1_Num);
                state.setInt(5, chinaNet_status2_Num);
                state.setInt(6, chinaNet_status3_Num);
                
                state.setInt(7, status);
                
                state.executeUpdate();
                
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
               try { if (null != state) state.close();  } catch (SQLException e) {}
               try { if (null != conn) conn.close();  } catch (SQLException e) {}
        }
    }
    
    private static int getUsableNum(String category, int status){
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        
        int num = 0;
        String sql = QUERY_SQL.replace("${category}", category);
        try {                
                conn = getConnection();
                pstat =  conn.prepareStatement(sql);
                pstat.setInt(1, status);
                rs = pstat.executeQuery();
                
                if (rs.next()){
                    num = rs.getInt("num");   
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
               try { rs.close();  } catch (SQLException e) {}
               try { pstat.close();  } catch (SQLException e) {}
               try { conn.close();  } catch (SQLException e) {}
        }
        
        return num;
    }
    
    
    private static Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String dbpath = "jdbc:mysql://192.168.0.36:3306/wifiapp2?useUnicode=true&characterEncoding=utf-8";
        Connection conn = DriverManager.getConnection(dbpath, "wifi", "wifiapp");

        return conn;
    }
    
    public int sendSMS(int cmcc_status1, int cmcc_status2, int cmcc_status3, int chinaNet_status1, int chinaNet_status2, int chinaNet_status3) {
        System.out.println("send sms");
        
        String smscontent="CMCC 状态1:" + cmcc_status1 + ",状态2:" + cmcc_status2 +",状态3:" + cmcc_status3;
        smscontent += ",ChinaNet 状态1:" + chinaNet_status1 + ",状态2:" + chinaNet_status2 + ",状态3:" + chinaNet_status3;
        
        String url = "http://smsapi.c123.cn/OpenPlatform/OpenApi?action=sendOnce&ac=1001@500933530001&authkey=F9FE80B75E91FD430E0B81FFF5B5191B&cgid=1754&csid=101&c=" + URLEncode(smscontent, "UTF-8")+"&m=13570804309";
        byte[] response = postdata(url, "");
        String responsestr = response == null ? "" : new String(response);
        
        int sendStatus = 1;
        if(responsestr.length()>0 && responsestr.indexOf("result=\"1\"")>0){
            sendStatus = 0;
        }
        
        return sendStatus;
    }
    
    public String URLEncode(String str, String lang) {
        String returnval = "";
        if (str != null) {
            try{
                if(lang!=null && lang.length()>0){
                    returnval=java.net.URLEncoder.encode(str,lang);
                }else{
                    returnval=java.net.URLEncoder.encode(str,"UTF-8");
                }
            }catch(Exception e){
            }
        }
        return returnval;
    }   
    
    public byte[] postdata(String strurl,String data){
        URL url = null;   
        byte[] val=null;
        
        HttpURLConnection httpConn = null;   
        
        try {
             url = new URL(strurl);   
              
             httpConn = (HttpURLConnection) url.openConnection();   
             HttpURLConnection.setFollowRedirects(false);   
             httpConn.setInstanceFollowRedirects(false);
             httpConn.setDoOutput(true);
             
             java.io.OutputStream os= httpConn.getOutputStream();
             os.write(data.getBytes("UTF-8"));
             os.flush();
        
             java.io.InputStream is= httpConn.getInputStream();
             java.io.ByteArrayOutputStream bos=new ByteArrayOutputStream();
             
             int rlength=0;
             byte[] buf=new byte[1024*1];
             while((rlength=is.read(buf))!=-1){
                 bos.write(buf, 0, rlength);
             }
             
            val = bos.toByteArray();
             
             os.close(); os=null;
             is.close(); is=null;
             
             httpConn.disconnect();
             httpConn=null;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return val;
    }
    
}
