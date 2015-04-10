package com.aora.wifi.action;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;


import com.aora.wifi.dao.impl.SysAccountDaoImpl;


public class SystemLoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private SysAccountDaoImpl sysAccountDao;
	
	private String pctoolsURL;

	public SysAccountDaoImpl getSysAccountDao() {
		return sysAccountDao;
	}

	public void setSysAccountDao(SysAccountDaoImpl sysAccountDao) {
		this.sysAccountDao = sysAccountDao;
	}	
	
	


	public void doLogin() throws Exception{
		//request.setCharacterEncoding("UTF-8");
		String content="2";

			//if(request.getMethod().equalsIgnoreCase("post")){
				String username=this.getParameter(request, "username", "");
				String userpwd=this.getParameter(request, "userpwd", "");
				HttpSession session=request.getSession();
				

				
//				
//	
//			if(username.toUpperCase().startsWith("PCT")){
//				content=pctools_login("username="+username+"&userpwd="+userpwd);
//				
//			}else{
//				userpwd=userpwd.toUpperCase();	
//				Map mp=this.sysAccountDao.getAccount(username);
//				
//				if(mp!=null && mp.size()>0){
//					if (mp.get("passwordSrc").equals(userpwd)){
//						content="0"; //succ
//					}else{
//						content="1"; // pass not match
//					}
//					
//				}else{
//					content="2";  // user not found
//				}
//			}
//			response.setContentType("text/html;charset=UTF-8");		
			PrintWriter out=response.getWriter();
			
			out.write(content);
			out.flush();
			out.close();
			
			
		}
	
	
	
	
	
	private String pctools_login(String strdata){
		
		//System.out.println(pctoolsURL);
		byte content[]=null;
		String value="";
		try{
			content=postdata(pctoolsURL, strdata, null);
			value=new String(content,"UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(value.length()==0){
			
			return "2";
		}else{
			return value;
		}
		
		
		
	}
	
	public String homePage(){

			return SUCCESS;

	}
	
	public String doLogout(){
		

		
		return this.LOGIN;
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
        		// System.out.println(awob[i].toString());
        	 }

        	 
        	 os.close(); os=null;
        	 is.close(); is=null;
        	 
        	 
        	 httpConn.disconnect();
        	 httpConn=null;
        	
        }catch(Exception e){
        	e.printStackTrace();
        }
  return val;
}

	public String getPctoolsURL() {
		return pctoolsURL;
	}

	public void setPctoolsURL(String pctoolsURL) {
		this.pctoolsURL = pctoolsURL;
	}
	
	
}
