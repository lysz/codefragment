package com.aora.wifi.action.interfaces;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.aora.wifi.action.BaseAction;
import com.aora.wifi.action.ChongZhiAction;
import com.aora.wifi.bean.*;

import com.aora.wifi.dao.impl.SysAccountDaoImpl;
import com.aora.wifi.dao.impl.SysSharedAccountDaoImpl;
import com.aora.wifi.servlet.SysParameter;
import com.aora.wifi.tools.PropertyInfo;
import com.aora.wifi.util.Tools;
import com.aora.wifi.util.cachectl;
import com.lowagie.text.Image;

public class func2xxAction extends BaseBean {


private static SysAccountDaoImpl sysAccountDao=new SysAccountDaoImpl();
	

private final static Logger logger = Logger.getLogger(func1xxAction.class);



private HttpServletRequest request;
private HttpServletResponse response;

public func2xxAction(HttpServletRequest request,HttpServletResponse response){
	this.request=request;
	this.response=response;
}

public void process(requestBean obj) throws Exception{
	switch(Integer.parseInt(obj.getPara_function())){
	case 201:
		this.func201();	
		break;
	case 202:
		this.func202();	
		break;
	case 203:
		this.func203();	
		break;
	case 204:
		this.func204();	
		break;
	case 205:
		this.func205();	
		break;
	case 206:
		this.func206();	
		break;
	case 207:
		this.func207();	
		break;
	case 208:
		this.func208();	
		break;
	case 209:
		this.func209();	
		break;
	case 210:
		this.func210();	
		break;
	case 211:
		this.func211();	
		break;
	case 212:
		this.func212();	
		break;
	case 213:
		this.func213();	
		break;
	case 214:
		this.func214();	
		break;
	case 215:
		this.func215();	
		break;
	case 216:
		this.func216();	
		break;				
	case 217:
		this.func217();	
		break;
	case 218:
		this.func218();	
		break;
	case 219:
		this.func219();	
		break;
	case 220:
		this.func220();	
		break;
	case 221:
		this.func221();	
		break;
	case 222:
		this.func222();	
		break;
	case 223:
		this.func223();	
		break;
	case 224:
		this.func224();	
		break;					
	case 225:
		this.func225();	
		break;
	case 230:
		this.func230();	
		break;
	case 231:
		this.func231();	
		break;	
	case 232:
		this.func232();	
		break;
	case 233:
		this.func233();	
		break;
	case 234:
		this.func234();	
		break;		
	case 235:
		this.func235();	
		break;
	case 236:
		this.func236();	
		break;		
	case 237:
		this.func237();	
		break;
	case 238:
		this.func238();	
		break;			
	case 239:
		this.func239();	
		break;
	case 240:
		this.func240();	
		break;
	case 243:
		this.func243();	
		break;			
	}
	
}



