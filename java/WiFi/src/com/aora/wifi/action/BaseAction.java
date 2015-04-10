package com.aora.wifi.action;

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
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected final String WIFI_ENCRIPTION="WIFI_ENCRIPTION";	
	
	private final static Logger logger = Logger.getLogger(BaseAction.class);
	
	Map<String, Object> session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	public String cue;
	

	public String getCue() {
		return cue;
	}

	public void setCue(String cue) {
		this.cue = cue;
	}

	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session=arg0;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
	} 

	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}

	
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
	
	

	protected void respData(String data){
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
			String tmpencription=request.getHeader(this.WIFI_ENCRIPTION);
			if(tmpencription!=null && tmpencription.equalsIgnoreCase("false")){
					//data=data;
			}else{
				data=this.desEncrypt(data);
			}
//			if(tmpencription!=null && tmpencription.equalsIgnoreCase("true")){
//				data=this.desEncrypt(data);
//			}			
			
			response.setHeader("content-type", "application/json");
			response.getWriter().println(data);
			response.getWriter().flush();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		
			try{data=null;}catch(Exception e){}
//			try{request=null;}catch(Exception e){}
//			try{response=null;}catch(Exception e){}
			try{System.gc();}catch(Exception e){}
		}
		
	}

	protected void respData(byte[] data){
		try{
			//this.logger.info(data);
			response.setHeader("content-type", "application/json");
			response.getOutputStream().write(data);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
		
			try{data=null;}catch(Exception e){}
//			try{request=null;}catch(Exception e){}
//			try{response=null;}catch(Exception e){}
			try{System.gc();}catch(Exception e){}
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
	
	
    private static BASE64Encoder base64 = new BASE64Encoder();
    private static byte[] myIV = { 0x32, 0x30, 0x31, 0x30, 0x30, 0x35, 0x31, 0x38 };
    //private static String strkey = "W9qPIzjaVGKUp7CKRk/qpCkg/SCMkQRu"; // 字节数必须是8的倍数
    //private  String strkey = "01234567890123456789012345678912";
    private static String strkey = "";
    
    private static String KEYDESC="$X53P;H8KGO:U,F>A>)2VBcX$U>%K)9dDQN7-b[)^K7E_X`P<5-7:2XdX_STI0@O>_-d@aIS`PZ(bH'@A/RHLI*K'JV/9:U1XGI[1;,<5P@EM)7Jcb(@'G,4bJd8CSMG";
    private static int SYS_REGISTER_SCORE=100;  //注册送U币
    private static int SYS_TRY_USE_TWICE=3;		//体验次数	
    private static int SYS_SIGN_SCORE_BASCE=5; //签到送的基础U币
    private static int SYS_SIGN_SCORE_PER_DAY=1;//签到送的递增U币
    private static int SYS_TRY_USE_MINUTE=10;   //体验时长
    private static int SYS_USE_SHARE_WIFI_SCORE=10; //用户第一次使用共享WIFI送U币给占领者
    private static int SYS_USE_SCORE_PER_DAY=100; //用户每天上网消费的U币
    private static int SYS_USE_MINUTE_PER_DAY=120; //用户每天消费获得上网时长
    
    protected  String desEncrypt(String input) throws Exception 
    { 
        
        BASE64Decoder base64d = new BASE64Decoder();
        DESedeKeySpec p8ksp = null;
        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(strkey));
        Key key = null;
        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);
        
        input = padding(input);
        byte[] plainBytes = (byte[])null;
        Cipher cipher = null;
        byte[] cipherText = (byte[])null;
        
        plainBytes = input.getBytes("UTF8");
        cipher = Cipher.getInstance("DESede/CBC/NoPadding");
        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
        IvParameterSpec ivspec = new IvParameterSpec(myIV);
        cipher.init(1, myKey, ivspec);
       
        cipherText = cipher.doFinal(plainBytes);
        return removeBR(base64.encode(cipherText));
        
    } 
     
    protected  String desDecrypt(String cipherText) throws Exception 
    { 
        
        BASE64Decoder base64d = new BASE64Decoder();
        DESedeKeySpec p8ksp = null;
        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(strkey));
        Key key = null;
        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);
        
        Cipher cipher = null;
        byte[] inPut = base64d.decodeBuffer(cipherText);
        cipher = Cipher.getInstance("DESede/CBC/NoPadding");
        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
        IvParameterSpec ivspec = new IvParameterSpec(myIV);
        cipher.init(2, myKey, ivspec);
        byte[] output = removePadding(cipher.doFinal(inPut));

        return new String(output, "UTF8");
        
    } 
     	
	
    private  String removeBR(String str) {
        StringBuffer sf = new StringBuffer(str);

        for (int i = 0; i < sf.length(); ++i)
        {
          if (sf.charAt(i) == '\n')
          {
            sf = sf.deleteCharAt(i);
          }
        }
        for (int i = 0; i < sf.length(); ++i)
          if (sf.charAt(i) == '\r')
          {
            sf = sf.deleteCharAt(i);
          }

        return sf.toString();
      }

      private  String padding(String str)
      {
        byte[] oldByteArray;
        try
        {
            oldByteArray = str.getBytes("UTF8");
            int numberToPad = 8 - oldByteArray.length % 8;
            byte[] newByteArray = new byte[oldByteArray.length + numberToPad];
            System.arraycopy(oldByteArray, 0, newByteArray, 0, oldByteArray.length);
            for (int i = oldByteArray.length; i < newByteArray.length; ++i)
            {
                newByteArray[i] = 0;
            }
            return new String(newByteArray, "UTF8");
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println("Crypter.padding UnsupportedEncodingException"); 
        }
        return null;
      }

      private  byte[] removePadding(byte[] oldByteArray)
      {
        int numberPaded = 0;
        for (int i = oldByteArray.length; i >= 0; --i)
        {
          if (oldByteArray[(i - 1)] != 0)
          {
            numberPaded = oldByteArray.length - i;
            break;
          }
        }

        byte[] newByteArray = new byte[oldByteArray.length - numberPaded];
        System.arraycopy(oldByteArray, 0, newByteArray, 0, newByteArray.length);

        return newByteArray;
      }    
    

      
      
	
	public String getStrkey() {
		return strkey;
	}

	public void setStrkey(String strkey) {
		this.strkey = strkey;
	}

	public String getKEYDESC() {
		return KEYDESC;
	}

	public void setKEYDESC(String keydesc) {
		KEYDESC = keydesc;
	}

	

	public static int getSYS_REGISTER_SCORE() {
		return SYS_REGISTER_SCORE;
	}

	public static void setSYS_REGISTER_SCORE(int sys_register_score) {
		SYS_REGISTER_SCORE = sys_register_score;
	}

	public static int getSYS_TRY_USE_TWICE() {
		return SYS_TRY_USE_TWICE;
	}

	public static void setSYS_TRY_USE_TWICE(int sys_try_use_twice) {
		SYS_TRY_USE_TWICE = sys_try_use_twice;
	}


	
	
	public static int getSYS_SIGN_SCORE_BASCE() {
		return SYS_SIGN_SCORE_BASCE;
	}

	public static void setSYS_SIGN_SCORE_BASCE(int sys_sign_score_basce) {
		SYS_SIGN_SCORE_BASCE = sys_sign_score_basce;
	}

	public static int getSYS_SIGN_SCORE_PER_DAY() {
		return SYS_SIGN_SCORE_PER_DAY;
	}

	public static void setSYS_SIGN_SCORE_PER_DAY(int sys_sign_score_per_day) {
		SYS_SIGN_SCORE_PER_DAY = sys_sign_score_per_day;
	}

	public static int getSYS_TRY_USE_MINUTE() {
		return SYS_TRY_USE_MINUTE;
	}

	public static void setSYS_TRY_USE_MINUTE(int sys_try_use_minute) {
		SYS_TRY_USE_MINUTE = sys_try_use_minute;
	}

	public static int getSYS_USE_SHARE_WIFI_SCORE() {
		return SYS_USE_SHARE_WIFI_SCORE;
	}

	public static void setSYS_USE_SHARE_WIFI_SCORE(int sys_use_share_wifi_score) {
		SYS_USE_SHARE_WIFI_SCORE = sys_use_share_wifi_score;
	}

	public static int getSYS_USE_SCORE_PER_DAY() {
		return SYS_USE_SCORE_PER_DAY;
	}

	public static void setSYS_USE_SCORE_PER_DAY(int sys_use_score_per_day) {
		SYS_USE_SCORE_PER_DAY = sys_use_score_per_day;
	}

	public static int getSYS_USE_MINUTE_PER_DAY() {
		return SYS_USE_MINUTE_PER_DAY;
	}

	public static void setSYS_USE_MINUTE_PER_DAY(int sys_use_minute_per_day) {
		SYS_USE_MINUTE_PER_DAY = sys_use_minute_per_day;
	}


	protected final static String ACCOUNTINFO = "accountInfo";
	protected final static String FALE="fale";

	protected final static String REQUESTBEAN="RequestBean";

	//存放U币相关参数
	private  static Map<String, Object> params = new HashMap<String, Object>();

	protected static Map<String, Object> getParams() {
		
        return params;
    }
	
}
