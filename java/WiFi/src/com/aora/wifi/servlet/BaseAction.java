package com.aora.wifi.servlet;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


import com.aora.wifi.bean.func103Req;
import com.aora.wifi.bean.requestBean;
import com.aora.wifi.dao.impl.SysAccountDaoImpl;
import com.aora.wifi.util.DesTools;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction  extends  HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
