package com.aora.wifi.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.aora.wifi.action.AdCallBackAction;
import com.aora.wifi.dao.impl.SysAccountDaoImpl;

public class AD_YM extends BaseAction{

	private final static Logger logger = Logger.getLogger(AD_YM.class);
	
	private SysAccountDaoImpl sysAccountDao=new SysAccountDaoImpl();	




	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
    	 
    	 if(user.length()>0 && user.split(":").length==3){
    		 user=user.split(":")[1];
		 }    	 
    	 
    	 this.sysAccountDao.executedSql("insert into tbl_ad_logs(name,postdata,user,adname,pkg,score,platform,indate) values('有米',?,?,?,?,?,'Android',now())", new String[]{postdata,user,adname,pkg,String.valueOf(score)});
    	 
    	 this.sysAccountDao.executedSql("update tbl_reg_account set score=score+"+score+" where account=?", new String[]{user});
    	 
    	 response.setCharacterEncoding("utf-8");			
			response.getWriter().println("ok");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

	
}