	public void func201() throws Exception{
		func201Req obj=(func201Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		String regcode="";
		List ls=null;			
		try{
			if(obj.getAccount()!=null && obj.getAccount().length()==11 && obj.getAccount().startsWith("1")){
//				ls=this.sysAccountDao.getData("select * from tbl_user_account where account=?  ",new String[]{obj.getAccount()});
//				if(ls==null && ls.size()==0){
					ls=this.sysAccountDao.getData("select count(*) 'cnt' from tbl_sendsms where mobile=? and indate between date_add(now(), interval -30 minute) and now() ",new String[]{obj.getAccount()});	
					if(ls!=null && ls.size()>0){
						Map mp=(Map)ls.get(0);
						if(mp.get("cnt")!=null && Integer.parseInt(mp.get("cnt").toString())<5){
							int random=1+(int)(Math.random()*100000);
							regcode=Tools.lPad(String.valueOf(random), 6, "0");
							
							String smscontent="《WIFI畅游》操作验证码："+regcode+",请在半小时之内使用，超过半小时将失效！";
							smscontent="验证码："+regcode+", 请及时输入，验证码半小时有效。";
							String url="http://smsapi.c123.cn/OpenPlatform/OpenApi?action=sendOnce&ac=1001@500933530001&authkey=F9FE80B75E91FD430E0B81FFF5B5191B&cgid=1754&csid=101&c="+Tools.URLEncode(smscontent, "UTF-8")+"&m="+obj.getAccount();
							byte[] response =Tools.postdata(url, "", null);
							String responsestr=(response==null?"":new String(response));
							if(responsestr.length()>0 && responsestr.indexOf("result=\"1\"")>0){
								resultcode="0";
								msg="success";
								this.sysAccountDao.executedSql("insert into tbl_sendsms(mobile,smscontent,regcode,status,response,indate) values(?,?,?,'0',?,now())", new String[]{obj.getAccount(),smscontent,regcode,responsestr});
								
							}else{
								resultcode="2";
								msg="sms sent faild!";
								msg="短信发送失败!";
								this.sysAccountDao.executedSql("insert into tbl_sendsms(mobile,smscontent,regcode,status,response,indate) values(?,?,?,'2',?,now())", new String[]{obj.getAccount(),smscontent,regcode,responsestr});								
							}
							
							
							
						}else{
							resultcode="1";
							msg="there are more than 5 twice, please try again after half hour.";
							msg="获取验证码过于频繁，请稍后再试!";
						}
						
					}else{
						
					}

//				}else{
//					resultcode="3";
//					msg="this mobile had been registed! ";
//				}
			}else{
				resultcode="-99";
				msg="account is not valaible!";
				msg="帐号无效!";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		regcode="xxx";
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("code", regcode);
		jsondata.put("function", obj.getPara_function());

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 201:"+jsondata.toString());		
		
	}
	
	public void func202() throws Exception{
		func202Req obj=(func202Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		String regcode="";
		List ls=null;			
		try{
			if(obj.getReg_account()==null|| obj.getReg_account().length()==0 ||obj.getReg_password().length()==0 || obj.getReg_code().length()==0){
				resultcode="-1";
				msg="post data is wrong!";
			}else{
				ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=?  ",new String[]{obj.getReg_account()});
				if(ls==null || ls.size()==0){				
					ls=this.sysAccountDao.getData("select * from tbl_sendsms where mobile=? and regcode=? and indate between date_add(now(), interval -30 minute) and now() ",new String[]{obj.getReg_account(),obj.getReg_code()});
					if(ls==null || ls.size()==0){
						resultcode="1";
						msg="操作验证码已失效！";
						
					}else{
						try{
							
							int tmpscore=SysParameter.getInstatnce().getSYS_REGISTER_SCORE();
//							if(obj.getChannel()!=null && (obj.getChannel().trim().equalsIgnoreCase("wandoujia"))){
//								tmpscore=3000;
//								this.sysAccountDao.executedSql("insert into tbl_user_score_additional(account,remark,score,indate) values(?,'注册加送','2900',now())", new String[]{obj.getReg_account()});								
//							}else{
//								tmpscore=SysParameter.getInstatnce().getSYS_REGISTER_SCORE();
//							}
							tmpscore=0;
							this.sysAccountDao.executedSql("insert into tbl_reg_account(account,password,username,status,indate,score,channel,level,alipay_account,chi_name) values(?,?,'','0',now(),"+tmpscore+",?,'1','','')", new String[]{obj.getReg_account(),obj.getReg_password(),obj.getChannel()});
							
							resultcode="0";
							msg="succ";
						}catch(Exception err){
							err.printStackTrace();
							resultcode="2";
							msg="this mobile had been registed.";
							msg="帐号已经注册了！";
							
						}
					}
				}else{
					resultcode="2";
					msg="this mobile had been registed! ";
					msg="帐号已经注册了！";
				}					
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 202:"+jsondata.toString());		
	}
	
	
	public void func203() throws Exception{
		func203Req obj=(func203Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		String regcode="";
		int score=0;
		int flowers=0;
		int signdays=1;
		int signscore=0;
		int issigntoday=0;
		String username="";
		String image="";
		String alipay_account="";
		String chi_name="";
		String level="";
		String groupid="";
		String status="";
		String password="";
		String acc_score="";
		String acc_flowers="";
		String city="";
		String province="";
		String country="";
		String sex="";
		String group_qq="";
		String group_mobile="";
		String group_wx="";
		String isnew="0";
		List ls=null;
		int uid=-1;
		
		try{
			if(obj.getLogin_account()==null|| obj.getLogin_account().length()==0  ){
				
				List lsdata=this.sysAccountDao.getData("select * from tbl_user_account where  uid=? order by reg_account desc limit 1", new String[]{obj.getUID()});
//				String uid="";
				if(lsdata!=null && lsdata.size()>0){
					Map map=(Map)lsdata.get(0);
					score=Integer.parseInt((map.get("score")!=null?map.get("score").toString():"0"));
				}else{
					score=0;
				}
				resultcode="0";
				msg="succ";				

			}else{
				if(obj.getHeader_latitude().trim().length()==0){
					obj.setHeader_latitude("0");
				}
				if(obj.getHeader_longitude().trim().length()==0){
					obj.setHeader_longitude("0");
				}				
				ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=?",new String[]{obj.getLogin_account()});
				if(ls==null || ls.size()==0){
					if(obj.getLogin_type().equals("1")){ // MOBILE LOGIN						
						resultcode="1";
						msg="account not found.";
						msg="帐号有误！";
					}else {
						if(obj.getLogin_type().equals("2")){
							this.sysAccountDao.executedSql("insert into tbl_reg_account(account,password,username,status,score,flowers,indate,image,channel,sex,city,province,country,privilege,unionid,account_type,lat,lng,logindate) values(?,'',?,0,0,0,now(),?,?,?,?,?,?,?,?,2,?,?,now())", 
									new String[]{obj.getLogin_account(),obj.getNickname(),obj.getImage(),obj.getChannel(),obj.getSex(),obj.getCity(),obj.getProvince(),obj.getCountry(),obj.getPrivilege(),obj.getUnionid(),obj.getHeader_latitude(),obj.getHeader_longitude()});
							group_wx=obj.getLogin_account();
							isnew="1";
							resultcode="0";
							msg="succ";
						}else if(obj.getLogin_type().equals("3")){
							this.sysAccountDao.executedSql("insert into tbl_reg_account(account,password,username,status,score,flowers,indate,image,channel,sex,city,province,country,privilege,unionid,account_type,lat,lng,logindate) values(?,'',?,0,0,0,now(),?,?,?,?,?,?,?,?,3,?,?,now())", 
									new String[]{obj.getLogin_account(),obj.getNickname(),obj.getImage(),obj.getChannel(),obj.getSex(),obj.getCity(),obj.getProvince(),obj.getCountry(),obj.getPrivilege(),obj.getUnionid(),obj.getHeader_latitude(),obj.getHeader_longitude()});						
							group_qq=obj.getLogin_account();
							isnew="1";							
							resultcode="0";
							msg="succ";
						}else {
							resultcode="4";
							msg="请求有误！";							
						}
						groupid="";
						status="0";
						password="";
						acc_score="0";
						acc_flowers="0";
						
						//username=obj.getNickname();
						//image=obj.getImage();
						alipay_account="";
						chi_name="";
						uid=-1;
						level="";							
						if(resultcode.equals("0")){
							List lsdata=this.sysAccountDao.getData("select * from tbl_user_account where  uid=?  limit 1", new String[]{obj.getUID()});
							int uid_score=0;
							int uid_flowers=0;
							if(lsdata!=null && lsdata.size()>0){
								Map map=(Map)lsdata.get(0);
								uid_score=Integer.parseInt((map.get("score")!=null?map.get("score").toString():"0"));
								uid_flowers=Integer.parseInt((map.get("flowers")!=null?map.get("flowers").toString():"0"));
							}else{
								uid_score=0;
								uid_flowers=0;
							}
							
							sysAccountDao.executedSql("update tbl_user_account set score=0,flowers=0 where uid=?", new String[]{obj.getUID()});
							sysAccountDao.executedSql("update tbl_wifi_share_account_logs set remark=uid,uid='',reg_account=? where uid=?", new String[]{obj.getLogin_account(),obj.getUID()});
							sysAccountDao.executedSql("update tbl_wifi_share_login_logs set remark=uid,uid='',account=? where uid=?", new String[]{obj.getLogin_account(),obj.getUID()});
							
							score=Integer.parseInt(acc_score);
							score=score+uid_score;
							flowers=Integer.parseInt(acc_flowers)+uid_flowers;
							sysAccountDao.executedSql("update tbl_reg_account set score=score+"+uid_score+",flowers=flowers+"+uid_flowers+",lat="+obj.getHeader_latitude()+",lng="+obj.getHeader_longitude()+",logindate=now() where account=?", new String[]{obj.getLogin_account()});

							if(obj.getNickname().length()>0){username=obj.getNickname();}
							if(obj.getImage().length()>0){image=obj.getImage();}
							if(obj.getSex().length()>0){sex=obj.getSex();}
							if(obj.getCity().length()>0){city=obj.getCity();}
							if(obj.getProvince().length()>0){province=obj.getProvince();}
							if(obj.getCountry().length()>0){country=obj.getCountry();}							
							
						}
				
					}
				}else{
					Map mp=(Map)ls.get(0);
					groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
					status=(mp.get("status")==null?"":mp.get("status").toString());
					password=(mp.get("password")==null?"":mp.get("password").toString());
					acc_score=(mp.get("score")!=null?mp.get("score").toString():"0");
					acc_flowers=(mp.get("flowers")!=null?mp.get("flowers").toString():"0");
					
					username=(mp.get("username")!=null?mp.get("username").toString():"");

					image=(mp.get("image")==null?"":(mp.get("image").toString().startsWith("http")?mp.get("image").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+mp.get("image").toString()));
					alipay_account=(mp.get("alipay_account")!=null?mp.get("alipay_account").toString():"");
					chi_name=(mp.get("chi_name")!=null?mp.get("chi_name").toString():"");
					uid=Integer.parseInt((mp.get("id")!=null?mp.get("id").toString():"-1"));
					level=(mp.get("level")!=null?mp.get("level").toString():"");					
					sex=(mp.get("sex")!=null?mp.get("sex").toString():"");
					city=(mp.get("city")!=null?mp.get("city").toString():"");
					province=(mp.get("province")!=null?mp.get("province").toString():"");
					country=(mp.get("country")!=null?mp.get("country").toString():"");
					
						if(obj.getLogin_type().equals("1")){ // MOBILE LOGIN					
									if(!status.equalsIgnoreCase("0")){
										resultcode="2";
										msg="账号已锁定。如需申诉，请加官方申诉QQ群：390744849.";
									}else{
										if(!password.equalsIgnoreCase(obj.getLogin_password())){
											resultcode="3";
											msg="password is in-correct.";
											msg="帐号密码错误！";
										}else{
											group_mobile=obj.getLogin_account();
											resultcode="0";
											msg="succ";
										}
									}
						}else if(obj.getLogin_type().equals("2")){ //WX login
										if(!status.equalsIgnoreCase("0")){
											resultcode="2";
											msg="账号已锁定。如需申诉，请加官方申诉QQ群：390744849.";
										}else{
											resultcode="0";
											msg="succ";								
										}
						}else if(obj.getLogin_type().equals("3")){ //QQ login
										if(!status.equalsIgnoreCase("0")){
											resultcode="2";
											msg="账号已锁定。如需申诉，请加官方申诉QQ群：390744849.";
										}else{
											resultcode="0";
											msg="succ";								
										}							
						}else{
							
										resultcode="4";
										msg="请求有误！";								
						}
						
						if(resultcode.equals("0")){
								List lsdata=this.sysAccountDao.getData("select * from tbl_user_account where  uid=?  limit 1", new String[]{obj.getUID()});
								int uid_score=0;
								int uid_flowers=0;
								if(lsdata!=null && lsdata.size()>0){
									Map map=(Map)lsdata.get(0);
									uid_score=Integer.parseInt((map.get("score")!=null?map.get("score").toString():"0"));
									uid_flowers=Integer.parseInt((map.get("flowers")!=null?map.get("flowers").toString():"0"));
								}else{
									uid_score=0;
									uid_flowers=0;
								}
								
								sysAccountDao.executedSql("update tbl_user_account set score=0,flowers=0 where uid=?", new String[]{obj.getUID()});
								sysAccountDao.executedSql("update tbl_wifi_share_account_logs set remark=uid,uid='',reg_account=? where uid=?", new String[]{obj.getLogin_account(),obj.getUID()});
								sysAccountDao.executedSql("update tbl_wifi_share_login_logs set remark=uid,uid='',account=? where uid=?", new String[]{obj.getLogin_account(),obj.getUID()});
								
								if(groupid.length()==0){
									sysAccountDao.executedSql("update tbl_reg_account set score=score+"+uid_score+",flowers=flowers+"+uid_flowers+",lat="+obj.getHeader_latitude()+",lng="+obj.getHeader_longitude()+",logindate=now() where account=?", new String[]{obj.getLogin_account()});
									score=Integer.parseInt(acc_score);
									score=score+uid_score;
									flowers=Integer.parseInt(acc_flowers)+uid_flowers;
									if(obj.getLogin_type().equals("1")){group_mobile=obj.getLogin_account();}
									else if(obj.getLogin_type().equals("2")){group_wx=obj.getLogin_account();}
									else if(obj.getLogin_type().equals("3")){group_qq=obj.getLogin_account();}	
									
								}else{
									lsdata=this.sysAccountDao.getData("select * from tbl_reg_account where groupid=? and status=0", new String[]{groupid});
									if(lsdata!=null && lsdata.size()>0){
										score=0;
										for(int i=0;i<lsdata.size();i++){
											Map map=(Map)lsdata.get(i);
											score+=Integer.parseInt((map.get("score")!=null?map.get("score").toString():"0"));
											if(map.get("account_type")!=null && map.get("account_type").toString().equals("1")){group_mobile=(map.get("account")!=null? map.get("account").toString():"");}
											else if(map.get("account_type")!=null && map.get("account_type").toString().equals("2")){group_wx=(map.get("account")!=null? map.get("account").toString():"");}
											else if(map.get("account_type")!=null && map.get("account_type").toString().equals("3")){group_qq=(map.get("account")!=null? map.get("account").toString():"");}
										}
										
									}else{
										score=0;
									}									
									score=score+uid_score;
									sysAccountDao.executedSql("update tbl_reg_account a cross join (select sum(ifnull(score,0))+"+uid_score+" 'score' ,sum(ifnull(flowers,0))+"+uid_score+" 'flowers' from tbl_reg_account where groupid=?)  b  set  a.score=b.score,a.flowers=b.flowers,a.lat="+obj.getHeader_latitude()+",a.lng="+obj.getHeader_longitude()+",a.logindate=now() where a.account=?",new String[]{groupid,obj.getLogin_account()});
									sysAccountDao.executedSql("update tbl_reg_account set  remark=score, score=0,flowers=0  where groupid=? and account!=?;",new String[]{groupid,obj.getLogin_account()});
									obj.setLogin_account(groupid+":"+obj.getLogin_account()+":"+obj.getLogin_type());	
								}
							
						}
					}
				}				
				
				
				//resultcode="0";
				//msg="succ";

	
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		DecimalFormat df = new DecimalFormat("0.00");  
		double dscore = (double)score;  
		jsondata.put("score", df.format(dscore/100).toString()+"元");
		jsondata.put("flowers", String.valueOf(flowers));
		jsondata.put("nickname", username);
		jsondata.put("username", obj.getLogin_account());
		jsondata.put("level", level);
		jsondata.put("image", image);
		jsondata.put("alipay_account", alipay_account);
		jsondata.put("chi_name", chi_name);
		jsondata.put("userid", uid);
		jsondata.put("city", city);
		jsondata.put("province", province);	
		jsondata.put("country", country);
		jsondata.put("sex", sex);	
		jsondata.put("group_mobile", group_mobile);
		jsondata.put("group_wx", group_wx);
		jsondata.put("group_qq", group_qq);
		jsondata.put("isnew", isnew);
		
		jsondata.put("function", obj.getPara_function());
		
		
//		jsondata.put("signdays", signdays);
//		jsondata.put("signscore", signscore);

//		jsondata.put("issigntoday", issigntoday);
		

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 203:"+jsondata.toString());		
	}
		

	
	public void func204() throws Exception{

	}

	public void func205() throws Exception{
		func205Req obj=(func205Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		String regcode="";
		List ls=null;			
		try{
			if(obj.getReset_account()==null|| obj.getReset_account().length()==0 ||obj.getReset_password().length()==0 || obj.getReset_code().length()==0){
				resultcode="-1";
				msg="post data is wrong!";
			}else{
				ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=?  ",new String[]{obj.getReset_account()});
				if(ls==null || ls.size()==0){				
					resultcode="2";
					msg="account had not been registed! ";
				}else{
					ls=this.sysAccountDao.getData("select * from tbl_sendsms where mobile=? and regcode=? and indate between date_add(now(), interval -30 minute) and now() ",new String[]{obj.getReset_account(),obj.getReset_code()});
					if(ls==null || ls.size()==0){
						resultcode="1";
						msg="regist code is expired.";
						
					}else{
						try{
							this.sysAccountDao.executedSql("update tbl_reg_account set password=? where account=?", new String[]{obj.getReset_password(),obj.getReset_account()});
							resultcode="0";
							msg="succ";
						}catch(Exception err){
							err.printStackTrace();
							resultcode="3";
							msg="update faild please contact admin!";
							
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
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 205:"+jsondata.toString());			
	}

	public void func206() throws Exception{
		func206Req obj=(func206Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		String regcode="";
		List ls=null;			
		try{
			if(obj.getLogout_account()==null|| obj.getLogout_account().length()==0 ||obj.getLogout_password().length()==0 ){
				resultcode="-1";
				msg="post data is wrong!";
			}else{
				ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=?  ",new String[]{obj.getLogout_account()});
				if(ls==null || ls.size()==0){				
					resultcode="2";
					msg="account had not been registed! ";
				}else{
					Map mp=(Map)ls.get(0);
					if(!mp.get("password").toString().equalsIgnoreCase(obj.getLogout_password())){
						resultcode="1";
						msg="password is faild.";						
					}else{
						resultcode="0";
						msg="succ";						
						
						String header=(obj.getHeader_version()==null?"|":obj.getHeader_version()+"|");
						header+=(obj.getUID()==null?"|":obj.getUID()+"|");
						header+=(obj.getHeader_imei()==null?"|":obj.getHeader_imei()+"|");
						header+=(obj.getHeader_imsi()==null?"|":obj.getHeader_imsi()+"|");
						header+=("");
						
						//addCookie(response, "WIFI_CLIENT_INFO", header);						
						
						//this.sysAccountDao.executedSql("update tbl_user_login_log set logoutdate=now() where account=? and indate> date_add(now(), interval -120 minute) and logoutdate is null " , new String[]{obj.getLogout_account()});						
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
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 206:"+jsondata.toString());
	}
			
	public void func207() throws Exception{
		func207Req obj=(func207Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		String regcode="";
		String image="";
		List ls=null;			
		try{
			if(obj.getLogin_account()==null|| obj.getLogin_account().length()==0){
				resultcode="-1";
				msg="post data is wrong!";
			}else{
				ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=?  ",new String[]{obj.getLogin_account()});
				if(ls==null || ls.size()==0){				
					resultcode="2";
					msg="account had not been registed! ";
				}else{
					Map mp=(Map)ls.get(0);

					if(obj.getGroupid().length()==0){
						try{
 
							System.out.println(obj.getModify_image());
							
							
							List para=new ArrayList();
							String sql="update tbl_reg_account set account=?";
							para.add(obj.getLogin_account());
							
							if(obj.getModify_name()!=null && obj.getModify_name().length()>0 ){
								sql+=",username=?";
								para.add(obj.getModify_name());
							}
							if(obj.getModify_sex().length()>0){
								sql+=",sex=?";
								para.add(obj.getModify_sex());
								
							}
							if(obj.getModify_city().length()>0){
								sql+=",city=?";
								para.add(obj.getModify_city());
								
							}
							
							if(obj.getModify_province().length()>0){
								sql+=",province=?";
								para.add(obj.getModify_province());
								
							}
							
							
							if(obj.getModify_image()!=null && obj.getModify_image().length()>0){
								 BASE64Decoder base64d = new BASE64Decoder();
								 byte[] buf=base64d.decodeBuffer(obj.getModify_image());
								 image=obj.getLogin_account()+"_"+(1+(int)(Math.random()*100))+".jpg";
								 FileOutputStream fos=new java.io.FileOutputStream(request.getRealPath("/").replaceAll("\\\\", "/")+"/images/user/"+ image);
								 fos.write(buf);
								 fos.close();

								 sql+=",image=?";
								 para.add(image);

								 
							}else{
								//image=(mp.get("image")==null?"":mp.get("image").toString());
							}
							
							sql+=" where account=?";
							para.add(obj.getLogin_account());
							
							this.sysAccountDao.executedSql(sql, para.toArray());
							
							resultcode="0";
							msg="succ";
						}catch(Exception err){
							err.printStackTrace();
							resultcode="3";
							msg="update faild please contact admin!";
							
						}
					}else{
						try{
							 
							System.out.println(obj.getModify_image());
							
							
							List para=new ArrayList();
							String sql="update tbl_reg_account set groupid=?";
							para.add(obj.getGroupid());
							
							if(obj.getModify_name()!=null && obj.getModify_name().length()>0 ){
								sql+=",username=?";
								para.add(obj.getModify_name());
							}
							if(obj.getModify_sex().length()>0){
								sql+=",sex=?";
								para.add(obj.getModify_sex());
								
							}
							if(obj.getModify_city().length()>0){
								sql+=",city=?";
								para.add(obj.getModify_city());
								
							}
							
							if(obj.getModify_province().length()>0){
								sql+=",province=?";
								para.add(obj.getModify_province());
								
							}
							
							
							if(obj.getModify_image()!=null && obj.getModify_image().length()>0){
								 BASE64Decoder base64d = new BASE64Decoder();
								 byte[] buf=base64d.decodeBuffer(obj.getModify_image());
								 image=obj.getLogin_account()+"_"+(1+(int)(Math.random()*100))+".jpg";
								 FileOutputStream fos=new java.io.FileOutputStream(request.getRealPath("/").replaceAll("\\\\", "/")+"/images/user/"+ image);
								 fos.write(buf);
								 fos.close();

								 sql+=",image=?";
								 para.add(image);

								 
							}else{
								//image=(mp.get("image")==null?"":mp.get("image").toString());
							}
							
							sql+=" where groupid=?";
							para.add(obj.getGroupid());
							
							this.sysAccountDao.executedSql(sql, para.toArray());
							
							resultcode="0";
							msg="succ";
						}catch(Exception err){
							err.printStackTrace();
							resultcode="3";
							msg="update faild please contact admin!";
							
						}						
						
					}
				}					
									
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("image", (image.length()==0?"":PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+image));
		jsondata.put("nickname", (resultcode.equalsIgnoreCase("0")?obj.getModify_name():""));
		jsondata.put("sex", (resultcode.equalsIgnoreCase("0")?obj.getModify_sex():""));
		jsondata.put("city", (resultcode.equalsIgnoreCase("0")?obj.getModify_city():""));
		jsondata.put("province", (resultcode.equalsIgnoreCase("0")?obj.getModify_province():""));
		jsondata.put("function", obj.getPara_function());

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 207:"+jsondata.toString());
		
		
	}	
	
	
	public void func208() throws Exception{

	}
	
	
	
	public void func209() throws Exception{

				
	}
	
	public void func224() throws Exception{

	
	}
		
	
	
	public void func210() throws Exception{
		func210Req obj=(func210Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		int signday=-1;
		int score=-1;
		int days=30;
		
		List ls=null;			
		try{
			
			
//			if(obj.getAccount()!=null && obj.getAccount().trim().length()==11){
//				ls=this.sysAccountDao.getData("select * from tbl_user_account where account=?",new String[]{obj.getAccount()});
//				if(ls==null || ls.size()==0){
//					resultcode="1";
//					msg="account not found.";
//					
//				}else{
//					Map mp=(Map)ls.get(0);
//					score=Integer.parseInt((mp.get("score")==null?"0":mp.get("score").toString()));
//					signday=Integer.parseInt((mp.get("signdays")==null?"0":mp.get("signdays").toString()));
					String	sql="";
						if(obj.getAccount().trim().length()==0){	
							sql="select * from tbl_user_question where (uid=?) and indate between date_add(now(), interval -150 day) and now() order by id ";
							ls=this.sysAccountDao.getData(sql,new String[]{obj.getUID()});							
						}else{
							
							sql="select * from tbl_user_question where (user=?) and indate between date_add(now(), interval -150 day) and now()order by id  ";
							ls=this.sysAccountDao.getData(sql,new String[]{obj.getAccount()});
						}
					
					
					for(int i=0;i<ls.size();i++){
						Map map=(Map)ls.get(i);
						//System.out.println(map.toString()+":"+map.get("indate"));
						JSONObject item=new JSONObject();	
						item.put("date",  map.get("indate")==null?"0":map.get("indate").toString());
						item.put("ask", map.get("ask")==null?"0":map.get("ask").toString());
						item.put("answer", map.get("answer")==null?"":map.get("answer").toString());
						item.put("contact", map.get("contact")==null?"":map.get("contact").toString());
						
						jsonarray.add(item);						
						
					}
					resultcode="0";
					msg="succ";
//				}

				
				
//			}else{
//				resultcode="-1";
//				msg="invalidate account.";
//			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		jsondata.put("data", jsonarray);

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 210:"+jsondata.toString());		
	}
	
	public void func211() throws Exception{
		func211Req obj=(func211Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		int signday=-1;
		int score=-1;
		int days=30;
		
		List ls=null;			
		try{
			
			
			if(obj.getAsk().length()>0){
////				if(obj.getAccount().trim().length()==0){
////					ls=this.sysAccountDao.getData("select * from tbl_user_account where uid=? ",new String[]{obj.getUID()});
////				}else{
////					ls=this.sysAccountDao.getData("select * from tbl_user_account where account=? ",new String[]{obj.getAccount()});
////				}
////				if(ls==null || ls.size()==0){
////					resultcode="1";
////					msg="account not found.";
////					
////				}else{
//					Map mp=(Map)ls.get(0);
//					score=Integer.parseInt((mp.get("score")==null?"0":mp.get("score").toString()));
//					signday=Integer.parseInt((mp.get("signdays")==null?"0":mp.get("signdays").toString()));
//					
					this.sysAccountDao.executedSql("insert into tbl_user_question(user,ask,contact,indate,uid,channel) values(?,?,?,now(),?,?)", new String[]{obj.getAccount(),obj.getAsk(),(obj.getContact()==null?"":obj.getContact()),obj.getUID(),obj.getChannel()});
					
					resultcode="0";
					msg="succ";
//				}

				
				
			}else{
				resultcode="-1";
				msg="post parameter error!";
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());


		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 211:"+jsondata.toString());		
	}	
	
	public void func212() throws Exception{
		func212Req obj=(func212Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		int signday=-1;
		int score=-1;
		int days=30;
		
		String description="";
		
		List ls=null;			
		try{
			
			
			if(obj.getAccount()!=null  && obj.getGroupid().length()==0){
				ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=? limit 5",new String[]{obj.getAccount()});
				if(ls!=null && ls.size()>0){
					Map mp=(Map)ls.get(0);
					score=Integer.parseInt((mp.get("score")==null?"0":mp.get("score").toString()));

					String sql=" ";
					

					sql="select a.id,a.status,a.longitude,a.latitude,a.account,a.wifi_gps_address,a.wifi_address,a.mac_address,a.wifi_type,a.createby_login_id,b.score ,b.id 'tid',b.indate, case when a.status>0 and a.createby_client_id=b.remark then '1' else '0' end 'active' from tbl_wifi_share_account a,tbl_wifi_share_account_logs b where a.mac_address=b.mac_address  and b.reg_account='"+obj.getAccount()+"' order by b.indate desc ";
					
					
					ls=this.sysAccountDao.getData(sql,new String[]{});
					for(int i=0;i<ls.size();i++){
						Map map=(Map)ls.get(i);
						
						JSONObject item=new JSONObject();	
						
						
						item.put("longitude", map.get("longitude")==null?"0":map.get("longitude").toString());
						item.put("latitude", map.get("latitude")==null?"":map.get("latitude").toString());				
						item.put("name", map.get("account")==null?"":map.get("account").toString());
						item.put("gps_address", map.get("wifi_gps_address")==null?"":map.get("wifi_gps_address").toString());
						item.put("address", map.get("wifi_address")==null?"":map.get("wifi_address").toString());

						int scored=Integer.parseInt((map.get("score")==null?"0":map.get("score").toString()));
						DecimalFormat df = new DecimalFormat("0.00");  
						double dscore = (double)scored;  
						item.put("score_count", df.format(dscore/100).toString()+"元");
						
						//item.put("score_count", map.get("score")==null?"0":map.get("score").toString());
						item.put("mac", map.get("mac_address")==null?"":map.get("mac_address").toString());
						item.put("type", map.get("wifi_type")==null?"":map.get("wifi_type").toString());
						item.put("indate", map.get("indate")==null?"":map.get("indate").toString().subSequence(0,10));
						item.put("active", map.get("active")==null?"0":map.get("active").toString());
						
						jsonarray.add(item);						
						
					}
					
					resultcode="0";
					msg="succ";
				}
				
			}else if(obj.getGroupid().length()>0){
				ls=this.sysAccountDao.getData("select * from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupid()});
				String user="";
				if(ls!=null && ls.size()>0){
					for(int i=0;i<ls.size();i++){
						Map mp=(Map)ls.get(i);
						user+="'"+(mp.get("account")==null?"":mp.get("account").toString())+"',";
					}

					if(user.length()>0){user=user.substring(0,user.length()-1);}					
					String sql=" ";
					sql="select a.id,a.status,a.longitude,a.latitude,a.account,a.wifi_gps_address,a.wifi_address,a.mac_address,a.wifi_type,a.createby_login_id,b.score ,b.id 'tid',b.indate, case when a.status>0 and a.createby_client_id=b.remark then '1' else '0' end 'active' from tbl_wifi_share_account a,tbl_wifi_share_account_logs b where a.mac_address=b.mac_address  and b.reg_account in ("+user+") order by b.indate desc ";
					
					System.out.println(sql);
					
					ls=this.sysAccountDao.getData(sql,new String[]{});
					for(int i=0;i<ls.size();i++){
						Map map=(Map)ls.get(i);
						
						JSONObject item=new JSONObject();	
						
						
						item.put("longitude", map.get("longitude")==null?"0":map.get("longitude").toString());
						item.put("latitude", map.get("latitude")==null?"":map.get("latitude").toString());				
						item.put("name", map.get("account")==null?"":map.get("account").toString());
						item.put("gps_address", map.get("wifi_gps_address")==null?"":map.get("wifi_gps_address").toString());
						item.put("address", map.get("wifi_address")==null?"":map.get("wifi_address").toString());

						int scored=Integer.parseInt((map.get("score")==null?"0":map.get("score").toString()));
						DecimalFormat df = new DecimalFormat("0.00");  
						double dscore = (double)scored;  
						item.put("score_count", df.format(dscore/100).toString()+"元");
						
						//item.put("score_count", map.get("score")==null?"0":map.get("score").toString());
						item.put("mac", map.get("mac_address")==null?"":map.get("mac_address").toString());
						item.put("type", map.get("wifi_type")==null?"":map.get("wifi_type").toString());
						item.put("indate", map.get("indate")==null?"":map.get("indate").toString().subSequence(0,10));
						item.put("active", map.get("active")==null?"0":map.get("active").toString());
						
						jsonarray.add(item);						
						
					}
					
					resultcode="0";
					msg="succ";					
					
					
					
				}
				
			}else{
				ls=this.sysAccountDao.getData("select * from tbl_user_account where uid=? limit 5",new String[]{obj.getUID()});
				if(ls!=null && ls.size()>0){
					Map mp=(Map)ls.get(0);
					score=Integer.parseInt((mp.get("score")==null?"0":mp.get("score").toString()));
					//signday=Integer.parseInt((mp.get("signdays")==null?"0":mp.get("signdays").toString()));
					
					String sql=" ";
					
					sql="select longitude,latitude,account,wifi_gps_address,wifi_address,mac_address,wifi_type, sum(share_score) 'score',min(indate) 'indate' from ( " +
					//	"  select a.id,a.status,a.longitude,a.latitude,a.wifi_type,a.account,a.wifi_gps_address,a.wifi_address,a.mac_address,a.createby_login_id,b.share_account,b.share_score,b.id 'tid',a.indate from tbl_wifi_share_account a, tbl_wifi_share_login_logs b where a.mac_address=b.mac_address and b.share_uid='"+obj.getUID()+"'" + 
					//	"  union " +
						"  select a.id,a.status,a.longitude,a.latitude,a.wifi_type,a.account,a.wifi_gps_address,a.wifi_address,a.mac_address,a.createby_login_id,'',b.score 'share_score',b.id 'tid',a.indate from tbl_wifi_share_account a,tbl_wifi_share_account_logs b where a.mac_address=b.mac_address and a.status>0 and a.createby_client_id=b.uid  and b.uid='"+obj.getUID()+"'" +
						" ) x group by longitude,latitude,wifi_gps_address,wifi_address,mac_address,wifi_type order by indate desc;";
					
					sql="select a.id,a.status,a.longitude,a.latitude,a.account,a.wifi_gps_address,a.wifi_address,a.mac_address,a.wifi_type,a.createby_login_id,b.score ,b.id 'tid',b.indate, case when a.status>0 and a.createby_client_id=b.uid then '1' else '0' end 'active' from tbl_wifi_share_account a,tbl_wifi_share_account_logs b where a.mac_address=b.mac_address  and b.uid='"+obj.getUID()+"' order by b.indate desc ";
					
					
					ls=this.sysAccountDao.getData(sql,new String[]{});
					for(int i=0;i<ls.size();i++){
						Map map=(Map)ls.get(i);
						
						JSONObject item=new JSONObject();	
						
						
						item.put("longitude", map.get("longitude")==null?"0":map.get("longitude").toString());
						item.put("latitude", map.get("latitude")==null?"":map.get("latitude").toString());				
						item.put("name", map.get("account")==null?"":map.get("account").toString());
						item.put("gps_address", map.get("wifi_gps_address")==null?"":map.get("wifi_gps_address").toString());
						item.put("address", map.get("wifi_address")==null?"":map.get("wifi_address").toString());

						int scored=Integer.parseInt((map.get("score")==null?"0":map.get("score").toString()));
						DecimalFormat df = new DecimalFormat("0.00");  
						double dscore = (double)scored;  
						item.put("score_count", df.format(dscore/100).toString()+"元");
						
						//item.put("score_count", map.get("score")==null?"0":map.get("score").toString());
						item.put("mac", map.get("mac_address")==null?"":map.get("mac_address").toString());
						item.put("type", map.get("wifi_type")==null?"":map.get("wifi_type").toString());
						item.put("indate", map.get("indate")==null?"":map.get("indate").toString().subSequence(0,10));
						item.put("active", map.get("active")==null?"0":map.get("active").toString());
						
						jsonarray.add(item);						
						
					}
					
					resultcode="0";
					msg="succ";
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String tips="温馨提示：\n从2014-12-5 至 2014-12-30 共享一个wifi热点密码可获得0.60元现金红包（提现功能，近期开放，敬请期待）.";
		if(SysParameter.getInstatnce().getParams().get("my_occupy_wifi_tips")!=null){
			tips=SysParameter.getInstatnce().getParams().get("my_occupy_wifi_tips").toString();
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		jsondata.put("tips", tips);
		
		

		jsondata.put("data", jsonarray);
		


		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 212:"+jsondata.toString());		
	}	

	
	public void func213() throws Exception{
		func213Req obj=(func213Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";

		int score=-1;
		int money=-1;
		int price=0;
		int min_score=0;
		int max_score=0;
		int min_qb=0;
		int max_qb=0;
		int currentdate_qq_coin=0;
		int currentdate_huafei_coin=0;
		int per_day_qq_coin_stock=10000;
		int per_day_telephone_coin_stack =100000;
		
		
		String alipay_account="";
		String chi_name="";
		String tips="";
		String caption="";
		
		List ls=null;			
		try{
			
			price=Integer.valueOf(SysParameter.getInstatnce().getParams().get("occupy_wifi_present_coin").toString());
			tips="温馨提示：\n从2014-12-5 至 2014-12-30 共享一个wifi热点密码可获得0.60元现金红包（提现功能，近期开放，敬请期待）.";
			if(SysParameter.getInstatnce().getParams().get("my_occupy_wifi_tips")!=null){
				tips=SysParameter.getInstatnce().getParams().get("my_occupy_wifi_tips").toString();
			}			
			if(SysParameter.getInstatnce().getParams().get("my_occupy_wifi_tips")!=null){
				caption=SysParameter.getInstatnce().getParams().get("my_occupy_wifi_caption").toString();
			}	
			
			if(SysParameter.getInstatnce().getParams().get("cash_min_score")!=null){
				min_score=Integer.valueOf(SysParameter.getInstatnce().getParams().get("cash_min_score").toString());
			}			
			if(SysParameter.getInstatnce().getParams().get("cash_max_score")!=null){
				max_score=Integer.valueOf(SysParameter.getInstatnce().getParams().get("cash_max_score").toString());
			}	
			
			if(SysParameter.getInstatnce().getParams().get("cash_min_qb")!=null){
				min_qb=Integer.valueOf(SysParameter.getInstatnce().getParams().get("cash_min_qb").toString());
			}			
			if(SysParameter.getInstatnce().getParams().get("cash_max_qb")!=null){
				max_qb=Integer.valueOf(SysParameter.getInstatnce().getParams().get("cash_max_qb").toString());
			}	
			
			if(SysParameter.getInstatnce().getParams().get("per_day_qq_coin_stock")!=null){
				per_day_qq_coin_stock=Integer.valueOf(SysParameter.getInstatnce().getParams().get("per_day_qq_coin_stock").toString());
			}			
            			
			if(SysParameter.getInstatnce().getParams().get("per_day_telephone_coin_stack")!=null){
				per_day_telephone_coin_stack=Integer.valueOf(SysParameter.getInstatnce().getParams().get("per_day_telephone_coin_stack").toString());
			}		

		
			
			
			if(obj.getAccount().length()>0){

				ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=?",new String[]{obj.getAccount()});
				if(ls==null || ls.size()==0){
					resultcode="1";
					msg="account not found.";
					msg="帐号有误！";
				}else{
					Map mp=(Map)ls.get(0);
					score=Integer.parseInt((mp.get("score")==null?"0":mp.get("score").toString()));
					alipay_account=(mp.get("alipay_account")!=null?mp.get("alipay_account").toString():"");
					chi_name=(mp.get("chi_name")!=null?mp.get("chi_name").toString():"");
					
					if(obj.getGroupid().length()>0){
						ls=this.sysAccountDao.getData("select * from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupid()});
						String user="";
						if(ls!=null && ls.size()>0){
							for(int i=0;i<ls.size();i++){
								Map map=(Map)ls.get(i);
								user+="'"+(map.get("account")==null?"":map.get("account").toString())+"',";
								if(map.get("account_type").toString().equals("1")){
									alipay_account=(map.get("alipay_account")!=null?map.get("alipay_account").toString():"");
									chi_name=(map.get("chi_name")!=null?map.get("chi_name").toString():"");
																		
								}
							}

							if(user.length()>0){user=user.substring(0,user.length()-1);}
							ls=this.sysAccountDao.getData("select ifnull(sum(money),0) 'money' from tbl_cash_logs where account in ("+user+")",new String[]{});
							mp=(Map)ls.get(0);
							money=Integer.parseInt((mp.get("money")==null?"0":mp.get("money").toString()));
						}else{
							money=0;
						}
						
					}else{
						ls=this.sysAccountDao.getData("select ifnull(sum(money),0) 'money' from tbl_cash_logs where account=?",new String[]{obj.getAccount()});
						mp=(Map)ls.get(0);
						money=Integer.parseInt((mp.get("money")==null?"0":mp.get("money").toString()));
					}
					
		            String sql = "select sum( price ) as 'total' from tbl_chongzhi  where category = 1 and (status = 0 or status = 2) and date(createtime)=curdate()";
		            List list = sysAccountDao.getData(sql, new String[]{});
		            if (null != list &&  !list.isEmpty()){
		                Map map = (Map)list.get(0);
		                currentdate_qq_coin = (map.get("total") == null ? 0 : Integer.valueOf(map.get("total").toString()));
		            }					
					
		            sql = "select sum( price ) as 'total' from tbl_chongzhi  where category = 2 and (status = 0 or status = 2) and date(createtime)=curdate()";
		            list = sysAccountDao.getData(sql, new String[]{});
		            if (null != list &&  !list.isEmpty()){
		                Map map = (Map)list.get(0);
		                currentdate_huafei_coin = (map.get("total") == null ? 0 : Integer.valueOf(map.get("total").toString()));
		            }			            
					
					//if(!mp.get("status").toString().equalsIgnoreCase("0")){
					
					
					resultcode="0";
					msg="succ";
				}
				
			}else{
				resultcode="-1";
				msg="post parameter error!";
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		DecimalFormat df = new DecimalFormat("0.00");  
		double dscore = (double)score;  
		jsondata.put("price", df.format(((double)price)/100).toString());		
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		
		jsondata.put("alipay_account", alipay_account);
		jsondata.put("chi_name", chi_name);
		
		jsondata.put("min_score", df.format(((double)min_score)/100));
		jsondata.put("max_score", df.format(((double)max_score)/100));
		jsondata.put("inventory_huafei",  df.format(((double)(per_day_telephone_coin_stack-currentdate_huafei_coin<0?0:per_day_telephone_coin_stack-currentdate_huafei_coin))/100));
		jsondata.put("min_qb",  df.format(((double)min_qb)/100));
		jsondata.put("max_qb",  df.format(((double)max_qb)/100));		
		jsondata.put("inventory_qb",  df.format(((double)(per_day_qq_coin_stock-currentdate_qq_coin<0?0:per_day_qq_coin_stock-currentdate_qq_coin))/100));
			
		
		jsondata.put("score", df.format(((double)score)/100));
		jsondata.put("active_score",df.format(((double)money)/100) );	
		jsondata.put("tips",tips );
		jsondata.put("caption",caption);
		
		jsondata.put("function", obj.getPara_function());


		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 213:"+jsondata.toString());	
		
	}	
	
	public void func214() throws Exception{
		func214Req obj=(func214Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";

		int score=-1;
		int max_cash_money=1000000;
		int money=0;
		int min_score=0;
		int max_score=0;
		String status="0";
		String alipay_account="";
		String chi_name="";
		String tips="";
		List ls=null;
		
		if(SysParameter.getInstatnce().getParams().get("per_day_allow_cash_maximum")!=null){
			max_cash_money=Integer.parseInt(SysParameter.getInstatnce().getParams().get("per_day_allow_cash_maximum").toString());
		}
		if(SysParameter.getInstatnce().getParams().get("cash_min_score")!=null){
			min_score=Integer.valueOf(SysParameter.getInstatnce().getParams().get("cash_min_score").toString());
		}			
		if(SysParameter.getInstatnce().getParams().get("cash_max_score")!=null){
			max_score=Integer.valueOf(SysParameter.getInstatnce().getParams().get("cash_max_score").toString());
		}		
		
		try{

			if(obj.getAccount().length()>0){
			  synchronized (Tools.synchronized_214_obj){ 
				ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=?",new String[]{obj.getAccount()});
				if(ls==null || ls.size()==0){
					resultcode="1";
					msg="account not found.";
					msg="帐号有误！";
				}else{
					Map mp=(Map)ls.get(0);
					score=Integer.parseInt((mp.get("score")==null?"0":mp.get("score").toString()));
					alipay_account=(mp.get("alipay_account")!=null&& mp.get("alipay_account").toString().length()>5?mp.get("alipay_account").toString():obj.getAlipay_account());
					chi_name=(mp.get("chi_name")!=null && mp.get("chi_name").toString().length()>0?mp.get("chi_name").toString():obj.getChi_name());
					status=(mp.get("status")!=null?mp.get("status").toString():"0");;
					
					if(!alipay_account.equalsIgnoreCase(obj.getAlipay_account()) ){
						resultcode="2";
						msg="支附宝帐号不匹配！";
					}else if(!status.equals("0")){
						resultcode="6";
						msg="帐号异常，请与客服务联系！";
						
					}else if( score<obj.getMoney()){
						resultcode="6";
						msg="帐户余额不足！";
						
					}else if(obj.getMoney()<min_score && obj.getMoney()> max_score){
						resultcode="3";
						msg="亲，每次只能提"+(min_score/100)+"~"+(max_score/100)+"元现金哦！";	
					}else{
						if(obj.getGroupid().length()>0){
							ls=this.sysAccountDao.getData("select * from tbl_reg_account where groupid!=? and alipay_account=?",new String[]{obj.getGroupid(),obj.getAlipay_account()});							
						}else{
							ls=this.sysAccountDao.getData("select * from tbl_reg_account where account!=? and alipay_account=?",new String[]{obj.getAccount(),obj.getAlipay_account()});
						}
						if(ls!=null && ls.size()>0){
							resultcode="6";
							msg="该支付宝账号已被绑定，请重新输入!";							
						}else{
							resultcode="0";
							msg="succ";
						}
					}
					

					
					
					if(resultcode.equalsIgnoreCase("0")){
						if(obj.getGroupid().length()>0){
							ls=this.sysAccountDao.getData("select * from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupid()});
							String user="";
							if(ls!=null && ls.size()>0){
								for(int i=0;i<ls.size();i++){
									Map map=(Map)ls.get(i);
									user+="'"+(map.get("account")==null?"":map.get("account").toString())+"',";
								}

								if(user.length()>0){user=user.substring(0,user.length()-1);}
							}
							ls=this.sysAccountDao.getData("select account,indate,money from tbl_cash_logs where date_format(indate,'%y-%m-%d')=date_format(now(),'%y-%m-%d') and account in ("+user+")",new String[]{});							

						}else{
							ls=this.sysAccountDao.getData("select account,indate,money from tbl_cash_logs where account=? and date_format(indate,'%y-%m-%d')=date_format(now(),'%y-%m-%d')",new String[]{obj.getAccount()});
						}
						
						if((ls!=null && ls.size()>0)){
							resultcode="4";
							msg="亲，您今日已提现，请明天再来！";								
							
						}else{
							ls=this.sysAccountDao.getData("select ifnull(sum(money),0) 'money' from tbl_cash_logs where date_format(indate,'%y-%m-%d')=date_format(now(),'%y-%m-%d')",new String[]{});
							mp=(Map)ls.get(0);
							money=Integer.parseInt((mp.get("money")==null?"0":mp.get("money").toString()));
							
							if(money+obj.getMoney()>max_cash_money){
								resultcode="5";
								msg="兑换金额已超出库存金额！";								
								
							}else{
								if(this.sysAccountDao.executedUpdateSql("update tbl_reg_account set score=score-"+(obj.getMoney())+",chi_name=?,alipay_account=? where account=? and score=?", new String[]{obj.getChi_name(),obj.getAlipay_account(),obj.getAccount(),String.valueOf(score)})==1){
									this.sysAccountDao.executedSql("insert into tbl_cash_logs(account,indate,alipay_account,chi_name,money,status) values(?,now(),?,?,?,0)", new String[]{obj.getAccount(),obj.getAlipay_account(),obj.getChi_name(),String.valueOf(obj.getMoney())});
									score-=(obj.getMoney());
									resultcode="0";
									msg="succ";
								}else{
									resultcode="4";
									msg="提现异常，请重试！";
								}
							}
							
						}
						
						
					}
				}
			  }
			}else{
				resultcode="-1";
				msg="post parameter error!";
			}
			
			
			
					
		}catch(Exception e){
			e.printStackTrace();
		}
		
		DecimalFormat df = new DecimalFormat("0.00");  
		double dscore = (double)score;  
		jsondata.put("score", df.format(((double)score)/100));
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);		
		jsondata.put("function", obj.getPara_function());


		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 214:"+jsondata.toString());	
		
	}		
	
	
	public void func215() throws Exception{
		func215Req obj=(func215Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		int signday=-1;
		int score=-1;
		int days=30;
		
		String description="";
		
		List ls=null;			
		try{
			
			
			if(obj.getAccount()!=null && obj.getGroupid().length()==0){
				ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=? limit 5",new String[]{obj.getAccount()});
				if(ls!=null && ls.size()>0){
					Map mp=(Map)ls.get(0);
					score=Integer.parseInt((mp.get("score")==null?"0":mp.get("score").toString()));

					String sql=" ";
					

					sql="select account,indate,alipay_account,chi_name,money 'money',remark,status from tbl_cash_logs where account='"+obj.getAccount()+"' order by indate desc ";
					
					
					ls=this.sysAccountDao.getData(sql,new String[]{});
					for(int i=0;i<ls.size();i++){
						Map map=(Map)ls.get(i);
						
						JSONObject item=new JSONObject();	
						
						String status=(map.get("status")==null?"":map.get("status").toString());
						if(status.equals("0")){
							status="审核中";
							
						}else if(status.equals("1")){
							status="提现成功";
							
						}else if(status.equals("2")){
							status="提现失败";
						}else{
							status="未知";
						}
						
						item.put("remark", map.get("remark")==null?"":map.get("remark").toString());
						item.put("date", map.get("indate")==null?"":map.get("indate").toString());				
						item.put("alipay_account", map.get("alipay_account")==null?"":map.get("alipay_account").toString());
						item.put("status", status);
						item.put("chi_name", map.get("chi_name")==null?"":map.get("chi_name").toString());
						
						int scored=Integer.parseInt((map.get("money")==null?"0":map.get("money").toString()));
						
						DecimalFormat df = new DecimalFormat("0.00");  
						double dscore = (double)scored;  
						item.put("active_score", df.format(dscore/100).toString()+"元");
						
						jsonarray.add(item);						
						
					}
					
					resultcode="0";
					msg="succ";
				}
			}else if(obj.getGroupid().length()>0){
				ls=this.sysAccountDao.getData("select * from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupid()});
				String user="";
				if(ls!=null && ls.size()>0){
					for(int i=0;i<ls.size();i++){
						Map mp=(Map)ls.get(i);
						user+="'"+(mp.get("account")==null?"":mp.get("account").toString())+"',";
					}

					if(user.length()>0){user=user.substring(0,user.length()-1);}	
				}
					String sql=" ";
					

					sql="select account,indate,alipay_account,chi_name,money 'money',remark,status from tbl_cash_logs where account in ("+user+") order by indate desc ";
					
					//System.out.println(sql);
					
					ls=this.sysAccountDao.getData(sql,new String[]{});
					for(int i=0;i<ls.size();i++){
						Map map=(Map)ls.get(i);
						
						JSONObject item=new JSONObject();	
						
						String status=(map.get("status")==null?"":map.get("status").toString());
						if(status.equals("0")){
							status="审核中";
							
						}else if(status.equals("1")){
							status="提现成功";
							
						}else if(status.equals("2")){
							status="提现失败";
						}else{
							status="未知";
						}
						
						item.put("remark", map.get("remark")==null?"":map.get("remark").toString());
						item.put("date", map.get("indate")==null?"":map.get("indate").toString());				
						item.put("alipay_account", map.get("alipay_account")==null?"":map.get("alipay_account").toString());
						item.put("status", status);
						item.put("chi_name", map.get("chi_name")==null?"":map.get("chi_name").toString());
						
						int scored=Integer.parseInt((map.get("money")==null?"0":map.get("money").toString()));
						
						DecimalFormat df = new DecimalFormat("0.00");  
						double dscore = (double)scored;  
						item.put("active_score", df.format(dscore/100).toString()+"元");
						
						jsonarray.add(item);						
											
					}
					
					resultcode="0";
					msg="succ";				
				
			}else{

				resultcode="-1";
				msg="post parameter error!";

			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String tips="温馨提示：\n从2014-12-5 至 2014-12-30 共享一个wifi热点密码可获得0.60元现金红包（提现功能，近期开放，敬请期待）.";
		if(SysParameter.getInstatnce().getParams().get("my_occupy_wifi_tips")!=null){
			tips=SysParameter.getInstatnce().getParams().get("my_occupy_wifi_tips").toString();
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		jsondata.put("tips", tips);
		
		jsondata.put("data", jsonarray);
		


		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 215:"+jsondata.toString());		
	}			
	
   public void func216() throws Exception{
        Map<String, Object> params = SysParameter.getInstatnce().getParams();
        
        func216Req obj = (func216Req)request.getAttribute(SysParameter.REQUESTBEAN);
        int type = obj.getType();
  
        synchronized (Tools.synchronized_216_obj){         
        logger.info("ffunc216:{type:" + type + ", qty: " + obj.getQty() +"}");
        if (type == 0){
            
            //取参数
            int min_QQ_coin = Integer.valueOf(params.get("cash_min_qb").toString());
            int max_QQ_coin = Integer.valueOf(params.get("cash_max_qb").toString());
            int per_day_qq_coin_stock = Integer.valueOf(params.get("per_day_qq_coin_stock").toString());
            
            JSONObject jsondata=new JSONObject();
            jsondata.put("function", obj.getPara_function());
            
            int qty  = obj.getQty() * 100;  //转换单位为分
            
            if (qty < min_QQ_coin){  // 小于最小Q币兑换的数量
                
                jsondata.put("resultcode", "1");
                jsondata.put("msg", "亲，Q币兑换的数量一次不能少于" + (min_QQ_coin/100) + "个哦！");
                respData(jsondata.toString(), request, response);
                return;
            } else if (qty > max_QQ_coin){ //大于最大Q币兑换的数量
                
                jsondata.put("resultcode", "2");
                jsondata.put("msg", "亲，Q币兑换的数量一次不能大于" + (max_QQ_coin/100) + "个哦!");  
                respData(jsondata.toString(), request, response);
                return;
            } 
            
            int score = 0;
            //查询帐户余额
            List list = this.sysAccountDao.getData("select score, status from tbl_reg_account where account=?", new String[]{obj.getUsername()});
            if (null != list && !list.isEmpty()){
                Map map = (Map)list.get(0);
                
                int status = Integer.parseInt((map.get("status") == null ? "1" : map.get("status").toString()));
                if (status != 0){
                    jsondata.put("resultcode", "10");
                    jsondata.put("msg", "此帐号已被冻结，暂时不能兑换Q币！");    
                    respData(jsondata.toString(), request, response);
                    return;
                }
                
                score = Integer.parseInt((map.get("score") == null ? "0" : map.get("score").toString()));
                
                if (qty > score){
                    jsondata.put("resultcode", "7");
                    jsondata.put("msg", "帐户余额不足！");    
                    respData(jsondata.toString(), request, response);
                    return;
                }
            } else {
                jsondata.put("resultcode", "8");
                jsondata.put("msg", "帐户有误！");    
                respData(jsondata.toString(), request, response);
                return;
            }
            
            //获取当天己充Q币总数量
            int currentdate_qq_coin = 0;
            String sql = "select sum( price ) as 'total' from tbl_chongzhi  where category = 1 and (status = 0 or status = 2) and date(createtime)=curdate()";
            list = sysAccountDao.getData(sql, new String[]{});
            if (null != list &&  !list.isEmpty()){
                Map map = (Map)list.get(0);
                currentdate_qq_coin = map.get("total") == null ? 0 : Integer.valueOf(map.get("total").toString());
                
                //查询当天己充Q币总数量是否超过当天库存限制
                if ((currentdate_qq_coin > per_day_qq_coin_stock) || ((currentdate_qq_coin + qty) > per_day_qq_coin_stock)){
                    jsondata.put("resultcode", "3");
                    jsondata.put("msg", "亲，Q币今天己经没库存了，请明天早点来兑换哦！"); 
                    respData(jsondata.toString(), request, response);
                    return;
                } 
            }
            
            //查询当天是否有过兑换Q币的记录
            sql = "select id from tbl_chongzhi where (used_login_id = ? or target=?) and (status = 0 or status = 2) and category = 1 and date(createtime) = curdate()"; 
            list = sysAccountDao.getData(sql, new String[]{obj.getUsername(),obj.getActive_account()});
            if (null != list && !list.isEmpty()){
                jsondata.put("resultcode", "4");
                jsondata.put("msg", "亲，Q币每天只能兑换一次，请明天再来吧!");    
                respData(jsondata.toString(), request, response);
                return;
            }
            
            //扣除账户的余额
            int rowCount = sysAccountDao.executedUpdateSql("update tbl_reg_account set score = score- ? where account=? and score=? and score >= ?", new String[]{qty+"", obj.getUsername(), score+"", qty+""});
            if (rowCount == 0){
                jsondata.put("resultcode", "4");
                jsondata.put("msg", "兑换异常，请重试！");    
                respData(jsondata.toString(), request, response);
                return;
            }
            
            DecimalFormat df = new DecimalFormat("0.00");
            
            ChongZhiAction cz = new ChongZhiAction();
            
            String out_trade_id = java.util.UUID.randomUUID().toString();
            String account = obj.getActive_account();
            String account_info = "";
            int quantity = obj.getQty();
            String product_id= "10316";
            String client_ip = ChongZhiAction.getIpAddr(request);
            int expired_mini = 5;
            
//            Ref<String> msg = new Ref<String>();
//            Map<String, String> resultMap = new HashMap<String, String>();
//            
//            int result = cz.Direct(out_trade_id, product_id, quantity, account, account_info, client_ip, expired_mini, msg, resultMap);
//            
//            String msgStr = "兑换失败！";
//            int status = 1; //状态1表示充值失败,状态0表示充值成功
//            String resultcode = "9"; //兑换失败
//            
//            if (result == 0){ 
//                status = 2; //状态2表示充值中
//                resultcode = "0";
//                msgStr = "兑换中，请稍后！";
//            }
            
//            sql = "INSERT INTO tbl_chongzhi (out_trade_id, uid, ip_addr, used_login_id, category, price, target, createtime, msg, code, jsondata, status) values(?,?,?,?,?,?,?,NOW(),?,?,?,?)";
            
            sql = "INSERT INTO tbl_chongzhi (out_trade_id, uid, ip_addr, used_login_id, category, price, target, createtime, status) values(?,?,?,?,?,?,?,NOW(),?)";
            sysAccountDao.executedSql(sql, new String[]{out_trade_id, obj.getUID(), client_ip, obj.getUsername(), "1", qty+"", obj.getActive_account(), "2"});
            
            jsondata.put("resultcode", "0");
            jsondata.put("msg", "24小时内审核通过后到账！");
            jsondata.put("score", df.format((score - qty) / 100.0));
            jsondata.put("inventory", df.format((per_day_qq_coin_stock - currentdate_qq_coin - qty) /100.0));
            respData(jsondata.toString(), request, response);
            long response_start=System.currentTimeMillis();
    		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
    				"response 216:"+jsondata.toString());	
            
        } else if (type == 1 || type == 2 || type == 3){
            
            JSONObject jsondata=new JSONObject();
            jsondata.put("function", obj.getPara_function());
            
            logger.info("type:" + type);
            
            int per_day_telephone_coin_stack = Integer.valueOf(params.get("per_day_telephone_coin_stack").toString());
            int qty  = obj.getQty() * 100;  //转换单位为分
            int score = 0;
            
            //查询帐户余额
            List list = this.sysAccountDao.getData("select score, status from tbl_reg_account where account=?", new String[]{obj.getUsername()});
            if (null != list && !list.isEmpty()){
                Map map = (Map)list.get(0);
                
                int status = Integer.parseInt((map.get("status") == null ? "1" : map.get("status").toString()));
                if (status != 0){
                    jsondata.put("resultcode", "10");
                    jsondata.put("msg", "此帐号已被冻结，暂时不能兑换话费！");    
                    respData(jsondata.toString(), request, response);
                    return;
                }
                
                score = Integer.parseInt((map.get("score") == null ? "0" : map.get("score").toString()));
                if (qty > score){
                    jsondata.put("resultcode", "7");
                    jsondata.put("msg", "帐户余额不足！");    
                    respData(jsondata.toString(), request, response);
                    return;
                }
            } else {
                jsondata.put("resultcode", "8");
                jsondata.put("msg", "帐户有误！");    
                respData(jsondata.toString(), request, response);
                return;
            }
            
            //获取当天己充话费总数量
            int currentdate_huafei_coin = 0;
            String sql = "select sum( price ) as 'total' from tbl_chongzhi  where category = 2 and (status = 0 or status = 2) and date(createtime)=curdate()";
            list = sysAccountDao.getData(sql, new String[]{});
            if (null != list &&  !list.isEmpty()){
                Map map = (Map)list.get(0);
                currentdate_huafei_coin = map.get("total") == null ? 0 : Integer.valueOf(map.get("total").toString());
                
                //查询当天己充话费总数量是否超过当天库存限制
                if ((currentdate_huafei_coin > per_day_telephone_coin_stack) || ((currentdate_huafei_coin + qty) > per_day_telephone_coin_stack)){
                    jsondata.put("resultcode", "5");
                    jsondata.put("msg", "亲，话费今天己经没库存了，请明天早点来兑换哦！"); 
                    respData(jsondata.toString(), request, response);
                    
                    return;
                } 
            }
            
            //查询当天是否有过兑换话费的记录
            sql = "select id from tbl_chongzhi where (used_login_id = ? or target=?)  and category = 2 and (status = 0 or status = 2) and date(createtime) = curdate()"; 
            list = sysAccountDao.getData(sql, new String[]{obj.getUsername(),obj.getActive_account()});
            if (null != list && !list.isEmpty()){
                jsondata.put("resultcode", "6");
                jsondata.put("msg", "亲，话费每天只能兑换一次，请明天再来吧!");    
                respData(jsondata.toString(), request, response);
                return;
            }
            
            //扣除账户的余额
            int rowCount = sysAccountDao.executedUpdateSql("update tbl_reg_account set score = score- ? where account=? and score=? and score >= ?", new String[]{qty+"", obj.getUsername(), score+"", qty+""});
            if (rowCount == 0){
                jsondata.put("resultcode", "4");
                jsondata.put("msg", "兑换异常，请重试！");    
                respData(jsondata.toString(), request, response);
                return;
            }
            
            DecimalFormat df = new DecimalFormat("0.00");
            
            ChongZhiAction cz = new ChongZhiAction();
            String out_trade_id = java.util.UUID.randomUUID().toString();
            String account = obj.getActive_account();
            String account_info = "";
            int quantity = 1;
            String value = obj.getQty() + "";
            String client_ip = ChongZhiAction.getIpAddr(request);
            int expired_mini = 5;
            Ref<String> msg = new Ref<String>();
            Map<String, String> resultMap = new HashMap<String, String>();
            
//            int result = cz.Huafei(out_trade_id, account, account_info, quantity, value, client_ip, expired_mini, msg, resultMap);
//            
//            String msgStr = "兑换失败！";
//            int status = 1; //状态1表示充值失败,状态0表示充值成功
//            String resultcode = "9"; //兑换失败
//            
//            if (result == 0){                 
//                resultcode = "0";
//                status = 2; //状态2表示充值中
//                msgStr = "兑换中，请稍后！";
//            }
            
//            sql = "INSERT INTO tbl_chongzhi (out_trade_id, uid, ip_addr, used_login_id, category, price, target, createtime, msg, code, jsondata, status) values(?,?,?,?,?,?,?,NOW(),?,?,?,?)";
//            sysAccountDao.executedSql(sql, new String[]{out_trade_id, obj.getUID(), client_ip, obj.getUsername(), "2", qty+"", obj.getActive_account(), msg.toString(), result+"", resultMap.toString(), status+""});
            
            sql = "INSERT INTO tbl_chongzhi (out_trade_id, uid, ip_addr, used_login_id, category, price, target, createtime, status) values(?,?,?,?,?,?,?,NOW(),?)";
            sysAccountDao.executedSql(sql, new String[]{out_trade_id, obj.getUID(), client_ip, obj.getUsername(), "2", qty+"", obj.getActive_account(), "2"});
            
            jsondata.put("resultcode", "0");
            jsondata.put("msg", "24小时内审核通过后到账！");
            jsondata.put("score", df.format((score - qty) / 100.0));
            jsondata.put("inventory", df.format((per_day_telephone_coin_stack - currentdate_huafei_coin - qty) /100.0));
            respData(jsondata.toString(), request, response);
            long response_start=System.currentTimeMillis();
    		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
    				"response 216:"+jsondata.toString());	            
        }
        
        }
        
    }
	
	public void func217() throws Exception{

	    func217Req obj = (func217Req)request.getAttribute(SysParameter.REQUESTBEAN);
	    String uid = obj.getUID();
	    String username = obj.getUsername();
	    
	    List ls=null;
	    
		if(obj.getGroupid().length()>0){
			ls=this.sysAccountDao.getData("select * from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupid()});
			String user="";
			String sql="";
			if(ls!=null && ls.size()>0){
				for(int i=0;i<ls.size();i++){
					Map map=(Map)ls.get(i);
					user+="'"+(map.get("account")==null?"":map.get("account").toString())+"',";
				}

				if(user.length()>0){user=user.substring(0,user.length()-1);}
				sql="select date_format(createtime, '%Y-%m-%d %H:%i:%s') as createtime, category, target, price/10/10 as price, status from tbl_chongzhi where used_login_id in ("+user+")";
				System.out.println(sql);
				ls=this.sysAccountDao.getData(sql,new String[]{});
			}else{
				ls=new ArrayList();
			}
			
		}else{
	    	String sql = "select date_format(createtime, '%Y-%m-%d %H:%i:%s') as createtime, category, target, price/10/10 as price, status from tbl_chongzhi where used_login_id=?";
	    	ls = sysAccountDao.getData(sql, new String[]{username});
		}
        
	    JSONObject jsondata=new JSONObject();
        JSONArray jsonarray=new JSONArray();
	        
	    if (ls != null && !ls.isEmpty()){
	        for (int i = 0; i < ls.size(); i++){
	            Map map = (Map)ls.get(i);
	            
	            JSONObject item = new JSONObject();
	            String createtime = map.get("createtime").toString();
	            int category = Integer.valueOf(map.get("category").toString());
	            int qty = (int)Double.valueOf(map.get("price").toString()).doubleValue();
	            
                String qtyStr = "";
                if (category == 2){
                    qtyStr = qty + "元";
                } else if (category == 1){
                    qtyStr = qty + "Q币";
                }
                
	            item.put("date", createtime);
	            item.put("qty", qtyStr);
	            item.put("type", category == 2 ? "话费" : (category == 1 ? "Q币" : ""));
	            item.put("active_account", map.get("target"));
	            item.put("status", Integer.valueOf(map.get("status")==null?"0":map.get("status").toString()));
	            jsonarray.add(item);
	        }
	    }
	    
        jsondata.put("resultcode", "0");
        jsondata.put("msg", "success");
        jsondata.put("data", jsonarray);
        jsondata.put("function", obj.getPara_function());
        
		long response_start=System.currentTimeMillis();
        respData(jsondata.toString(), request, response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 217:"+jsondata.toString());	
	}	
	
	public void func218() throws Exception{

	}	
	
	public void func219() throws Exception{

	}				

	public void func220() throws Exception{

	}

	public void func221() throws Exception{

	}				

	public void func222() throws Exception{
		func222Req obj=(func222Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		List ls=null;			
		try{
			if(obj.getError_description()!=null && obj.getError_description().length()>800){
				obj.setError_description(obj.getError_description().substring(0,799));
			}
			
			this.sysAccountDao.executedSql("insert into tbl_report_error_logs(uid,account,error_description,error_function,longitude,latitude,city,version,platform,rip,indate) values(?,?,?,?,?,?,?,?,?,?,now())",new String[]{obj.getUID(),obj.getAccount(),obj.getError_description(),obj.getError_function(),obj.getLongitude(),obj.getLatitude(),obj.getCity(),obj.getHeader_version(),obj.getPlatform(),obj.getReal_ip()});
						
			resultcode="0";
			msg="succ";
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 222:"+jsondata.toString());
		
	}	
	
	public void func223() throws Exception{
	     
        func223Req obj=(func223Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		List ls=null;			
		try{
			if(obj.getCollect_description()!=null && obj.getCollect_description().length()>200){
				obj.setCollect_description(obj.getCollect_description().substring(0,195));
			}
			
			String header=obj.getHeader_version()+"|";
			 header+=obj.getHeader_imei()+"|";
			 header+=obj.getHeader_imsi()+"|";
			 header+=obj.getHeader_brand()+"|";
			 header+=obj.getHeader_model()+"|";
			 header+=obj.getHeader_longitude()+"|";
			 header+=obj.getHeader_latitude()+"|";
			 header+=obj.getHeader_city();
			 
			 
			this.sysAccountDao.executedSql("insert into tbl_report_data(uid,account,description,function,longitude,latitude,city,version,platform,rip,header,channel,indate) values(?,?,?,?,?,?,?,?,?,?,?,?,now())",new String[]{obj.getUID(),obj.getAccount(),obj.getCollect_description(),obj.getCollect_function(),obj.getLongitude(),obj.getLatitude(),obj.getCity(),obj.getHeader_version(),obj.getPlatform(),obj.getReal_ip(),header,obj.getChannel()});
						
			resultcode="0";
			msg="succ";
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 223:"+jsondata.toString());		
   
	}	
			
	
	public void func225() throws Exception{
	    
 
   
	}	
	
	public void func230() throws Exception{
	     
        func230Req obj=(func230Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		String groupid="";
		String status="";
		String password="";
		String acc_score="";
		String acc_flowers="";
		
		String username="";
		       
		String alipay_account="";
		String chi_name="";
		int uid=0;
		String level="";
		String account_type="";
		String sex="";
		String city="";
		String province="";
		String country="";
		String unionid="";
		String group_mobile="";
		String group_wx="";
		String group_qq="";		
		List ls=null;		
		int score=0;
		int flowers=0;
		try{
			ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=?",new String[]{obj.getAccount()});
			if(ls==null || ls.size()==0){
									
					resultcode="1";
					msg="account not found.";
					msg="帐号有误！";
			

			}else{
				Map mp=(Map)ls.get(0);
				groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
				status=(mp.get("status")==null?"":mp.get("status").toString());
				password=(mp.get("password")==null?"":mp.get("password").toString());
				acc_score=(mp.get("score")!=null?mp.get("score").toString():"0");
				acc_flowers=(mp.get("flowers")!=null?mp.get("flowers").toString():"0");
				
				username=(mp.get("username")!=null?mp.get("username").toString():"");
				image=(mp.get("image")==null?"":(mp.get("image").toString().startsWith("http")?mp.get("image").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+mp.get("image").toString()));
				alipay_account=(mp.get("alipay_account")!=null?mp.get("alipay_account").toString():"");
				chi_name=(mp.get("chi_name")!=null?mp.get("chi_name").toString():"");
				uid=Integer.parseInt((mp.get("id")!=null?mp.get("id").toString():"-1"));
				level=(mp.get("level")!=null?mp.get("level").toString():"");
				account_type=(mp.get("account_type")!=null?mp.get("account_type").toString():"1");
				sex=(mp.get("sex")!=null?mp.get("sex").toString():"");
				city=(mp.get("city")!=null?mp.get("city").toString():"");
				province=(mp.get("province")!=null?mp.get("province").toString():"");
				country=(mp.get("country")!=null?mp.get("country").toString():"");
				unionid=(mp.get("unionid")!=null?mp.get("unionid").toString():"");	
				if(obj.getLogin_type()==null || obj.getLogin_type().length()==0){
					obj.setLogin_type((mp.get("account_type")!=null?mp.get("account_type").toString():"1"));
				}
				
				resultcode="0";
				msg="succ";

				if(account_type.equals(obj.getBind_type()) && resultcode.equals("0") ){
					resultcode="5";
					msg="用户帐号和绑定帐号属性相同，不能被绑定！";		
				}
				
				
				if(groupid.length()>0 && resultcode.equals("0")){
					List lsdata=this.sysAccountDao.getData("select * from tbl_reg_account where  groupid=? and account_type=? limit 1", new String[]{groupid,obj.getBind_type()});
					if(lsdata!=null && lsdata.size()>0){
						resultcode="2";
						msg="用户帐号已存在绑定过帐号！";						
					}
				}
				if(obj.getAction_code().length()>0 && obj.getBind_type().equals("1") && resultcode.equals("0")){
					ls=this.sysAccountDao.getData("select * from tbl_sendsms where mobile=? and regcode=? and indate between date_add(now(), interval -30 minute) and now() ",new String[]{obj.getBind_id(),obj.getAction_code()});
					if(ls==null || ls.size()==0){
						resultcode="7";
						msg="操作验证码已失效！";
					}						
					
				}
				
				if(resultcode.equals("0")){
					List lsdata=this.sysAccountDao.getData("select * from tbl_reg_account where  account=?  limit 1", new String[]{obj.getBind_id()});
					int uid_score=0;
					int uid_flowers=0;
					if(lsdata!=null && lsdata.size()>0){
						Map map=(Map)lsdata.get(0);
						String Tgroupid=(map.get("groupid")==null?"":map.get("groupid").toString());
						String Tstatus=(map.get("status")==null?"":map.get("status").toString());
						String Timage=(map.get("image")==null?"":map.get("image").toString());
						String Tsex=(map.get("sex")==null?"":map.get("sex").toString());
						String Tcity=(map.get("city")==null?"":map.get("city").toString());
						String Tprovince=(map.get("province")==null?"":map.get("province").toString());
						String Tusername=(map.get("username")==null?"":map.get("username").toString());
						if(Tusername.length()==0){Tusername=username;}
						if(Tsex.length()==0){Tsex=sex;}
						if(Tcity.length()==0){Tcity=city;}
						if(Timage.length()==0){Timage=image;}
						if(Tprovince.length()==0){Tprovince=province;}
						
						
						
						uid_score=Integer.parseInt((map.get("score")!=null?map.get("score").toString():"0"));
						uid_flowers=Integer.parseInt((map.get("flowers")!=null?map.get("flowers").toString():"0"));
						
						score=Integer.parseInt(acc_score)+uid_score;
						flowers=Integer.parseInt(acc_flowers)+uid_flowers;

						List ldata=this.sysAccountDao.getData("select * from tbl_reg_account where  groupid=? and account_type=? limit 1", new String[]{Tgroupid,obj.getBind_type()});
						if(ldata!=null && ldata.size()>0 && Tgroupid.length()>0 && Tgroupid.equals(groupid)){
							resultcode="2";
							msg="用户帐号已存在绑定过帐号！";	
						}else if( Tgroupid.length()>0 && groupid.length()==0 && this.sysAccountDao.getData("select * from tbl_reg_account where  groupid=? and account_type=? limit 1", new String[]{Tgroupid,account_type}).size()>0){
							resultcode="2";
							msg="用户帐号已存在绑定过帐号！";					
						}else if(Tgroupid.length()>0 && !Tgroupid.equals(groupid) && groupid.length()>0){
							resultcode="3";
							msg="绑定帐号,已被别人绑定！";	
						}else if (Tgroupid.length()>0 && Tgroupid.equals(groupid)){
							resultcode="4";
							msg="绑定帐号,重复绑定！";	
						}else if(!Tstatus.equals("0")){
							resultcode="6";
							msg="绑定帐号已所定！";	
							
						}else{
							String psql="";
							if(obj.getBind_type().equals("1")){ // mobile
								if(Tusername.length()>0){psql+=",username='"+Tusername+"'";username=Tusername;}
								if(Timage.length()>0){	psql+=",image='"+Timage+"' ";}
								if(Tsex.length()>0){psql+=",sex='"+Tsex+"'";sex=Tsex;}
								if(Tcity.length()>0){psql+=",city='"+Tcity+"'";city=Tcity;}
								if(Tprovince.length()>0){psql+=",province='"+Tprovince+"'";province=Tprovince;}
								
							}else if(obj.getBind_type().equals("2")){ // mobile
								if(obj.getNickname().length()>0){psql+=",username='"+obj.getNickname()+"'";username=obj.getNickname();}
								if(obj.getImage().length()>0){psql+=",image='"+obj.getImage()+"'";image=obj.getImage();}
								if(obj.getSex().length()>0){psql+=",sex='"+obj.getSex()+"'";sex=obj.getSex();}
								if(obj.getCity().length()>0){psql+=",city='"+obj.getCity()+"'";city=obj.getCity();}
								if(obj.getProvince().length()>0){psql+=",province='"+obj.getProvince()+"'";province=obj.getProvince();}
								if(obj.getCountry().length()>0){psql+=",country='"+obj.getCountry()+"'";country=obj.getCountry();}
								
							}else if(obj.getBind_type().equals("3")){ // mobile
								if(obj.getNickname().length()>0){psql+=",username='"+obj.getNickname()+"'"; username=obj.getNickname();}
								if(obj.getImage().length()>0){psql+=",image='"+obj.getImage()+"'";image=obj.getImage();}
								if(obj.getSex().length()>0){psql+=",sex='"+obj.getSex()+"'";sex=obj.getSex();}
								if(obj.getCity().length()>0){psql+=",city='"+obj.getCity()+"'";city=obj.getCity();}
								if(obj.getProvince().length()>0){psql+=",province='"+obj.getProvince()+"'";province=obj.getProvince();}
								if(obj.getCountry().length()>0){psql+=",country='"+obj.getCountry()+"'"; country=obj.getCountry();}							
								if(obj.getUnionid().length()>0){psql+=",unionid='"+obj.getUnionid()+"'";unionid=obj.getUnionid();}
							

																
							}
							
							if(groupid.length()==0 && Tgroupid.length()>0){
								groupid=Tgroupid;
							}
							
							if(groupid.length()==0){
								groupid=String.valueOf(System.currentTimeMillis());
							}
							
							
							if(Tgroupid.length()>0){
								sysAccountDao.executedSql("update tbl_reg_account a cross join (select sum(ifnull(score,0)) 'score',sum(ifnull(flowers,0)) 'flowers' from tbl_reg_account where groupid=? or account=? )  b  set  a.score=b.score,a.flowers=b.flowers , a.groupid=? where a.account=?",new String[]{Tgroupid,obj.getBind_id(),Tgroupid,obj.getAccount()});
								sysAccountDao.executedSql("update tbl_reg_account set  remark=score, score=0,flowers=0  where groupid=? and  account!=? ;",new String[]{groupid,obj.getAccount()});
							}else{
								sysAccountDao.executedSql("update tbl_reg_account a cross join (select sum(ifnull(score,0)) 'score',sum(ifnull(flowers,0)) 'flowers'  from tbl_reg_account where account=? or account=? )  b  set  a.score=b.score,a.flowers=b.flowers , a.groupid=? where a.account=?",new String[]{obj.getAccount(),obj.getBind_id(),groupid,obj.getAccount()});
								sysAccountDao.executedSql("update tbl_reg_account set  remark=score, score=0 ,flowers=0,groupid=? where account=? ;",new String[]{groupid,obj.getBind_id()});
							}
							
							
							sysAccountDao.executedSql("update tbl_reg_account set  groupid=?"+psql +" where groupid=?",new String[]{groupid,groupid});															
							
						}
					}else{

						String psql="";						
						if(groupid.length()==0){
							groupid=String.valueOf(System.currentTimeMillis());
							sysAccountDao.executedSql("update tbl_reg_account set  groupid=?,binddate=now()  where account=? ;",new String[]{groupid,obj.getAccount()});
						}						
						
						psql+="groupid='"+groupid+"'";

						
						if(obj.getBind_type().equals("1")){ // mobile
							this.sysAccountDao.executedSql("insert into tbl_reg_account(account,password,username,status,score,flowers,indate,image,channel,sex,city,province,country,privilege,unionid,groupid,account_type,binddate,chi_name,alipay_account) values(?,?,?,0,0,0,now(),?,?,?,?,?,?,'','',?,'1',now(),?,?)", 
									new String[]{obj.getBind_id(),obj.getPassword(),username,image,obj.getChannel(),sex,city,province,country,groupid,chi_name,alipay_account});
							
						}else if(obj.getBind_type().equals("2")){ // mobile
							this.sysAccountDao.executedSql("insert into tbl_reg_account(account,password,username,status,score,flowers,indate,image,channel,sex,city,province,country,privilege,unionid,groupid,account_type,binddate,chi_name,alipay_account) values(?,'',?,0,0,0,now(),?,?,?,?,?,?,?,?,?,'2',now(),?,?)", 
									new String[]{obj.getBind_id(),obj.getNickname(),obj.getImage(),obj.getChannel(),obj.getSex(),obj.getCity(),obj.getProvince(),obj.getCountry(),obj.getPrivilege(),obj.getUnionid(),groupid,chi_name,alipay_account});

							if(obj.getNickname().length()>0){psql+=",username='"+obj.getNickname()+"'"; username=obj.getNickname();}
							if(obj.getImage().length()>0){psql+=",image='"+obj.getImage()+"'"; image=obj.getImage();}
							if(obj.getSex().length()>0){psql+=",sex='"+obj.getSex()+"'"; sex=obj.getSex();}
							if(obj.getCity().length()>0){psql+=",city='"+obj.getCity()+"'"; city=obj.getCity();}
							if(obj.getProvince().length()>0){psql+=",province='"+obj.getProvince()+"'"; province=obj.getProvince();}
							if(obj.getCountry().length()>0){psql+=",country='"+obj.getCountry()+"'"; country=obj.getCountry();}							
							if(obj.getUnionid().length()>0){psql+=",unionid='"+obj.getUnionid()+"'";unionid=obj.getUnionid();}
							
						}else if(obj.getBind_type().equals("3")){ // mobile
							this.sysAccountDao.executedSql("insert into tbl_reg_account(account,password,username,status,score,flowers,indate,image,channel,sex,city,province,country,privilege,unionid,groupid,account_type,binddate,chi_name,alipay_account) values(?,'',?,0,0,0,now(),?,?,?,?,?,?,?,?,?,'3',now(),?,?)", 
									new String[]{obj.getBind_id(),obj.getNickname(),obj.getImage(),obj.getChannel(),obj.getSex(),obj.getCity(),obj.getProvince(),obj.getCountry(),obj.getPrivilege(),obj.getUnionid(),groupid,chi_name,alipay_account});
							
							if(obj.getNickname().length()>0){psql+=",username='"+obj.getNickname()+"'"; username=obj.getNickname();}
							if(obj.getImage().length()>0){psql+=",image='"+obj.getImage()+"'"; image=obj.getImage();}
							if(obj.getSex().length()>0){psql+=",sex='"+obj.getSex()+"'"; sex=obj.getSex();}
							if(obj.getCity().length()>0){psql+=",city='"+obj.getCity()+"'"; city=obj.getCity();}
							if(obj.getProvince().length()>0){psql+=",province='"+obj.getProvince()+"'"; province=obj.getProvince();}
							if(obj.getCountry().length()>0){psql+=",country='"+obj.getCountry()+"'"; country=obj.getCountry();}							
							if(obj.getUnionid().length()>0){psql+=",unionid='"+obj.getUnionid()+"'";unionid=obj.getUnionid();}							
						}
						
						sysAccountDao.executedSql("update tbl_reg_account set  "+psql+"  where groupid=? ;",new String[]{groupid});						
						
						score=Integer.parseInt(acc_score);
						flowers=Integer.parseInt(acc_flowers);
					}
					
					
					lsdata=this.sysAccountDao.getData("select * from tbl_reg_account where groupid=? and status=0", new String[]{groupid});
					score=0;
					for(int i=0;i<lsdata.size();i++){
						Map map=(Map)lsdata.get(i);
						score+=Integer.parseInt((map.get("score")!=null?map.get("score").toString():"0"));
						if(map.get("account_type")!=null && map.get("account_type").toString().equals("1")){group_mobile=(map.get("account")!=null? map.get("account").toString():"");}
						else if(map.get("account_type")!=null && map.get("account_type").toString().equals("2")){group_wx=(map.get("account")!=null? map.get("account").toString():"");}
						else if(map.get("account_type")!=null && map.get("account_type").toString().equals("3")){group_qq=(map.get("account")!=null? map.get("account").toString():"");}
					}					
					
				}
				
				
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		DecimalFormat df = new DecimalFormat("0.00"); 
		
		double dscore = (double)score;  
		jsondata.put("score", df.format(dscore/100).toString()+"元");
		jsondata.put("flowers", String.valueOf(flowers));
		jsondata.put("nickname", username);
		jsondata.put("username", groupid+":"+obj.getAccount()+":"+obj.getLogin_type());
		jsondata.put("level", level);
		jsondata.put("image", image);
		jsondata.put("alipay_account", alipay_account);
		jsondata.put("chi_name", chi_name);
		jsondata.put("userid", uid);
		jsondata.put("city", city);
		jsondata.put("province", province);	
		jsondata.put("country", country);
		jsondata.put("sex", sex);
		jsondata.put("group_mobile", group_mobile);
		jsondata.put("group_wx", group_wx);
		jsondata.put("group_qq", group_qq);
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 230:"+jsondata.toString());		
   
	}		
			
	
	
	public void func231() throws Exception{
	     
        func231Req obj=(func231Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		String newuser="";
		List ls=null;			
		try{
			ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=?",new String[]{obj.getAccount()});
			if(ls==null || ls.size()==0){
									
					resultcode="1";
					msg="account not found.";
					msg="帐号有误！";
			

			}else{
				Map mp=(Map)ls.get(0);
				String groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
				String status=(mp.get("status")==null?"":mp.get("status").toString());
				String password=(mp.get("password")==null?"":mp.get("password").toString());
				String acc_score=(mp.get("score")!=null?mp.get("score").toString():"0");
				
				String username=(mp.get("username")!=null?mp.get("username").toString():"");
				       image=(mp.get("image")!=null && mp.get("image").toString().length()>10?PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+mp.get("image").toString():"");
				String alipay_account=(mp.get("alipay_account")!=null?mp.get("alipay_account").toString():"");
				String chi_name=(mp.get("chi_name")!=null?mp.get("chi_name").toString():"");
				int uid=Integer.parseInt((mp.get("id")!=null?mp.get("id").toString():"-1"));
				String level=(mp.get("level")!=null?mp.get("level").toString():"");
				String account_type=(mp.get("account_type")!=null?mp.get("account_type").toString():"1");
				
				resultcode="0";
				msg="succ";

				if(account_type.equals(obj.getBind_type()) && resultcode.equals("0") ){
					resultcode="5";
					msg="用户帐号和绑定帐号属性相同，不能被绑定！";		
				}
				
				
				if(groupid.length()>0){
					List lsdata=this.sysAccountDao.getData("select * from tbl_reg_account where  groupid=? and account_type=? limit 1", new String[]{groupid,obj.getBind_type()});
					if(lsdata!=null && lsdata.size()>0){
						resultcode="2";
						msg="用户帐号已存在绑定过帐号！";						
					}
				}
				
				if(resultcode.equals("0")){
					List lsdata=this.sysAccountDao.getData("select * from tbl_reg_account where  account=?  limit 1", new String[]{obj.getBind_id()});
					int uid_score=0;
					if(lsdata!=null && lsdata.size()>0){
						Map map=(Map)lsdata.get(0);
						String Tgroupid=(map.get("groupid")==null?"":map.get("groupid").toString());
						String Tstatus=(map.get("status")==null?"":map.get("status").toString());

						List ldata=this.sysAccountDao.getData("select * from tbl_reg_account where  groupid=? and account_type=? limit 1", new String[]{Tgroupid,obj.getBind_type()});
						if(ldata!=null && ldata.size()>0 && Tgroupid.length()>0 && Tgroupid.equals(groupid)){
							resultcode="2";
							msg="用户帐号已存在绑定过帐号！";	
						}else if( Tgroupid.length()>0 && groupid.length()==0 && this.sysAccountDao.getData("select * from tbl_reg_account where  groupid=? and account_type=? limit 1", new String[]{Tgroupid,account_type}).size()>0){
							resultcode="2";
							msg="用户帐号已存在绑定过帐号！";	
						}else if(Tgroupid.length()>0 && !Tgroupid.equals(groupid) && groupid.length()>0){
							resultcode="3";
							msg="绑定帐号,已被别人绑定！";	
						}else if (Tgroupid.length()>0 && Tgroupid.equals(groupid)){
							resultcode="4";
							msg="绑定帐号,重复绑定！";	
						}else if(!Tstatus.equals("0")){
							resultcode="6";
							msg="绑定帐号已所定！";	
						}
						newuser="0";
					}else{
						newuser="1";
					}
				}
				
				
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("newuser", newuser);
		jsondata.put("function", obj.getPara_function());
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 231:"+jsondata.toString());		
   
	}		
		
	
	public void func232() throws Exception{
	     
        func232Req obj=(func232Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		String newuser="";
		List ls=null;			
		try{
			ls=this.sysAccountDao.getData("select * from tbl_reg_account where account=?",new String[]{obj.getAccount()});
			if(ls==null || ls.size()==0){
									
					resultcode="1";
					msg="account not found.";
					msg="帐号有误！";
			

			}else{
				Map mp=(Map)ls.get(0);
				String groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
				String status=(mp.get("status")==null?"":mp.get("status").toString());
				String password=(mp.get("password")==null?"":mp.get("password").toString());
				String acc_score=(mp.get("score")!=null?mp.get("score").toString():"0");
				
				String username=(mp.get("username")!=null?mp.get("username").toString():"");
				       image=(mp.get("image")!=null && mp.get("image").toString().length()>10?PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+mp.get("image").toString():"");
				String alipay_account=(mp.get("alipay_account")!=null?mp.get("alipay_account").toString():"");
				String chi_name=(mp.get("chi_name")!=null?mp.get("chi_name").toString():"");
				int uid=Integer.parseInt((mp.get("id")!=null?mp.get("id").toString():"-1"));
				String level=(mp.get("level")!=null?mp.get("level").toString():"");
				String account_type=(mp.get("account_type")!=null?mp.get("account_type").toString():"1");
				
				resultcode="0";
				msg="succ";

				if(account_type.equals(obj.getUnbind_type()) && resultcode.equals("0") ){
					resultcode="5";
					msg="用户帐号和绑定帐号属性相同，不能被解徐绑定！";		
				}
				
				
				if(groupid.length()>0){
					List lsdata=this.sysAccountDao.getData("select * from tbl_reg_account where  groupid=? and account=? limit 1", new String[]{groupid,obj.getUnbind_id()});
					if(lsdata==null || lsdata.size()==0){
						resultcode="3";
						msg="用户帐号不存存在绑定过的帐号！";	
					}
				}else{
					resultcode="3";
					msg="用户帐号不存存在绑定过的帐号！";	
					
				}
				
				if(resultcode.equals("0")){
					
					this.sysAccountDao.executedSql("update tbl_reg_account set groupid='' where groupid=? and account=?", new String[]{groupid,obj.getUnbind_id()});

				}
				
				
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 232:"+jsondata.toString());		
   
	}			
	
	public void func233() throws Exception{
	     
        func233Req obj=(func233Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		String groupid="";
		String status="";
		String password="";
		String acc_score="0";
		
		String username="";
		       
		String account="";
		String alipay_account="";
		String chi_name="";
		int uid=0;
		String level="";
		String account_type="";
		String sex="";
		String city="";
		String province="";
		String country="";
		String unionid="";
		String group_mobile="";
		String group_wx="";
		String group_qq="";		
		List ls=null;		
		int score=0;		
		try{
			
			if(obj.getMac_address().length()==0){
				
				resultcode="1";
				msg="account not found.";
				msg="参数有误！";
		

			}else{
				ls=this.sysAccountDao.getData("SELECT a.* FROM tbl_reg_account a inner join tbl_wifi_share_account_logs b on a.account=b.reg_account  where a.status=0 and  b.mac_address=? order by b.id desc limit 2",new String[]{obj.getMac_address()});				
				if(ls!=null && ls.size()>0){
					Map mp=(Map)ls.get(0);
					groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
					status=(mp.get("status")==null?"":mp.get("status").toString());
					password=(mp.get("password")==null?"":mp.get("password").toString());
					acc_score=(mp.get("score")!=null?mp.get("score").toString():"0");
					
					account=(mp.get("account")!=null?mp.get("account").toString():"0");
					
					username=(mp.get("username")!=null?mp.get("username").toString():"");
					image=(mp.get("image")==null?"":(mp.get("image").toString().startsWith("http")?mp.get("image").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+mp.get("image").toString()));
					alipay_account=(mp.get("alipay_account")!=null?mp.get("alipay_account").toString():"");
					chi_name=(mp.get("chi_name")!=null?mp.get("chi_name").toString():"");
					uid=Integer.parseInt((mp.get("id")!=null?mp.get("id").toString():"-1"));
					level=(mp.get("level")!=null?mp.get("level").toString():"");
					account_type=(mp.get("account_type")!=null?mp.get("account_type").toString():"1");
					sex=(mp.get("sex")!=null?mp.get("sex").toString():"");
					city=(mp.get("city")!=null?mp.get("city").toString():"");
					province=(mp.get("province")!=null?mp.get("province").toString():"");
					country=(mp.get("country")!=null?mp.get("country").toString():"");
					unionid=(mp.get("unionid")!=null?mp.get("unionid").toString():"");	
					
					if(groupid.length()>0){
						account=groupid+":"+account+":"+account_type;
					}
					
					resultcode="0";
					msg="succ";
				}else{
					resultcode="1";
					msg="无此地主信息！";
				}
//				if(account_type.equals(obj.getUnbind_type()) && resultcode.equals("0") ){
//					resultcode="5";
//					msg="用户帐号和绑定帐号属性相同，不能被解徐绑定！";		
//				}
//				
//				
//				if(groupid.length()>0){
//					List lsdata=this.sysAccountDao.getData("select * from tbl_reg_account where  groupid=? and account=? limit 1", new String[]{groupid,obj.getUnbind_id()});
//					if(lsdata==null || lsdata.size()==0){
//						resultcode="3";
//						msg="用户帐号不存存在绑定过的帐号！";	
//					}
//				}else{
//					resultcode="3";
//					msg="用户帐号不存存在绑定过的帐号！";	
//					
//				}
//				
//				if(resultcode.equals("0")){
//					
//					this.sysAccountDao.executedSql("update tbl_reg_account set groupid='' where groupid=? and account=?", new String[]{groupid,obj.getUnbind_id()});
//
//				}
				
				
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		DecimalFormat df = new DecimalFormat("0.00"); 
		score=Integer.parseInt(acc_score);
		double dscore = (double)score;  
		jsondata.put("score", df.format(dscore/100).toString()+"元");
		jsondata.put("nickname", username);
		jsondata.put("username", account);
		jsondata.put("level", level);
		jsondata.put("image", image);
		jsondata.put("alipay_account", alipay_account);
		jsondata.put("chi_name", chi_name);
		jsondata.put("userid", uid);
		jsondata.put("city", city);
		jsondata.put("province", province);	
		jsondata.put("country", country);
		jsondata.put("sex", sex);
		jsondata.put("group_mobile", group_mobile);
		jsondata.put("group_wx", group_wx);
		jsondata.put("group_qq", group_qq);		
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 233:"+jsondata.toString());		
   
	}				
	public void func234() throws Exception{
	     
        func234Req obj=(func234Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		String groupid="";
		String status="";
		String password="";
		String acc_score="";
		
		String username="";
		       
		String alipay_account="";
		String chi_name="";
		int uid=0;
		String level="";
		String account_type="";
		String sex="";
		String city="";
		String province="";
		String country="";
		String unionid="";
		String group_mobile="";
		String group_wx="";
		String group_qq="";
		String account="";
		String login_date="";
		List ls=null;		
		int score=0;		
		try{
			
			if(obj.getMac_address().length()==0){
				
				resultcode="1";
				msg="account not found.";
				msg="参数有误！";
		

			}else{
				ls=this.sysAccountDao.getData("SELECT a.*,b.ldate FROM tbl_reg_account a  inner join (select account,max(indate) 'ldate' from tbl_wifi_share_login_logs  where mac_address=? group by account limit 30) b  on a.account=b.account  where a.status=0 order by ldate desc ",new String[]{obj.getMac_address()});				
				if(ls!=null && ls.size()>0){
					for(int i=0;i<ls.size();i++){
						Map mp=(Map)ls.get(i);
						account=(mp.get("account")==null?"":mp.get("account").toString());
						groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
						acc_score=(mp.get("score")!=null?mp.get("score").toString():"0");
						
						username=(mp.get("username")!=null?mp.get("username").toString():"");
						image=(mp.get("image")==null?"":(mp.get("image").toString().startsWith("http")?mp.get("image").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+mp.get("image").toString()));
						alipay_account=(mp.get("alipay_account")!=null?mp.get("alipay_account").toString():"");
						chi_name=(mp.get("chi_name")!=null?mp.get("chi_name").toString():"");
						
						level=(mp.get("level")!=null?mp.get("level").toString():"");
						account_type=(mp.get("account_type")!=null?mp.get("account_type").toString():"1");
						sex=(mp.get("sex")!=null?mp.get("sex").toString():"");
						city=(mp.get("city")!=null?mp.get("city").toString():"");
						province=(mp.get("province")!=null?mp.get("province").toString():"");
						country=(mp.get("country")!=null?mp.get("country").toString():"");
						login_date=(mp.get("ldate")!=null?mp.get("ldate").toString():"");
						JSONObject item=new JSONObject();
						
						
						if(groupid.length()>0){
							account=groupid+":"+account+":"+account_type;
						}
						
						item.put("username", account);
						item.put("nickname", username);
						item.put("image", image);
						item.put("sex", sex);
						item.put("city", city);
						item.put("province", province);
						item.put("longin_date", login_date);						
						jsonarray.add(item);
						
					}
					resultcode="0";
					msg="succ";
				}else{
					resultcode="2";
					msg="无此地主信息！";
				}

				
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		
		jsondata.put("data", jsonarray);
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 234:"+jsondata.toString());		
   
	}					
	
	public void func235() throws Exception{
	     
        func235Req obj=(func235Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		String groupid="";
		String status="";

		       
		String alipay_account="";
		String chi_name="";
		int uid=0;
		String level="";
		String account_type="";
		String sex="";
		String city="";
		String province="";
		String country="";
		String unionid="";
		String group_mobile="";
		String group_wx="";
		String group_qq="";
		String username="";
		String acc_score="0";
		String token="";
		List ls=null;	
		String userid="";
		int score=0;		
		try{

			ls=this.sysAccountDao.getData("SELECT * FROM tbl_reg_account where status=0 and account=?",new String[]{obj.getAccount()});			
			if(ls==null || ls.size()==0){
				
				resultcode="1";
				msg="account not found.";
				msg="帐号有误！";
		

			}else{
				

					Map mp=(Map)ls.get(0);
					groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
					status=(mp.get("status")==null?"":mp.get("status").toString());
					//password=(mp.get("password")==null?"":mp.get("password").toString());
					//acc_score=(mp.get("score")!=null?mp.get("score").toString():"0");
					
					username=(mp.get("username")!=null?mp.get("username").toString():"");
					image=(mp.get("image")==null?"":(mp.get("image").toString().startsWith("http")?mp.get("image").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+mp.get("image").toString()));
					alipay_account=(mp.get("alipay_account")!=null?mp.get("alipay_account").toString():"");
					chi_name=(mp.get("chi_name")!=null?mp.get("chi_name").toString():"");
					uid=Integer.parseInt((mp.get("id")!=null?mp.get("id").toString():"-1"));
					level=(mp.get("level")!=null?mp.get("level").toString():"");
					account_type=(mp.get("account_type")!=null?mp.get("account_type").toString():"1");
					sex=(mp.get("sex")!=null?mp.get("sex").toString():"");
					city=(mp.get("city")!=null?mp.get("city").toString():"");
					province=(mp.get("province")!=null?mp.get("province").toString():"");
					country=(mp.get("country")!=null?mp.get("country").toString():"");
					unionid=(mp.get("unionid")!=null?mp.get("unionid").toString():"");	



					String App_Key="3argexb6rn3ae";
					String APP_Secret="WavVQiKCJ3I";
					String Nonce=String.valueOf(new java.util.Random(10).nextInt(10000000));
					String Timestamp=String.valueOf(System.currentTimeMillis()/1000);
					String Signature= Tools.sha1(APP_Secret+Nonce+Timestamp);
					


					userid="userId="+(groupid.length()>0?groupid:obj.getAccount());
					
					
					String name="name="+(username.length()>0?username:"");
					String img_url="portraitUri="+Tools.URLEncode(image, "UTF-8");
					//userId=jlk456j5&name=Ironman&portraitUri=http%3A%2F%2Fabc.com%2Fmyportrait.jpg
					HashMap header=new HashMap();
					 header.put("App-Key", App_Key);
					 header.put("Nonce", Nonce);
					 header.put("Timestamp", Timestamp);
					 header.put("Signature", Signature);
					 
					 
					String data=userid+"&"+name+"&"+img_url;
					 
					String posturl="https://api.cn.rong.io/user/getToken.json";
					 					
					byte[] response =Tools.postdata(posturl, data, header);					
					
					System.out.println(new String(response));
					
					if(response.length>10){
						JSONObject js=(JSONObject) JSONSerializer.toJSON( new String(response) );
						if(js.containsKey("code")&& js.get("code").toString().equals("200")){
							token=(js.containsKey("token")?js.getString("token"):"");
							userid=(js.containsKey("userId")?js.getString("userId"):"");
							resultcode="0";
							msg="succ";
						}
						else{
							resultcode="2";
							msg="api 调用失败！";
							
						}
						
					}else{
						resultcode="2";
						msg="api 调用失败！";
					}

			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		DecimalFormat df = new DecimalFormat("0.00"); 
		score=Integer.parseInt(acc_score);
		double dscore = (double)score;  
		jsondata.put("score", df.format(dscore/100).toString()+"元");
		jsondata.put("userid", userid);
		jsondata.put("username", username);
		jsondata.put("token", token);

		jsondata.put("image", image);
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 235:"+jsondata.toString());		
   
	}

	public void func236() throws Exception{
	     
        func236Req obj=(func236Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		String groupid="";
		String status="";
		int flowers=0;
		int score=0;	
		int hotpoints=0;
		List ls=null;
		try{

			ls=this.sysAccountDao.getData("SELECT * FROM tbl_reg_account where status=0 and account=?",new String[]{obj.getAccount()});			
			if(ls==null || ls.size()==0){
				
				resultcode="1";
				msg="account not found.";
				msg="帐号有误！";
		

			}else{
					Map mp=(Map)ls.get(0);
					groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
					status=(mp.get("status")==null?"":mp.get("status").toString());
					//password=(mp.get("password")==null?"":mp.get("password").toString());
					score=Integer.parseInt((mp.get("score")!=null?mp.get("score").toString():"0"));
					flowers=Integer.parseInt((mp.get("flowers")!=null?mp.get("flowers").toString():"0"));

					if(groupid.length()==0){
						ls=this.sysAccountDao.getData("SELECT * FROM tbl_wifi_share_account_logs where reg_account=?",new String[]{obj.getAccount()});
					}else{
						ls=this.sysAccountDao.getData("select * from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{groupid});
						String user="";
						if(ls!=null && ls.size()>0){
							for(int i=0;i<ls.size();i++){
								Map map=(Map)ls.get(i);
								user+="'"+(map.get("account")==null?"":map.get("account").toString())+"',";
							}
						}
						if(user.length()>0){user=user.substring(0,user.length()-1);}
						ls=this.sysAccountDao.getData("SELECT * FROM tbl_wifi_share_account_logs where reg_account in("+user+") order by id", new String[]{});
					}
				
					if(ls!=null && ls.size()>0){
						hotpoints=ls.size();
					}					
					resultcode="0";
					msg="succ";	

			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		DecimalFormat df = new DecimalFormat("0.00"); 
		
		double dscore = (double)score;  
		jsondata.put("score", df.format(dscore/100).toString());
		jsondata.put("flowers", String.valueOf(flowers));
		jsondata.put("hotpoints", String.valueOf(hotpoints));
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 236:"+jsondata.toString());		
   
	}	
	public void func237() throws Exception{
	     
        func237Req obj=(func237Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		String groupid="";
		String status="";
		int flowers=0;
		int score=0;	
		int hotpoints=0;
		String description="";
		List ls=null;
		try{

			ls=this.sysAccountDao.getData("SELECT * FROM tbl_reg_account where status=0 and account=?",new String[]{obj.getAccount()});			
			if(ls==null || ls.size()==0){
				
				resultcode="1";
				msg="account not found.";
				msg="帐号有误！";
		

			}else{
					Map mp=(Map)ls.get(0);
					groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
					status=(mp.get("status")==null?"":mp.get("status").toString());
					//password=(mp.get("password")==null?"":mp.get("password").toString());
					score=Integer.parseInt((mp.get("score")!=null?mp.get("score").toString():"0"));
					flowers=Integer.parseInt((mp.get("flowers")!=null?mp.get("flowers").toString():"0"));

				
					
					if(SysParameter.getInstatnce().getParams().get("wifi_account_flowers")!=null){
						description=SysParameter.getInstatnce().getParams().get("wifi_account_flowers").toString();
					}else{
						description="赚取小红花方式一：您分享的WiFi被其他用户成功连接，即送1朵小红花 \r\n\r\n 小红花作用：\r\n1 参与活动时享受更多的次数与特权\r\n2 可兑换实物礼品、优惠券等（敬请期待\r\n\r\n赚取小红花方式：您分享的WiFi被其他用户成功连接，即送1朵小红花 小红花作用：\r\n1 参与活动时享受更多的次数与特权 \r\n";
					}

					resultcode="0";
					msg="succ";		
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		DecimalFormat df = new DecimalFormat("0.00"); 
		
		double dscore = (double)score;  
		//jsondata.put("score", df.format(dscore/100).toString()+"元");
		jsondata.put("flowers", String.valueOf(flowers));
		//jsondata.put("hotpoints", String.valueOf(hotpoints));
		jsondata.put("description", description);
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 237:"+jsondata.toString());		
   
	}	
	
	public void func238() throws Exception{
	     
        func238Req obj=(func238Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		String groupid="";
		String status="";
		String password="";
		String acc_score="";
		
		String username="";
		       
		String alipay_account="";
		String chi_name="";
		int uid=0;
		String level="";
		String account_type="";
		String sex="";
		String city="";
		String province="";
		String country="";
		String unionid="";
		String group_mobile="";
		String group_wx="";
		String group_qq="";
		String account="";
		String login_date="";
		List ls=null;		
		int score=0;		
		try{
			
			if(obj.getLat()<=0 && obj.getLng()<=0){
				
				resultcode="1";
				msg="account not found.";
				msg="参数有误！";
		

			}else{
				String sql="select * from tbl_reg_account where lat > "+obj.getLat()+"-1 and lat < "+obj.getLat()+"+1 and lng > "+obj.getLng()+"-1 and lng < "+obj.getLng()+"+1 order by ACOS(SIN(("+obj.getLat()+" * 3.1415) / 180 ) *SIN(("+obj.getLng()+" * 3.1415) / 180 ) +COS(("+obj.getLat()+" * 3.1415) / 180 ) * COS((lat * 3.1415) / 180 ) *COS(("+obj.getLng()+"* 3.1415) / 180 - (lng * 3.1415) / 180 ) ) * 6380 asc limit 30";
				System.out.println(sql);
				ls=this.sysAccountDao.getData(sql,new String[]{});				
				if(ls!=null && ls.size()>0){
					HashMap groupMap=new HashMap();
					
					
					for(int i=0;i<ls.size();i++){
						Map mp=(Map)ls.get(i);
						account=(mp.get("account")==null?"":mp.get("account").toString());
						groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
						acc_score=(mp.get("score")!=null?mp.get("score").toString():"0");
						
						Double tmp_lat=Double.valueOf(mp.get("lat")!=null?mp.get("lat").toString():"0");
						Double tmp_lng=Double.valueOf(mp.get("lng")!=null?mp.get("lng").toString():"0");
						
						username=(mp.get("username")!=null?mp.get("username").toString():"");
						image=(mp.get("image")==null?"":(mp.get("image").toString().startsWith("http")?mp.get("image").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+mp.get("image").toString()));
						alipay_account=(mp.get("alipay_account")!=null?mp.get("alipay_account").toString():"");
						chi_name=(mp.get("chi_name")!=null?mp.get("chi_name").toString():"");
						
						level=(mp.get("level")!=null?mp.get("level").toString():"");
						account_type=(mp.get("account_type")!=null?mp.get("account_type").toString():"1");
						sex=(mp.get("sex")!=null?mp.get("sex").toString():"");
						city=(mp.get("city")!=null?mp.get("city").toString():"");
						province=(mp.get("province")!=null?mp.get("province").toString():"");
						country=(mp.get("country")!=null?mp.get("country").toString():"");
						login_date=(mp.get("logindate")!=null?mp.get("logindate").toString():"");
						
						int distace = Tools.calculateDistance(obj.getLat(), obj.getLng(), tmp_lat, tmp_lng);
						
						
						
						JSONObject item=new JSONObject();
						
						
						if(groupid.length()>0){
							account=groupid+":"+account+":"+account_type;
							if(groupMap.containsKey(groupid)){
								continue;
							}else{
								groupMap.put(groupid, groupid);
							}
						}
						
						item.put("username", account);
						item.put("nickname", username);
						item.put("image", image);
						item.put("sex", sex);
						item.put("city", city);
						item.put("province", province);
						item.put("longin_date", login_date);						
						item.put("distance", distace+"");
						jsonarray.add(item);
						
					}
					resultcode="0";
					msg="succ";
				}else{
					resultcode="0";
					msg="succ";
				}

				
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		
		jsondata.put("data", jsonarray);
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 238:"+jsondata.toString());		
   
	}	

	public void func239() throws Exception{
	     
        func239Req obj=(func239Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";

		List ls=null;		
		int score=0;		
		try{
			
			if(obj.getStatus().length()==0){
				
				resultcode="1";
				msg="account not found.";
				msg="参数有误！";
		

			}else{
				ls=this.sysAccountDao.getData("select * from tbl_sys_images where status=? order by sort,id ",new String[]{obj.getStatus()});				
				if(ls!=null && ls.size()>0){
					for(int i=0;i<ls.size();i++){
						
						JSONObject ditem=new JSONObject();
						Map	dmap=(Map)ls.get(i);
						ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
						ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
						ditem.put("image", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
						ditem.put("url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));					
						jsonarray.add(ditem);
						
					}
				}
				resultcode="0";
				msg="succ";

				
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		
		jsondata.put("data", jsonarray);
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 239:"+jsondata.toString());		
   
	}						
	
	public void func240() throws Exception{
	     
        func240Req obj=(func240Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		int flower=0;
		List ls=null;		
		int score=0;		
		try{
			
			if(obj.getAccount().length()==0){
				
				resultcode="1";
				msg="account not found.";
				msg="参数有误！";
		

			}else{
				ls=this.sysAccountDao.getData("select account from tbl_video_logs where account=? and indate>= curDate() order by id ",new String[]{obj.getAccount()});				
				if(ls!=null && ls.size()>=0 && ls.size()<10){
					flower=1;
				
					this.sysAccountDao.executedSql("insert into tbl_video_logs(account,flowers,indate) values(?,?,now())", new String[]{obj.getAccount(),String.valueOf(flower)});
					this.sysAccountDao.executedSql("update tbl_reg_account set flowers=ifnull(flowers,0)+? where account=?", new String[]{String.valueOf(flower),obj.getAccount()});

					msg="获得一朵小红花！";
					
				}else{

					msg="今天小红花已领完！";
					
				}
				
				resultcode="0";
								
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		
		jsondata.put("data", jsonarray);
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 239:"+jsondata.toString());		
   
	}						
		
	public void func243() throws Exception{
	     
        func243Req obj=(func243Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";

		List ls=null;		
		int score=0;		
		try{
			

				this.sysAccountDao.executedSql("insert into tbl_rongyu_logs(groupid,account,account_type,tgroupid,taccount,taccount_type,indate) values(?,?,?,?,?,?,now())", new String[]{obj.getGroupid(),obj.getUsername(),obj.getLogin_type(),obj.getTgroupid(),obj.getTalk_id(),obj.getTlogin_type()});				
				resultcode="0";
				msg="succ";

				

				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		
		//jsondata.put("data", jsonarray);
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 243:"+jsondata.toString());		
   
	}							
	
}
