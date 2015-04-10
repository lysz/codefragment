package com.aora.wifi.action.interfaces;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import com.aora.wifi.action.BaseAction;
import com.aora.wifi.bean.*;

import com.aora.wifi.dao.impl.SysAccountDaoImpl;
import com.aora.wifi.dao.impl.SysSharedAccountDaoImpl;
import com.aora.wifi.servlet.SysParameter;
import com.aora.wifi.tools.LatitudeLontitudeUtil;
import com.aora.wifi.tools.LogoutUtil;
import com.aora.wifi.tools.PropertyInfo;
import com.aora.wifi.tools.LatitudeLontitudeUtil.Location;
import com.aora.wifi.util.DesTools;
import com.aora.wifi.util.Tools;
import com.aora.wifi.util.cachectl;
import com.aora.wifi.tools.PropertyInfo;

public class func1xxAction extends BaseBean  {

	private static SysAccountDaoImpl sysAccountDao=new SysAccountDaoImpl();
	
	private static SysSharedAccountDaoImpl sysSharedAccountDao=new SysSharedAccountDaoImpl();
	
	private final static Logger logger = Logger.getLogger(func1xxAction.class);
	
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public func1xxAction(HttpServletRequest request,HttpServletResponse response){
		this.request=request;
		this.response=response;
	}
	
	public void process(requestBean obj) throws Exception{
		switch(Integer.parseInt(obj.getPara_function())){
		case 100:
			this.func100();
			break;
		case 101:
			this.func101();	
			break;
		case 102:
			this.func102();	
			break;
		case 103:
			this.func103();		
			break;
		case 104:
			this.func104();	
			break;
		case 105:
			this.func105();	
			break;
		case 106:
			this.func106();		
			break;
		case 107:
			//this.func107();		
			break;
		case 108:
			this.func108();		
			break;
		case 109:
			//this.func109();		
			break;
		case 110:
			this.func110();	
			break;
		case 111:
			this.func111();	
			break;
		case 112:
			this.func112();	
			break;
		case 113:
			this.func113();	
			break;
		case 114:
			this.func114();	
			break;			
		}

		
	}
	
