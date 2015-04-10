package com.aora.wifi.action;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Map;

import org.apache.log4j.Logger;

import com.aora.wifi.dao.impl.SysAccountDaoImpl;

public class AdCallBackAction extends BaseAction {
	
	
	private final static Logger logger = Logger.getLogger(AdCallBackAction.class);
	
	private SysAccountDaoImpl sysAccountDao;	

	public void setSysAccountDao(SysAccountDaoImpl sysAccountDao) {
		this.sysAccountDao = sysAccountDao;
	}

	public void callback_ym() {
		
		this.logger.info("[AD_YM_Response] query:"+request.getQueryString());
        byte[] buf=new byte[1024*1]; 
		try{
			//System.out.println(request.getRealPath("/"));

			// String image="test.txt";
			// FileOutputStream fos=new java.io.FileOutputStream(request.getRealPath("/").replaceAll("\\\\", "/")+"/images/user/"+ image);
			// fos.write("sss".getBytes());
			// fos.close();
			
			
       	 java.io.InputStream is= request.getInputStream();
    	 java.io.ByteArrayOutputStream bos=new ByteArrayOutputStream();
    	 int rlength=0;
    	 while((rlength=is.read(buf))!=-1){
    		 bos.write(buf, 0, rlength);
    	 }
    	 

    	 
    	 
    	 byte[] val=bos.toByteArray();
    	 if(val!=null && val.length>0){
    		 logger.info(new String(val));
    	 }
    	
    	 String postdata=(request.getQueryString()==null?"":request.getQueryString());
    	 String user=(request.getParameter("user")==null?"":request.getParameter("user"));
    	 String adname=(request.getParameter("ad")==null?"":request.getParameter("ad"));
    	 String pkg=(request.getParameter("pkg")==null?"":request.getParameter("pkg"));
    	 int score=Integer.parseInt((request.getParameter("points")==null?"0":request.getParameter("points")));
    	 
    	 this.sysAccountDao.executedSql("insert into tbl_ad_logs(name,postdata,user,adname,pkg,score,platform,indate) values('有米',?,?,?,?,?,'Android',now())", new String[]{postdata,user,adname,pkg,String.valueOf(score)});
    	 
    	 this.sysAccountDao.executedSql("update tbl_user_account set score=score+"+score+" where account=?", new String[]{user});
    	 
    				
			response.getWriter().println("ok");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

public void callback_ym_ios() {
		
		this.logger.info("[AD_YM_Response] query:"+request.getQueryString());
        byte[] buf=new byte[1024*1]; 
		try{
			//System.out.println(request.getRealPath("/"));

			// String image="test.txt";
			// FileOutputStream fos=new java.io.FileOutputStream(request.getRealPath("/").replaceAll("\\\\", "/")+"/images/user/"+ image);
			// fos.write("sss".getBytes());
			// fos.close();
			
			
       	 java.io.InputStream is= request.getInputStream();
    	 java.io.ByteArrayOutputStream bos=new ByteArrayOutputStream();
    	 int rlength=0;
    	 while((rlength=is.read(buf))!=-1){
    		 bos.write(buf, 0, rlength);
    	 }
    	 

    	 
    	 
    	 byte[] val=bos.toByteArray();
    	 if(val!=null && val.length>0){
    		 logger.info(new String(val));
    	 }
    	
    	 String postdata=(request.getQueryString()==null?"":request.getQueryString());
    	 String user=(request.getParameter("user")==null?"":request.getParameter("user"));
    	 String adname=(request.getParameter("ad")==null?"":request.getParameter("ad"));
    	 String pkg=(request.getParameter("pkg")==null?"":request.getParameter("pkg"));
    	 int score=Integer.parseInt((request.getParameter("points")==null?"0":request.getParameter("points")));
    	 
    	 this.sysAccountDao.executedSql("insert into tbl_ad_logs(name,postdata,user,adname,pkg,score,platform,indate) values('有米',?,?,?,?,?,'IOS',now())", new String[]{postdata,user,adname,pkg,String.valueOf(score)});
    	 
    	 this.sysAccountDao.executedSql("update tbl_user_account set score=score+"+score+" where account=?", new String[]{user});
    	 
    				
			response.getWriter().println("ok");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void callback_dm(){
		this.logger.info("[AD_DM_Response] query:"+request.getQueryString());
        byte[] buf=new byte[1024*1]; 
		try{
			//System.out.println(request.getRealPath("/"));

			// String image="test.txt";
			// FileOutputStream fos=new java.io.FileOutputStream(request.getRealPath("/").replaceAll("\\\\", "/")+"/images/user/"+ image);
			// fos.write("sss".getBytes());
			// fos.close();
			
			
       	 java.io.InputStream is= request.getInputStream();
    	 java.io.ByteArrayOutputStream bos=new ByteArrayOutputStream();
    	 int rlength=0;
    	 while((rlength=is.read(buf))!=-1){
    		 bos.write(buf, 0, rlength);
    	 }
    	 
    	 String postdata=(request.getQueryString()==null?"":request.getQueryString());
    	 String user=(request.getParameter("user")==null?"":request.getParameter("user"));
    	 String adname=(request.getParameter("ad")==null?"":request.getParameter("ad"));
    	 String pkg=(request.getParameter("adid")==null?"":request.getParameter("adid"));
    	 int score=Integer.parseInt((request.getParameter("point")==null?"0":request.getParameter("point")));
    	 
    	 this.sysAccountDao.executedSql("insert into tbl_ad_logs(name,postdata,user,adname,pkg,score,platform,indate) values('多盟',?,?,?,?,?,'Android',now())", new String[]{postdata,user,adname,pkg,String.valueOf(score)});
    	 
    	 this.sysAccountDao.executedSql("update tbl_user_account set score=score+"+score+" where account=?", new String[]{user});    	
    	 
    	 
    	 byte[] val=bos.toByteArray();
    	 if(val!=null && val.length>0){
    		 logger.info(new String(val));
    	 }		
			response.getWriter().println("ok");
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public void callback_dm_ios(){
		this.logger.info("[AD_DM_Response] query:"+request.getQueryString());
        byte[] buf=new byte[1024*1]; 
		try{
			//System.out.println(request.getRealPath("/"));

			// String image="test.txt";
			// FileOutputStream fos=new java.io.FileOutputStream(request.getRealPath("/").replaceAll("\\\\", "/")+"/images/user/"+ image);
			// fos.write("sss".getBytes());
			// fos.close();
			
			
       	 java.io.InputStream is= request.getInputStream();
    	 java.io.ByteArrayOutputStream bos=new ByteArrayOutputStream();
    	 int rlength=0;
    	 while((rlength=is.read(buf))!=-1){
    		 bos.write(buf, 0, rlength);
    	 }
    	 
    	 String postdata=(request.getQueryString()==null?"":request.getQueryString());
    	 String user=(request.getParameter("user")==null?"":request.getParameter("user"));
    	 String adname=(request.getParameter("ad")==null?"":request.getParameter("ad"));
    	 String pkg=(request.getParameter("adid")==null?"":request.getParameter("adid"));
    	 int score=Integer.parseInt((request.getParameter("point")==null?"0":request.getParameter("point")));
    	 
    	 this.sysAccountDao.executedSql("insert into tbl_ad_logs(name,postdata,user,adname,pkg,score,platform,indate) values('多盟',?,?,?,?,?,'IOS',now())", new String[]{postdata,user,adname,pkg,String.valueOf(score)});
    	 
    	 this.sysAccountDao.executedSql("update tbl_user_account set score=score+"+score+" where account=?", new String[]{user});    	
    	 
    	 
    	 byte[] val=bos.toByteArray();
    	 if(val!=null && val.length>0){
    		 logger.info(new String(val));
    	 }		
			response.getWriter().println("ok");
		}catch(Exception e){
			e.printStackTrace();
		}		
	}	
	
	public void callback_wp(){
		this.logger.info("[AD_WP_Response] query:"+request.getQueryString());
        byte[] buf=new byte[1024*1]; 
		try{
			//System.out.println(request.getRealPath("/"));

			// String image="test.txt";
			// FileOutputStream fos=new java.io.FileOutputStream(request.getRealPath("/").replaceAll("\\\\", "/")+"/images/user/"+ image);
			// fos.write("sss".getBytes());
			// fos.close();
			

			//adv_id=6f4b85f55f7a18d7090e1150e8bebb74&app_id=893114dd2fadcfeeca22d744b07a8e2c&key=13798570258&udid=351554053592052&bill=50.0&points=50&activate_time=2014-07-30+11%3A28%3A27.0&order_id=AF55C1790FE30C1AFC4D9DC1A73438BE&secret_key=F67F193D58F144A2BDC142567EFE077C&random_code=07301128273128791550&status=1&ad_name=MM%E5%95%86%E5%9C%BA			
			
			
       	 java.io.InputStream is= request.getInputStream();
    	 java.io.ByteArrayOutputStream bos=new ByteArrayOutputStream();
    	 int rlength=0;
    	 while((rlength=is.read(buf))!=-1){
    		 bos.write(buf, 0, rlength);
    	 }
    	 

    	 
    	 
    	 byte[] val=bos.toByteArray();
    	 if(val!=null && val.length>0){
    		 logger.info(new String(val));
    	 }		
    	 
    	 String postdata=(request.getQueryString()==null?"":request.getQueryString());
    	 String user=(request.getParameter("key")==null?"":request.getParameter("key"));
    	 String adname=(request.getParameter("ad_name")==null?"":request.getParameter("ad_name"));
    	 String pkg=(request.getParameter("adv_id")==null?"":request.getParameter("adv_id"));
    	 int score=Integer.parseInt((request.getParameter("points")==null?"0":request.getParameter("points")));
    	 
    	 this.sysAccountDao.executedSql("insert into tbl_ad_logs(name,postdata,user,adname,pkg,score,platform,indate) values('万普',?,?,?,?,?,'Android',now())", new String[]{postdata,user,adname,pkg,String.valueOf(score)});
    	 
    	 this.sysAccountDao.executedSql("update tbl_user_account set score=score+"+score+" where account=?", new String[]{user});    	 
    	 
			response.getWriter().println("{\"message\":\"成功接收\",\"success\":true}");
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public void callback_wp_ios(){
		this.logger.info("[AD_WP_Response] query:"+request.getQueryString());
        byte[] buf=new byte[1024*1]; 
		try{
			//System.out.println(request.getRealPath("/"));

			// String image="test.txt";
			// FileOutputStream fos=new java.io.FileOutputStream(request.getRealPath("/").replaceAll("\\\\", "/")+"/images/user/"+ image);
			// fos.write("sss".getBytes());
			// fos.close();
			

			//adv_id=6f4b85f55f7a18d7090e1150e8bebb74&app_id=893114dd2fadcfeeca22d744b07a8e2c&key=13798570258&udid=351554053592052&bill=50.0&points=50&activate_time=2014-07-30+11%3A28%3A27.0&order_id=AF55C1790FE30C1AFC4D9DC1A73438BE&secret_key=F67F193D58F144A2BDC142567EFE077C&random_code=07301128273128791550&status=1&ad_name=MM%E5%95%86%E5%9C%BA			
			
			
       	 java.io.InputStream is= request.getInputStream();
    	 java.io.ByteArrayOutputStream bos=new ByteArrayOutputStream();
    	 int rlength=0;
    	 while((rlength=is.read(buf))!=-1){
    		 bos.write(buf, 0, rlength);
    	 }
    	 

    	 
    	 
    	 byte[] val=bos.toByteArray();
    	 if(val!=null && val.length>0){
    		 logger.info(new String(val));
    	 }		
    	 
    	 String postdata=(request.getQueryString()==null?"":request.getQueryString());
    	 String user=(request.getParameter("key")==null?"":request.getParameter("key"));
    	 String adname=(request.getParameter("ad_name")==null?"":request.getParameter("ad_name"));
    	 String pkg=(request.getParameter("adv_id")==null?"":request.getParameter("adv_id"));
    	 int score=Integer.parseInt((request.getParameter("points")==null?"0":request.getParameter("points")));
    	 
    	 this.sysAccountDao.executedSql("insert into tbl_ad_logs(name,postdata,user,adname,pkg,score,platform,indate) values('万普',?,?,?,?,?,'IOS',now())", new String[]{postdata,user,adname,pkg,String.valueOf(score)});
    	 
    	 this.sysAccountDao.executedSql("update tbl_user_account set score=score+"+score+" where account=?", new String[]{user});    	 
    	 
			response.getWriter().println("{\"message\":\"成功接收\",\"success\":true}");
		}catch(Exception e){
			e.printStackTrace();
		}		
	}	
	
}
