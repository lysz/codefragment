package com.aora.wifi.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.aora.wifi.util.DesTools;
import com.aora.wifi.util.Tools;

public class FindDataAPI {
	
	private static Logger logger = Logger.getLogger(WiFiCrackUtil.class);

	private String driver=null;
	private String dbPath=null;
	private String dbUser=null;
	private String dbPwd=null;
	//http://www.gpsspg.com/ajax/maps_get.aspx?lat=22.541817&lng=114.038079&type=0
	
	public FindDataAPI(){
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
	
	  private void loger(String s){
		  System.out.println(s);
	  }		
	 	
	  
	  public static void main(String[] arg){
		  FindDataAPI obj=new FindDataAPI();
		  //obj.process3_news();
		  obj.process4_video();
		  //obj.process6_beauties();
		  //obj.process7_fiction();
		  //obj.process8_joke();
		  //obj.process9_app();
		  
	  }
	  
	  
	  
		 public void process3_news(){
			 
			 final String   url="http://c.3g.163.com/wificy/nc/article/list/T1348647909107/0-20.html";
			 Connection conn=null;
			 PreparedStatement PreState=null;
			 Statement state=null;
			 ResultSet rs=null;
			 try{
				 
	        	 String postdata=new String(getdata(url, "", null),"UTF8");


	        	 System.out.println(postdata);
	        	 JSONObject json=(JSONObject) JSONSerializer.toJSON( postdata );			 
				 
	        	 if(json.containsKey("T1348647909107")){
	        		 JSONArray jary=(JSONArray)json.getJSONArray("T1348647909107");
				 
//				 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//		 	     conn=   ds.getConnection(); 
		         Class.forName(driver);
		         conn=DriverManager.getConnection(dbPath,dbUser,dbPwd);

		         state=conn.createStatement();
		         
		         if(jary!=null && jary.size()>0){
		        	 for(int i=0;i<jary.size();i++){
		        		 JSONObject item=jary.getJSONObject(i);
		        		 String title=(item.containsKey("title")?item.getString("title"):"");
		        		 String img_url=(item.containsKey("imgsrc")?item.getString("imgsrc"):"");
		        		 String url_3w=(item.containsKey("url_3w")?item.getString("url_3w"):"");
		        		 String cid=(item.containsKey("cid")?item.getString("cid"):"");
		        		 
		        		 
		        		 if(url_3w.length()>0){
		        			 String sql="insert into tbl_find3_news(title,remark,hyper_url,type,indate,status,sort) values('"+title+"','"+img_url+"','"+url_3w+"','"+cid+"',now(),'1','"+(i+1)+"')";
		        			 state.execute(sql);
		        		 }
		        		 
		        	 }
		        	 
		        	 
		         }

		         
	        	}
		         
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 
			 		 
			 
			 
			 
		 }	  
	  
	  
	 public void process4_video(){
		 
		 //final String   url="http://expand.video.iqiyi.com/api/album/udlist.json?apiKey=0aad2d8f03b04181b2c888dcec7cfee0&startTime=&endTime=&pageNo=&pageSize=3&status=0&categoryId=2";
		 final String   url="http://expand.video.iqiyi.com/api/video/list.json?apiKey=0aad2d8f03b04181b2c888dcec7cfee0&pageSize=10&startTime="+Tools.date2String(new Date(System.currentTimeMillis()-1000*3600*24L), "yyyyMMddHHmmss")+"&endTime="+Tools.date2String(new Date(), "yyyyMMddHHmmss");
		 Connection conn=null;
		 PreparedStatement PreState=null;
		 Statement state=null;
		 ResultSet rs=null;
		 try{
			 
        	 String postdata=new String(getdata(url, "", null),"UTF8");


        	 System.out.println(postdata);
        	 JSONObject json=(JSONObject) JSONSerializer.toJSON( postdata );			 
			 
        	 if(json.containsKey("data")){
        		 JSONArray jary=(JSONArray)json.getJSONArray("data");
			 
//			 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//	 	     conn=   ds.getConnection(); 
	         Class.forName(driver);
	         conn=DriverManager.getConnection(dbPath,dbUser,dbPwd);

	         state=conn.createStatement();
	         
	         if(jary!=null && jary.size()>0){
	        	 for(int i=0;i<jary.size();i++){
	        		 JSONObject item=jary.getJSONObject(i);
	        		 String title=(item.containsKey("tvName")?item.getString("tvName"):"");
	        		 String desc=(item.containsKey("desc")?item.getString("desc"):"");
	        		 String img_url=(item.containsKey("videoImage")?item.getString("videoImage"):"");
	        		 String html5url=(item.containsKey("html5PlayUrl")?item.getString("html5PlayUrl"):"");
	        		 String videoUrl=(item.containsKey("videoUrl")?item.getString("videoUrl"):"");
	        		 String tvId=(item.containsKey("tvId")?item.getString("tvId"):"");
	        		 
	        		 title=title.replace("'", "’");

	        		 String sql="insert into tbl_find4_video(title,img_url,hyper_url,type,indate,status,sort,remark) values('"+title+"','"+img_url+"','"+html5url+"','"+tvId+"',now(),'1','"+(i+1)+"','"+videoUrl+"')";
	        		 state.execute(sql);
	        		 
	        	 }
	        	 
	        	 
	         }

	         
        	}
	         
	         
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 		 
		 
		 
		 
	 }
	  
 public void process6_beauties(){
		 
		 final String   url="http://partner.service.androidesk.com/wifichangyou/img/list";
		 Connection conn=null;
		 PreparedStatement PreState=null;
		 Statement state=null;
		 ResultSet rs=null;
		 try{
			 
        	 String postdata=new String(getdata(url, "", null),"UTF8");


        	 System.out.println(postdata);
        	 JSONObject json=(JSONObject) JSONSerializer.toJSON( postdata );			 
			 
        	 if(json.containsKey("resp")){
        		 JSONArray jary=(JSONArray)json.getJSONArray("resp");
			 
//			 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//	 	     conn=   ds.getConnection(); 
	         Class.forName(driver);
	         conn=DriverManager.getConnection(dbPath,dbUser,dbPwd);

	         state=conn.createStatement();
	         
	         if(jary!=null && jary.size()>0){
	        	 for(int i=0;i<jary.size();i++){
	        		 JSONObject item=jary.getJSONObject(i);
	        		 String cid=(item.containsKey("cid")?item.getString("cid"):"");
	        		 String img_url="";
	        		 String obj_id="";
	        		 JSONObject fobjs=(JSONObject)(item.containsKey("fobjs")?item.getJSONObject("fobjs"):null);
	        		 if(fobjs!=null){
	        			 img_url=(fobjs.containsKey("480x320")?fobjs.getString("480x320"):"");
	        		 }
	        		 if(img_url.length()>0 && img_url.lastIndexOf("/")>0){
	        			 obj_id=img_url.substring(img_url.lastIndexOf("/")+1);
	        		 }
	        		 
	        		 String detail_url="http://m.wandoujia.androidesk.com/?page=detail&fobjid="+obj_id+"&cid="+cid;
	        		 detail_url="http://m.partner.adesk.com/wifichangyou?page=detail&fobjid="+obj_id+"&cid="+cid;
	        		 String sql="insert into tbl_find6_beauties(title,img_url,hyper_url,type,indate,status,sort,remark) values('"+""+"','"+img_url+"','"+detail_url+"','"+cid+"',now(),'1','"+(i+1)+"','"+obj_id+"')";
	        		 state.execute(sql);
	        		 
	        	 }
	        	 
	        	 
	         }

	         
        	}
	         
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 
		 
		 
	 }
	 
	 public void process7_fiction(){
		 
		 final String   url="http://s.iyd.cn/mobile/dataH5/wifichangyou/002400029";
		 Connection conn=null;
		 PreparedStatement PreState=null;
		 Statement state=null;
		 ResultSet rs=null;
		 try{
			 
        	 String postdata=new String(postdata(url, "", null),"UTF8");


        	 
        	 JSONArray json=(JSONArray) JSONSerializer.toJSON( postdata );			 
			 

			 
//			 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//	 	     conn=   ds.getConnection(); 
	         Class.forName(driver);
	         conn=DriverManager.getConnection(dbPath,dbUser,dbPwd);

	         state=conn.createStatement();
	         
	         if(json!=null && json.size()>0){
	        	 for(int i=0;i<json.size();i++){
	        		 JSONObject item=json.getJSONObject(i);
	        		 String resource_id=(item.containsKey("resource_id")?item.getString("resource_id"):"");
	        		 String book_url=(item.containsKey("book_url")?item.getString("book_url"):"");
	        		 String category_name=(item.containsKey("category_name")?item.getString("category_name"):"");
	        		 String resource_name=(item.containsKey("resource_name")?item.getString("resource_name"):"");
	        		 String book_pic_url=(item.containsKey("book_pic_url")?item.getString("book_pic_url"):"");
	        		 String wap_book_url=(item.containsKey("wap_book_url")?item.getString("wap_book_url"):"");	        		 
	        		 String udate=(item.containsKey("udate")?item.getString("udate"):"");	        		 
	        		 
	        		 String sql="insert into tbl_find7_fiction(title,img_url,hyper_url,type,indate,status,remark,udate,ref_id) values('"+resource_name+"','"+book_pic_url+"','"+book_url+"','"+category_name+"',now(),'1','"+wap_book_url+"','"+udate+"','"+resource_id+"')";
	        		 state.execute(sql);
	        		 
	        	 }
	        	 
	        	 
	         }

	         
	         
	         
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 
		 
		 
	 }
	 
	 public void process8_joke(){
		 
		 final String   url="http://api.qiushibaike.com/json/jinliwifi_api.json";
		 Connection conn=null;
		 PreparedStatement PreState=null;
		 Statement state=null;
		 ResultSet rs=null;
		 try{
			 
        	 String postdata=new String(getdata(url, "", null),"UTF8");


        	 System.out.println(postdata);
        	 JSONArray json=(JSONArray) JSONSerializer.toJSON( postdata );			 
			 

			 
//			 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//	 	     conn=   ds.getConnection(); 
	         Class.forName(driver);
	         conn=DriverManager.getConnection(dbPath,dbUser,dbPwd);

	         state=conn.createStatement();
	         
	         if(json!=null && json.size()>0){
	        	 for(int i=0;i<json.size();i++){
	        		 JSONObject item=json.getJSONObject(i);
	        		 String content=(item.containsKey("content")?item.getString("content"):"");
	        		 String img_url=(item.containsKey("src")?item.getString("src"):"");
	        		 String url_detail=(item.containsKey("url")?item.getString("url"):"");
	        		 String time=(item.containsKey("time")?item.getString("time"):"");
	        		
	        		 
	        		 
	        		 
	        		 //System.out.println(item.getString(2)+","+item.getString(5)+","+url_detail);
	        		 
	        		 
	        		 if(img_url.length()==0){
	        			 String sql="insert into tbl_find8_joke(title,hyper_url,img_url,remark,indate,status,sort) values('"+content+"','"+url_detail+"','"+img_url+"','"+time+"',now(),'1','"+(i+1)+"')";
	        			 state.execute(sql);
	        		 }
	        		 
	        	 }
	        	 
	        	 
	         }

	         

	         
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 		 
		 
		 
		 
	 }	  	 
	  
	 public void process9_app(){
		 
		 final String   url="http://adres.myaora.net:81/api.php?TAG=GIONEE_FEATURES_HOME&INDEX_START=1&INDEX_SIZE=20";
		 Connection conn=null;
		 PreparedStatement PreState=null;
		 Statement state=null;
		 ResultSet rs=null;
		 try{
			 
        	 String postdata=new String(getdata(url, "", null),"UTF8");


        	 System.out.println(postdata);
        	 JSONObject json=(JSONObject) JSONSerializer.toJSON( postdata );			 
			 
        	 if(json.containsKey("ARRAY")){
        		 JSONArray jary=(JSONArray)json.getJSONArray("ARRAY");
			 
//			 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//	 	     conn=   ds.getConnection(); 
	         Class.forName(driver);
	         conn=DriverManager.getConnection(dbPath,dbUser,dbPwd);

	         state=conn.createStatement();
	         
	         if(jary!=null && jary.size()>0){
	        	 for(int i=0;i<jary.size();i++){
	        		 JSONArray item=jary.getJSONArray(i);
	        		 String softid=(item.getString(0));
	        		 String img_url=(item.getString(1));
	        		 String title=(item.getString(2));
	        		
	        		 
	        		 
	        		 String url_detail="http://m.anzhuoapk.com/mobile/soft/detail?vid=7&tid=11&fr=wificy&a=9&id="+softid;
	        		 //System.out.println(item.getString(2)+","+item.getString(5)+","+url_detail);
	        		 
	        		 
	        		 if(url_detail.length()>0){
	        			 String sql="insert into tbl_find9_app(title,description,hyper_url,img_url,indate,status,sort) values('"+title+"','"+softid+"','"+url_detail+"','"+img_url+"',now(),'1','"+(i+1)+"')";
	        			 state.execute(sql);
	        		 }
	        		 
	        	 }
	        	 
	        	 
	         }

	         
        	}
	         
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 		 
		 
		 
		 
	 }	  
  	 
	 
	  
	public byte[] postdata(String strurl,String data,HashMap header){
	        URL url = null;   
	        byte[] val=null;
	        HttpURLConnection httpConn = null;   
	  
	        InputStream in = null;   
	  
	        FileOutputStream out = null; 
	        
	        byte[] buf=new byte[1024*1]; 
	  
	        try {
	        	 url = new URL(strurl);   
	        	  
	        	 httpConn = (HttpURLConnection) url.openConnection();   
	        	 //methods that result in the connection being established).   
	        	 httpConn.setFollowRedirects(false);   
	        	 //inorder to disable the redirects   
	        	 httpConn.setInstanceFollowRedirects(false);
	        	 httpConn.setDoOutput(true);
	        	 

	        	 if(header!=null){
	        		 Iterator it= header.keySet().iterator();
	        		 while(it!=null && it.hasNext()){
	        			 String strkey=it.next().toString();
	        			 httpConn.setRequestProperty(strkey, header.get(strkey).toString());
	        			 //System.out.println(strkey+":"+header.get(strkey).toString());
	        		 }
	        	 }



	        	 
	        	 java.io.OutputStream os= httpConn.getOutputStream();
	        	 os.write(data.getBytes("UTF-8"));
	        	 os.flush();
	        
	        	 java.io.InputStream is= httpConn.getInputStream();
	        	 java.io.ByteArrayOutputStream bos=new ByteArrayOutputStream();
	        	 int rlength=0;
	        	 while((rlength=is.read(buf))!=-1){
	        		 bos.write(buf, 0, rlength);
	        	 }
	        	 

	        	 
	        	 
	        	 val=bos.toByteArray();
	        	
	        	 
	        	 Map mp=httpConn.getHeaderFields();
	        	 Object[] awob=mp.entrySet().toArray();
	        	 
	        	 for(int i=0;i<awob.length;i++){
	        		 System.out.println(awob[i].toString());
	        	 }
/*		        	 
	        	 Iterator rit= mp.keySet().iterator();
	        	 while(rit.hasNext()){
	        		 Object ob=rit.next();
	        		 if(ob!=null){
	        			 String key=ob.toString();
	        			 String keycontent=httpConn.getHeaderField(key).toString();
	        			 System.out.println("\t\r[header]\t"+key+":"+keycontent);
	        		 }
	        	 }
*/		        	 
	        	 
	        	 os.close(); os=null;
	        	 is.close(); is=null;
	        	 
	        	 
	        	 httpConn.disconnect();
	        	 httpConn=null;
	        	
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	  return val;
}	  
	  
	public byte[] getdata(String strurl,String data,HashMap header){
        URL url = null;   
        byte[] val=null;
        HttpURLConnection httpConn = null;   
  
        InputStream in = null;   
  
        FileOutputStream out = null; 
        
        byte[] buf=new byte[1024*1]; 
  
        try {
        	 url = new URL(strurl);   
        	  
        	 httpConn = (HttpURLConnection) url.openConnection();   
        	 //methods that result in the connection being established).   
        	 httpConn.setFollowRedirects(false);   
        	 //inorder to disable the redirects   
        	 httpConn.setInstanceFollowRedirects(false);
        	 httpConn.setDoOutput(false);
        	 

        	 if(header!=null){
        		 Iterator it= header.keySet().iterator();
        		 while(it!=null && it.hasNext()){
        			 String strkey=it.next().toString();
        			 httpConn.setRequestProperty(strkey, header.get(strkey).toString());
        			 //System.out.println(strkey+":"+header.get(strkey).toString());
        		 }
        	 }



        	 
        	 //java.io.OutputStream os= httpConn.getOutputStream();
        	 //os.write(data.getBytes("UTF-8"));
        	 //os.flush();
        
        	 java.io.InputStream is= httpConn.getInputStream();
        	 java.io.ByteArrayOutputStream bos=new ByteArrayOutputStream();
        	 int rlength=0;
        	 while((rlength=is.read(buf))!=-1){
        		 bos.write(buf, 0, rlength);
        	 }
        	 

        	 
        	 
        	 val=bos.toByteArray();
        	
        	 
        	 Map mp=httpConn.getHeaderFields();
        	 Object[] awob=mp.entrySet().toArray();
        	 
        	 for(int i=0;i<awob.length;i++){
        		 System.out.println(awob[i].toString());
        	 }
/*		        	 
        	 Iterator rit= mp.keySet().iterator();
        	 while(rit.hasNext()){
        		 Object ob=rit.next();
        		 if(ob!=null){
        			 String key=ob.toString();
        			 String keycontent=httpConn.getHeaderField(key).toString();
        			 System.out.println("\t\r[header]\t"+key+":"+keycontent);
        		 }
        	 }
*/		        	 
        	 
//        	 os.close(); os=null;
        	 is.close(); is=null;
        	 
        	 
        	 httpConn.disconnect();
        	 httpConn=null;
        	
        }catch(Exception e){
        	e.printStackTrace();
        }
  return val;
}	  	
	

}

