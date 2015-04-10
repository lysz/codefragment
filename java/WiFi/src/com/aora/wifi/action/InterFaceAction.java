package com.aora.wifi.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;



import com.aora.wifi.bean.*;
import com.aora.wifi.dao.impl.SysAccountDaoImpl;
import com.aora.wifi.util.testAction;





import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

import net.sf.json.JSONSerializer;

public class InterFaceAction extends BaseAction {

	//private static final Logger logger = LoggerFactory.getLogger(InterFaceAction.class);
	private final static Logger logger = Logger.getLogger(InterFaceAction.class);
	
	private SysAccountDaoImpl sysAccountDao;	
	
	public String _interface(){
		
		//String type=this.getParameter(request,"type","100");
		
		//request.setAttribute("info", "wifiinfo");
		long request_start=System.currentTimeMillis();

		String header="";
		JSONObject jsondata=null;
		String resultcode="";
		String postdata=null;
		
		try{
			
			String tmpheader=getCookie(request,WIFI_CLIENT_INFO);
			if(tmpheader!=null && tmpheader.length()>0){
				if(tmpheader!=null && tmpheader.split("\\|").length>=5){
					header=tmpheader;
				}
			}

			if(this.getStrkey()==null || this.getStrkey().length()==0){
				_init_DesKey();
			}	
			
			if (getParams().isEmpty())
			    initParams();
			
			
			String channel=(request.getHeader("CHANNEL")==null?"":request.getHeader("CHANNEL"));
			String real_ip=(request.getHeader("X-Real-IP")==null?"":request.getHeader("X-Real-IP"));
			String model=(request.getHeader("MODEL")==null?"":request.getHeader("MODEL"));
			boolean isEncription=false;
			String tmpencription=request.getHeader(WIFI_ENCRIPTION);
//			if(tmpencription!=null && tmpencription.equalsIgnoreCase("true")){
//				isEncription=true;
//			}
			if(tmpencription!=null && tmpencription.equalsIgnoreCase("false")){
				isEncription=false;
			}else{
				isEncription=true;
			}
			
			
			
			if(request.getMethod().equalsIgnoreCase("post")){
				 byte[] buf=new byte[1024*1];
	        	 java.io.InputStream is= request.getInputStream();
	        	 java.io.ByteArrayOutputStream bos=new ByteArrayOutputStream();
	        	 int rlength=0;
	        	 //System.out.println(is.available());
	        	 while((rlength=is.read(buf))!=-1){
	        		 bos.write(buf, 0, rlength);
	        	 }

	        	 postdata=bos.toString("UTF8");

	        	 if(isEncription && postdata.indexOf("\"function\":\"100\"")<0){
	        		 try{
	        			 postdata=this.desDecrypt(postdata);
	        		 }catch(Exception e){
	        			 e.printStackTrace();
	        			 throw e;
	        		 }
	        	 }
	        	 
	        	 
	        	 this.logger.info("request:"+postdata);
	        	 this.logger.info("request header:"+header +";"+channel+";"+model+";"+real_ip);
	        	// System.out.println(postdata);
	        	 
	        	 
	        	 
	        	 jsondata=(JSONObject) JSONSerializer.toJSON( postdata );

	        	 //System.out.println(jsondata.toString());
			}
			
			

		
		if(jsondata==null){	return this.ERROR;}
		String functionkey=jsondata.getString("function");
		String uidkey=(jsondata.getString("uid")==null?"":jsondata.getString("uid"));
		if(functionkey==null){return this.ERROR;}
		resultcode="func"+functionkey;
	
		
		requestBean obj=null;
		
		
		switch(Integer.parseInt(functionkey)){
			case 100:
				obj=new func100Req();	
				break;
			case 101:
				obj=new func101Req();	
				break;
			case 102:
				obj=new func102Req();	
				break;
			case 103:
				obj=new func103Req();	
				break;
			case 104:
				obj=new Func104Req();	
				break;
			case 105:
				obj=new Func105Req();	
				break;
			case 106:
				obj=new func106Req();	
				break;
//			case 107:
//				obj=new func107Req();	
//				break;
			case 108:
				obj=new func108Req();	
				break;
//			case 109:
//				obj=new func109Req();	
//				break;
			case 110:
				obj=new Func110Req();	
				break;
//			case 201:
//				obj=new func201Req();	
//				break;
//			case 202:
//				obj=new func202Req();	
//				break;
//			case 203:
//				obj=new func203Req();	
//				break;
//			case 204:
//				obj=new func204Req();	
//				break;
//			case 205:
//				obj=new func205Req();	
//				break;
//			case 206:
//				obj=new func206Req();	
//				break;
//			case 207:
//				obj=new func207Req();	
//				break;
//			case 208:
//				obj=new func208Req();	
//				break;
//			case 209:
//				obj=new func209Req();	
//				break;
//			case 210:
//				obj=new func210Req();	
//				break;
//			case 211:
//				obj=new func211Req();	
//				break;
//			case 212:
//				obj=new func212Req();	
//				break;
//			case 213:
//				obj=new func213Req();	
//				break;
//			case 214:
//				obj=new func214Req();	
//				break;
//			case 215:
//				obj=new func215Req();	
//				break;
//			case 216:
//				obj=new func216Req();	
//				break;				
//			case 217:
//				obj=new func217Req();	
//				break;
//			case 218:
//				obj=new func218Req();	
//				break;
//			case 219:
//				obj=new func219Req();	
//				break;
//			case 220:
//				obj=new func220Req();	
//				break;
//			case 221:
//				obj=new func221Req();	
//				break;
//			case 222:
//				obj=new func222Req();	
//				break;
//			case 223:
//				obj=new func223Req();	
//				break;
//			case 224:
//				obj=new func224Req();	
//				break;					
//			case 225:
//				obj=new func225Req();	
//				break;
//			case 301:
//				obj=new func301Req();	
//				break;
//			case 302:
//				obj=new func302Req();	
//				break;					
			default:
				resultcode=this.ERROR;
		}
		
//		if(functionkey.equals("311")){
//			
//			long response_start=System.currentTimeMillis();
//			respData(new testAction().func215(null));
//			this.logger.info("<"+(System.currentTimeMillis()-request_start)+","+(System.currentTimeMillis()-response_start)+">"+
//					"response 215:"+jsondata.toString());	
//			return null;
//		}
		
		obj.parse(jsondata);
		obj.parseHeader(header);
		obj.setChannel(channel);
		obj.setReal_ip(real_ip);
		request.setAttribute(REQUESTBEAN, obj);

		this.sysAccountDao.action_log((obj.getHeader_mac()==null?uidkey:obj.getHeader_mac()), (obj.getHeader_username()==null?"":obj.getHeader_username()), functionkey,tmpheader, postdata,channel);			
		//this.logger.debug("forword key:"+resultcode);
		
		obj.setRequest_start(request_start);
		obj.setRequest_forward(System.currentTimeMillis());
		request.setAttribute(REQUESTBEAN, obj);
		//return resultcode;
		
		}catch(Exception e){
			e.printStackTrace();
			jsondata=null;
			resultcode=this.ERROR;
			//return resultcode;
		}finally{

			try{jsondata=null;}catch(Exception e){}
			//try{request=null;}catch(Exception e){}
			//try{response=null;}catch(Exception e){}
			try{System.gc();}catch(Exception e){}			
			
		}
		return resultcode;
		
	}
	
	
	public void _init_DesKey(){
	
		try{
		    //给吴琼用来测试参数的正确性,在后台修改参数后,直接请求此方法, 用来删除内存的参数, 以便重新读取数据库参数. 测试完成立即删除此代码
		    /***************************************/
		   /*****/  getParams().clear();    /******/
		   /***************************************/
		    
			List ls=this.sysAccountDao.getData("select * from tbl_wifi_parameter where name='DES_KEY'", new String[]{});

			ByteArrayOutputStream bos = new ByteArrayOutputStream();   

			String tmp_key=this.getKEYDESC();
			if(ls!=null && ls.size()>0){
				Map mp=(Map)ls.get(0);
				tmp_key=((mp.get("value")==null && mp.get("value").toString().length()>=128) ?this.getKEYDESC():mp.get("value").toString());
				this.setKEYDESC(tmp_key);
			}
			
			int i=0;
			byte[] buf=new byte[8];
			i=tmp_key.charAt(0)%16;
			System.arraycopy(tmp_key.getBytes(), i*8, buf,0 , 8);
			bos.write(buf);

			i=tmp_key.charAt(1)%16;
			System.arraycopy(tmp_key.getBytes(), i*8, buf,0 , 8);
			bos.write(buf);
			
			i=tmp_key.charAt(2)%16;
			System.arraycopy(tmp_key.getBytes(), i*8, buf,0 , 8);
			bos.write(buf);		
			
			i=tmp_key.charAt(3)%16;
			System.arraycopy(tmp_key.getBytes(), i*8, buf,0 , 8);
			bos.write(buf);							
		
			this.setStrkey(bos.toString());
			
			this.logger.info("Init encrytion key:"+this.getStrkey());
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void _error() throws Exception{
		
		JSONObject obj=new JSONObject();
		
		obj.put("resultcode", "-98");
		obj.put("msg", "Invalid Request!");
		obj.put("data", new JSONArray());
		response.getWriter().write(obj.toString());
		
	}
	
	private final String WIFI_CLIENT_INFO="WIFI_CLIENT_INFO";
	


	public SysAccountDaoImpl getSysAccountDao() {
		return sysAccountDao;
	}


	public void setSysAccountDao(SysAccountDaoImpl sysAccountDao) {
		this.sysAccountDao = sysAccountDao;
	}
	
	
	private synchronized void initParams(){
	    try {
	        getParams().clear();
	        List<Map<String, Object>> ls = (List<Map<String, Object>>)sysAccountDao.getData("SELECT name, value FROM tbl_wifi_parameter", new String[]{});
	        
	        if (null == ls)
	            return;
	        
	        for (Map<String, Object> map : ls){
	        	
	            getParams().put(map.get("name").toString(), map.get("value"));
	            
//	            private static int SYS_REGISTER_SCORE=100;  //注册送U币
//	            private static int SYS_TRY_USE_TWICE=3;		//体验次数	
//	            private static int SYS_SIGN_SCORE_BASCE=5; //签到送的基础U币
//	            private static int SYS_SIGN_SCORE_PER_DAY=1;//签到送的递增U币
//	            private static int SYS_TRY_USE_MINUTE=10;   //体验时长
//	            private static int SYS_USE_SHARE_WIFI_SCORE=10; //用户第一次使用共享WIFI送U币给占领者
//	            private static int SYS_USE_SCORE_PER_DAY=100; //用户每天上网消费的U币
//	            private static int SYS_USE_MINUTE_PER_DAY=120; //用户每天消费获得上网时长	            
	            if (map.get("name").toString().equalsIgnoreCase("registe_present_coin")){
	            	try{
	            		int value= Integer.parseInt(map.get("value").toString());
	            		this.setSYS_REGISTER_SCORE(value);
	            	}catch(Exception er){}
	            }else if(map.get("name").toString().equalsIgnoreCase("experience_number")){
	            	try{
	            		int value= Integer.parseInt(map.get("value").toString());
	            		this.setSYS_TRY_USE_TWICE(value);
	            	}catch(Exception er){}
	            }else if(map.get("name").toString().equalsIgnoreCase("signin_present_coin")){
		            	try{
		            		int value= Integer.parseInt(map.get("value").toString());
		            		this.setSYS_SIGN_SCORE_BASCE(value);
		            	}catch(Exception er){}
	            }else if(map.get("name").toString().equalsIgnoreCase("xxxxxxxxxxx")){
	            	try{
	            		int value= Integer.parseInt(map.get("value").toString());
	            		this.setSYS_SIGN_SCORE_PER_DAY(value);
	            	}catch(Exception er){}		            	            	
	            }else if (map.get("name").toString().equalsIgnoreCase("experience_time")){
	            	try{
	            		int value= Integer.parseInt(map.get("value").toString());
	            		this.setSYS_TRY_USE_MINUTE(value);
	            	}catch(Exception er){}	     
	            }else if (map.get("name").toString().equalsIgnoreCase("use_share_wifi_present_coin_to_owner")){
	            	try{
	            		int value= Integer.parseInt(map.get("value").toString());
	            		this.setSYS_USE_SHARE_WIFI_SCORE(value);
	            	}catch(Exception er){}	   	            	
	            }else if (map.get("name").toString().equalsIgnoreCase("100_coin_change_to_time")){
	            	try{
	            		int value= Integer.parseInt(map.get("value").toString());
	            		this.setSYS_USE_MINUTE_PER_DAY(value);
	            	}catch(Exception er){}	            	
	            }	            
	            
	            
	        }
        } catch (Exception e) {
            logger.error("Initialize params failed.", e);
        }
	}
}
