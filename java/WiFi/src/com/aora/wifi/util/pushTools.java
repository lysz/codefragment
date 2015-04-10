package com.aora.wifi.util;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;  
import java.util.Date;
import java.util.HashMap;
import java.util.List;  
import java.util.Map;
import java.util.Properties;
  
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  

import sun.misc.BASE64Encoder;



public class pushTools {
	
	

	private String driver=null;
	private String dbPath=null;
	private String dbUser=null;
	private String dbPwd=null;
	
	private Map<String, Integer> params = new HashMap<String, Integer>();
	
	public pushTools(){
		try{loadProperties("/configuration/jdbc.properties");}catch(Exception e){e.printStackTrace();}
	}
	
	  private void loger(String s){
		  System.out.println(s);
	  }		
	 private  void loadProperties(String fileName) throws Exception
	  {

		 this.loger("\t\t"+(new Date()).toString());
		 //System.out.println("Reading configuration file " + fileName + "...");

	      Properties properties = new Properties();
	      properties.load(getClass().getResourceAsStream(fileName) );

		 
	      driver=properties.getProperty("jdbc.driverClassName"); loger("\t\t srcPath:"+driver);
	      dbPath=properties.getProperty("jdbc.url");  			 loger("\t\t dbPath:"+dbPath);
	      dbUser=properties.getProperty("jdbc.username");   	 loger("\t\t dbUser:"+dbUser);
	      dbPwd=properties.getProperty("jdbc.password");	 	 loger("\t\t dbPwd:"+dbPwd);

	  }	

//  public static final String PUSH_URL = "https://api.jpush.cn:443/sendmsg/sendmsg";   
    public static final String PUSH_URL_V2 = "http://api.jpush.cn:8800/sendmsg/sendmsg";
    public static final String PUSH_URL_V3 = "https://api.jpush.cn/v3/push";
      
