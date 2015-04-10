package com.aora.wifi.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import com.aora.wifi.util.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GenerateWIFICityData {
	private String driver=null;
	private String dbPath=null;
	private String dbUser=null;
	private String dbPwd=null;
	private String wificitypath=null;
	
	public GenerateWIFICityData(){
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
	      wificitypath=properties.getProperty("wificitypath");	 	 loger("\t\t wificitypath:"+wificitypath);
	      //System.out.println(properties.getProperty("test", ""));	
	  }

	  private void loger(String s){
		  System.out.println(s);
	  }	
	  
	 public static void main(String[] arg){
		 new GenerateWIFICityData().process();
		 
		 
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
//		     PreState=conn.prepareStatement("SELECT distinct city  FROM tbl_wifi_share_account where city is not null");
//		     rs=PreState.executeQuery();
//		     while(rs.next()){
//		    	 	
//		    	 String city=(rs.getString("city")==null?"":rs.getString("city"));
//		    	 
//
//		    	 	JSONArray jsonarray=null;	
//			    	 if(city.length()>2){
	         			 JSONArray jsonarray=null;
			    		 jsonarray=new JSONArray();			    		 
			    		 //ResultSet drs= state.executeQuery("select mac_address,account from tbl_wifi_share_account where city='"+city+"' and status=1");
			    		 ResultSet drs= state.executeQuery("select * from tbl_hotspot where city='上海市' and type='cmcc' order by rand() limit 10000 ");
			    		 while(drs.next()){
			    			 JSONObject item=new JSONObject();
			    			 
			    			 item.put("lng",  (drs.getString("gaode_longitude")!=null?drs.getString("gaode_longitude").toString():"0"));
			    			 item.put("lat",  (drs.getString("gaode_latitude")!=null?drs.getString("gaode_latitude").toString():"0"));
			    			 item.put("name", (drs.getString("hotname")!=null?drs.getString("hotname").toString():""));
			    			 item.put("address", (drs.getString("address")!=null?drs.getString("address").toString():""));
			    			 jsonarray.add(item);		    		 
			    		 }
			    		 
				    	 //String filename=Tools.asHex(city.getBytes("UTF-8"))+".txt";
			    		 String filename="sh_cmcc"+".ini";
			    		 
//			    		  FileOutputStream fos = null;
//			     		  OutputStreamWriter writer = null;	
//			    		   File file = new File(wificitypath+filename);
//			     		   fos = new FileOutputStream(file);
//			     		   writer = new OutputStreamWriter(fos,"utf-8");
//			     		   System.out.println(jsonarray.size());
//			    		 jsonarray.write(writer);
//			  		   writer.flush();
//			 		   writer.close();writer=null;
//			 		   fos.close();fos=null; 
				    	 createUTF8File(wificitypath+filename,jsonarray.toString());			    		 
//			    	 }
//      
//		    	 
//		     }
 	 
		     
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			try{state.close();state=null;}catch(Exception e){}
		 	try{PreState.close();PreState=null;}catch(Exception e){}
		 	try{conn.close();conn=null;}catch(Exception e){} 
		 }
		 		 
		 
	 }
 
 	private void createUTF8File(String filestr,String data){
 		
 		  FileOutputStream fos = null;
 		  OutputStreamWriter writer = null;
 		  try {
 		   File file = new File(filestr);
 		   fos = new FileOutputStream(file);
 		   writer = new OutputStreamWriter(fos,"utf-8");
 		   writer.write(data);
 		   writer.flush();
 		   writer.close();writer=null;
 		   fos.close();fos=null;
 		  } catch (Exception e) {
 			  e.printStackTrace();
 		  } finally {
 			  try { if(fos != null){  fos.close();} } catch (Exception e1) {e1.printStackTrace();}
 			  try { if(writer != null) { writer.close();} } catch (Exception e) {e.printStackTrace(); }
 		  }
 		}
	 
	 	 
}