	public void func100() throws Exception{
 	
		func100Req obj=(func100Req)request.getAttribute(SysParameter.REQUESTBEAN);
		JSONObject jsondata=new JSONObject();
		String resultcode="0";
		String msg="success";
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("key", SysParameter.getInstatnce().getKEYDESC());
		jsondata.put("function", "100");
		
		
		List ls=this.sysAccountDao.getData("select * from tbl_user_account where uid=? limit 1",new String[]{obj.getUID()});
		if(ls==null ||(ls!=null && ls.isEmpty())){
			String sql="insert into tbl_user_account(uid,status,score,signdays,indate,remark,use_minute,channel) values(?,0,0,0,now(),?,"+SysParameter.getInstatnce().getSYS_USE_MINUTE_PER_DAY()+",'"+obj.getChannel()+"')";
			this.sysAccountDao.executedSql(sql, new String[]{obj.getUID(),obj.getHeader_city()});
		}
        
		long response_start=System.currentTimeMillis();
		this.respData(jsondata.toString().getBytes(), request, response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 100:"+jsondata.toString());
		
		
	}
	

	public void func101() throws Exception{
	
		func101Req obj=(func101Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		List ls=null;
		List lsCmcc=null;
		List lsNet=null;
		String sql="";
		try{
	
//			if(true){
//			resultcode="3";
//			msg="太多用户在排队了，没有抢到上网机会哦,再试试吧!";		
//			jsondata.put("resultcode", resultcode);
//			jsondata.put("msg", msg);
//			jsondata.put("data", jsonarray);
//			jsondata.put("function", obj.getPara_function());
//			this.logger.info("response 101:"+jsondata.toString());
//			respData(jsondata.toString(), request, response);
//			return;										
//			}
				sql="select * from tbl_user_account where uid=?";
				ls=this.sysAccountDao.getData(sql,new String[]{obj.getUID()});
				if(ls!=null && ls.size()>0){
					Map mp=(Map)ls.get(0);
					int use_time=(mp.get("use_minute")!=null?Integer.parseInt(mp.get("use_minute").toString()):0);
					
					int signdays=(mp.get("signdays")!=null?Integer.parseInt(mp.get("signdays").toString()):0);
					
					if(use_time>0){
						
					}else{
						resultcode="3";
						msg="太多用户在排队了，没有抢到上网机会哦,再试试吧!";		
						jsondata.put("resultcode", resultcode);
						jsondata.put("msg", msg);
						jsondata.put("data", jsonarray);
						jsondata.put("function", obj.getPara_function());
						this.logger.info("response 101:"+jsondata.toString());
						respData(jsondata.toString(), request, response);
						return;							
					}
					
					if(signdays>=2 && obj.getChinanet()!=null ){
						resultcode="1";
						msg="太多用户在排队了，没有抢到上网机会哦,再试试吧!";
						jsondata.put("resultcode", resultcode);
						jsondata.put("msg", msg);
						jsondata.put("data", jsonarray);
						jsondata.put("function", obj.getPara_function());
						this.logger.info("response 101:"+jsondata.toString());
						respData(jsondata.toString(), request, response);
						return;						
					}
					
					
					
					
				}else{
					sql="insert into tbl_user_account(uid,status,score,signdays,indate,use_minute,channel) values(?,0,0,0,now(),"+SysParameter.getInstatnce().getSYS_USE_MINUTE_PER_DAY()+",'"+obj.getChannel()+"')";
					this.sysAccountDao.executedSql(sql, new String[]{obj.getUID()});
				}

			

			
			
			
			
			if(obj.getChinanet()!=null && obj.getCmcc()!=null && Integer.parseInt(obj.getCmcc().get("wifi_priority").toString())>=Integer.parseInt(obj.getChinanet().get("wifi_priority").toString())){
				sql="select * from tbl_wifi_account where ((status=2 and action_twice<2) or status=3)  and category='CMCC'  and used_client_id=?";	
			}else if(obj.getChinanet()!=null && obj.getCmcc()!=null && Integer.parseInt(obj.getCmcc().get("wifi_priority").toString())<Integer.parseInt(obj.getChinanet().get("wifi_priority").toString())){
				sql="select * from tbl_wifi_account where ((status=2 and action_twice<3) or status=3) and category='CHINANET' and used_client_id=?";
			}else if(obj.getCmcc()!=null && obj.getChinanet()==null){
				sql="select * from tbl_wifi_account where ((status=2 and action_twice<2) or status=3)  and category='CMCC'  and used_client_id=?";
			}else if(obj.getChinanet()!=null && obj.getCmcc()==null){
				sql="select * from tbl_wifi_account where ((status=2 and action_twice<3) or status=3) and category='CHINANET' and used_client_id=?";
			}else if(obj.getSsid().equalsIgnoreCase("CTM-WIFI") || obj.getSsid().equalsIgnoreCase("CHT Wi-Fi(HiNet)")|| obj.getSsid().equalsIgnoreCase("iTaiwan") || obj.getSsid().equalsIgnoreCase("TPE-Free")){
				sql="select * from tbl_wifi_account where ((status=2 and action_twice<3) or status=3) and category='"+obj.getSsid()+"' and used_client_id=?";
			}else{
				sql="select * from tbl_wifi_account where (status=2 || status=3) and 1=0 and used_client_id=?";
			}			
			
			ls=this.sysAccountDao.getData(sql,new String[]{obj.getUID()});
			
			if(ls==null || ls.size()==0){
				List sharels=null;
				if(obj.getMac_string()!=null && obj.getMac_string().length()>0){
					sharels=this.sysSharedAccountDao.getShareWifiList(obj.getMac_string());
				}
				if(sharels!=null &&sharels.size()>0){            // exist shared account 
					for(int i=0;i<sharels.size();i++){
						JSONObject item=new JSONObject();
						Map sharedbmp=(Map)sharels.get(i);
						for(int j=0;j<obj.getLsdata().size();j++){
							Map mp=(Map)obj.getLsdata().get(j);
							if(sharedbmp.get("mac_address").equals(mp.get("wifi_mac"))){
								item.put("mac", mp.get("wifi_mac"));
								item.put("name", mp.get("wifi_name"));
								item.put("priority", mp.get("wifi_priority"));
								item.put("acc", mp.get("wifi_name"));
								item.put("pass", sharedbmp.get("passwd"));
								item.put("type", "SharedWifi");
								jsonarray.add(item);	
							}
						}
						
					}
				}else{                                         // without shared account
					sql="";
					if(obj.getChinanet()!=null && obj.getCmcc()!=null && Integer.parseInt(obj.getCmcc().get("wifi_priority").toString())>=Integer.parseInt(obj.getChinanet().get("wifi_priority").toString())){
						sql="select * from tbl_wifi_account   where status=1 and (expiry_date is null or timediff(now(),expiry_date)>'00:01:00') order by category desc, rand() limit 5";	
					}else if(obj.getChinanet()!=null && obj.getCmcc()!=null && Integer.parseInt(obj.getCmcc().get("wifi_priority").toString())<Integer.parseInt(obj.getChinanet().get("wifi_priority").toString())){
						sql="select * from tbl_wifi_account   where status=1 and (expiry_date is null or timediff(now(),expiry_date)>'00:01:00') order by category, rand() limit 5";
					}else if(obj.getCmcc()!=null && obj.getChinanet()==null){
						sql="select * from tbl_wifi_account   where status=1 and category='CMCC'  and used_client_id!='"+obj.getUID()+"' and (expiry_date is null or timediff(now(),expiry_date)>'00:01:00') order by rand() limit 5";
					}else if(obj.getChinanet()!=null && obj.getCmcc()==null){
						sql="select * from tbl_wifi_account   where status=1 and category='ChinaNet'  and (expiry_date is null or timediff(now(),expiry_date)>'00:01:00')  order by rand() limit 5";
					}else if(obj.getSsid().equalsIgnoreCase("CTM-WIFI") || obj.getSsid().equalsIgnoreCase("CHT Wi-Fi(HiNet)")|| obj.getSsid().equalsIgnoreCase("iTaiwan") || obj.getSsid().equalsIgnoreCase("TPE-Free")){
						sql="select * from tbl_wifi_account   where status=1 and category='"+obj.getSsid()+"' and used_client_id!='"+obj.getUID()+"' and (expiry_date is null or timediff(now(),expiry_date)>'00:01:00') order by  rand() limit 5";			
					}else{
						sql="select * from tbl_wifi_account where 1=0  and used_client_id!='"+obj.getUID()+"'";
					}
					
					
//					int num = 0;
//					int max_online_user_chinanet = 0;
//                    if(obj.getChinanet()!=null && obj.getCmcc()==null) {
//                        num = getOnlineNumChinaNet();
//                        max_online_user_chinanet = Integer.valueOf(SysParameter.getInstatnce().getParams().get("max_online_user_chinanet").toString());
//                    }
//					//请求chinanet时, 在线人数少于200才能分配卡
//                    if (num  <=  max_online_user_chinanet){
                        List wifils=this.sysAccountDao.getData(sql, new String[]{});
                        if(wifils!=null &&wifils.size()>0){
                            Map mp=(Map)wifils.get(0);
                            JSONObject item=new JSONObject();                   
                            //item.put("mac",  mp.get("category").toString().equalsIgnoreCase("chinanet")?obj.getChinanet().get("wifi_mac"):obj.getCmcc().get("wifi_mac"));
                            //item.put("name", mp.get("category").toString().equalsIgnoreCase("chinanet")?obj.getChinanet().get("wifi_name"):obj.getCmcc().get("wifi_name"));
                            //item.put("priority", mp.get("category").toString().equalsIgnoreCase("chinanet")?obj.getChinanet().get("wifi_priority"):obj.getCmcc().get("wifi_priority"));
                            item.put("mac",  obj.getSmac());
                            item.put("name", obj.getSsid());
                            item.put("priority","3");
                            item.put("acc", mp.get("account"));
                            item.put("pass", mp.get("passwd"));
                            item.put("type", mp.get("category"));
                            jsonarray.add(item);
                            this.sysAccountDao.executedSql("update tbl_wifi_account set status=2,action_twice=ifnull(action_twice,0)+1,action_date=now(),expiry_date=date_add(now(), interval 1 minute),used_client_id=?,used_login_id=? where id=?", new String[]{obj.getUID(),obj.getUsername(),mp.get("id").toString()});

                            if(mp.get("category").toString().equalsIgnoreCase("cmcc") && mp.get("logout_url").toString().length()>0 &&  mp.get("cookies").toString().length()>0 &&  mp.get("postdata").toString().length()>0){
                                LogoutUtil.logoutCMCC((String)mp.get("logout_url"), (String)mp.get("cookies"), (String)mp.get("postdata"), (String)mp.get("sessionid"));
                            }

                            
                        
                        }else{                      // without available account check existing expirydate account?
                            
                            sql="";
                            if(obj.getChinanet()!=null && obj.getCmcc()!=null && Integer.parseInt(obj.getCmcc().get("wifi_priority").toString())>=Integer.parseInt(obj.getChinanet().get("wifi_priority").toString())){
                                sql="SELECT * FROM tbl_wifi_account  where (status=2 or status=3) and expiry_date<now() and category='CMCC' order by status desc,expiry_date desc"; 
                            }else if(obj.getChinanet()!=null && obj.getCmcc()!=null && Integer.parseInt(obj.getCmcc().get("wifi_priority").toString())<Integer.parseInt(obj.getChinanet().get("wifi_priority").toString())){
                                sql="SELECT * FROM tbl_wifi_account  where (status=2 or status=3) and expiry_date<now() and category='ChinaNet' order by status desc,expiry_date desc";
                            }else if(obj.getCmcc()!=null && obj.getChinanet()==null){
                                sql="SELECT * FROM tbl_wifi_account  where (status=2 or status=3) and expiry_date<now() and category='CMCC' order by status desc,expiry_date desc";
                            }else if(obj.getChinanet()!=null && obj.getCmcc()==null){
                                sql="SELECT * FROM tbl_wifi_account  where (status=2 or status=3) and expiry_date<now() and category='ChinaNet' order by status desc,expiry_date desc";
                            }else if(obj.getSsid().equalsIgnoreCase("CTM-WIFI") || obj.getSsid().equalsIgnoreCase("CHT Wi-Fi(HiNet)")|| obj.getSsid().equalsIgnoreCase("iTaiwan") || obj.getSsid().equalsIgnoreCase("TPE-Free")){
                                sql="SELECT * FROM tbl_wifi_account  where (status=2 or status=3) and expiry_date<now() and category='"+obj.getSsid()+"'  order by status desc,expiry_date desc ";          
                                 
                            }else{
                                sql="select * from tbl_wifi_account where 1=0";
                            }                       
                            List expiryls=this.sysAccountDao.getData(sql, new String[]{});
                            if(expiryls!=null &&expiryls.size()>0){
                                Map mp=(Map)expiryls.get(0);
                                JSONObject item=new JSONObject();   
                                //item.put("mac",  mp.get("category").toString().equalsIgnoreCase("chinanet")?obj.getChinanet().get("wifi_mac"):obj.getCmcc().get("wifi_mac"));
                                //item.put("name", mp.get("category").toString().equalsIgnoreCase("chinanet")?obj.getChinanet().get("wifi_name"):obj.getCmcc().get("wifi_name"));
                                //item.put("priority", mp.get("category").toString().equalsIgnoreCase("chinanet")?obj.getChinanet().get("wifi_priority"):obj.getCmcc().get("wifi_priority"));
                                item.put("mac",  obj.getSmac());
                                item.put("name", obj.getSsid());
                                item.put("priority","3");
                                item.put("acc", mp.get("account"));
                                item.put("pass", mp.get("passwd"));
                                item.put("type", mp.get("category"));
                                jsonarray.add(item);

                                if(mp.get("category").toString().equalsIgnoreCase("cmcc") && mp.get("status")!=null && mp.get("status").equals("3")){
                                    LogoutUtil.logoutCMCC((String)mp.get("logout_url"), (String)mp.get("cookies"), (String)mp.get("postdata"), (String)mp.get("sessionid"));
                                }

                                this.sysAccountDao.executedSql("update tbl_wifi_account set status=2,action_twice=ifnull(action_twice,0)+1,action_date=now(),expiry_date=date_add(now(), interval 1 minute),used_client_id=?,used_login_id=?,cookies='',sessionid='',postdata='' ,logout_url='' where id=?", new String[]{obj.getUID(),obj.getUsername(),mp.get("id").toString()});
                            }
                            
                            
                        }
//                    }
                    
				}
			
		}else{											  // exist already login cmcc/chinanet account
			Map dbmp=(Map)ls.get(0);
			
			//this.logger.info(dbmp);
			
			resultcode="0";
			msg="success";
			JSONObject item=new JSONObject();

				//item.put("mac", (cmccmp!=null&&cmccmp.get("wifi_mac")!=null?cmccmp.get("wifi_mac"):""));
				//item.put("name", (cmccmp!=null&&cmccmp.get("wifi_name")!=null?cmccmp.get("wifi_name"):""));
				//item.put("priority", (cmccmp!=null&&cmccmp.get("wifi_priority")!=null?cmccmp.get("wifi_priority"):""));
				item.put("mac",  obj.getSmac());
				item.put("name", obj.getSsid());
				item.put("priority","3");				
				item.put("acc", dbmp.get("account"));
				item.put("pass", dbmp.get("passwd"));
				item.put("type", dbmp.get("category"));
				
				if(dbmp.get("category")!=null && dbmp.get("category").toString().equalsIgnoreCase("CMCC") && dbmp.get("status")!=null && dbmp.get("status").toString().equals("3")){
					
					LogoutUtil.logoutCMCC((String)dbmp.get("logout_url"), (String)dbmp.get("cookies"), (String)dbmp.get("postdata"), (String)dbmp.get("sessionid"));
				}
			
			if(dbmp.get("status")!=null && dbmp.get("status").toString().equals("2")){
				this.sysAccountDao.executedSql("update tbl_wifi_account set status=2,action_twice=ifnull(action_twice,0)+1,action_date=now(),expiry_date=date_add(now(), interval 1 minute),used_client_id=?,used_login_id=? where id=?", new String[]{obj.getUID(),obj.getUsername(),dbmp.get("id").toString()});
			}
			jsonarray.add(item);			

		}

		//response.getOutputStream().write("adsfasdfdsafasd".getBytes());
		//response.getOutputStream().close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(jsonarray.size()>0){
			resultcode="0";
			msg="success";

		}else{
			resultcode="1";
			msg="not available account!";
			msg="太多用户在排队了，没有抢到上网机会哦,再试试吧!";
		}
		
	//	resultcode="2";
	//	msg="account U scorc is not enough!";		

	//	resultcode="3";
	//	msg="try use time is no.";		
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("data", jsonarray);
		jsondata.put("function", obj.getPara_function());
		
		String header=(obj.getChannel()==null?"|":obj.getChannel()+"|");
		header+=(obj.getUID()==null?"|":obj.getUID()+"|");
		header+=(null==null?"|":""+"|");
		header+=(null==null?"|":""+"|");
		header+=(obj.getUsername()==null?"":obj.getUsername());
		
		//addCookie(response, "WIFI_CLIENT_INFO", header);
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(), request, response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 101:"+jsondata.toString());		
	}
	


	public void func102() throws Exception{
		
		func102Req obj=(func102Req)request.getAttribute(SysParameter.getInstatnce().REQUESTBEAN);
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		String timeout="1800000";
		String user_type="1";
		List ls=null;			
		String sql="";
		int use_minute=-1;
		int score=0;
		int cmcc_minute=30;
		int chinanet_minute=30;
		try{
			
			
			this.sysAccountDao.executedSql("insert into tbl_wifi_use_succ_logs(uid,wifi_mac,wifi_name,wifi_acc,wifi_type,wifi_cookie,wifi_postdata,wifi_sessionid,wifi_logout_url,user,version,city,longitude,latitude,indate,channel) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?)"
					,new String[]{obj.getUid(),obj.getWifi_mac(),obj.getWifi_name(),obj.getWifi_acc(),obj.getWifi_type(),obj.getWifi_cookie(),obj.getWifi_postdata(),obj.getWifi_sessionid(),obj.getWifi_logout_url(),obj.getUsername(),(obj.getHeader_version()==null?"":obj.getHeader_version()),obj.getCity(),obj.getLongitude(),obj.getLatitude(),obj.getChannel()});
			
			if(obj.getWifi_type().equalsIgnoreCase("CMCC") ||obj.getWifi_type().equalsIgnoreCase("ChinaNet") ||obj.getWifi_type().equalsIgnoreCase("CTM-WIFI")  || obj.getWifi_type().equalsIgnoreCase("CHT Wi-Fi(HiNet)") || obj.getWifi_type().equalsIgnoreCase("iTaiwan") || obj.getWifi_type().equalsIgnoreCase("TPE-Free")){
				resultcode="0";
				msg="success";
				ls=this.sysAccountDao.getData("select * from tbl_wifi_account where (status=2) and used_client_id=?  and account=? order by action_date desc limit 2",new String[]{obj.getUid(),obj.getWifi_acc()});	
				if(ls!=null && ls.size()>0){
					Map mp=(Map)ls.get(0);
					//this.sysAccountDao.executedSql("update tbl_wifi_account set status=3,action_date=now(),expiry_date=date_add(now(), interval 30 minute),cookies=?,sessionid=?,postdata=?,used_login_id=?,logout_url=? where id=? ", new String[]{obj.getWifi_cookie(),obj.getWifi_sessionid(),obj.getWifi_postdata(),obj.getWifi_name(),obj.getWifi_logout_url(),mp.get("id").toString()});
					
					/// update user score
					if(obj.getWifi_type().equalsIgnoreCase("ChinaNet")){
						this.sysAccountDao.executedSql("update tbl_user_account set signdays=signdays+1 where uid=?", new String[]{obj.getUid()});						
					}

						//System.out.println(msg);
						// add by yangliu 新增了一个 account_used_time 字段用来记录帐号联网的时间
						if(obj.getWifi_postdata()!=null && obj.getWifi_postdata().length()>10 && obj.getWifi_logout_url()!=null && obj.getWifi_logout_url().length()>20){
							if(obj.getWifi_type().equalsIgnoreCase("CMCC")){
								this.sysAccountDao.executedSql("update tbl_wifi_account set status=3,action_date=now(), account_used_time=now(), expiry_date=date_add(now(), interval "+cmcc_minute+" minute),cookies=?,sessionid=?,postdata=?,used_login_id=?,logout_url=? where id=? ", new String[]{obj.getWifi_cookie(),obj.getWifi_sessionid(),obj.getWifi_postdata(),obj.getUsername(),obj.getWifi_logout_url(),mp.get("id").toString()});
								timeout=String.valueOf(30*60*1000);
								timeout=String.valueOf(cmcc_minute*60*1000);
							}else{
								this.sysAccountDao.executedSql("update tbl_wifi_account set status=3,action_date=now(), account_used_time=now(), expiry_date=date_add(now(), interval "+chinanet_minute+" minute),cookies=?,sessionid=?,postdata=?,used_login_id=?,logout_url=? where id=? ", new String[]{obj.getWifi_cookie(),obj.getWifi_sessionid(),obj.getWifi_postdata(),obj.getUsername(),obj.getWifi_logout_url(),mp.get("id").toString()});
								timeout=String.valueOf(60*60*1000);
								timeout=String.valueOf(chinanet_minute*60*1000);
								
							}
						}else{
							if(obj.getWifi_type().equalsIgnoreCase("CMCC")){
								this.sysAccountDao.executedSql("update tbl_wifi_account set status=3,action_date=now(), account_used_time=now(), expiry_date=date_add(now(), interval "+cmcc_minute+" minute),used_login_id=? where id=? ", new String[]{obj.getUsername(),mp.get("id").toString()});
								timeout=String.valueOf(30*60*1000);
								timeout=String.valueOf(cmcc_minute*60*1000);
							}else{
								this.sysAccountDao.executedSql("update tbl_wifi_account set status=3,action_date=now(), account_used_time=now(), expiry_date=date_add(now(), interval "+chinanet_minute+" minute),used_login_id=? where id=? ", new String[]{obj.getUsername(),mp.get("id").toString()});
								timeout=String.valueOf(60*60*1000);
								timeout=String.valueOf(chinanet_minute*60*1000);
							}

							
						}
						
					
				}
				
				
				
				

				
			}else{
				resultcode="0";
				msg="success";
				msg="";
				
				/// update user score
				String share_account="";
				String share_uid="";
				ls=this.sysAccountDao.getData("select * from tbl_wifi_share_account_logs where mac_address=? order by id limit 2", new String[]{obj.getWifi_mac()});
				if(ls!=null && ls.size()>0){
					Map map=(Map)ls.get(0);
					share_account=(map.get("reg_account")!=null?map.get("reg_account").toString():"");
					share_uid=(map.get("uid")!=null?map.get("uid").toString():(map.get("remark")!=null?map.get("remark").toString():""));;	
					if(share_account.equalsIgnoreCase(obj.getUsername())&& obj.getUsername().length()>0){
//						msg="占领用户，使用自己的wifi";
//						msg="";
//						user_type="4";
						
					}else{
						int share_score=0;
						if(obj.getUsername().length()>0){
							if(obj.getGroupid().length()==0){
								ls=this.sysAccountDao.getData("select * from tbl_wifi_share_login_logs where mac_address=? and account=? order by id limit 2", new String[]{obj.getWifi_mac(),obj.getUsername()});
							}else{
								ls=this.sysAccountDao.getData("select * from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupid()});
								String user="";
								if(ls!=null && ls.size()>0){
									for(int i=0;i<ls.size();i++){
										Map mp=(Map)ls.get(i);
										user+="'"+(mp.get("account")==null?"":mp.get("account").toString())+"',";
									}
								}
								if(user.length()>0){user=user.substring(0,user.length()-1);}								
								ls=this.sysAccountDao.getData("select * from tbl_wifi_share_login_logs where mac_address=? and account in("+user+") order by id limit 2", new String[]{obj.getWifi_mac(),obj.getUsername()});									
							}
							if(ls!=null && ls.size()>0){
								share_score=0;
							}else{
								share_score=1;
							}
							this.sysAccountDao.executedSql("insert into tbl_wifi_share_login_logs(account,remark,mac_address,wifi_type,indate,share_account,share_uid,share_score,uid) values(?,?,?,'share',now(),?,?,?,'')", new String[]{obj.getUsername(),obj.getUid(),obj.getWifi_mac(),share_account,share_uid,String.valueOf(share_score)});
							this.sysAccountDao.executedSql("update tbl_reg_account set flowers=ifnull(flowers,0)+"+share_score+" where account=?", new String[]{obj.getUsername()});
						}else{
							ls=this.sysAccountDao.getData("select * from tbl_wifi_share_login_logs where mac_address=? and (uid=? or remark=?) order by id limit 2", new String[]{obj.getWifi_mac(),obj.getUid(),obj.getUid()});
							if(ls!=null && ls.size()>0){
								share_score=0;
							}else{
								share_score=1;
							}
							if(share_score>0){
								ls=this.sysAccountDao.getData("select * from tbl_wifi_share_login_logs where mac_address=? and length(account)>3 order by id limit 50", new String[]{obj.getWifi_mac()});
								if(ls.size()>20){
									share_score=0;
								}
							}
							
							this.sysAccountDao.executedSql("insert into tbl_wifi_share_login_logs(account,uid,mac_address,wifi_type,indate,share_account,share_uid,share_score,remark) values(?,?,?,'share',now(),?,?,?,'')", new String[]{obj.getUsername(),obj.getUid(),obj.getWifi_mac(),share_account,share_uid,String.valueOf(share_score)});
							this.sysAccountDao.executedSql("update tbl_user_account set flowers=ifnull(flowers,0)+"+share_score+" where uid=?", new String[]{obj.getUid()});
							
						}
						
												
//						int share_score=SysParameter.getInstatnce().getSYS_USE_SHARE_WIFI_SCORE();
//						ls=this.sysAccountDao.getData("select * from tbl_wifi_share_login_logs where mac_address=?", new String[]{obj.getWifi_mac()});
//						if(ls==null || ls.size()==0){
//							this.sysAccountDao.executedSql("insert into tbl_wifi_share_login_logs(account,uid,mac_address,wifi_type,indate,share_account,share_uid,share_score) values(?,?,?,'share',now(),?,?)", new String[]{obj.getUsername(),obj.getUid(),obj.getWifi_mac(),share_account,share_uid,String.valueOf(share_score)});
//							if(obj.getUsername()!=null && obj.getUsername().length()==11){
//								//this.sysAccountDao.executedSql("update tbl_user_account set score=score+"+share_score+" where account=?",new String[]{share_account});
//							}else{
//								this.sysAccountDao.executedSql("update tbl_user_account set score=score+"+share_score+" where uid=?",new String[]{share_uid});
//							}
//						}
					}
				}
				
				//update user score							
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("timeout", timeout);
		jsondata.put("user_type", user_type);
		jsondata.put("score", score);
		jsondata.put("function", obj.getPara_function());
		

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(), request, response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 102:"+jsondata.toString());		
	}
	
	
	public void func103() throws Exception{
		
		func103Req obj=(func103Req)request.getAttribute(SysParameter.getInstatnce().REQUESTBEAN);
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		String timeout="1800";
		List ls=null;			
		try{		
			if(obj.getWifi_type().equalsIgnoreCase("CMCC") ||obj.getWifi_type().equalsIgnoreCase("ChinaNet")||obj.getWifi_type().equalsIgnoreCase("CTM-WIFI")|| obj.getWifi_type().equalsIgnoreCase("CHT Wi-Fi(HiNet)") || obj.getWifi_type().equalsIgnoreCase("iTaiwan") || obj.getWifi_type().equalsIgnoreCase("TPE-Free") ){
				ls=this.sysAccountDao.getData("select * from tbl_wifi_account where used_client_id=?  and account=? order by action_date desc limit 1",new String[]{obj.getUid(),obj.getWifi_acc()});
				if(ls!=null && ls.size()>0){
					Map mp=(Map)ls.get(0);
					int status = 1;
					//得到该账户的总使用时长 (单位是: 分钟)
                    Object accountUseLength = mp.get("account_used_length");
                    int length = 0;
                    
                    //account_used_time为帐号最近一次分配出去的连网的时间
                    if (mp.get("account_used_time") != null){ 
                        String account_used_time = mp.get("account_used_time").toString().substring(0,19);
                        //用当前时间减去 account_used_time 得到上网时长
                        long time = System.currentTimeMillis() - Tools.string2Date(account_used_time, "yyyy-MM-dd HH:mm:ss").getTime();
                        int minute = (int)(time/1000/60);
                        
                        if (null != accountUseLength){
                             //帐号的总时长加上当前使用的时长
                            length = Integer.valueOf(accountUseLength.toString()) + minute;
                        }else{
                            length = minute;
                        }
                    }
                    
					//this.sysAccountDao.executedSql("update tbl_wifi_account set status=1,action_date=now(),expiry_date=now(),cookies=?,sessionid=?,postdata=?,used_login_id=? ,used_client_id=?,logout_url=? where id=? ", new String[]{"","","","","","",mp.get("id").toString()});
					if(obj.getWifi_type().equalsIgnoreCase("CHINANET")){
						this.sysAccountDao.executedSql("update tbl_wifi_account set status=4, account_used_time=now(), account_used_length=?,action_twice=0,action_date=now(),expiry_date=now(),used_login_id=? ,used_client_id=? where id=? ", new String[]{ String.valueOf(length),"","",mp.get("id").toString()});						
					}else{
						this.sysAccountDao.executedSql("update tbl_wifi_account set status=?, account_used_time=now(), account_used_length=?,action_twice=0,action_date=now(),expiry_date=now(),used_login_id=? ,used_client_id=? where id=? ", new String[]{String.valueOf(status), String.valueOf(length),"","",mp.get("id").toString()});
					}
					
					//cmcc踢人
					if (obj.getWifi_type().equalsIgnoreCase("CMCC")){					    
					    LogoutUtil.logoutCMCC((String)mp.get("logout_url"), (String)mp.get("cookies"), (String)mp.get("postdata"), (String)mp.get("sessionid"));
					}
					
					
					resultcode="0";
					msg="success";					
				}
				
				//this.sysAccountDao.executedSql("update tbl_user_login_log set logoutdate=now() where account=? and indate> date_add(now(), interval -120 minute) and logoutdate is null " , new String[]{obj.getUsername()});
				if(!resultcode.equals("0")){
					resultcode="0";
					msg="record not found!";
				}
				
			}else{
				resultcode="0";
				msg="success";					
				
			}
						

		}catch(Exception e){
			e.printStackTrace();
		}
			
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(), request, response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 103:"+jsondata.toString());		
	}

	/**
	 * wifi分享接口
	 * @throws Exception
	 */
	public void func104() throws Exception{
	    
        Func104Req obj=(Func104Req)request.getAttribute(SysParameter.getInstatnce().REQUESTBEAN);
                
        Map<String, String> map = new HashMap<String, String>();
        
        logger.info("request to 104");
        
       try{
        
            //标志位,是否占领成功(只有占领成功才能加U币)
            boolean isHoldSuccess = false;
            Integer actionType = obj.getAction_type();
            int score = Integer.valueOf(SysParameter.getInstatnce().getParams().get("occupy_wifi_present_coin").toString());
            obj.setScore(score);
            
            //根据mac判断该帐号是否己共享
            List list = sysSharedAccountDao.getShareWifiList("'" + obj.getWifi_mac() + "'");
            if (null != list && !list.isEmpty()){
                
                Map data = (Map)list.get(0);
                
                //action_type是完善信息
                if (actionType == 2){
                	obj.setCreateby_client_id("");
                	obj.setWifi_latitude(0);
                	obj.setWifi_longitude(0);
                    sysSharedAccountDao.updateSharedWIFIAccount(obj);
                    map.put("resultcode", "0");
                    map.put("msg", "修改成功！");
                }else{
//                    //判断该 wifi 帐号是否有效
//                    int status = (Integer)data.get("status");
//                    // 如果该 wifi 帐号有效, 则表示该wifi已经被占领
//                    if (status == 1){
//                        map.put("resultcode", "1");
//                        map.put("msg", "此WIFI已被占领，试试其它WIFI吧！");
//                    }
//                    //如果status等于 0 表示该 wifi失效, 修改该wifi信息,重新占领
//                    else if (status == 0){
//                        sysSharedAccountDao.updateSharedWIFIAccount(obj);
//                        map.put("resultcode", "0");
//                        map.put("msg", "修改成功！");
//                        
//                        //根据用户注册帐号和mac地址查询该用户是否曾经有占领过该wifi的历史记录
//                        List ls = sysSharedAccountDao.getShareWifiLog(obj.getCreateby_login_id(), obj.getWifi_mac());
//                        
//                        //只有客户端传的actionType等于0, 历史记录为空时才可以占领成功送U币
//                        if (actionType == 0 && ls.isEmpty()){
//                            //插入一条wifi占领成功的历史记录
//                            sysSharedAccountDao.addWifiShareAccountLog(obj);
//                            isHoldSuccess = true;
//                        }
//                    }
                }
            }
            else{
                if (actionType == 0){
                    //成功占领  // more than 10
                	list=sysSharedAccountDao.getData("select * from tbl_wifi_share_account where wifi_type>0 and wifi_type<9 and indate>=curdate() and indate < date_add(curdate(),INTERVAL 1 DAY) and createby_client_id=? ",new String[]{obj.getCreateby_client_id()});
                	if(list!=null && list.size()>=10){
                		map.put("resultcode", "2");
                		map.put("msg", "系统检测到你的操作过于频繁，请明天再试！");     
                	}else{
                		list=sysSharedAccountDao.getData("select * from tbl_block_mac where key_mac=?",new String[]{obj.getWifi_mac().substring(0,8)});                		
                		if(obj.getWifi_name().toLowerCase().indexOf("iphone")>=0 ||obj.getWifi_name().toLowerCase().indexOf("360wifi")>=0||obj.getWifi_name().toLowerCase().indexOf("360免费wifi")>=0||obj.getWifi_name().toLowerCase().indexOf("全民wifi")>=0||obj.getWifi_name().indexOf("随身")>=0 || list.size()>0){                		
                        	map.put("resultcode", "4");
                            map.put("msg", "此类热点不可共享密码!");  
                		}else{
                  		  // more than 3 ssid                			
	                    	list=sysSharedAccountDao.getData("select * from tbl_wifi_share_account where wifi_type>0 and wifi_type<9 and  account=? and createby_client_id=?",new String[]{obj.getWifi_name(),obj.getCreateby_client_id()});
	                    	if(list!=null && list.size()>=3){
	                    		map.put("resultcode", "2");
	                    		map.put("msg", "系统检测到你的操作过于频繁，请明天再试！");                    		
	                    	}else{
	                    		
	                    		list = sysSharedAccountDao.getData("select * from tbl_wifi_share_account where  mac_address=?",new String[]{obj.getWifi_mac()} );
	                    		if(null != list && list.size()>0){
	                    			Map mp=(Map)list.get(0);
	                    			if(mp.get("status").toString().equals("0")){
	                    				sysSharedAccountDao.updateSharedWIFIAccount(obj);
	                    				List ls = sysSharedAccountDao.getShareWifiLog(obj.getCreateby_client_id(), obj.getWifi_mac());
	                    				if(ls==null ||ls.size()==0){
	                    					sysSharedAccountDao.addWifiShareAccountLog(obj);
	                    				}else{
	                    					score=0;
	                    				}
	                    				isHoldSuccess = true;	 
	                    			}else{
	                    				
	                    				isHoldSuccess = false;	 
	                    			}
	                    		
	                    		
	                    		}else{
	                				sysSharedAccountDao.addWifiShareAccount(obj);
	                				isHoldSuccess = true;
	                				
	                			}
	                    	}                			
                		}
                	}
                }else{
                	map.put("resultcode", "1");
                    map.put("msg", "参数错误！");                	
                }
            }
            
            //占领成功
            if (isHoldSuccess){ 
            	if(obj.getChannel()!=null && obj.getChannel().trim().equalsIgnoreCase("aoratec")){  
            		//this.sysAccountDao.executedSql("insert into tbl_user_score_additional(account,remark,score,indate) values(?,'占领加送','50',now())", new String[]{obj.getCreateby_login_id()});
            		//score+=50;
            	}
            	
            	if(obj.getCreateby_login_id().length()>0){
            		sysAccountDao.executedSql("UPDATE tbl_reg_account SET score=score+" + score +" WHERE account=?",new String[]{obj.getCreateby_login_id()});        	   
            	}else{
            		sysAccountDao.executedSql("UPDATE tbl_user_account SET score=score+" + score +" WHERE uid=?",new String[]{obj.getCreateby_client_id()});
            	}
                
				DecimalFormat df = new DecimalFormat("0.00");  
				double dscore = (double)score;  
				
                
                map.put("resultcode", "0");
                if(score==0){
                	map.put("msg", "很抱歉 ，你已领过该红包！");
                }else{
                	map.put("msg", "恭喜你，共享密码成功 ，获得"+df.format(dscore/100).toString()+"元红包！");
                }
                map.put("score", df.format(dscore/100).toString()+"元");
            }else{
            	if(!map.containsKey("resultcode")){
            		map.put("resultcode", "3");
            		map.put("msg", "啊喔，您来晚了一步，该热点已经被分享了。快去抢下一个红包吧！");
            	}
            	map.put("score", "0.00元");            	
            	
            }
            
        }catch(Exception e){
            logger.error("request 104 failed!", e);
            
            map.put("resultcode", "-99");
            map.put("msg", "占领失败！");
            map.put("score", "0.00元");         
        }
        
        map.put("function", obj.getPara_function());
        String output = JSONSerializer.toJSON(map).toString();


		long response_start=System.currentTimeMillis();
        respData(output, request, response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 104:" + output);        
	}



	/**
	 * Wifi热点查询接口
	 */
	public void func105(){
	    Func105Req obj=(Func105Req)request.getAttribute(SysParameter.getInstatnce().REQUESTBEAN);
	    
	    Map<String, Object> map = new HashMap<String, Object>();
	    
	    try{	
	        double leftBottomLat = obj.getLeftBottomLat();
	        double leftTopLat = obj.getLeftTopLat();
	        double leftTopLong = obj.getLeftTopLong();
	        double rightTopLong = obj.getRightTopLong();
	        
	        Map<String, Double> params = new HashMap<String, Double>();
	        
	        params.put("leftBottomLat", leftBottomLat);
	        params.put("leftTopLat", leftTopLat);
	        params.put("leftTopLong", leftTopLong);
	        params.put("rightTopLong", rightTopLong);
	        
	        params.put("myLong", obj.getMyLong());
	        params.put("myLat", obj.getMyLat());
	        
	        params.put("cur_scale", Double.valueOf(obj.getCur_scale()));
	        
	        params.put("maptype_baidu", Double.valueOf(obj.getMaptype().endsWith("baidu")?1:0));
	        
	        logger.info("params:" + params);
            //根据四个角的经纬度坐标点在数据库查询此范围内的有效的wifi热点
            List<Map<String, Object>> data = sysSharedAccountDao.getShareWifiHotspot(params, obj.getIsIOS());
	       // List<Map<String, Object>> data=new ArrayList();
            map.put("resultcode", "0");
            map.put("msg", "success");
            map.put("data", data);
	        
	    }catch(Exception e){
	        logger.error("request 105 failed!", e);
	        
	        map.put("resultcode", "-99");
            map.put("msg", "request invalid");
              
	    }
	    
        map.put("function", obj.getPara_function());
        String output = JSONSerializer.toJSON(map).toString();


		long response_start=System.currentTimeMillis();
        respData(output, request, response);
        output=null;
        
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 105:" + output);        
		
	}


	public void func106() throws Exception{
	    
		func106Req obj=(func106Req)request.getAttribute(SysParameter.getInstatnce().REQUESTBEAN);
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		List ls=null;			
		try{
			if(!obj.getWifi_name().equalsIgnoreCase("CMCC") && !obj.getWifi_name().equalsIgnoreCase("ChinaNet")){

				ls=this.sysAccountDao.getData("select * From tbl_wifi_share_account where mac_address=?", new String[]{obj.getWifi_mac()});
				if(ls!=null && ls.size()>0){
					ls=this.sysAccountDao.getData("select * from tbl_wifi_logs where indate>=str_to_date(?,'%Y-%m-%d') and action='101' and used_client_id=?",new String[]{Tools.date2String(new Date(), "yyyy-MM-dd"),obj.getUid()});
					if(ls==null || ls.size()==0){
						resultcode="2";
						msg="SharedWIFI";						
						jsondata.put("mac", obj.getWifi_mac());
						jsondata.put("name", obj.getWifi_name());
						jsondata.put("acc", "");
						jsondata.put("pass", "");
						jsondata.put("type", "SharedWIFI");
						jsondata.put("cookie", "");
						jsondata.put("postdata", "");
						jsondata.put("sessionid", "");
						jsondata.put("logout_url", "");
						jsondata.put("timeout", "-1");
					}else{
						resultcode="0";
						msg="ShareWIFI";						
						jsondata.put("mac", obj.getWifi_mac());
						jsondata.put("name", obj.getWifi_name());
						jsondata.put("acc", "");
						jsondata.put("pass", "");
						jsondata.put("type", "SharedWifi");
						jsondata.put("cookie", "");
						jsondata.put("postdata", "");
						jsondata.put("sessionid", "");
						jsondata.put("logout_url", "");
						jsondata.put("timeout", "");
					}
				}else{
					resultcode="1";
					msg="ShareWIFI";						
					jsondata.put("mac", obj.getWifi_mac());
					jsondata.put("name", obj.getWifi_name());
					jsondata.put("acc", "");
					jsondata.put("pass", "");
					jsondata.put("type", "SharedWifi");
					jsondata.put("cookie", "");
					jsondata.put("postdata", "");
					jsondata.put("sessionid", "");
					jsondata.put("logout_url", "");
					jsondata.put("timeout", "");					
				}
			}else{
				ls=this.sysAccountDao.getInUsedWIFIAccount(obj.getUid());	
				if(ls==null || ls.size()==0){
					resultcode="1";
					msg="Not found the data!";						
					jsondata.put("mac", obj.getWifi_mac());
					jsondata.put("name", obj.getWifi_name());
					jsondata.put("acc", "");
					jsondata.put("pass", "");
					jsondata.put("type", "");
					jsondata.put("cookie", "");
					jsondata.put("postdata", "");
					jsondata.put("sessionid", "");
					jsondata.put("logout_url", "");					
					jsondata.put("timeout", "");					
					
				}else{
					Map dbmp=(Map)ls.get(0);
					resultcode="0";
					msg="success";
					String expiry_date=dbmp.get("expiry_date")!=null?dbmp.get("expiry_date").toString():"";
					if(expiry_date.length()>0){
						expiry_date=expiry_date.substring(0,expiry_date.lastIndexOf("."));
					}
					

					jsondata.put("mac", obj.getWifi_mac());
					jsondata.put("name", obj.getWifi_name());
	
					jsondata.put("acc", dbmp.get("account"));
					jsondata.put("pass", dbmp.get("passwd"));
					jsondata.put("type", dbmp.get("category"));
					
					this.logger.info("expiry_date:"+expiry_date+":"+Tools.string2Date(expiry_date, "yyyy-MM-dd HH:mm:ss").getTime()+":"+System.currentTimeMillis()+":"+Tools.date2String(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss")+":");
					if(dbmp.get("status").toString().equalsIgnoreCase("3") && Tools.string2Date(expiry_date, "yyyy-MM-dd HH:mm:ss").getTime()-System.currentTimeMillis()>0){
						resultcode="0";
						msg="success";
						jsondata.put("cookie", dbmp.get("cookies"));
						jsondata.put("postdata", dbmp.get("postdata"));
						jsondata.put("sessionid", dbmp.get("sessionid"));
						jsondata.put("logout_url", dbmp.get("logout_url"));
						jsondata.put("timeout", Tools.string2Date(expiry_date, "yyyy-MM-dd HH:mm:ss").getTime()-System.currentTimeMillis());
						
					}else{
						resultcode="2";
						msg="data is expired!";					
						jsondata.put("cookie", "");
						jsondata.put("postdata", "");
						jsondata.put("sessionid", "");
						jsondata.put("logout_url", "");						
						jsondata.put("timeout", "");
						if(dbmp.get("status").toString().equalsIgnoreCase("3") ){
							LogoutUtil.logoutCMCC((String)dbmp.get("logout_url"), (String)dbmp.get("cookies"), (String)dbmp.get("postdata"), (String)dbmp.get("sessionid"));
						}
						
					}
					
				}						
			}  
		}catch(Exception e){
			e.printStackTrace();
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());


		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(), request, response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 106:"+jsondata.toString());   
	}

	
	public void func108() throws Exception{
	    
        func108Req obj=(func108Req)request.getAttribute(SysParameter.getInstatnce().REQUESTBEAN);

		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		List ls=null;			
		try{
//			ls=this.sysAccountDao.getInUsedWIFIAccount(obj.getUID());	
//			if(ls==null || ls.size()==0){
				List sharels=null;
				if(obj.getMac_string()!=null && obj.getMac_string().length()>0){
					sharels=this.sysSharedAccountDao.getShareWifiList(obj.getMac_string());
					//sharels=new ArrayList();
				}
				if(sharels!=null &&sharels.size()>0){            // exist shared account 
					for(int i=0;i<sharels.size();i++){
						JSONObject item=new JSONObject();
						Map sharedbmp=(Map)sharels.get(i);
						for(int j=0;j<obj.getLsdata().size();j++){
							Map mp=(Map)obj.getLsdata().get(j);
							if(sharedbmp.get("mac_address").equals(mp.get("wifi_mac"))){
								item.put("mac", mp.get("wifi_mac"));
								item.put("name", mp.get("wifi_name"));
								item.put("pass", sharedbmp.get("passwd"));
								item.put("type", (sharedbmp.get("wifi_type")==null?"3":sharedbmp.get("wifi_type").toString()));
								item.put("image", (sharedbmp.get("icon")==null||sharedbmp.get("icon").toString().length()==0?"":SysParameter.getInstatnce().getDomain()+request.getContextPath()+"/images/icon/"+sharedbmp.get("icon").toString()));
								item.put("promo", "0");
								jsonarray.add(item);
								break;
							}
						}
						
					}
				}

		}catch(Exception e){
			e.printStackTrace();
		}

		if(jsonarray.size()>0){
			resultcode="0";
			msg="success";

		}else{
			resultcode="0";
			msg="not available account!";			
		}
		


		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("data", jsonarray);
		jsondata.put("function", obj.getPara_function());

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(), request, response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 108:"+jsondata.toString());		
	}	
	
	
	
	

	/**
	 * 共享wifi错误密码上报, 修改共享wifi的状态为0
	 */
	public void func110(){

	    logger.info("request to 110");
	    
        Func110Req obj = (Func110Req) request.getAttribute(SysParameter.getInstatnce().REQUESTBEAN);
        
        JSONObject jsondata=new JSONObject();
        String resultcode = "-99";
        String msg = null;
        
       try {
            
            int failureNumber = 0;
            
            //取出该帐号联网失败次数
            List list = sysSharedAccountDao.getData("SELECT id, mac_address, connectfailureNumber FROM tbl_wifi_share_account WHERE mac_address=?", new String[] { obj.getMac_address() });
            if(list!=null && !list.isEmpty()){
                Map mp=(Map)list.get(0);
                failureNumber = mp.get("connectfailureNumber") == null ? 0 : Integer.valueOf(mp.get("connectfailureNumber").toString());
            }
            
            //取出共享帐号联网最大失败次数
            int maxFailureNumber = Integer.valueOf(SysParameter.getInstatnce().getParams().get("connect_failure_max_number").toString());
            
            maxFailureNumber=100000;
            
            if (failureNumber >= maxFailureNumber){                
                sysSharedAccountDao.executedSql("UPDATE tbl_wifi_share_account SET status = 0 WHERE mac_address=?", new String[]{obj.getMac_address()});
            }else{
                failureNumber += 1;
                sysSharedAccountDao.executedSql("UPDATE tbl_wifi_share_account SET connectfailureNumber = " + failureNumber + " WHERE mac_address=?", new String[]{obj.getMac_address()});
            }
            
            logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+">"+
    				"request 110 success!");
            
            msg = "success";
            resultcode = "0";
        } catch (Exception e) {
            msg = e.getMessage();
            logger.error("request 110 failed", e);
        }
        
        jsondata.put("resultcode", resultcode);
        jsondata.put("msg", msg);
        jsondata.put("function", obj.getPara_function());
        
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(), request, response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 110:"+jsondata.toString());	
	}
	
	public void func111() throws Exception{
	    
        func111Req obj=(func111Req)request.getAttribute(SysParameter.getInstatnce().REQUESTBEAN);

		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		JSONArray jsonarray1=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		String sql="";
		List ls=null;			
		try{
			sql="select mac,password from tbl_wifi_cracker_collect_data where mac in("+obj.getMac_string()+")";
			System.out.println(sql);
			
			ls=this.sysAccountDao.getData(sql, new String[]{});
			if(ls!=null && ls.size()>0){
				for(int i=0;i<ls.size();i++){
					Map mp=(Map)ls.get(i);
					JSONObject item=new JSONObject();
					item.put("mac", mp.get("mac"));
					item.put("pass", mp.get("password"));
					jsonarray.add(item);
				}
			}
			//if(jsonarray.size()==0){
				ls=this.sysAccountDao.getData("select password from tbl_recommend_password order by sort ", new String[]{});
				if(ls!=null && ls.size()>0){
					for(int i=0;i<ls.size();i++){
						Map mp=(Map)ls.get(i);
						JSONObject item=new JSONObject();
						item.put("pass", mp.get("password"));
						jsonarray1.add(item);
	
					}
				}
			//}
			

		}catch(Exception e){
			e.printStackTrace();
		}

		if(jsonarray.size()>0){
			resultcode="0";
			msg="success";
		}
		


		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("data", jsonarray);
		jsondata.put("recommend_data", jsonarray1);
		jsondata.put("function", obj.getPara_function());

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(), request, response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 111:"+jsondata.toString());		
	}		
	
	
	public void func112() throws Exception{
	     
        func112Req obj=(func112Req)request.getAttribute(SysParameter.REQUESTBEAN);

		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		List ls=null;			
		try{


						String uid=obj.getUID();
						String name=obj.getName();
						String city=obj.getCity();
						String latitude=obj.getLatitude();
						String longitude=obj.getLongitude();
						String wifi_mac=obj.getMac();
						String passwd=obj.getPassword();
						String rip=obj.getReal_ip();
			
						
						JSONObject item=new JSONObject();
						java.util.Date curdate=new java.util.Date(); 

							ls=this.sysAccountDao.getData("select mac from tbl_wifi_cracker_collect_data where mac=? limit 1", new String[]{obj.getMac()});
							if(ls==null || (ls!=null && ls.size()==0)){
								
									try{
										this.sysAccountDao.executedSql("insert into tbl_wifi_cracker_collect_data(uid,name,mac,password,longitude,latitude,city,rip,indate,remark,channel,type) values(?,?,?,?,?,?,?,?,now(),?,?,?)",
												new String[]{uid,name,wifi_mac,passwd,longitude,latitude,city,rip,"112",obj.getChannel(),obj.getType()});
										
//										if(obj.getType().equalsIgnoreCase("cracker")){
//								            this.sysAccountDao.executedSql("INSERT INTO tbl_wifi_share_account(account,passwd,status,mac_address,longitude,latitude,wifi_address,wifi_type,wifi_gps_address,indate,modify_date,city,createby_login_id,createby_client_id,channel) VALUES(?,?,?,?,?,?,?,?,?,now(),now(),?,?,?,?)", 
//							            			new String[]{name,passwd,"1",wifi_mac,longitude,latitude,"","112","",city,"",uid,obj.getChannel()});
//							            											
//											
//										}
										
									}catch(Exception err){
										err.printStackTrace();
									}
										 
								}else{
									try{
										this.sysAccountDao.executedSql("update  tbl_wifi_cracker_collect_data set uid=?,name=?,password=?,channel=? where mac=?",new String[]{uid,name,passwd,obj.getChannel(),wifi_mac});
									}catch(Exception err){
										err.printStackTrace();
									}
								}
						resultcode="0";
						msg="success";						

		}catch(Exception e){
			e.printStackTrace();
			
						
		}

		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response "+obj.getPara_function()+":"+jsondata.toString());		
   
	}		
	
	public void func113() throws Exception{
	     
        func113Req obj=(func113Req)request.getAttribute(SysParameter.REQUESTBEAN);

		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		List ls=null;			
		try{


						String uid=obj.getUID();
						String name=obj.getName();
						String city=obj.getCity();
						String latitude=obj.getLatitude();
						String longitude=obj.getLongitude();
						String wifi_mac=obj.getMac();
						String passwd=obj.getPassword();
						String rip=obj.getReal_ip();
			
						
						JSONObject item=new JSONObject();
						java.util.Date curdate=new java.util.Date(); 

							ls=this.sysAccountDao.getData("select mac from tbl_wifi_collect_free_data  where mac=? limit 1", new String[]{obj.getUID()});
							if(ls==null || (ls!=null && ls.size()==0)){
								
									try{
										this.sysAccountDao.executedSql("insert into tbl_wifi_collect_free_data (uid,name,mac,password,longitude,latitude,city,rip,indate,remark,channel) values(?,?,?,?,?,?,?,?,now(),?,?)",
												new String[]{uid,name,wifi_mac,passwd,longitude,latitude,city,rip,"113",obj.getChannel()});
									}catch(Exception err){
										err.printStackTrace();
									}
										 
								} 
						resultcode="0";
						msg="success";						

		}catch(Exception e){
			e.printStackTrace();
			
						
		}

		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response "+obj.getPara_function()+":"+jsondata.toString());		
   
	}		
		
	
	public void func114() throws Exception{
	
		func114Req obj=(func114Req)request.getAttribute(SysParameter.getInstatnce().REQUESTBEAN);

		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		List ls=null;			
		try{
//			ls=this.sysAccountDao.getInUsedWIFIAccount(obj.getUID());	
//			if(ls==null || ls.size()==0){
				List sharels=null;
				if(obj.getMac()!=null && obj.getMac().length()>0){
					sharels=this.sysSharedAccountDao.getShareWifiList("'"+obj.getMac()+"'");
				}
				if(sharels!=null &&sharels.size()>0){            // exist shared account 
					for(int i=0;i<sharels.size();i++){
						
						Map sharedbmp=(Map)sharels.get(i);
						
						//item.put("pass", sharedbmp.get("passwd"));
						jsondata.put("own_uid", sharedbmp.get("createby_client_id"));		
						jsondata.put("exists", "1");
						break;
					}
				}else{
					jsondata.put("own_uid", "");		
					jsondata.put("exists", "0");					
				}
				
				resultcode="0";
				msg="success";
				jsondata.put("mac", obj.getMac());
				jsondata.put("name", obj.getName());
				List list=sysSharedAccountDao.getData("select * from tbl_block_mac where key_mac=?",new String[]{obj.getMac().substring(0,8)});
        		if(obj.getMac().toLowerCase().indexOf("iphone")>=0 ||obj.getMac().toLowerCase().indexOf("360wifi")>=0||obj.getMac().toLowerCase().indexOf("360免费wifi")>=0||obj.getMac().toLowerCase().indexOf("全民wifi")>=0||obj.getMac().indexOf("随身")>=0 || list.size()>0){                		
        			jsondata.put("exists", "2");
        			
        		}
        			
		}catch(Exception e){
			e.printStackTrace();
		}

		String tips1="温馨提示：\n从2014-12-5 至 2014-12-30 共享一个wifi热点密码可获得0.60元现金红包（提现功能，近期开放，敬请期待）.";
		if(SysParameter.getInstatnce().getParams().get("my_occupy_wifi_tips")!=null){
			tips1=SysParameter.getInstatnce().getParams().get("my_occupy_wifi_tips").toString();
		}
		
		int score = Integer.valueOf(SysParameter.getInstatnce().getParams().get("occupy_wifi_present_coin").toString());
		DecimalFormat df = new DecimalFormat("0.00");  
		double dscore = (double)score;  

		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("tips", "赶紧共享WiFi密码领取"+df.format(dscore/100).toString()+"元现金红包吧！");
		jsondata.put("tips1", tips1);
		
		jsondata.put("function", obj.getPara_function());

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(), request, response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response "+obj.getPara_function()+":"+jsondata.toString());	

	}	
	
		
	
	public void setSysAccountDao(SysAccountDaoImpl sysAccountDao) {
		this.sysAccountDao = sysAccountDao;
	}


	public void setSysSharedAccountDao(SysSharedAccountDaoImpl sysSharedAccountDao) {
		this.sysSharedAccountDao = sysSharedAccountDao;
	}	
	
	/**
	 * 读取配置文件获取热点搜索的半径
	 * @return
	 */
	private double getRadius(){
	    String radiusStr = PropertyInfo.getValue("hotspot.search.radius", null);	    
	    return Double.valueOf(radiusStr);	    
	}	
	
	private int getOnlineNumChinaNet() throws Exception{
	    int num = 0;
	    
        //查询chinanet同时在线人数
        String search_sql = "select count(*) as 'num' from tbl_wifi_account where category='CHINANET' and status = 3";
        List list = sysAccountDao.getData(search_sql, new String[]{});
        if (list != null && list.size() > 0){
            Map map = (Map)list.get(0);
            num = map.get("num") == null ? 0 : Integer.valueOf(map.get("num").toString());
        }
        
        return num;
	}
}
