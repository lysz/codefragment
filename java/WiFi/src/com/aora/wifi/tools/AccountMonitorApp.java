package com.aora.wifi.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;



public class AccountMonitorApp {

	private String driver=null;
	private String dbPath=null;
	private String dbUser=null;
	private String dbPwd=null;
	
	private Map<String, Integer> params = new HashMap<String, Integer>();
	
	public AccountMonitorApp(){
		try{loadProperties("/configuration/jdbc.properties");}catch(Exception e){e.printStackTrace();}
//	    try{loadProperties("E:\\codes\\Wifi\\src\\configuration\\jdbc.properties");}catch(Exception e){e.printStackTrace();}
	}
	
	 private  void loadProperties(String fileName) throws Exception
	  {

		 this.loger("\t\t"+(new Date()).toString());
		 //System.out.println("Reading configuration file " + fileName + "...");
//	      FileInputStream propsFile = new FileInputStream(fileName);
//	      Properties properties = new Properties();
//	      properties.load(propsFile);
//	      propsFile.close();

	      Properties properties = new Properties();
	      properties.load(getClass().getResourceAsStream(fileName) );

		 
	      driver=properties.getProperty("jdbc.driverClassName"); loger("\t\t srcPath:"+driver);
	      dbPath=properties.getProperty("jdbc.url");  			 loger("\t\t dbPath:"+dbPath);
	      dbUser=properties.getProperty("jdbc.username");   	 loger("\t\t dbUser:"+dbUser);
	      dbPwd=properties.getProperty("jdbc.password");	 	 loger("\t\t dbPwd:"+dbPwd);
	      //System.out.println(properties.getProperty("test", ""));	
	  }

	 public static void main(String[] arg){
		 new AccountMonitorApp().process();
	     
	 }
	 
