package com.aora.wifi.action.interfaces;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.aora.wifi.servlet.BaseAction;
import com.aora.wifi.servlet.SysParameter;
import com.aora.wifi.util.DesTools;

public class BaseBean {


	
	private final static Logger logger = Logger.getLogger(BaseBean.class);
	

	
	protected String getParameter(HttpServletRequest request,String paraName, String default_val){
		try{
			if(request.getMethod().equalsIgnoreCase("post")){
				return request.getParameter(paraName)==null?default_val:request.getParameter(paraName).trim();
			}else{
				//pro
					return request.getParameter(paraName)==null?default_val:request.getParameter(paraName).trim();
				//testing
					//return request.getParameter(paraName)==null?default_val:new String(request.getParameter(paraName).getBytes("ISO8859-1"),"UTF-8").trim();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return default_val;
		}
		
	}
	
	

	protected void respData(String data,HttpServletRequest request,HttpServletResponse response){
		try{
			
//			try{
//				
////				requestBean obj=(requestBean)request.getAttribute(REQUESTBEAN);	
////				if(obj!=null && dao!=null){
////					String uid=(obj.getHeader_mac()==null?obj.getPara_uid():obj.getHeader_mac());
////					String fno=obj.getPara_function();
////					String resp=(data.length()>480?data.substring(0,480):data);
////					System.out.println(uid+","+fno);
////					String sql="update tbl_wifi_logs a,(select max(id) 'id' from tbl_wifi_logs  where indate>date_add(now(), interval -2 minute) and used_client_id=? and action=?  and response is null ) b set a.response=? where a.id=b.id";
////					dao.executedSql(sql, new String[]{uid,fno,resp});					
////					
////				}
//				
//				
//				
//			}catch(Exception err){
//			  err.printStackTrace();	
//			}
			
			//this.logger.info(data);
			String tmpencription=request.getHeader(SysParameter.WIFI_ENCRIPTION);
			if(tmpencription!=null && tmpencription.equalsIgnoreCase("false")){
					//data=data;
			}else{
				data=new DesTools().desEncrypt(data);
			}
//			if(tmpencription!=null && tmpencription.equalsIgnoreCase("true")){
//				data=this.desEncrypt(data);
//			}			
			response.setCharacterEncoding("utf-8");
			response.setHeader("content-type", "application/json");
			//response.getWriter().println(data);
			//response.getWriter().flush();
			InputStream   in_withcode   =   new  ByteArrayInputStream(data.getBytes("UTF-8"));
		       byte[]   buf   =   new   byte[4096]; 
		        for   (int   n;   (n   =   in_withcode.read(buf))   !=   -1;)   { 
		        	response.getOutputStream().write(buf,0,n);
					response.getOutputStream().flush();
		        } 

			

			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		

		}
		
	}

	protected void respData(byte[] data,HttpServletRequest request,HttpServletResponse response){
		try{
			//this.logger.info(data);
			response.setCharacterEncoding("utf-8");
			response.setHeader("content-type", "application/json");
			response.getOutputStream().write(data);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
		

		}
	

	}	
	
	
	protected void addCookie(HttpServletResponse response,String cookiename,String value){
		Cookie cookie = new Cookie(cookiename,value);
		// 2011-11315
		// 20120328 - fallback
		cookie.setMaxAge(-1);
		//cookie.setMaxAge(3600);	// set timeout to 1 hour
		//allow cookie to be shared within sub-domain 
		//cookie.setDomain("three.com.hk");
		//cookie.setPath("/");
		response.addCookie(cookie);
		
	}	

	public static String getCookie(HttpServletRequest request,String cookiename){
		String jumpUrl = "";
		Cookie[] cookie = request.getCookies();
		if (cookie != null){
			//System.out.println(cookie.length);
			for(int j=0;j<cookie.length;j++){
				//System.out.println(cookie[j].getName()+":"+cookiename+":"+cookie[j].getValue());
				if (cookie[j].getName().equals(cookiename)){
					jumpUrl = cookie[j].getValue();
					break;
				}
			}
		} 
		return jumpUrl;
	}
	
	
}
