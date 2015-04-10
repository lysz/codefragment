package com.aora.wifi.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.htmlparser.parserapplications.StringExtractor;
import org.htmlparser.util.ParserException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class WiFiCrackUtil {
	private static Logger logger = Logger.getLogger(WiFiCrackUtil.class);

	private String driver=null;
	private String dbPath=null;
	private String dbUser=null;
	private String dbPwd=null;
	//http://www.gpsspg.com/ajax/maps_get.aspx?lat=22.541817&lng=114.038079&type=0
	
	public WiFiCrackUtil(){
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
	 
	public static JSONObject decodeHtmlResopnse(byte[] data)throws Exception {
		if(data==null || data.length % 8 != 0) throw new IllegalArgumentException();
		
		data = cipherData(Cipher.DECRYPT_MODE, data);
		//return new JSONObject(new String(data, "UTF-8"));
		return (JSONObject) JSONSerializer.toJSON( new String(data, "UTF-8") );
	}
	
	private static byte[] encodeHtmlParamS(Map map){
		StringBuilder localStringBuilder = new StringBuilder();
		String paramEncodeType = "UTF-8";
		byte[] arrayOfByte = null;
		try {
			Iterator localIterator = map.entrySet().iterator();
			while (localIterator.hasNext()) {
				Map.Entry localEntry = (Map.Entry) localIterator.next();
				localStringBuilder.append(URLEncoder.encode(
						(String) localEntry.getKey(), paramEncodeType));
				localStringBuilder.append('=');
				localStringBuilder.append(URLEncoder.encode(
						(String) localEntry.getValue(), paramEncodeType));
				localStringBuilder.append('&');
			}
			logger.info("tag============" + localStringBuilder.toString());
			arrayOfByte = localStringBuilder.toString().getBytes(paramEncodeType);
		} catch (Exception localUnsupportedEncodingException) {
			throw new RuntimeException(
					"Encoding not supported: " + paramEncodeType,
					localUnsupportedEncodingException);
		}
		return arrayOfByte;
	}
	
	private static byte[] cipherData(int mode, byte[] input)
			throws IllegalArgumentException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		if (mode != Cipher.ENCRYPT_MODE && mode != Cipher.DECRYPT_MODE)
			throw new IllegalArgumentException();
		byte[] password = {(byte)0x78,(byte)0x11,(byte)0x5b,(byte)0x40,(byte)0x52,(byte)0x2a,(byte)0x34,(byte)0x32,(byte)0x6f,(byte)0x6d,
				(byte)0x5d,(byte)0x5c,(byte)0x4e,(byte)0x0a,(byte)0x0f,(byte)0x20};
		SecretKeySpec localSecretKeySpec = new SecretKeySpec(password, "AES");
		Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		localCipher.init(mode, localSecretKeySpec,
				new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 }));
		byte[] out = localCipher.doFinal(input);
		return out;
	}
	
	
	public  JSONObject CrackData(List data){
		JSONObject value=null;
		try{
			List params = new ArrayList();
			JSONArray jsons = new JSONArray();

			for (int i = 0; i < data.size(); i++) {
				String [] tmpstr=(String [])data.get(i);
				if(tmpstr!=null && tmpstr.length>=3){
					Map map = new HashMap<String, String>();
					String bssid =tmpstr[0];
					String ssid = tmpstr[1];
					String capability = tmpstr[2];
					if (bssid == null || ssid == null || capability == null)
						throw new IllegalArgumentException();
					JSONArray obj = new JSONArray();
					obj.add(bssid);
					obj.add(ssid);
					obj.add(capability);
					jsons.add(obj);
				}
			}
			Map map = new HashMap<String, String>();
			map.put("info", jsons.toString());
			map.put("udid", "23407debb23b43b3bcb274a37a611bc30c8f5204");
			map.put("versionName", "2.0.0");
			map.put("versionCode", "8");
			map.put("apiVersion", "1");			
			
			byte[] bufdata = encodeHtmlParamS(map);
			bufdata = cipherData(Cipher.ENCRYPT_MODE, bufdata);
			
			
			byte[] body =bufdata;
			
			//byte[] body = WiFiCrackUtil.encodeWifiInfo(params);

			//注意设置参数
			HttpURLConnection conn = (HttpURLConnection)(new URL("http://autowifi.wandoujia.com/api/get").openConnection());
			conn.setRequestMethod("POST");// 提交模式
			conn.setRequestProperty("user-agent", "autowifi");
			conn.setDoOutput(true);// 是否输入参数
			conn.getOutputStream().write(body);// 输入参数
			InputStream inStream=conn.getInputStream();
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			int len=-1;
			byte[] buf = new byte[1024];
			while((len=inStream.read(buf)) != -1){
				bao.write(buf, 0, len);
			}
			
			byte[] read = bao.toByteArray();
			
			value = WiFiCrackUtil.decodeHtmlResopnse(read);			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return value;
	}
	
	
	
	
	
	 public void process(){
		 
		 Connection conn=null;
		 PreparedStatement PreState=null;
		 PreparedStatement state=null;
		 ResultSet rs=null;
		 try{
			 
//			 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//	 	     conn=   ds.getConnection(); 
	         Class.forName(driver);
	         dbPath="jdbc:mysql://127.0.0.1:3306/wifiapp?useUnicode=true&characterEncoding=utf8";
	         conn=DriverManager.getConnection(dbPath,dbUser,dbPwd);

	         state=conn.prepareStatement("update wifilog_sum set processdate=now(),password=?,remark=?,city=?  where id=?");
		     PreState=conn.prepareStatement("SELECT * FROM wifilog_sum  where processdate is null  order by id  limit 8000");
		     rs=PreState.executeQuery();
		     while(rs.next()){

		    	 
		    	 int id=rs.getInt("id");
		    	 String decrypt_data="";
		    	 String password="";
		    	 boolean result=false;
		    	 String mac=(rs.getString("mac")==null?"":rs.getString("mac"));
		    	 String name=(rs.getString("name")==null?"":rs.getString("name"));
		    	 String encrypt_method=(rs.getString("encrypt_method")==null?"":rs.getString("encrypt_method"));
		    	 String city=(rs.getString("city")==null?"":rs.getString("city"));
		    	 String rip=(rs.getString("rip")==null?"":rs.getString("rip"));
		 		try{
			 		List ls=new ArrayList();
					ls.add(new String[]{ mac, name, encrypt_method});	    	 
			 		JSONObject json =CrackData(ls);
		 			
			 		if(json!=null){
			 			decrypt_data=json.toString();
			 			if( json.getJSONArray("password").size()>0){
			 				password=json.getJSONArray("password").getJSONArray(0).get(2).toString();
			 				if(password.length()>30){
			 					password="";
			 				}
			 			}
			 		}
			 		if(city.length()==0 && rip.length()>0){
			 			city=this.test1(rip);
			 		}
			 		decrypt_data=decrypt_data.replaceAll("'", "`");
			 		if(decrypt_data.length()>290){
			 			decrypt_data=decrypt_data.substring(0,290);
			 		}
			 		
			 		
			 	
			 		//System.out.println("update tbl_wifi_cracker_data set processdate=now(),password='"+password+"',remark='"+decrypt_data+"',city='"+city+"'  where id="+id);
			 		state.setString(1, password);
			 		state.setString(2, decrypt_data);
			 		state.setString(3, city);
			 		state.setInt(4, id);
			 		state.execute();
			 		
		 		}catch(Exception e){
		 			e.printStackTrace();
		 		}
//		    	
//		    	 
//	    	 	if(result){
//	    	 		
//	    			 sql="update tbl_wifi_account set status=1,action_twice=0,action_date=now(),expiry_date=now(),cookies='',sessionid='',postdata='',used_login_id='' ,used_client_id='',logout_url='' where id="+id;
//	    		 }else{
//	    			 sql="update tbl_wifi_account set status=1,action_twice=0,action_date=now(),expiry_date=now(),cookies='',sessionid='',postdata='',used_login_id='' ,used_client_id='',logout_url='' where id="+id;
//	    		 }
	    	
	         
	         

		     }
		     
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			try{state.close();state=null;}catch(Exception e){}
		 	try{PreState.close();PreState=null;}catch(Exception e){}
		 	try{conn.close();conn=null;}catch(Exception e){} 
		 }
		 		 
		 
	 }	
	
	 public void process1(){
		 
		 Connection conn=null;
		 PreparedStatement PreState=null;
		 PreparedStatement state=null;
		 ResultSet rs=null;
		 try{
			 
//			 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//	 	     conn=   ds.getConnection(); 
	         Class.forName(driver);
	         dbPath="jdbc:mysql://192.168.0.216:3306/wifiapp?useUnicode=true&characterEncoding=utf8";
	         conn=DriverManager.getConnection(dbPath,"root","123");

	         state=conn.prepareStatement("update tmp4 set process=now(),tmp3=?  where id=?");
		     PreState=conn.prepareStatement("SELECT * FROM tmp4  where process is null  order by tmp1  limit 30000");
		     rs=PreState.executeQuery();
		     while(rs.next()){

		    	 
		    	 String id=(rs.getString("id")==null?"":rs.getString("id"));
		    	 String decrypt_data="";
		    	 String password="";
		    	 boolean result=false;
		    	 String mac=(rs.getString("tmp2")==null?"":rs.getString("tmp2"));
		    	 String name=(rs.getString("tmp1")==null?"":rs.getString("tmp1"));
		    	 String encrypt_method="";
		    	 String city="";
		    	 String rip="";	         
		 		try{
			 		List ls=new ArrayList();
					ls.add(new String[]{ mac, name, encrypt_method});	    	 
			 		JSONObject json =CrackData(ls);
		 			
			 		if(json!=null){
			 			decrypt_data=json.toString();
			 			if( json.getJSONArray("password").size()>0){
			 				password=json.getJSONArray("password").getJSONArray(0).get(2).toString();
			 				if(password.length()>30){
			 					password="";
			 				}
			 			}
			 		}
//			 		if(city.length()==0 && rip.length()>0){
//			 			city=this.test1(rip);
//			 		}
			 		decrypt_data=decrypt_data.replaceAll("'", "`");
			 		if(decrypt_data.length()>290){
			 			decrypt_data=decrypt_data.substring(0,290);
			 		}
			 		
			 		
			 	
			 		System.out.println("update tmp4 set process=now(),tmp3='"+password+"'  where id='"+id+"'");
			 		state.setString(1, password);
			 		state.setString(2, id);
//			 		state.setString(3, city);
//			 		state.setInt(4, id);
			 		System.out.println(state.executeUpdate());
			 		
		 		}catch(Exception e){
		 			e.printStackTrace();
		 		}
//		    	
//		    	 
//	    	 	if(result){
//	    	 		
//	    			 sql="update tbl_wifi_account set status=1,action_twice=0,action_date=now(),expiry_date=now(),cookies='',sessionid='',postdata='',used_login_id='' ,used_client_id='',logout_url='' where id="+id;
//	    		 }else{
//	    			 sql="update tbl_wifi_account set status=1,action_twice=0,action_date=now(),expiry_date=now(),cookies='',sessionid='',postdata='',used_login_id='' ,used_client_id='',logout_url='' where id="+id;
//	    		 }
	    	
	         
	         

		     }
		     
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			try{state.close();state=null;}catch(Exception e){}
		 	try{PreState.close();PreState=null;}catch(Exception e){}
		 	try{conn.close();conn=null;}catch(Exception e){} 
		 }
		 		 
		 
	 }		 
	 
	 
		public String  test1(String ip){
			String city="";
			String org_data="";
			try {
				
//				StringExtractor se = new StringExtractor("http://www.oeeee.com/");
				StringExtractor se = new StringExtractor("http://www.ip138.com/ips1388.asp?ip="+ip+"&action=2");
				
				String content = se.extractStrings(false);
//				String contentWithLinks = se.extractStrings(true);
//				System.out.println(content);
//				System.out.println("================================================");
//				System.out.println(contentWithLinks);

				if(content.indexOf("本站主数据：")>0){
					city=content.substring(content.indexOf("本站主数据：")+6,content.indexOf(" ", content.indexOf("本站主数据：")+6));
					org_data=city;
					if(city.length()>0&& city.indexOf("省")>0){
						city=city.substring(city.indexOf("省")+1);
					}
					
					if(city.length()>0&& city.indexOf("区")>0){
						if(city.indexOf("区")>0 && city.indexOf("市")>0  && city.indexOf("区")> city.indexOf("市") ){
							city=city.substring(0,city.indexOf("市"));
						}else if(city.indexOf("区")>0 && city.indexOf("市")>0  && city.indexOf("市")> city.indexOf("区") ){
							city=city.substring(city.indexOf("区")+1);
						}						
						
					}
					
				}
				
				
			} catch (Exception e) {
				System.out.println(ip);
				e.printStackTrace();
			}
			System.out.println(org_data+"==>"+city);
			return city;
		}	 
	 
	 public void process_city(){
		 
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
		     PreState=conn.prepareStatement("SELECT * FROM tbl_wifi_cracker_data t where length(password)>0 and city!='null' and length(city)=0 and length(rip)>0 order by id  limit 25000");
		     rs=PreState.executeQuery();
		     while(rs.next()){

		    	 
		    	 int id=rs.getInt("id");
		    	 String decrypt_data="";
		    	 String password="";
		    	 String city="";
		    	 boolean result=false;
		    	 String rip=(rs.getString("rip")==null?"":rs.getString("rip"));
	    	 
		 		try{
		 			if(rip.length()>0){
		 				city=this.test1(rip);
		 				state.executeUpdate("update tbl_wifi_cracker_data set city='"+city+"'  where id="+id);
		 			}
		 		}catch(Exception e){
		 			e.printStackTrace();
		 		}
//		    	
//		    	 
//	    	 	if(result){
//	    	 		
//	    			 sql="update tbl_wifi_account set status=1,action_twice=0,action_date=now(),expiry_date=now(),cookies='',sessionid='',postdata='',used_login_id='' ,used_client_id='',logout_url='' where id="+id;
//	    		 }else{
//	    			 sql="update tbl_wifi_account set status=1,action_twice=0,action_date=now(),expiry_date=now(),cookies='',sessionid='',postdata='',used_login_id='' ,used_client_id='',logout_url='' where id="+id;
//	    		 }
	    	
	         
	         

		     }
		     
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			try{state.close();state=null;}catch(Exception e){}
		 	try{PreState.close();PreState=null;}catch(Exception e){}
		 	try{conn.close();conn=null;}catch(Exception e){} 
		 }
		 		 
		 
	 }		 
	 
	
	public static void main(String[] args) {
		
		WiFiCrackUtil obj=new WiFiCrackUtil();
//		List ls=new ArrayList();
//		ls.add(new String[]{ "9c:21:6a:3b:96:78", "luoyuxuan7951", ""});
//		JSONObject json =obj.CrackData(ls);
//		if(json!=null){
//			System.out.println(json.toString());
//			System.out.println(json.getJSONArray("password").getJSONArray(0).get(2));
//		}
		
		obj.process();
		//obj.process1();
//		obj.process_city();
		//System.out.println(obj.test1("223.104.4.38"));
		
		
		
		// TODO Auto-generated method stub
//		String[] paramsArr = {"c0:61:18:d6:77:d8", "TP-LINK_jisheng", "[WPA-PSK-CCMP][WPA2-PSK-CCMP][ESS]",
//		"6c:e8:73:96:68:e0", "8A-2", "[WPA-PSK-TKIP+CCMP][WPA2-PSK-TKIP+CCMP][WPS][ESS]",
//		"d8:42:ac:41:5e:24", "Feixun_415E24", "[WPA-PSK-TKIP+CCMP][WPA2-PSK-TKIP+CCMP][WPS][ESS]"};
//		String[] paramsArr = { "c0:61:18:d6:77:d8", "TP-LINK_jisheng", "",
//				"6c:e8:73:96:68:e0", "8A-2", "", "d8:42:ac:41:5e:24",
//				"Feixun_415E24", "" };
//		

	}
	
	
}