	 public void process(){
		 
		 Connection conn=null;
		 PreparedStatement PreState=null;
		 Statement state=null;
		 ResultSet rs=null;
		 try{
			 
//			 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//	 	     conn=   ds.getConnection(); 
	         Class.forName(driver);
	         conn=DriverManager.getConnection(dbPath,dbUser,dbPwd);

	         state=conn.createStatement();
	         
	         //---- add by yangliu
	         String getParamsSQL = "SELECT name, value FROM tbl_wifi_parameter WHERE name = 'CMCC_Max_Time_Hours' or name = 'ChinaNet_Max_Time_Hours'";
             PreState = conn.prepareStatement(getParamsSQL);
             rs = PreState.executeQuery();
             while(rs.next()){
                 String name = rs.getString("name");
                 int value = rs.getInt("value");
                 params.put(name, value);
             }
             //------------
             
/// used minute count
             this.loger("Start :step1"+System.currentTimeMillis());
	         String sql="select id,uid,use_minute,used_minute from tbl_user_account a inner join (select used_client_id,sum(case when fLOOR((TIME_TO_SEC(now())-TIME_TO_SEC(action_date))/60)>=5 then 5 else fLOOR((TIME_TO_SEC(now())-TIME_TO_SEC(action_date))/60) end ) 'used_minute'  from tbl_wifi_account where status=3  group by used_client_id) b on a.uid=b.used_client_id where a.status=0";
	         PreState=conn.prepareStatement(sql);
		     rs=PreState.executeQuery();
		     while(rs.next()){
		    	 int id=rs.getInt("id");
		    	 int user_minute=rs.getInt("use_minute");
		    	 int used_minute=rs.getInt("used_minute");
		    	 String uid=rs.getString("uid");
		    	 if(user_minute>=0){
		    		 if(used_minute<0){
		    			 state.executeUpdate("update tbl_wifi_account set expiry_date=now() where used_login_id='"+uid+"'");
		    		 }else if(user_minute>used_minute){
		    			 state.executeUpdate("update tbl_user_account set use_minute="+(user_minute-used_minute)+" where uid='"+uid+"'");
		    		 }else{
		    			 state.executeUpdate("update tbl_user_account set use_minute=0 where uid='"+uid+"'");
		    			 state.executeUpdate("update tbl_wifi_account set expiry_date=now() where used_client_id='"+uid+"'");
		    		 }
		    	 }
		    	 
		     }
		     this.loger("end :step1"+System.currentTimeMillis());		     
/// used minute count		     
	        
//		     log("start....");
//		     Set<String> invalidAccount = new HashSet<String>();
//		      ///-------set card status
//            PreState = conn.prepareStatement("SELECT * FROM tbl_wifi_account where status = 3");
//            rs = PreState.executeQuery();
//            while (rs.next()) {
//                log("------");
//                int id = rs.getInt("id");
//                String account = rs.getString("account");
//                String sessionid=(rs.getString("sessionid")==null?"":rs.getString("sessionid"));
//                String cookies=(rs.getString("cookies")==null?"":rs.getString("cookies"));
//                String postdata=(rs.getString("postdata")==null?"":rs.getString("postdata"));
//                String logout_url=(rs.getString("logout_url")==null?"":rs.getString("logout_url"));
//                String category = rs.getString("category") == null ? "" : rs.getString("category");
//                int length = rs.getObject("account_used_length") == null ? 0 : Integer.valueOf(rs.getObject("account_used_length").toString());
//                int status = rs.getInt("status");
//                
//                log("id[" + id + "], account[" + account + "], type[" + category + "], status[" + status + "], length[" + length + "]");
//                
//                if (invalidAccount.contains(account)){
//                    log("error: The account was invalided.");
//                    continue;
//                }
//                
//                Object totalLengthObj = null;
//                //如果是CMCC类型, 则要把三条数据合并, 计算总时长
//                if (category.equalsIgnoreCase("CMCC")){
//                                        
//                    PreparedStatement pstat = null;
//                    ResultSet rset = null;
//                    try{
//                        pstat = conn.prepareStatement("SELECT SUM(account_used_length) AS account_used_length FROM tbl_wifi_account WHERE category='CMCC' AND account = '" + account + "' GROUP BY account;");
//                        rset = pstat.executeQuery();
//                        while (rset.next()){
//                            totalLengthObj = rset.getObject("account_used_length");
//                        }
//                    }catch(Exception e){
//                        log("" +  e.getStackTrace());
//                    }finally{
//                        try{rset.close();rset=null;}catch(Exception e){}
//                        try{pstat.close();pstat=null;}catch(Exception e){}
//                    } 
//                }else if (category.equalsIgnoreCase("ChinaNet")){
//                    totalLengthObj = rs.getObject("account_used_length");
//                }
//                
//                int totalLength = totalLengthObj == null ? 0 : Integer.valueOf(totalLengthObj.toString());
//                
//                log("id[" + id + "], account[" + account + "], type[" + category + "], status[" + status + "], totalLength[" + totalLength + "], length[" + length + "]");
//                log("totalLength + 5...");
//                //当前上网时长(分钟)
//                int minute = 5;
//                                
//                if (category.equalsIgnoreCase("CMCC")){
//                    //总时长加上当前己用时长
//                    totalLength += minute;
//                    
//                    //CMCC最大可用时长
//                    int maxTimeHours = Integer.valueOf(params.get("CMCC_Max_Time_Hours").toString());
//                    //当前帐号的总使用时长大于最大可用时长,要将状态设置为 异常
//                    if (totalLength >= maxTimeHours){
//                        status = 0;
//                    }
//                }else if (category.equalsIgnoreCase("ChinaNet")){
//                    //总时长加上当前己用时长
//                    totalLength += minute;
//                    
//                    //chinanet最大可用时长
//                    int maxTimeHours = Integer.valueOf(params.get("ChinaNet_Max_Time_Hours").toString());
//                    //当前帐号的总使用时长大于最大可用时长,要将状态设置为 异常
//                    if (totalLength >= maxTimeHours){
//                        status = 0;
//                    }
//                }
//                
//                int currentLength = length + minute;
//                
//                //踢人
//                if (category.equalsIgnoreCase("CMCC") && status == 0 && logout_url.trim().length()>10){
//                    log("tiren....");
//                    LogoutUtil.logoutCMCC(logout_url, cookies, postdata, sessionid);
//                }
//                
//                log("account[" + account + "], type[" + category + "], status[" + status + "], totalLength[" + totalLength + "], currentLength[" + currentLength + "]");
//                
//                if( status == 0){   
//                    invalidAccount.add(account);
//                    sql = "update tbl_wifi_account set account_used_time=now(), status=0  where account='" + account + "'";
//                }else{
//                    sql = "update tbl_wifi_account set account_used_time=now(), status=" + status + ", account_used_length=" + currentLength + " where id=" + id;
//                }
//                
//                log("SQL[" + sql + "]");
//                state.executeUpdate(sql);
//            }
//            log("end....");
//            ///-------set card status 
		     
		     this.loger("Start :step2"+System.currentTimeMillis());
		     PreState=conn.prepareStatement("SELECT * FROM tbl_wifi_account  where (status=2 or status=3) and expiry_date<=now() order by expiry_date desc");
		     rs=PreState.executeQuery();
		     while(rs.next()){

                 int id=rs.getInt("id");
                 sql="";
                 boolean result=false;
                 String sessionid=(rs.getString("sessionid")==null?"":rs.getString("sessionid"));
                 String cookies=(rs.getString("cookies")==null?"":rs.getString("cookies"));
                 String postdata=(rs.getString("postdata")==null?"":rs.getString("postdata"));
                 String logout_url=(rs.getString("logout_url")==null?"":rs.getString("logout_url"));
                 String category=(rs.getString("category")==null?"":rs.getString("category"));
                 String account=(rs.getString("account")==null?"":rs.getString("account"));
                 
                 int status=rs.getInt("status");

                 //this.loger("\t\tprocessed:"+id);
                 
                if(status==3 && logout_url.trim().length()>10){
                    
                    result=false;
                    try{
                        if ("CMCC".equalsIgnoreCase(category)){
                            LogoutUtil.logoutCMCC(logout_url, cookies, postdata, sessionid);
                        }
                        result=true;
                    }catch(Exception e){
                        e.printStackTrace();
                        result=false;
                    }
                    
                    this.loger(System.currentTimeMillis()+"account:"+account+",logout result:"+result+","+logout_url);                    
                    
                }
                
                 
                if ("CMCC".equalsIgnoreCase(category)||category.equalsIgnoreCase("CTM-WIFI")||category.equalsIgnoreCase("CHT Wi-Fi(HiNet)")){
                    
                     sql="update tbl_wifi_account set status=1,action_twice=0,action_date=now(),expiry_date=now(),cookies='',sessionid='',postdata='',used_login_id='' ,used_client_id='',logout_url='' where id="+id;
                 }else{
                	 if(status==3){
                		 	sql="update tbl_wifi_account set status=4,action_twice=0,modify_date=now(),cookies='',sessionid=concat(ifnull(used_client_id,''),';',ifnull(used_login_id,'')),postdata='',used_login_id='' ,used_client_id='',logout_url='' where id="+id;
                	 }else{
             		 		sql="update tbl_wifi_account set status=5,action_twice=0,action_date=now(),expiry_date=now(),cookies='',sessionid=concat(ifnull(used_client_id,''),';',ifnull(used_login_id,'')),postdata='',used_login_id='' ,used_client_id='',logout_url='' where id="+id;                		 	
                	 }
                      
                 }
                 state.executeUpdate(sql);
		     }
		     this.loger("End :step2"+System.currentTimeMillis());		     
		     
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			try{state.close();state=null;}catch(Exception e){}
		 	try{PreState.close();PreState=null;}catch(Exception e){}
		 	try{conn.close();conn=null;}catch(Exception e){} 
		 }
		 
	 }
	 

	 
	  private void loger(String s){
		  System.out.println(s);
	  }	
	  
	  
	  private void log(String s){

//	      File file = new File("/wifiapp/logs/accountStatusMonitor.log");
//	      FileOutputStream fis = null;
//	      BufferedOutputStream bos = null;
//	      
//	      try{
//	          if (!file.exists()){
//	              file.createNewFile();
//	          }
//	          
//	          fis = new FileOutputStream(file, true);
//	          bos = new BufferedOutputStream(fis);
//	          String date = com.aora.wifi.util.Tools.date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
//	          bos.write((date + "\t" +s + "\n").getBytes());
//	          
//	          bos.flush();
//	      }catch(Exception e){
//	          e.printStackTrace();
//	      }
//	      finally{
//	          try{
//	              if (bos != null)
//	                  bos.close();
//	              if (fis != null)
//	                  fis.close();
//	          }catch(Exception e){}
//	      }
	      
	  }
	  

	  
}