    public  void pushMsg_V2(String msg) {  
        BasicNameValuePair name = new BasicNameValuePair("username", "test");  //用户名   
        BasicNameValuePair sendno = new BasicNameValuePair("sendno", "3621");  // 发送编号。由开发者自己维护，标识一次发送请求   
        BasicNameValuePair appkeys = new BasicNameValuePair("appkeys", "your appkeys");  // 待发送的应用程序(appKey)，只能填一个。   
        BasicNameValuePair receiver_type = new BasicNameValuePair("receiver_type", "4");    
        //验证串，用于校验发送的合法性。   
        BasicNameValuePair verification_code = new BasicNameValuePair("verification_code", getVerificationCode());  
        //发送消息的类型：1 通知 2 自定义   
        BasicNameValuePair msg_type = new BasicNameValuePair("msg_type", "1");  
        BasicNameValuePair msg_content = new BasicNameValuePair("msg_content", msg);  
        //目标用户终端手机的平台类型，如： android, ios 多个请使用逗号分隔。   
        BasicNameValuePair platform = new BasicNameValuePair("platform", "android");  
        List<BasicNameValuePair> datas = new ArrayList<BasicNameValuePair>();  
        datas.add(name);  
        datas.add(sendno);  
        datas.add(appkeys);  
        datas.add(receiver_type);  
        datas.add(verification_code);  
        datas.add(msg_type);  
        datas.add(msg_content);  
        datas.add(platform);  
        try {  
            HttpEntity entity = new UrlEncodedFormEntity(datas, "utf-8");  
            HttpPost post = new HttpPost(PUSH_URL_V2);  
            post.setEntity(entity);  
            HttpClient client = null;//ClientUtil.getNewHttpClient();  
            HttpResponse reponse = client.execute(post);  
            HttpEntity resEntity = reponse.getEntity();  
            System.out.println(EntityUtils.toString(resEntity));  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
          
    }  
    
    public  void pushMsg_V3(String msg) {  
		JSONObject jsondata=new JSONObject();
		
		
		
		
//platform
			JSONArray platform_obj=new JSONArray();
			platform_obj.add("android");
			//platform_obj.add("ios");
		jsondata.put("platform", platform_obj);

//audience
			JSONObject audience_obj=new JSONObject();
				JSONArray tag_ary=new JSONArray();
				//tag_ary.add("412");
				//tag_ary.add("samsung");
				tag_ary.add("ffffffffaa7dfc8fffffffff86099480");
				tag_ary.add("00000000085a40ffffffffffca4ca74a");
				//tag_ary.add("13798574509");
			audience_obj.put("tag", tag_ary);	
//				JSONArray tag_and_ary=new JSONArray();
//				tag_ary.add("13800138000");
//				tag_ary.add("iphone");
//			audience_obj.put("tag_and", tag_and_ary);
//				JSONArray alias_ary=new JSONArray();
//				alias_ary.add("13800138000");
//				alias_ary.add("iphone");
//			audience_obj.put("alias", alias_ary);
//				JSONArray registration_id_ary=new JSONArray();
//				registration_id_ary.add("13800138000");
//				registration_id_ary.add("iphone");
//			audience_obj.put("registration_id", registration_id_ary);			
		jsondata.put("audience", audience_obj);
		
//notification
			JSONObject notification_obj=new JSONObject();
				JSONObject android_obj=new JSONObject();
				android_obj.put("alert", "点击开始赚红包");
				android_obj.put("title", "免费蹭网还能赚红包！");
//				android_obj.put("builder_id", "2423423");
				JSONObject extras_obj=new JSONObject();
					extras_obj.put("news_id", "news_id");
					extras_obj.put("my_key", "my_key");
//				android_obj.put("extras", extras_obj);
					
			notification_obj.put("android", android_obj);		
		jsondata.put("notification", notification_obj);	
		
//message		
		JSONObject message_obj=new JSONObject();
		message_obj.put("msg_content", "message_content");
		message_obj.put("title", "message_title");
		message_obj.put("content_type", "text");
			JSONObject extras_obj1=new JSONObject();
			extras_obj1.put("news_id", "news_id");
			extras_obj1.put("my_key", "my_key");
		message_obj.put("extras", extras_obj1);	
	  //  jsondata.put("message", message_obj);	
	    
//options
	    JSONObject options_obj=new JSONObject();
	    options_obj.put("sendno", "message_content");
	    options_obj.put("time_to_live", "message_title");
	    options_obj.put("override_msg_id", "text");
	    options_obj.put("apns_production", "text");
	    options_obj.put("big_push_duration", "text");
	    //jsondata.put("options", options_obj);	    
	    

	    
	    
	    
System.out.println(jsondata.toString());		
    	
        try {  
           // HttpEntity entity = new UrlEncodedFormEntity(datas, "utf-8");
        	StringEntity entity = new StringEntity(jsondata.toString(), "utf-8");
            HttpPost post = new HttpPost(PUSH_URL_V3);
            ///test
            //String keys="8e7e4bb4d1071365c7e81e2d";//"fc96e08310507ad375360af0";
            //String apis="cb7144b61d8f561ce6d7a2f7";//"338d0fa08b79f897fe737eb6";
            ///production
            String keys="fc96e08310507ad375360af0";
            String apis="338d0fa08b79f897fe737eb6";//"";
            
            String auth=keys+":"+apis;
            post.setHeader("Authorization", "Basic "+ new BASE64Encoder().encode(auth.getBytes()));
            post.setEntity(entity);  
            HttpClient client =ClientUtil.getNewHttpClient();  
            HttpResponse reponse = client.execute(post);  
            HttpEntity resEntity = reponse.getEntity();  
            System.out.println(EntityUtils.toString(resEntity));  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
          
    }      
    
    
    
    public void get_rongyu_loginid() throws Exception {
		String App_Key="3argexb6rn3ae";
		String Nonce=String.valueOf(new java.util.Random(10).nextInt(10000000));
		String Timestamp=String.valueOf(System.currentTimeMillis()/1000);
		String Signature= sha1(App_Key+Nonce+Timestamp);

		String userid="userId=13800138000";
		String name="name=tester";
		String img_url="portraitUri=https%3A%2F%2Fwww.baidu.com%2Fimg%2Fbd_logo1.png";
		//userId=jlk456j5&name=Ironman&portraitUri=http%3A%2F%2Fabc.com%2Fmyportrait.jpg
		HashMap header=new HashMap();
		 header.put("App_Key", App_Key);
		 header.put("Nonce", Nonce);
		 header.put("Timestamp", Timestamp);
		 header.put("Signature", Signature);
		 
		 
		String data=userid+"&"+name+"&"+img_url;
		 
		 String posturl="https://api.cn.rong.io/user/getToken.json";
	    	
	        try {  
	           // HttpEntity entity = new UrlEncodedFormEntity(datas, "utf-8");
	        	StringEntity entity = new StringEntity(data, "utf-8");
	            HttpPost post = new HttpPost(posturl);

	            post.setHeader("App_Key",App_Key);
	            post.setHeader("Nonce",Nonce);
	            post.setHeader("Timestamp",Timestamp);
	            post.setHeader("Signature",Signature);
	            
	            post.setEntity(entity);  
	            HttpClient client =ClientUtil.getNewHttpClient();  
	            HttpResponse reponse = client.execute(post);  
	            HttpEntity resEntity = reponse.getEntity();  
	            System.out.println(EntityUtils.toString(resEntity));  
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	        } 
    }
    
    public  String sha1(String data) throws Exception {  

        MessageDigest md = MessageDigest.getInstance("SHA1");  

        md.update(data.getBytes());  

        StringBuffer buf = new StringBuffer();  

        byte[] bits = md.digest();  

        for(int i=0;i<bits.length;i++){  

            int a = bits[i];  

            if(a<0) a+=256;  

            if(a<16) buf.append("0");  

            buf.append(Integer.toHexString(a));  

        }  

        return buf.toString();  

    }  
    
    
    
    public  void sendpush(){
    	
		 Connection conn=null;
		 PreparedStatement PreState=null;
		 Statement state=null;
		 ResultSet rs=null;
			JSONObject jsondata=new JSONObject();		 
		 try{
			 
//			 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//	 	     conn=   ds.getConnection(); 
//	         Class.forName("com.mysql.jdbc.Driver");
//	         String dbPath="jdbc:mysql://120.31.131.166:3306/wifiapp2?useUnicode=true&characterEncoding=utf8";
//	         conn=DriverManager.getConnection(dbPath,"wifi","wifiapp");

	         Class.forName(driver);
	         conn=DriverManager.getConnection(dbPath,dbUser,dbPwd);			 
			 
	         state=conn.createStatement();
		     PreState=conn.prepareStatement("SELECT * FROM tmp2  where 1=1 and  process is null order by tmp1 ");
		     rs=PreState.executeQuery();
		     System.out.println(new Date().toLocaleString());
		     int i=0;
		     String tag="";
				JSONArray tag_ary=new JSONArray();
				//tag_ary.add("ffffffffaa7dfc8fffffffff86099480");
				String tags="";
				
		     while(rs.next()){	  
		    	 
		    	 tag=rs.getString("tmp1");
		    	 
		    	 if(rs.isLast()&& tag.length()>0){
		    		 i=19;
		    	 }
		    	 if(i>0 && ((i+1)%20==0) ){
				    		 jsondata=new JSONObject();
				    	 
				    	//platform
							JSONArray platform_obj=new JSONArray();
							platform_obj.add("android");
							//platform_obj.add("ios");
						jsondata.put("platform", platform_obj);
		
						//audience
							JSONObject audience_obj=new JSONObject();
								tag_ary.add(tag.replaceAll("-", ""));
		
							audience_obj.put("tag", tag_ary);	
						jsondata.put("audience", audience_obj);
						
						//notification
							JSONObject notification_obj=new JSONObject();
								JSONObject android_obj=new JSONObject();
								android_obj.put("alert", "点击开始赚红包");
								android_obj.put("title", "免费蹭网还能赚红包！");
							notification_obj.put("android", android_obj);		
						jsondata.put("notification", notification_obj);
						
						System.out.println(jsondata.toString());
						
						String result="";
						
						try{
				        	StringEntity entity = new StringEntity(jsondata.toString(), "utf-8");
				            HttpPost post = new HttpPost(PUSH_URL_V3);
				            ///test
				            //String keys="8e7e4bb4d1071365c7e81e2d";//"fc96e08310507ad375360af0";
				            //String apis="cb7144b61d8f561ce6d7a2f7";//"338d0fa08b79f897fe737eb6";
				            ///production
				            String keys="fc96e08310507ad375360af0";
				            String apis="338d0fa08b79f897fe737eb6";//"";
				            
				            String auth=keys+":"+apis;
				            post.setHeader("Authorization", "Basic "+ new BASE64Encoder().encode(auth.getBytes()));
				            post.setEntity(entity);  
				            HttpClient client =ClientUtil.getNewHttpClient();  
				            HttpResponse reponse = client.execute(post);  
				            HttpEntity resEntity = reponse.getEntity();  
				            result=EntityUtils.toString(resEntity);
						}catch(Exception e){
							e.printStackTrace();
						}
			            
			            System.out.println((i+1)+"***"+result);  				
						
			            if(tags.length()==0){
			    			 tags="'"+tag+"'";
			    		 }else{
			    			 tags+=tag+"'";
			    		 }
			            //System.out.println("update tmp3 set tmp2='"+result+"' ,process=now() where tmp1 in ("+tags+")");
			            state.execute("update tmp2 set tmp2='"+result+"' ,process=now() where tmp1 in ("+tags+")");
			            
			            tag_ary=new JSONArray();
			            tags="";
		    	 }else{
		    		 tag_ary.add(tag.replaceAll("-", ""));
		    	 
		    		 if(tags.length()==0){
		    			 tags="'"+tag+"','";
		    		 }else{
		    			 tags+=tag+"','";
		    		 }
		    	 }
		    	 
		    	i++;
//		    	if(i>22){
//		    		break;
//		    	}
							
		     }
		     
		     
		     
		     
		 }catch(Exception e ){
			 e.printStackTrace();
		 }
    	
    }
    
    
    
    private static String getVerificationCode() {  
    	  
        String username = "test";  //username 是开发者Portal帐户的登录帐户名   
        String password = "pasword";  
        int sendno = 3621;  
        int receiverType = 4;  
        String md5Password = "";//StringUtils.toMD5(password);; //password 是开发者Portal帐户的登录密码   
           
        String input = username + sendno + receiverType + md5Password;  
        String verificationCode = "";//StringUtils.toMD5(input);  
        return verificationCode;  
    }  
      
    public static void main(String[] args) {  
        String msg = "{\"n_title\":\"来点外卖\",\"n_content\":\"你好\"}";  
        //System.out.println(msg);  
        //PushMsgUtil.pushMsg_V3(msg);
        new pushTools().sendpush();
    }      
	
}
