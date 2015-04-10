package com.aora.wifi.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

public class AccountDailyCheck {

	private String driver=null;
	private String dbPath=null;
	private String dbUser=null;
	private String dbPwd=null;
	
	public AccountDailyCheck(){
		try{loadProperties("/configuration/jdbc.properties");}catch(Exception e){e.printStackTrace();}
	}
	
	 private  void loadProperties(String fileName) throws Exception
	  {

		 this.loger("\t\t"+(new Date()).toString());
	      Properties properties = new Properties();
	      properties.load(getClass().getResourceAsStream(fileName) );
		 
	      driver=properties.getProperty("jdbc.driverClassName"); loger("\t\t srcPath:"+driver);
	      dbPath=properties.getProperty("jdbc.url");  			 loger("\t\t dbPath:"+dbPath);
	      dbUser=properties.getProperty("jdbc.username");   	 loger("\t\t dbUser:"+dbUser);
	      dbPwd=properties.getProperty("jdbc.password");	 	 loger("\t\t dbPwd:"+dbPwd);
	  }

	 public static void main(String[] arg){
		 new AccountDailyCheck().process();
		 
		 
	 }
	 
	 public void process(){
		 
		 Connection conn=null;
		 PreparedStatement PreState=null;
		 Statement state=null;
		 ResultSet rs=null;
		 try{
			 
	         Class.forName(driver);
	         conn=DriverManager.getConnection(dbPath,dbUser,dbPwd);

             String getParamsSQL = "SELECT value FROM tbl_wifi_parameter WHERE name = 'use_minute_everyday'";
             PreState = conn.prepareStatement(getParamsSQL);
             rs = PreState.executeQuery();
             
             int use_minute_everyday = 0;
             if (rs.next()){
                 use_minute_everyday = rs.getInt("value");
             }
             
             if (0 != use_minute_everyday){
                 state=conn.createStatement();
                 state.executeUpdate("update tbl_user_account set use_minute=360,signdays=0 ");                  
             }
             		     
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
		    try{if (rs != null) rs.close();}catch(Exception e){}
			try{if (state != null) state.close();state=null;}catch(Exception e){}
		 	try{if (PreState != null) PreState.close();PreState=null;}catch(Exception e){}
		 	try{if (conn != null) conn.close();conn=null;}catch(Exception e){} 
		 }
		 		 
		 
	 }
	 
	 
	  private void loger(String s){
		  System.out.println(s);
	  }	
}
