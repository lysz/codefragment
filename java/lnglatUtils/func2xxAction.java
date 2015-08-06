package com.aora.wifi.action.interfaces;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.aora.wifi.action.ChongZhiAction;
import com.aora.wifi.bean.*;

import com.aora.wifi.dao.impl.SysAccountDaoImpl;
import com.aora.wifi.dao.impl.SysSharedAccountDaoImpl;
import com.aora.wifi.servlet.SysParameter;
import com.aora.wifi.tools.LatitudeLontitudeUtil;
import com.aora.wifi.tools.PropertyInfo;
import com.aora.wifi.util.Tools;
import com.aora.wifi.util.cachectl;

public class func2xxAction extends BaseBean {


private static SysAccountDaoImpl sysAccountDao=new SysAccountDaoImpl();
	

private final static Logger logger = Logger.getLogger(func2xxAction.class);



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
	case 241:
		this.func241();	
		break;	
	case 242:
		this.func242();	
		break;		
	case 243:
		this.func243();	
		break;	
	case 244:
		this.func244();	
		break;	
	case 245:
		this.func245();	
		break;
	case 246:
		this.func246();	
		break;
	case 247:
		this.func247();	
		break;		
	case 251:
        this.func251(); 
        break; 
    case 252:
        this.func252(); 
        break; 
    case 253:
        this.func253(); 
        break; 
    case 254:
        this.func254(); 
        break; 
    case 255:
        this.func255(); 
        break; 
    case 256:
        this.func256(); 
        break;
    case 257:
        this.func257(); 
        break;   
    case 260:
        this.func260(); 
        break; 
    case 261:
        this.func261(); 
        break;
    case 262:
        this.func262(); 
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
							tmpscore=0;
							int tmpflowers=0;
/* remark 20150625 */
							if(obj.getChannel()!=null && obj.getChannel().length()>0){
								ls=this.sysAccountDao.getData("select * from tbl_reg_channel_bonus where channel=? and start_date<=now() and now()<=end_date and status=1 limit 1 ",new String[]{obj.getChannel()});
								
								if(ls!=null && ls.size()>0){
									Map map=(Map)ls.get(0);
									tmpflowers=Integer.parseInt((map.get("reg_score")!=null?map.get("reg_score").toString():"0"));	
								}
							}
/**/
							
							this.sysAccountDao.executedSql("insert into tbl_reg_account(account,password,username,status,indate,score,flowers,channel,level,alipay_account,chi_name) values(?,?,?,'0',now(),"+tmpscore+","+tmpflowers+",?,'1','','')", new String[]{obj.getReg_account(),obj.getReg_password(),obj.getReg_account().subSequence(0, 3)+"****"+obj.getReg_account().substring(7),obj.getChannel()});
							
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
		int signdays=0;
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
		String acc_signdays="";
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
							sysAccountDao.executedSql("update tbl_wifi_share_account_logs set remark=?,uid='',reg_account=? where uid=?", new String[]{obj.getUID(),obj.getLogin_account(),obj.getUID()});
							sysAccountDao.executedSql("update tbl_wifi_share_login_logs set remark=?,uid='',account=? where uid=?", new String[]{obj.getUID(),obj.getLogin_account(),obj.getUID()});
							
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

					acc_signdays=(mp.get("signdays")!=null?mp.get("signdays").toString():"0");
					signdays=Integer.parseInt(acc_signdays);					
					signscore=SysParameter.getInstatnce().getSYS_SIGN_SCORE_BASCE()+(signdays<6?signdays*SysParameter.getInstatnce().getSYS_SIGN_SCORE_PER_DAY():5*SysParameter.getInstatnce().getSYS_SIGN_SCORE_PER_DAY());					
					
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
					
					
					if(groupid.length()>0){
//						ls=this.sysAccountDao.getData("select max(ifnull(signdays,0)) 'signdays' from tbl_reg_account where groupid=? and status=0 ",new String[]{groupid});
//						if(ls!=null && ls.size()>0){
//							Map mp1=(Map)ls.get(0);
//							signdays=Integer.parseInt(mp1.get("signdays")!=null?mp1.get("signdays").toString():"0");
//						}
						
						ls=this.sysAccountDao.getData("select * from tbl_user_signin_logs where groupid=? and sign_date=?",new String[]{groupid,Tools.date2String(new Date(), "yyyy-MM-dd")});
						//ls=this.sysAccountDao.getData("select * from tbl_user_signin_logs where account=? and sign_date=?",new String[]{groupid,Tools.date2String(new Date(), "yyyy-MM-dd")});
					}else{
						ls=this.sysAccountDao.getData("select * from tbl_user_signin_logs where account=? and sign_date=?",new String[]{obj.getLogin_account(),Tools.date2String(new Date(), "yyyy-MM-dd")});
					}
					if(ls!=null && ls.size()>0){
						issigntoday=1;
					
						Map mp1=(Map)ls.get(0);
						signscore=Integer.parseInt((mp1.get("addscore")!=null?mp1.get("addscore").toString():""));
						
					}else{
						issigntoday=0;
					}
						

					
					
					
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
								sysAccountDao.executedSql("update tbl_wifi_share_account_logs set remark=?,uid='',reg_account=? where uid=?", new String[]{obj.getUID(),obj.getLogin_account(),obj.getUID()});
								sysAccountDao.executedSql("update tbl_wifi_share_login_logs set remark=?,uid='',account=? where uid=?", new String[]{obj.getUID(),obj.getLogin_account(),obj.getUID()});
								
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
									sysAccountDao.executedSql("update tbl_reg_account a cross join (select sum(ifnull(score,0))+"+uid_score+" 'score' ,sum(ifnull(flowers,0))+"+uid_flowers+" 'flowers' from tbl_reg_account where groupid=?)  b  set  a.score=b.score,a.flowers=b.flowers,a.lat="+obj.getHeader_latitude()+",a.lng="+obj.getHeader_longitude()+",a.logindate=now() where a.account=?",new String[]{groupid,obj.getLogin_account()});
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
		
		
		jsondata.put("signdays", signdays);
		jsondata.put("signscore", signscore);
		jsondata.put("presignscore", (signdays<6?(++signscore):signscore));
		jsondata.put("issigntoday", issigntoday);
		

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 203:"+jsondata.toString());		
	}
		

	
	public void func204() throws Exception{
		func204Req obj=(func204Req)request.getAttribute(SysParameter.REQUESTBEAN);
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		int signday=-1;
		int score=-1;
		int flowers=-1;
		String groupid="";
		String status="";

		int signscore=0;
		int issigntoday=0;		
		
		List ls=null;			
		try{
			synchronized (Tools.synchronized_204_obj){ 			
			

			if(obj.getAccount()!=null && obj.getAccount().trim().length()>0){ 
				ls=this.sysAccountDao.getData("select * from tbl_reg_account where status=0 and account=?",new String[]{obj.getAccount()});
				if(ls==null || ls.size()==0){
					resultcode="1";
					msg="无此帐号信息。";
					
				}else{
					Map mp=(Map)ls.get(0);
					score=Integer.parseInt((mp.get("score")==null?"0":mp.get("score").toString()));
					flowers=Integer.parseInt((mp.get("flowers")==null?"0":mp.get("flowers").toString()));
					signday=Integer.parseInt((mp.get("signdays")==null?"0":mp.get("signdays").toString()));
					groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
					status=(mp.get("status")==null?"":mp.get("status").toString());					
					if(groupid.length()>0){
						ls=this.sysAccountDao.getData("select * from tbl_user_signin_logs where groupid=? and sign_date=?",new String[]{groupid,Tools.date2String(new Date(), "yyyy-MM-dd")});
					}else{
						ls=this.sysAccountDao.getData("select * from tbl_user_signin_logs where account=? and sign_date=?",new String[]{obj.getAccount(),Tools.date2String(new Date(), "yyyy-MM-dd")});
					}
					if(ls==null || ls.size()==0){
						int addscore=SysParameter.getInstatnce().getSYS_SIGN_SCORE_BASCE()+(signday<6?signday*SysParameter.getInstatnce().getSYS_SIGN_SCORE_PER_DAY():5*SysParameter.getInstatnce().getSYS_SIGN_SCORE_PER_DAY());
						flowers+=addscore;
						signday++;
						
						this.sysAccountDao.executedSql("insert into tbl_user_signin_logs(account,groupid,sign_date,indate,addscore) values(?,?,now(),now(),?)",new String[]{obj.getAccount(),groupid,String.valueOf(addscore)});

						if(groupid.length()>0){
							this.sysAccountDao.executedSql("update tbl_reg_account set flowers=?,signdays=? where account=?",new String[]{String.valueOf(flowers),String.valueOf(signday),obj.getAccount()});
							this.sysAccountDao.executedSql("update tbl_reg_account set signdays=? where groupid=?",new String[]{String.valueOf(signday),groupid});
						}else{
							this.sysAccountDao.executedSql("update tbl_reg_account set flowers=?,signdays=? where account=?",new String[]{String.valueOf(flowers),String.valueOf(signday),obj.getAccount()});
						}						
						
						signscore=addscore;
						issigntoday=1;
						
						resultcode="0";
						msg="succ";
					}else{

						resultcode="3";
						msg="今天已签到！";
					}

				}  
				
				
				
				}else{
					resultcode="-1";
					msg="帐号信息错误。";
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		jsondata.put("flowers", flowers);
		jsondata.put("signdays", signday);
		jsondata.put("signscore", signscore);
		jsondata.put("presignscore", (signday<6?(++signscore):signscore));
		jsondata.put("issigntoday", issigntoday);
		

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 204:"+jsondata.toString());				
     
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
					if(obj.getIsIOS().equals("1")){
						obj.setChannel("Apple IOS");
					}
					String clientinfo=obj.getHeader_version()+"|"+obj.getHeader_brand()+"|"+obj.getHeader_model()+"|"+obj.getHeader_latitude()+"|"+obj.getHeader_longitude()+"|"+obj.getHeader_city();
				
					this.sysAccountDao.executedSql("insert into tbl_user_question(user,ask,contact,indate,uid,channel,client_info) values(?,?,?,now(),?,?,?)", new String[]{obj.getAccount(),obj.getAsk(),(obj.getContact()==null?"":obj.getContact()),obj.getUID(),obj.getChannel(),clientinfo});
					
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
						item.put("score_count", String.valueOf(scored)+"金币");
						
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
					sql="select a.id,a.status,a.longitude,a.latitude,a.account,a.wifi_gps_address,a.wifi_address,a.mac_address,a.wifi_type,a.createby_login_id,b.score,b.share_score,b.id 'tid',b.indate, case when a.status>0 and a.createby_client_id=b.remark then '1' else '0' end 'active' from tbl_wifi_share_account a,tbl_wifi_share_account_logs b where a.mac_address=b.mac_address  and b.reg_account in ("+user+") order by b.indate desc ";
					
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
						int share_score=Integer.parseInt((map.get("share_score")==null?"0":map.get("share_score").toString()));
					
						
												
						DecimalFormat df = new DecimalFormat("0.00");  
						double dscore = (double)scored;  
						item.put("score_count", df.format(dscore/100).toString()+"元");
						item.put("score_count", String.valueOf(scored+share_score)+"金币");
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
					
					sql="select a.id,a.status,a.longitude,a.latitude,a.account,a.wifi_gps_address,a.wifi_address,a.mac_address,a.wifi_type,a.createby_login_id,b.score,b.share_score,b.id 'tid',b.indate, case when a.status>0 and a.createby_client_id=b.uid then '1' else '0' end 'active' from tbl_wifi_share_account a,tbl_wifi_share_account_logs b where a.mac_address=b.mac_address  and b.uid='"+obj.getUID()+"' order by b.indate desc ";
					
					
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
						int share_score=Integer.parseInt((map.get("share_score")==null?"0":map.get("share_score").toString()));

						
						DecimalFormat df = new DecimalFormat("0.00");  
						double dscore = (double)scored;  
						item.put("score_count", df.format(dscore/100).toString()+"元");
						item.put("score_count", String.valueOf(scored+share_score)+"金币");						
						
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

		int flowers=-1;
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
					flowers=Integer.parseInt((mp.get("flowers")==null?"0":mp.get("flowers").toString()));
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
		DecimalFormat df1 = new DecimalFormat("0.00");
		df = new DecimalFormat("###0");
		double dscore = (double)score;  
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("alipay_account", alipay_account);
		jsondata.put("chi_name", chi_name);

//		jsondata.put("price", df.format(((double)price)/100).toString());		
		jsondata.put("min_score", df.format(((double)min_score)/100));
		jsondata.put("max_score", df.format(((double)max_score)/100));
		jsondata.put("inventory_huafei",  df.format(((double)(per_day_telephone_coin_stack-currentdate_huafei_coin<0?0:per_day_telephone_coin_stack-currentdate_huafei_coin))/100));
		jsondata.put("min_qb",  df.format(((double)min_qb)/100));
		jsondata.put("max_qb",  df.format(((double)max_qb)/100));		
		jsondata.put("inventory_qb",  df.format(((double)(per_day_qq_coin_stock-currentdate_qq_coin<0?0:per_day_qq_coin_stock-currentdate_qq_coin))/100));
//		jsondata.put("score", df.format(((double)score)/100));
//		jsondata.put("active_score",df.format(((double)money)/100) );	
		
		jsondata.put("price", String.valueOf(price));		
//		jsondata.put("min_score", String.valueOf(min_score));
//		jsondata.put("max_score", String.valueOf(max_score));
//		jsondata.put("inventory_huafei",  String.valueOf((per_day_telephone_coin_stack-currentdate_huafei_coin<0?0:per_day_telephone_coin_stack-currentdate_huafei_coin)));
//		jsondata.put("min_qb",  String.valueOf(min_qb));
//		jsondata.put("max_qb",  String.valueOf(max_qb));		
//		jsondata.put("inventory_qb",  String.valueOf((per_day_qq_coin_stock-currentdate_qq_coin<0?0:per_day_qq_coin_stock-currentdate_qq_coin)));
		jsondata.put("score", df1.format(((double)score)/100));
		jsondata.put("flowers", String.valueOf(flowers));		
		jsondata.put("active_score",df1.format(((double)money)/100) );	
		
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

		int flowers=-1;		
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
					flowers=Integer.parseInt((mp.get("flowers")==null?"0":mp.get("flowers").toString()));
					alipay_account=(mp.get("alipay_account")!=null&& mp.get("alipay_account").toString().length()>5?mp.get("alipay_account").toString():obj.getAlipay_account());
					chi_name=(mp.get("chi_name")!=null && mp.get("chi_name").toString().length()>0?mp.get("chi_name").toString():obj.getChi_name());
					status=(mp.get("status")!=null?mp.get("status").toString():"0");;
					
					if(!alipay_account.equalsIgnoreCase(obj.getAlipay_account()) ){
						resultcode="2";
						msg="支附宝帐号不匹配！";
					}else if(!status.equals("0")){
						resultcode="6";
						msg="帐号异常，请与客服务联系！";
						
					//}else if( score<obj.getMoney()){
					}else if( flowers<obj.getMoney()){	
						resultcode="6";
						msg="帐户余额不足！";
						
					}else if(obj.getMoney()<min_score && obj.getMoney()> max_score){
						resultcode="3";
						msg="亲，每次只能提"+(min_score/100)+"~"+(max_score/100)+"元现金哦！";	
					}else{
						if(obj.getGroupid().length()>0){
							ls=this.sysAccountDao.getData("select * from tbl_reg_account where ifnull(groupid,'')!=? and alipay_account=?",new String[]{obj.getGroupid(),obj.getAlipay_account()});							
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
						String group_mobile="";
						if(obj.getGroupid().length()>0){
							ls=this.sysAccountDao.getData("select * from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupid()});
							String user="";
							
							if(ls!=null && ls.size()>0){
								for(int i=0;i<ls.size();i++){
									Map map=(Map)ls.get(i);
									user+="'"+(map.get("account")==null?"":map.get("account").toString())+"',";
									if(map.get("account_type")!=null && map.get("account_type").toString().equals("1")){
										group_mobile=(map.get("account")==null?"":map.get("account").toString());
									}
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
						}else if(obj.getGroupid().length()>0 && group_mobile.length()==0){
							resultcode="7";
							msg="帐号未绑定手机号，请绑定后重试!";								
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
									if(obj.getGroupid().length()>0){
										this.sysAccountDao.executedSql("update tbl_reg_account set chi_name=?,alipay_account=? where groupid=? and account_type=1", new String[]{obj.getChi_name(),obj.getAlipay_account(),obj.getGroupid()});										
									}
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
		//jsondata.put("score", df.format(((double)score)/100));
		
		jsondata.put("score", String.valueOf(flowers));
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
						//item.put("active_score", String.valueOf(scored)+"金币");
						
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
						//item.put("active_score", String.valueOf(scored)+"金币");
						
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
            List list = this.sysAccountDao.getData("select flowers as score, status from tbl_reg_account where account=?", new String[]{obj.getUsername()});
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
            
            String user="";
            if (StringUtils.isNotEmpty(obj.getGroupId())){
                List ls = sysAccountDao.getData("select account from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupId()});
                if(ls != null && ls.size() > 0){
                    for(int i=0; i < ls.size(); i++){
                        Map map=(Map)ls.get(i);
                        user+="'"+(map.get("account")==null?"":map.get("account").toString())+"',";
                    }
                }

                if(user.length()>0){user=user.substring(0,user.length()-1);}
            }else{
                user = "'" + obj.getUsername() + "'";
            }

            //查询当天是否有过兑换Q币的记录
            sql = "select id from tbl_chongzhi where (used_login_id in (" + user + ") or target='" + obj.getActive_account() + "') and (status = 0 or status = 2) and category = 1 and date(createtime) = curdate()"; 
            list = sysAccountDao.getData(sql, new String[]{});
            if (null != list && !list.isEmpty()){
                jsondata.put("resultcode", "4");
                jsondata.put("msg", "亲，Q币每天只能兑换一次，请明天再来吧!");    
                respData(jsondata.toString(), request, response);
                return;
            }
            
            //扣除账户的余额
            sql = "update tbl_reg_account set flowers = flowers- " + qty + " where account='" + obj.getUsername() + "' and flowers=" + score + " and flowers >= " + qty;
            int rowCount = sysAccountDao.executedUpdateSql(sql, new String[]{});
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
            List list = this.sysAccountDao.getData("select flowers as score, status from tbl_reg_account where account=?", new String[]{obj.getUsername()});
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
            
            String user="";
            if (StringUtils.isNotEmpty(obj.getGroupId())){
                List ls = sysAccountDao.getData("select account from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupId()});
                if(ls != null && ls.size() > 0){
                    for(int i=0; i < ls.size(); i++){
                        Map map=(Map)ls.get(i);
                        user+="'"+(map.get("account")==null?"":map.get("account").toString())+"',";
                    }
                }

                if(user.length()>0){user=user.substring(0,user.length()-1);}
            }else{
                user = "'" + obj.getUsername() + "'";
            }
            
            //查询当天是否有过兑换话费的记录
            sql = "select id from tbl_chongzhi where (used_login_id in (" + user + ") or target='" + obj.getActive_account() + "')  and category = 2 and (status = 0 or status = 2) and date(createtime) = curdate()"; 
            list = sysAccountDao.getData(sql, new String[]{});
            if (null != list && !list.isEmpty()){
                jsondata.put("resultcode", "6");
                jsondata.put("msg", "亲，话费每天只能兑换一次，请明天再来吧!");    
                respData(jsondata.toString(), request, response);
                return;
            }
            
            //扣除账户的余额
            int rowCount = sysAccountDao.executedUpdateSql("update tbl_reg_account set flowers = flowers- ? where account=? and flowers=? and flowers >= ?", new String[]{qty+"", obj.getUsername(), score+"", qty+""});
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
				sql="select date_format(createtime, '%Y-%m-%d %H:%i:%s') as createtime, category, target, price/10/10 as price, status from tbl_chongzhi where (category=1 or category=2) and used_login_id in ("+user+")";
				ls=this.sysAccountDao.getData(sql,new String[]{});
			}else{
				ls=new ArrayList();
			}
			
		}else{
	    	String sql = "select date_format(createtime, '%Y-%m-%d %H:%i:%s') as createtime, category, target, price/10/10 as price, status from tbl_chongzhi where (category=1 or category=2) and used_login_id=?";
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
			
			this.sysAccountDao.executedSql("insert into tbl_report_error_logs(uid,account,error_description,error_function,longitude,latitude,city,version,platform,rip,type,indate) values(?,?,?,?,?,?,?,?,?,?,?,now())",new String[]{obj.getUID(),obj.getAccount(),obj.getError_description(),obj.getError_function(),obj.getLongitude(),obj.getLatitude(),obj.getCity(),obj.getHeader_version(),obj.getPlatform(),obj.getReal_ip(),obj.getType()});
						
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
		String acc_signdays="0";
		
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
		int signdays=0;
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
				acc_signdays=(mp.get("signdays")!=null?mp.get("signdays").toString():"0");
				
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
					msg="异常情况，不能被绑定！";		
				}
				
				if(obj.getBind_id().length()<10){
					resultcode="5";
					msg="绑定ID异常，不能被绑定！";		
					
				}
				
				if(groupid.length()>0 && resultcode.equals("0")){
					List lsdata=this.sysAccountDao.getData("select * from tbl_reg_account where  groupid=? and account_type=? limit 1", new String[]{groupid,obj.getBind_type()});
					if(lsdata!=null && lsdata.size()>0){
						resultcode="2";
						msg="该号码已绑定过其它帐号！";						
					}
				}
				if(obj.getAction_code().length()>0 && obj.getBind_type().equals("1") && resultcode.equals("0")){
					ls=this.sysAccountDao.getData("select * from tbl_sendsms where mobile=? and regcode=? and indate between date_add(now(), interval -30 minute) and now() ",new String[]{obj.getBind_id(),obj.getAction_code()});
					if(ls==null || ls.size()==0){
						resultcode="7";
						msg="验证码错误或已失效！";
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
						String Talipay_account=(map.get("alipay_account")!=null?map.get("alipay_account").toString():"");
						String Tchi_name=(map.get("chi_name")!=null?map.get("chi_name").toString():"");
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
							msg="该号码已绑定过其它帐号！";	
						}else if( Tgroupid.length()>0 && groupid.length()==0 && this.sysAccountDao.getData("select * from tbl_reg_account where  groupid=? and account_type=? limit 1", new String[]{Tgroupid,account_type}).size()>0){
							resultcode="2";
							msg="该号码已绑定过其它帐号！";					
						}else if(Tgroupid.length()>0 && !Tgroupid.equals(groupid) && groupid.length()>0){
							resultcode="3";
							msg="该号码已被其它用户绑定！";	
						}else if (Tgroupid.length()>0 && Tgroupid.equals(groupid)){
							resultcode="4";
							msg="请勿重复绑定！";	
						}else if(!Tstatus.equals("0")){
							resultcode="6";
							msg="绑定账号已被冻结！";	
							
						}else{
							String psql="";
							if(obj.getBind_type().equals("1")){ // mobile
								if(Tusername.length()>0){psql+=",username='"+Tusername+"'";username=Tusername;}
								if(Timage.length()>0){	psql+=",image='"+Timage+"' ";}
								if(Tsex.length()>0){psql+=",sex='"+Tsex+"'";sex=Tsex;}
								if(Tcity.length()>0){psql+=",city='"+Tcity+"'";city=Tcity;}
								if(Tprovince.length()>0){psql+=",province='"+Tprovince+"'";province=Tprovince;}
								if(Talipay_account.length()>0){psql+=",alipay_account='"+Talipay_account+"'";alipay_account=Tprovince;}
								if(Tchi_name.length()>0){psql+=",chi_name='"+Tchi_name+"'";chi_name=Tchi_name;}
								
							}else if(obj.getBind_type().equals("2")){ // wx
								if(obj.getNickname().length()>0){psql+=",username='"+obj.getNickname()+"'";username=obj.getNickname();}
								if(obj.getImage().length()>0){psql+=",image='"+obj.getImage()+"'";image=obj.getImage();}
								if(obj.getSex().length()>0){psql+=",sex='"+obj.getSex()+"'";sex=obj.getSex();}
								if(obj.getCity().length()>0){psql+=",city='"+obj.getCity()+"'";city=obj.getCity();}
								if(obj.getProvince().length()>0){psql+=",province='"+obj.getProvince()+"'";province=obj.getProvince();}
								if(obj.getCountry().length()>0){psql+=",country='"+obj.getCountry()+"'";country=obj.getCountry();}
								if(obj.getLogin_type().equals("1")){
									if(alipay_account.length()>0){psql+=",alipay_account='"+alipay_account+"'";}
									if(chi_name.length()>0){psql+=",chi_name='"+chi_name+"'";}
								}else{
									if(alipay_account.length()>0 && Talipay_account.length()==0){psql+=",alipay_account='"+alipay_account+"'";}
									else if(alipay_account.length()==0 && Talipay_account.length()>0){psql+=",alipay_account='"+Talipay_account+"'";alipay_account=Tprovince;}
									if(chi_name.length()>0 && Tchi_name.length()==0){psql+=",chi_name='"+chi_name+"'";}
									else if(chi_name.length()==0 && Tchi_name.length()>0){psql+=",chi_name='"+Tchi_name+"'";chi_name=Tchi_name;}
								}
								
							}else if(obj.getBind_type().equals("3")){ // qq
								if(obj.getNickname().length()>0){psql+=",username='"+obj.getNickname()+"'"; username=obj.getNickname();}
								if(obj.getImage().length()>0){psql+=",image='"+obj.getImage()+"'";image=obj.getImage();}
								if(obj.getSex().length()>0){psql+=",sex='"+obj.getSex()+"'";sex=obj.getSex();}
								if(obj.getCity().length()>0){psql+=",city='"+obj.getCity()+"'";city=obj.getCity();}
								if(obj.getProvince().length()>0){psql+=",province='"+obj.getProvince()+"'";province=obj.getProvince();}
								if(obj.getCountry().length()>0){psql+=",country='"+obj.getCountry()+"'"; country=obj.getCountry();}							
								if(obj.getUnionid().length()>0){psql+=",unionid='"+obj.getUnionid()+"'";unionid=obj.getUnionid();}
								if(obj.getLogin_type().equals("1")){
									if(alipay_account.length()>0){psql+=",alipay_account='"+alipay_account+"'";}
									if(chi_name.length()>0){psql+=",chi_name='"+chi_name+"'";}
								}else{
									if(alipay_account.length()>0 && Talipay_account.length()==0){psql+=",alipay_account='"+alipay_account+"'";}
									else if(alipay_account.length()==0 && Talipay_account.length()>0){psql+=",alipay_account='"+Talipay_account+"'";alipay_account=Tprovince;}
									if(chi_name.length()>0 && Tchi_name.length()==0){psql+=",chi_name='"+chi_name+"'";}
									else if(chi_name.length()==0 && Tchi_name.length()>0){psql+=",chi_name='"+Tchi_name+"'";chi_name=Tchi_name;}
								}
																
							}
							
							if(groupid.length()==0 && Tgroupid.length()>0){
								groupid=Tgroupid;
							}
							
							if(groupid.length()==0){
								groupid=String.valueOf(System.currentTimeMillis());
							}
							
							
							if(Tgroupid.length()>0){
								sysAccountDao.executedSql("update tbl_reg_account a cross join (select sum(ifnull(score,0)) 'score',sum(ifnull(flowers,0)) 'flowers' from tbl_reg_account where groupid=? or account=? or account=? )  b  set  a.score=b.score,a.flowers=b.flowers , a.groupid=? where a.account=?",new String[]{Tgroupid,obj.getBind_id(),obj.getAccount(),Tgroupid,obj.getAccount()});
								sysAccountDao.executedSql("update tbl_reg_account set  remark=score, score=0,flowers=0  where groupid=? and  account!=? ;",new String[]{groupid,obj.getAccount()});
							}else{
								sysAccountDao.executedSql("update tbl_reg_account a cross join (select sum(ifnull(score,0)) 'score',sum(ifnull(flowers,0)) 'flowers'  from tbl_reg_account where account=? or account=? )  b  set  a.score=b.score,a.flowers=b.flowers , a.groupid=? where a.account=?",new String[]{obj.getAccount(),obj.getBind_id(),groupid,obj.getAccount()});
								sysAccountDao.executedSql("update tbl_reg_account set  remark=score, score=0 ,flowers=0,groupid=? where account=? ;",new String[]{groupid,obj.getBind_id()});
							}
							
							
							sysAccountDao.executedSql("update tbl_reg_account set  signdays=?,groupid=?"+psql +" where groupid=?",new String[]{acc_signdays,groupid,groupid});															
							
							sysAccountDao.executedSql("update tbl_user_signin_logs set groupid=? where account=? or account=?", new String[]{groupid,obj.getAccount(),obj.getBind_id()});
						}
					}else{

						String psql="";						
						if(groupid.length()==0){
							groupid=String.valueOf(System.currentTimeMillis());
							sysAccountDao.executedSql("update tbl_reg_account set  groupid=?,binddate=now()  where account=? ;",new String[]{groupid,obj.getAccount()});
						}						
						
						psql+="groupid='"+groupid+"'";

						
						if(obj.getBind_type().equals("1")){ // mobile
							if(username.length()==0){
								username=obj.getBind_id().substring(0,3)+"****"+obj.getBind_id().substring(7);
							}

							int tmpflowers=0;
/*remark 20150625	*/						
							if(obj.getChannel()!=null && obj.getChannel().length()>0){
								ls=this.sysAccountDao.getData("select * from tbl_reg_channel_bonus where channel=? and start_date<=now() and now()<=end_date and status=1 limit 1 ",new String[]{obj.getChannel()});
								
								if(ls!=null && ls.size()>0){
									Map map=(Map)ls.get(0);
									tmpflowers=Integer.parseInt((map.get("reg_score")!=null?map.get("reg_score").toString():"0"));	
								}
							}							
/*  */							
							this.sysAccountDao.executedSql("insert into tbl_reg_account(account,password,username,status,score,flowers,indate,image,channel,sex,city,province,country,privilege,unionid,groupid,account_type,binddate,chi_name,alipay_account) values(?,?,?,0,0,"+tmpflowers+",now(),?,?,?,?,?,?,'','',?,'1',now(),?,?)", 
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
						
						sysAccountDao.executedSql("update tbl_reg_account set  "+psql+" ,signdays=?  where groupid=? ;",new String[]{groupid,acc_signdays});
						
						sysAccountDao.executedSql("update tbl_user_signin_logs set groupid=? where (account=? or account=?) and sign_date=curdate()", new String[]{groupid,obj.getAccount(),obj.getBind_id()});						
						sysAccountDao.executedSql("delete from tbl_user_signin_logs where account=? and sign_date=curdate()", new String[]{obj.getBind_id()});
						
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
							msg="绑定账号已被冻结！";	
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
					username="游客";
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
					HashMap hp=new HashMap();
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
							if(hp.containsKey(groupid)){
								continue;
							}else{
								hp.put(groupid, groupid);								
							}
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
		JSONObject api_response=new JSONObject();
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



//					String App_Key="3argexb6rn3ae";
//					String APP_Secret="WavVQiKCJ3I";
//					String Nonce=String.valueOf(new java.util.Random(10).nextInt(10000000));
//					String Timestamp=String.valueOf(System.currentTimeMillis()/1000);
//					String Signature= Tools.sha1(APP_Secret+Nonce+Timestamp);
//
//					userid="userId="+(groupid.length()>0?groupid:obj.getAccount());
//					
//					String name="name="+(username.length()>0?username:"");
//					String img_url="portraitUri="+Tools.URLEncode(image, "UTF-8");
//					//userId=jlk456j5&name=Ironman&portraitUri=http%3A%2F%2Fabc.com%2Fmyportrait.jpg
//					HashMap header=new HashMap();
//					 header.put("App-Key", App_Key);
//					 header.put("Nonce", Nonce);
//					 header.put("Timestamp", Timestamp);
//					 header.put("Signature", Signature);
//					String data=userid+"&"+name+"&"+img_url;
//					String posturl="https://api.cn.rong.io/user/getToken.json";
//					c				
					
					String App_Account="8a48b5514d32a2a8014d504fad501617";
					String APP_Token="d5731c1bd59f48198de0df1c4f966b3e";
					String App_ID="aaf98f894d328b13014d505361d51697";
					String Timestamp=Tools.date2String(new Date(), "yyyyMMddHHmmss");

					String sig=Tools.asHex(Tools.MD5(App_Account+APP_Token+Timestamp));
					BASE64Encoder base64 = new BASE64Encoder();		
					String auth=base64.encode((App_Account+":"+Timestamp).getBytes());
					
					
					HashMap header=new HashMap();
					header.put("Accept", "application/json"); 
					header.put("Content-Type", "application/json;charset=utf-8");
					header.put("Authorization", auth);
					 
					JSONObject json=new JSONObject();					
					json.put("appId", App_ID);
					json.put("friendlyName", (obj.getGroupid().length()>0?obj.getGroupid():obj.getAccount()));
					String data=json.toString();
					String posturl="";
					byte[] response=null;
					JSONObject js=null;
					
					posturl="https://sandboxapp.cloopen.com:8883/2013-12-26/Accounts/"+App_Account+"/QuerySubAccountByName?sig="+sig;
					//posturl="https://app.cloopen.com:8883/2013-12-26/Accounts/"+App_Account+"/QuerySubAccountByName?sig="+sig;
					response =Tools.postdata(posturl, data, header);
					System.out.println(new String(response,"UTF-8"));
					if(response.length>10){
						js=(JSONObject) JSONSerializer.toJSON( new String(response,"UTF-8") );
						
						if(js.containsKey("statusCode")&& js.get("statusCode").toString().equals("000000") && js.containsKey("SubAccount") && js.optJSONObject("SubAccount")!=null){
							JSONObject SubAccount=(js.containsKey("SubAccount")?js.getJSONObject("SubAccount"):new JSONObject() );
							userid=(SubAccount.containsKey("subAccountSid")?SubAccount.getString("subAccountSid"):"" );
							api_response=SubAccount;
							resultcode="0";
							msg="succ";
						}else{
							posturl="https://sandboxapp.cloopen.com:8883/2013-12-26/Accounts/"+App_Account+"/SubAccounts?sig="+sig;					
							//posturl="https://app.cloopen.com:8883/2013-12-26/Accounts/"+App_Account+"/SubAccounts?sig="+sig;
							response =Tools.postdata(posturl, data, header);	
					
							System.out.println(new String(response,"UTF-8"));
							if(response.length>10){
								js=(JSONObject) JSONSerializer.toJSON( new String(response,"UTF-8") );
								if(js.containsKey("statusCode")&& js.get("statusCode").toString().equals("000000")){
									JSONObject SubAccount=(js.containsKey("SubAccount")?js.getJSONObject("SubAccount"):new JSONObject() );
									userid=(SubAccount.containsKey("subAccountSid")?SubAccount.getString("subAccountSid"):"" );
									api_response=SubAccount;
									resultcode="0";
									msg="succ";
								}
								else{
									resultcode="2";
									msg="api 调用失败！";
									api_response=js;
								}
								
							}else{
								resultcode="2";
								msg="api 调用失败！";
							}
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
		jsondata.put("api_data", api_response);
		DecimalFormat df = new DecimalFormat("0.00"); 
		score=Integer.parseInt(acc_score);
		double dscore = (double)score;  
		jsondata.put("score", df.format(dscore/100).toString()+"元");
		jsondata.put("userid", userid);
		jsondata.put("username", username);
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
		String username="";
		int flowers=0;
		String acc_flowers;
		String alipay_account="";
		String chi_name="";
		String level="";
		String password="";
		String acc_score="";
		String city="";
		String province="";
		String country="";
		String sex="";
		String group_qq="";
		String group_mobile="";
		String group_wx="";	
		int uid=0;
		int score=0;	
		int hotpoints=0;
		List ls=null;
		try{

			ls=this.sysAccountDao.getData("SELECT * FROM tbl_reg_account where status=0 and account=?",new String[]{obj.getAccount()});			
			if(ls==null || ls.size()==0){
				
				resultcode="2";
				msg="account not found.";
				msg="帐号有误！";
		

			}else{
					Map mp=(Map)ls.get(0);
					groupid=(mp.get("groupid")==null?"":mp.get("groupid").toString());
					status=(mp.get("status")==null?"":mp.get("status").toString());
					//password=(mp.get("password")==null?"":mp.get("password").toString());
					score=Integer.parseInt((mp.get("score")!=null?mp.get("score").toString():"0"));
					flowers=Integer.parseInt((mp.get("flowers")!=null?mp.get("flowers").toString():"0"));
					username=(mp.get("username")!=null?mp.get("username").toString():"");
					image=(mp.get("image")==null?"":(mp.get("image").toString().startsWith("http")?mp.get("image").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+mp.get("image").toString()));					
					acc_flowers=(mp.get("flowers")!=null?mp.get("flowers").toString():"0");
					
					alipay_account=(mp.get("alipay_account")!=null?mp.get("alipay_account").toString():"");
					chi_name=(mp.get("chi_name")!=null?mp.get("chi_name").toString():"");
					uid=Integer.parseInt((mp.get("id")!=null?mp.get("id").toString():"-1"));
					level=(mp.get("level")!=null?mp.get("level").toString():"");					
					sex=(mp.get("sex")!=null?mp.get("sex").toString():"");
					city=(mp.get("city")!=null?mp.get("city").toString():"");
					province=(mp.get("province")!=null?mp.get("province").toString():"");
					country=(mp.get("country")!=null?mp.get("country").toString():"");					
					
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
		jsondata.put("username", username);
		jsondata.put("image", image);

		jsondata.put("nickname", username);
		jsondata.put("username", obj.getAccount());
		jsondata.put("level", level);
		jsondata.put("alipay_account", alipay_account);
		jsondata.put("chi_name", chi_name);
		jsondata.put("userid", uid);
		jsondata.put("city", city);
		jsondata.put("province", province);	
		jsondata.put("country", country);
		jsondata.put("sex", sex);	
		//jsondata.put("group_mobile", group_mobile);
		//jsondata.put("group_wx", group_wx);
		//jsondata.put("group_qq", group_qq);		
		
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
		int myinfo_length=0;
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
			
		
			myinfo_length=Integer.parseInt(SysParameter.getInstatnce().getParams().get("myinfo_refresh_flag").toString());
			
				
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
		jsondata.put("myinfo_length", ""+myinfo_length);
		
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
				//System.out.println(sql);
				ls=this.sysAccountDao.getData(sql,new String[]{});				
				List ldata=new ArrayList();
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
						
						Map lmap=new HashMap();
						
						
						lmap.put("username", account);
						lmap.put("nickname", username);
						lmap.put("image", image);
						lmap.put("sex", sex);
						lmap.put("city", city);
						lmap.put("province", province);
						lmap.put("longin_date", login_date);						
						lmap.put("distances", distace);
						
						ldata.add(lmap);
						
//						item.put("username", account);
//						item.put("nickname", username);
//						item.put("image", image);
//						item.put("sex", sex);
//						item.put("city", city);
//						item.put("province", province);
//						item.put("longin_date", login_date);						
//						item.put("distance", distace+"");
//						
//						jsonarray.add(item);
						
					}


				}
				
				
			
					if( ldata.size()<8 ){
						HashMap groupMap=new HashMap();
						sql="select * from tbl_reg_account where status=8 order by rand() limit 8";
						
						ls=this.sysAccountDao.getData(sql,new String[]{});						
						for(int i=0;i<ls.size() && ldata.size()<8;i++){
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
							
							int distace = new java.util.Random().nextInt(3000);
							if(distace<0){distace=Math.abs(distace);}
							
							JSONObject item=new JSONObject();
							
							
							if(groupid.length()>0){
								account=groupid+":"+account+":"+account_type;
								if(groupMap.containsKey(groupid)){
									continue;
								}else{
									groupMap.put(groupid, groupid);
								}
							}
							
							Map lmap=new HashMap();
							
							//System.out.println(distace);
							
							lmap.put("username", account);
							lmap.put("nickname", username);
							lmap.put("image", image);
							lmap.put("sex", sex);
							lmap.put("city", city);
							lmap.put("province", province);
							lmap.put("longin_date", login_date);						
							lmap.put("distances", distace);
							
							ldata.add(lmap);
							
						}
					}
					
					SysSharedAccountDaoImpl sysSharedAccountDao=new SysSharedAccountDaoImpl();
					ldata=sysSharedAccountDao.sortbydata(ldata);
					for(int i=0;i<ldata.size();i++){
						JSONObject item=new JSONObject();
						Map lmap=(Map)ldata.get(i);
						item.put("username", lmap.get("username"));
						item.put("nickname", lmap.get("nickname"));
						item.put("image",  lmap.get("image"));
						item.put("sex", lmap.get("sex"));
						item.put("city", lmap.get("city"));
						item.put("province", lmap.get("province"));
						item.put("longin_date", lmap.get("longin_date"));						
						item.put("distance", lmap.get("distances").toString());
						
						jsonarray.add(item);						
						
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
				ls=(List)cachectl.getcontent("func_"+obj.getPara_function()+"_"+obj.getStatus());
				if(ls==null){
					ls=this.sysAccountDao.getData("select * from tbl_sys_images where status=? order by sort,id ",new String[]{obj.getStatus()});
					cachectl.addcontent("func_"+obj.getPara_function()+"_"+obj.getStatus(), ls);
				}

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

					msg="获得一个金币！";
					
				}else{

					msg="今天金币已领完！";
					
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
		
	
	public void func241() throws Exception{
	     
        func241Req obj=(func241Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
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

					this.sysAccountDao.executedSql("insert into tbl_nearby_logs(account,groupid,mac_address,lat,lng,type,indate) values(?,?,?,?,?,?,now())", new String[]{obj.getAccount(),obj.getGroupid(),obj.getMac_address(),obj.getLat(),obj.getLng(),obj.getType()});
					
				
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
				"response 241:"+jsondata.toString());		
   
	}		
	
	public void func242() throws Exception{
	     
        func242Req obj=(func242Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";

		List ls=null;		
		int score=0;		
		try{
			

				this.sysAccountDao.executedSql("insert into tbl_data_logs(groupid,account,uid,title,function,remark1,remark2,remark3,indate) values(?,?,?,?,?,?,?,?,now())", new String[]{obj.getGroupid(),obj.getAccount(),obj.getUID(),obj.getTitle(),obj.getFunc(),obj.getRemark1(),obj.getRemark2(),obj.getRemark3()});				
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
				"response 242:"+jsondata.toString());		
   
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
	
	
	public void func244() throws Exception{
	     
        func244Req obj=(func244Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray1=new JSONArray();
		JSONArray jsonarray2=new JSONArray();
		JSONArray jsonarray3=new JSONArray();
		
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";
		String groupid="";
		String status="";
		String password="";
		String acc_score="0";
		
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
			
			HashMap groupMap=new HashMap();
			
			
			
			


				
				if(obj.getMac_address().length()>0){
					////地主
					ls=this.sysAccountDao.getData("SELECT a.* FROM tbl_reg_account a inner join tbl_wifi_share_account_logs b on a.account=b.reg_account inner join tbl_wifi_share_account c on b.mac_address=c.mac_address  where a.status=0 and  b.mac_address=? order by b.id desc limit 2",new String[]{obj.getMac_address()});				
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
							groupMap.put(groupid, groupid);
						}else{
							groupMap.put(account, account);
						}
					}else{
						ls=this.sysAccountDao.getData("SELECT * FROM  tbl_wifi_share_account  where  mac_address=?  limit 1",new String[]{obj.getMac_address()});
						if(ls!=null && ls.size()>0){
							username="游客";
						}
						
					}
					if(ls.size()>0){
						JSONObject item=new JSONObject();
						DecimalFormat df = new DecimalFormat("0.00"); 
						score=Integer.parseInt(acc_score);
						double dscore = (double)score;  
						item.put("score", df.format(dscore/100).toString()+"元");
						item.put("nickname", username);
						item.put("username", account);
						item.put("level", level);
						item.put("image", image);
						item.put("alipay_account", alipay_account);
						item.put("chi_name", chi_name);
						item.put("userid", uid);
						item.put("city", city);
						item.put("province", province);	
						item.put("country", country);
						item.put("sex", sex);
						item.put("group_mobile", group_mobile);
						item.put("group_wx", group_wx);
						item.put("group_qq", group_qq);		
										
						jsonarray1.add(item);					
					}					
					
					//联过该热点的人
					ls=this.sysAccountDao.getData("SELECT a.*,b.ldate FROM tbl_reg_account a  inner join (select account,max(indate) 'ldate' from tbl_wifi_share_login_logs  where mac_address=? group by account limit 30) b  on a.account=b.account  where length(a.image) > 1 and a.status=0 order by ldate desc ",new String[]{obj.getMac_address()});				
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
								if(groupMap.containsKey(groupid)){
									continue;
								}else{
									groupMap.put(groupid, groupid);								
								}
							}else{
								if(groupMap.containsKey(account)){
									continue;
								}else{
									groupMap.put(account, account);								
								}
								
							}
							
							item.put("username", account);
							item.put("nickname", username);
							item.put("image", image);
							item.put("sex", sex);
							item.put("city", city);
							item.put("province", province);
							item.put("longin_date", login_date);						
							jsonarray2.add(item);
							
						}				
					}
				}
				
				if((obj.getLat()>=0 && obj.getLng()>=0)){
					
					if(obj.getGroupid().length()>0){
						if(groupMap.containsKey(obj.getGroupid())){
						
						}else{
							groupMap.put(obj.getGroupid(), obj.getGroupid());
						}
						
					}else{
						if(groupMap.containsKey(obj.getUsername())){
							
						}else{
							groupMap.put(obj.getUsername(), obj.getUsername());
						}						
					}
			
					
					//附近的她
					String sql="select * from tbl_reg_account where length(image) > 1 and lat > "+obj.getLat()+"-1 and lat < "+obj.getLat()+"+1 and lng > "+obj.getLng()+"-1 and lng < "+obj.getLng()+"+1 order by ACOS(SIN(("+obj.getLat()+" * 3.1415) / 180 ) *SIN(("+obj.getLng()+" * 3.1415) / 180 ) +COS(("+obj.getLat()+" * 3.1415) / 180 ) * COS((lat * 3.1415) / 180 ) *COS(("+obj.getLng()+"* 3.1415) / 180 - (lng * 3.1415) / 180 ) ) * 6380 asc limit 30";
					//System.out.println(sql);
					ls=this.sysAccountDao.getData(sql,new String[]{});				
					List ldata=new ArrayList();
					if(ls!=null && ls.size()>0){
				
	
						
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
							}else{
								if(groupMap.containsKey(account)){
									continue;
								}else{
									groupMap.put(account, account);
								}
								
							}
							
							Map lmap=new HashMap();
							
							lmap.put("username", account);
							lmap.put("nickname", username);
							lmap.put("image", image);
							lmap.put("sex", sex);
							lmap.put("city", city);
							lmap.put("province", province);
							lmap.put("longin_date", login_date);						
							lmap.put("distances", distace);
							
							ldata.add(lmap);
	
							
						}
	
	
					}
					
					
				
						if( ldata.size()<8 ){
							sql="select * from tbl_reg_account where status=8 order by rand() limit 8";
							
							ls=this.sysAccountDao.getData(sql,new String[]{});						
							for(int i=0;i<ls.size() && ldata.size()<8;i++){
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
								
								int distace = new java.util.Random().nextInt(3000);
								if(distace<0){distace=Math.abs(distace);}
								
								JSONObject item=new JSONObject();
								
								
								if(groupid.length()>0){
									account=groupid+":"+account+":"+account_type;
									if(groupMap.containsKey(groupid)){
										continue;
									}else{
										groupMap.put(groupid, groupid);
									}
								}else{
									if(groupMap.containsKey(account)){
										continue;
									}else{
										groupMap.put(account, account);
									}
									
								}
								
								Map lmap=new HashMap();
								
							
								lmap.put("username", account);
								lmap.put("nickname", username);
								lmap.put("image", image);
								lmap.put("sex", sex);
								lmap.put("city", city);
								lmap.put("province", province);
								lmap.put("longin_date", login_date);						
								lmap.put("distances", distace);
								
								ldata.add(lmap);
								
							}
						}
						
						SysSharedAccountDaoImpl sysSharedAccountDao=new SysSharedAccountDaoImpl();
						ldata=sysSharedAccountDao.sortbydata(ldata);
						for(int i=0;i<ldata.size();i++){
							JSONObject item=new JSONObject();
							Map lmap=(Map)ldata.get(i);
							item.put("username", lmap.get("username"));
							item.put("nickname", lmap.get("nickname"));
							item.put("image",  lmap.get("image"));
							item.put("sex", lmap.get("sex"));
							item.put("city", lmap.get("city"));
							item.put("province", lmap.get("province"));
							item.put("longin_date", lmap.get("longin_date"));						
							item.put("distance", lmap.get("distances").toString());
							
							jsonarray3.add(item);						
							
						}					
						
				}
						
				resultcode="0";
				msg="succ";
					

				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("function", obj.getPara_function());
		jsondata.put("data1", jsonarray1);
		jsondata.put("data2", jsonarray2);
		jsondata.put("data3", jsonarray3);
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 245" +
				":"+jsondata.toString());		
   
	}	
	
	
	public void func245() throws Exception{
	     
        func245Req obj=(func245Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="system error!";
		String regcode="";
		String image="";

		List ls=null;		
		int score=0;		
		try{
			
			

			ls=this.sysAccountDao.getData("select * from tbl_push_reg_logs where regid=? limit 1",new String[]{obj.getRegid()});
			if(ls!=null && ls.size()==0){
				
			
				this.sysAccountDao.executedSql("insert into tbl_push_reg_logs(account,uid,regid,regkey,indate) values(?,?,?,?,now())", new String[]{obj.getUsername(),obj.getUID(),obj.getRegid(),obj.getRegkey()});
			}else{
				this.sysAccountDao.executedSql("update tbl_push_reg_logs set account=?,uid=?,regkey=?,indate=now() where regid=?" ,new String []{obj.getUsername(),obj.getUID(),obj.getRegkey(),obj.getRegid()});
			}
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
				"response 245:"+jsondata.toString());		
   
	}	
		
	
	public void func246() throws Exception{
	     
        func246Req obj=(func246Req)request.getAttribute(SysParameter.REQUESTBEAN);

		
		
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
				if(obj.getGroupid().length()==0){
					ls=this.sysAccountDao.getData("select account from tbl_friends_logs where account=? and indate>= curDate() limit 5 ",new String[]{obj.getAccount()});
				}else{
					
					String user="";
					ls=this.sysAccountDao.getData("select * from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupid()});
					if(ls!=null && ls.size()>0){
						for(int i=0;i<ls.size();i++){
							Map mp=(Map)ls.get(i);
							user+="'"+(mp.get("account")==null?"":mp.get("account").toString())+"',";
						}
					}
					if(user.length()>0){
						user=user.substring(0,user.length()-1); 
						ls=this.sysAccountDao.getData("select id from tbl_friends_logs where account in("+user+") and indate>= curDate() limit 5", new String[]{});
					}else{
						ls=this.sysAccountDao.getData("select account from tbl_friends_logs where groupid=? and indate>= curDate() limit 5 ",new String[]{obj.getGroupid()});
					}
				}
				if(ls!=null && ls.size()==0 ){
					flower=3;
				
					this.sysAccountDao.executedSql("insert into tbl_friends_logs(account,flowers,indate,groupid,channel) values(?,?,now(),?,?)", new String[]{obj.getAccount(),String.valueOf(flower),obj.getGroupid(),obj.getType()});
					
					this.sysAccountDao.executedSql("update tbl_reg_account set flowers=ifnull(flowers,0)+? where account=?", new String[]{String.valueOf(flower),obj.getAccount()});

					msg="获得"+flower+"个金币！";
					
				}else{

					msg="今天金币已领完！";
					
				}
				
				resultcode="0";
								
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("addscore", flower);
		jsondata.put("function", obj.getPara_function());
		
		//jsondata.put("data", jsonarray);
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 246:"+jsondata.toString());		
   
	}		
	
	public void func247() throws Exception{
	     
	     
        func247Req obj=(func247Req)request.getAttribute(SysParameter.REQUESTBEAN);

		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray(); 
		JSONArray jsonarray1=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		List ls=null;			
		try{


			ls=(List)cachectl.getcontent("func_"+obj.getPara_function()+"_mystatus");
			if(ls==null){
				ls=this.sysAccountDao.getData("select * from tbl_sys_images where status=10 order by sort,id ",new String[]{});
				cachectl.addcontent("func_"+obj.getPara_function()+"_mystatus", ls);
			}

			if(ls!=null && ls.size()>0){
				for(int i=0;i<ls.size();i++){
					
					JSONObject ditem=new JSONObject();
					Map	dmap=(Map)ls.get(i);
														
					ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
					ditem.put("image", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
					ditem.put("url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));					
					ditem.put("remark", (dmap.get("remark")==null?"":dmap.get("remark").toString()));
					jsonarray.add(ditem);
					
				}
			}
			resultcode="0";
			msg="succ";


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
				"response "+obj.getPara_function()+":"+jsondata.toString());		
   
	}								
	
	public void func251() {
	    func251Req obj = (func251Req)request.getAttribute(SysParameter.REQUESTBEAN);
        
        String resultcode = "-1";
        String msg = "system error!";
        String coin = "";
        String alipay_account = "";
        String mobile_account = "";
        String groupid = "";
        JSONObject jsondata = new JSONObject();
        JSONArray jsonarray = new JSONArray();
	        
        try {
            logger.info("ffunc251 start.");
            
            if (StringUtils.isNotEmpty(obj.getUseraccount())){
                List list = sysAccountDao.getData("SELECT flowers, alipay_account, account_type, groupid FROM tbl_reg_account where status=0 and account=?", new String[]{obj.getUseraccount()});
                if (list != null && !list.isEmpty()){
                    Map map = (Map)list.get(0);
                    coin = map.get("flowers") != null ? map.get("flowers").toString() : "";
                    groupid = map.get("groupid") != null ? map.get("groupid").toString().trim() : "";
                    alipay_account = map.get("alipay_account") != null ? map.get("alipay_account").toString() : "";
                    mobile_account = map.get("account_type")!=null && map.get("account_type").toString().equals("1") ? obj.getUseraccount() : "";
                }else{
                    throw new RuntimeException("帐号异常");
                }
            }
            
            if (StringUtils.isEmpty(mobile_account) && StringUtils.isNotEmpty(groupid)){
                List list = sysAccountDao.getData("SELECT account FROM tbl_reg_account where status=0 and account_type=1 and groupid='" + groupid + "' limit 1", new String[]{});
                if (list != null && !list.isEmpty()){
                    Map map = (Map)list.get(0);
                    mobile_account = map.get("account") != null ? map.get("account").toString().trim() : "";
                }
            }
            
            Map<String, Object> params = SysParameter.getInstatnce().getParams();
            String cash_exchange_starttime = params.get("cash_exchange_starttime").toString();
            String cash_exchange_item = params.get("cash_exchange_items").toString();
            
            Map<String, String> res = getStartAndEndTime(cash_exchange_starttime);
            String starttime = res.get("starttime");
            String endtime = res.get("endtime");
            List ls = sysAccountDao.getData("select category, price, size, operators from tbl_chongzhi where (status = 0 or status = 2) and category = 3 and createtime between '" + starttime + "' and '" + endtime + "'", new String[]{});
            if (ls == null)
                ls = new ArrayList();
            
            //---------------------------------------------------
            JSONObject cashJson = new JSONObject();
            JSONArray arr = new JSONArray();
            cashJson.put("type", "cash");
            String[] cashs = cash_exchange_item.split(",");
            for (String item : cashs){
                JSONObject cashObj = new JSONObject();
                cashObj.put("money", Integer.valueOf(item)/100);
                cashObj.put("value", params.get("cash_exchange_item_" + item + "_coin"));
                //计算剩余库存
                int stock = getCashStock(item, ls, params, coin);
                cashObj.put("stock", stock+"");
                cashObj.put("msg", stock == 0 ? res.get("tips") : "");
                arr.add(cashObj);
            }
            
            cashJson.put("data", arr);
            jsonarray.add(cashJson);
            //------------------------------------------------------------------
            
            JSONObject streamJson = new JSONObject(); 
            JSONArray streamArr = new JSONArray();
            streamJson.put("type", "stream");
            
            String data_exchange_starttime = params.get("data_exchange_starttime").toString();
            res = getStartAndEndTime(data_exchange_starttime);
            starttime = res.get("starttime");
            endtime = res.get("endtime");
            ls = sysAccountDao.getData("select category, price, size, operators from tbl_chongzhi where (status = 0 or status = 2) and category = 4 and createtime between '" + starttime + "' and '" + endtime + "'", new String[]{});
            if (ls == null)
                ls = new ArrayList();

            //data_exchange_item的格式: 联通_10,电信_50
            String data_exchange_item = params.get("data_exchange_items").toString();
            String[] datas = data_exchange_item.split(",");
            for (String item: datas){
                JSONObject streamObj = new JSONObject();
                String[] osizes = item.split("_");
                streamObj.put("size", osizes[1]);
                streamObj.put("operators", osizes[0]);
                streamObj.put("area", "全国");
                streamObj.put("value", params.get("data_exchange_item_" + item + "_coin"));
                int stock = getDataStock(item, ls, params, coin);
                streamObj.put("stock", stock+"");
                streamObj.put("msg", stock == 0 ? res.get("tips") : "");
                streamArr.add(streamObj);
            }
            
            streamJson.put("data", streamArr);
            jsonarray.add(streamJson);
            //-----------------------------------
            
            resultcode = "0";
            msg = "success";
        } catch (Exception e) {
            logger.error("process ffunc251 failed.", e);
        }
        
        jsondata.put("coin", coin);
        jsondata.put("alipay_account", alipay_account);
        jsondata.put("mobile_account", mobile_account);
        jsondata.put("resultcode", resultcode);
        jsondata.put("msg", msg);
        jsondata.put("data", jsonarray);
        jsondata.put("function", obj.getPara_function());
        
        long response_start=System.currentTimeMillis();
        respData(jsondata.toString(),request,response);
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 251:"+jsondata.toString());   
	}
	
	/**
	 * 流量库存的单位是:个数
	 * @param item
	 * @param ls
	 * @param params
	 * @param scoin
	 * @return
	 */
	private int getDataStock(String item, List ls, Map<String, Object> params, String scoin){
	    int result = 0;
	    
	    String[] osizes = item.split("_");
	    String operators = osizes[0];
	    int size = Integer.valueOf(osizes[1]);
	    
	    int exchange_count = 0;
	    for (int i = 0; i < ls.size(); i++){
	        Map mp = (Map)ls.get(i);
            String scategory = mp.get("category").toString();
            
            String ssize = mp.get("size").toString();
            String soperators = mp.get("operators").toString();
            //category为4表示流量兑换
            if ("4".equals(scategory) && size == Integer.valueOf(ssize) && operators.equals(soperators)){
                exchange_count++;
            }
	    }
	    
	    int stock = Integer.valueOf(params.get("data_exchange_item_" + item + "_stock").toString());
	    
	    //剩余库存 = 总库存 - 已经兑换的库存
	    result = stock - exchange_count;
	    if (result < 0){
	        result = 0;
	    }
	    
	    return result;
	}
	
	private Map<String, String> getStartAndEndTime(String exchange_starttime) throws Exception{
	    
	    Map<String, String> res = new HashMap<String, String>();
	    
        Date cur = new Date();

        Map<String, Object> params = SysParameter.getInstatnce().getParams();
        
        //开始兑换的时间点
        String curdate = new SimpleDateFormat("yyyy-MM-dd").format(cur);
        Date t1 = new java.text.SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(curdate + exchange_starttime);
        
        String startTime = "";
        String endTime = "";
        String tips = "";
        if (cur.equals(t1) || cur.after(t1)){
            String nextdate = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(cur, 1));
            startTime = curdate + " " + exchange_starttime;
            endTime = nextdate + " " + exchange_starttime;
            tips = "明天" + exchange_starttime.substring(0, 2) + "点再来";
        }else if (cur.before(t1)){
            String beforedate = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(cur, -1));
            startTime = beforedate + " " + exchange_starttime;
            endTime = curdate + " " + exchange_starttime; 
            tips = exchange_starttime.substring(0, 2) + "点再来";
        }
        
        res.put("starttime", startTime);
        res.put("endtime", endTime);
        res.put("tips", tips);
        return res;
	}
	
	private int getCashStock(String cash, List ls, Map<String, Object> params, String scoin){
        int tmp_money = Integer.valueOf(cash);
        
        int cash_exchange_count = 0;
        for (int i = 0; i < ls.size(); i++){
            Map mp = (Map)ls.get(i);
            String scategory = mp.get("category").toString();
            //兑换的金额
            String sprice = mp.get("price").toString();
            //category为3表示现金兑换
            if ("3".equals(scategory) && tmp_money == Integer.valueOf(sprice)){
                cash_exchange_count += tmp_money;
            }
        }
        
        int stock = Integer.valueOf(params.get("cash_exchange_item_" + cash + "_stock").toString());
        
        //剩余库存 = 总库存 - 已兑换的库存 
        stock = stock - cash_exchange_count;
        if (stock < 0) {
            stock = 0;
        }
//        //用户未登陆
//        if ("".equals(scoin)){
//            if (stock <= 0){
//                stock = 0;
//            }
//        }else{
//            //兑换当前面额的现金需要的金币
//            int exchange_coin = Integer.valueOf(params.get("cash_exchange_item_" + cash + "_coin").toString());
//            int userCoin = Integer.valueOf(scoin);
//            if (userCoin < exchange_coin || stock <= 0){
//                stock = 0;
//            }
//        }
        
	    return stock;
	}
	
	
	public void func252(){
	    func252Req obj = (func252Req)request.getAttribute(SysParameter.REQUESTBEAN);
	    
        Map<String, Object> params = SysParameter.getInstatnce().getParams();
        String cash_exchange_tips = params.get("cash_exchange_tips").toString();
        
        JSONObject jsondata = new JSONObject();
        jsondata.put("resultcode", "0");
        jsondata.put("msg", "success");
        jsondata.put("tips", cash_exchange_tips);
        jsondata.put("function", obj.getPara_function());
        
        long response_start=System.currentTimeMillis();
        respData(jsondata.toString(),request,response);
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 252:"+jsondata.toString());   
	}
	
	public void func253(){
	    func253Req obj = (func253Req)request.getAttribute(SysParameter.REQUESTBEAN);
	    
	    JSONObject jsondata=new JSONObject();
        String resultcode = "-1";
        String msg = "parameters error!";
        String mobile = "";
        int money = obj.getMoney();
        int coin = obj.getCoin();
        
        try{
            if (money == 0 || coin == 0){
                throw new RuntimeException("parameters error!");
            }
            
            money = money * 100;
            
            List list = sysAccountDao.getData("select flowers, status, alipay_account, chi_name, account_type from tbl_reg_account where account=? and status=0", new String[]{obj.getUseraccount()});
            if (list == null || list.isEmpty()){
                throw new RuntimeException("账号异常");
            }
            
            Map mp = (Map)list.get(0);
            int score = mp.get("flowers") == null || "".equals(mp.get("flowers").toString())? 0 : Integer.valueOf(mp.get("flowers").toString());
            int status = mp.get("status") == null || "".equals(mp.get("status").toString())? 1 : Integer.valueOf(mp.get("status").toString());
            String alipay_account = mp.get("alipay_account") == null ? null : mp.get("alipay_account").toString().trim();
            String chi_name = mp.get("chi_name") == null ? null : mp.get("chi_name").toString().trim();
            String account_type = mp.get("account_type")!=null ? mp.get("account_type").toString() : "";
            
            if ("1".equals(account_type)){
                mobile = obj.getUseraccount();
            }
            
            //检查用户状态
            if (status != 0){
                throw new RuntimeException("账号有误");
            }
            //如果用户的金币小于兑换所需的金币
            if (score < coin){
                throw new RuntimeException("金币余额不足");
            }
            
            if (alipay_account == null || "".equals(alipay_account)){
                throw new RuntimeException("支付宝未绑定");
            }
            
            Date cur = new Date();

            Map<String, Object> params = SysParameter.getInstatnce().getParams();
            String exchange_starttime = params.get("cash_exchange_starttime").toString();
            
            //开始兑换的时间点
            String curdate = new SimpleDateFormat("yyyy-MM-dd").format(cur);
            Date t1 = new java.text.SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(curdate + exchange_starttime);
            
            String startTime = "";
            String endTime = "";
            
            String tips = "";
            if (cur.equals(t1) || cur.after(t1)){
                String nextdate = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(cur, 1));
                startTime = curdate + " " + exchange_starttime;
                endTime = nextdate + " " + exchange_starttime;
                tips = "明天再来";
            }else if (cur.before(t1)){
                String beforedate = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(cur, -1));
                startTime = beforedate + " " + exchange_starttime;
                endTime = curdate + " " + exchange_starttime;       
                tips = exchange_starttime.substring(0, 2) + "点再来";
            }
            
            //查询当天己兑换现金总量是否超过当天库存限制
            String sql = "select sum(price) as 'total' from tbl_chongzhi where category =3 and price=" + money + " and (status = 0 or status = 2) and createtime between '" + startTime + "' and '" + endTime + "'";
            logger.info("ffunc253 sql:" + sql);
            list = sysAccountDao.getData(sql, new String[]{});
            if (null != list &&  !list.isEmpty()){
                Map map = (Map)list.get(0);
                int cash_total = map.get("total") == null || "".equals(map.get("total").toString()) ? 0 : Integer.valueOf(map.get("total").toString());
                int stock = Integer.valueOf(params.get("cash_exchange_item_" + money + "_stock").toString());
                if ((cash_total >= stock) || ((cash_total + money) > stock)){
                    jsondata.put("resultcode", "3");
                    jsondata.put("msg", "已抢光，请" + tips); 
                    respData(jsondata.toString(), request, response);
                    return;
                } 
            }
            
            //查询当前帐号以及其绑定帐号是否有过兑换现金的记录
            String user="";
            if (StringUtils.isNotEmpty(obj.getGroupId())){
                List ls = sysAccountDao.getData("select account, account_type from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupId()});
                if(ls != null && ls.size() > 0){
                    for(int i=0; i < ls.size(); i++){
                        Map map=(Map)ls.get(i);
                        user+="'"+(map.get("account")==null?"":map.get("account").toString())+"',";
                        String accounttype = map.get("account_type")!=null ? map.get("account_type").toString() : "";
                        
                        if ("1".equals(accounttype)){
                            mobile = map.get("account") != null ? map.get("account").toString() : "";
                        }
                    }
                }

                if(user.length()>0){user=user.substring(0,user.length()-1);}
            }else{
                user = "'" + obj.getUseraccount() + "'";
            }
            
             //判断是否绑定手机号
            if (StringUtils.isEmpty(mobile)){
                throw new RuntimeException("未绑定手机号");
            }
            
            sql = "select id from tbl_chongzhi where used_login_id in (" + user + ") and (status = 0 or status = 2) and category = 3 and createtime between '" + startTime + "' and '" + endTime + "'";
            list = sysAccountDao.getData(sql, new String[]{});
            if (null != list && !list.isEmpty()){
                jsondata.put("resultcode", "4");
                jsondata.put("msg", tips);    
                respData(jsondata.toString(), request, response);
                return;
            }
            
            //扣除账户的余额
            sql = "update tbl_reg_account set flowers = flowers- " + coin + " where account='" + obj.getUseraccount() + "' and flowers =" + score + " and flowers >= " + coin;
            int rowCount = sysAccountDao.executedUpdateSql(sql, new String[]{});
            if (rowCount == 0){
                jsondata.put("resultcode", "4");
                jsondata.put("msg", "兑换异常，请重试！");    
                respData(jsondata.toString(), request, response);
                return;
            }
            
            sql = "INSERT INTO tbl_chongzhi (uid, used_login_id, category,  price, score, createtime, status, alipay_account, chi_name) values(?,?,?,?,?,NOW(),?, ?,?)";
            sysAccountDao.executedSql(sql, new String[]{obj.getUid(), obj.getUseraccount(), "3", money+"", coin+"", "2", alipay_account, chi_name});
            
            resultcode = "0";
            msg = "恭喜您，提交成功，5个工作日内审核，通过即打款到您的支付宝账号";
            
        }catch(Exception e){
            logger.error(e);
            resultcode = "-1";
            msg = e.getMessage();
        }
        
        jsondata.put("resultcode", resultcode);
        jsondata.put("msg", msg);
        jsondata.put("function", obj.getPara_function());
        
        respData(jsondata.toString(), request, response);
        long response_start=System.currentTimeMillis();
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 253:"+jsondata.toString());   
	    
	}
	
	public void func254(){
        func254Req obj = (func254Req) request.getAttribute(SysParameter.REQUESTBEAN);

        String operators = obj.getOperators();
        
        Map<String, Object> params = SysParameter.getInstatnce().getParams();
        String data_exchange_tips = params.get("data_exchange_tips").toString();
        
        if ("1".equals(operators)){
            data_exchange_tips = data_exchange_tips.replace("${operators}", "移动").replace("${other_operators}", "联通或电信"); 
        }else if ("2".equals(operators)){
            data_exchange_tips = data_exchange_tips.replace("${operators}", "联通").replace("${other_operators}", "移动或电信");
        }else if ("3".equals(operators)){
            data_exchange_tips = data_exchange_tips.replace("${operators}", "电信").replace("${other_operators}", "移动或联通");
        }
 
        JSONObject jsondata = new JSONObject();
        jsondata.put("resultcode", "0");
        jsondata.put("msg", "success");
        jsondata.put("tips", data_exchange_tips);
        jsondata.put("function", obj.getPara_function());
	        
        long response_start=System.currentTimeMillis();
        respData(jsondata.toString(),request,response);
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 254:"+jsondata.toString());   
	}
	
	public void func255(){
	       func255Req obj = (func255Req)request.getAttribute(SysParameter.REQUESTBEAN);
	        
	        JSONObject jsondata=new JSONObject();
	        String resultcode = "-1";
	        String msg = "parameters error!";
	        
	        String mobile_number = "";
	        int size = obj.getSize();
	        int coin = obj.getCoin();
	        String operators = obj.getOperators();
	        
	        try{
	            if (size == 0 || coin == 0 || StringUtils.isEmpty(operators)){
	                throw new RuntimeException("parameters error!");
	            }
	            
	            List list = sysAccountDao.getData("select flowers, status, account_type from tbl_reg_account where account=?", new String[]{obj.getUseraccount()});
	            if (list == null || list.isEmpty()){
	                throw new RuntimeException("账号有误");
	            }
	            
	            Map mp = (Map)list.get(0);
	            int score = mp.get("flowers") == null || "".equals(mp.get("flowers").toString())? 0 : Integer.valueOf(mp.get("flowers").toString());
	            int status = mp.get("status") == null || "".equals(mp.get("status").toString())? 1 : Integer.valueOf(mp.get("status").toString());
	            String account_type  = mp.get("account_type") == null ? "1" : mp.get("account_type").toString();
	            //检查用户状态
	            if (status != 0){
	                throw new RuntimeException("账号异常");
	            }
	            //如果用户的金币小于兑换所需的金币
	            if (score < coin){
	                throw new RuntimeException("金币余额不足");
	            }
	            
	            if ("1".equals(account_type)){
	                mobile_number = obj.getUseraccount();
	            }
	            	            
	            Date cur = new Date();

	            Map<String, Object> params = SysParameter.getInstatnce().getParams();
	            String exchange_starttime = params.get("data_exchange_starttime").toString();
	            
	            //开始兑换的时间点
	            String curdate = new SimpleDateFormat("yyyy-MM-dd").format(cur);
	            Date t1 = new java.text.SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(curdate + exchange_starttime);
	            
	            String startTime = "";
	            String endTime = "";
	            String tips = "";
	            if (cur.equals(t1) || cur.after(t1)){
	                String nextdate = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(cur, 1));
	                startTime = curdate + " " + exchange_starttime;
	                endTime = nextdate + " " + exchange_starttime;
	                tips = "明天" +exchange_starttime.substring(0, 2)+ "点再来";
	            }else if (cur.before(t1)){
	                String beforedate = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(cur, -1));
	                startTime = beforedate + " " + exchange_starttime;
	                endTime = curdate + " " + exchange_starttime;   
	                tips = exchange_starttime.substring(0, 2) + "点再来";
	            }
	            
	            //查询当天己兑换流量总量是否超过当天库存限制
	            String sql = "select count(id) as 'total' from tbl_chongzhi where category = 4 and size =" + size + " and operators='" + operators + "' and (status = 0 or status = 2) and createtime between '" + startTime + "' and '" + endTime + "'";
	            logger.info("ffunc255 sql:" + sql);
	            list = sysAccountDao.getData(sql, new String[]{});
	            if (null != list &&  !list.isEmpty()){
	                Map map = (Map)list.get(0);
	                int total = map.get("total") == null || "".equals(map.get("total").toString()) ? 0 : Integer.valueOf(map.get("total").toString());
	                int stock = Integer.valueOf(params.get("data_exchange_item_" + operators+"_" + size + "_stock").toString());
	                if ((total >= stock) || ((total + 1) > stock)){
	                    jsondata.put("resultcode", "3");
	                    jsondata.put("msg", "已抢光，请" + tips); 
	                    respData(jsondata.toString(), request, response);
	                    return;
	                } 
	            }
	            
	            //查询当前帐号以及其绑定帐号是否有过兑换现金的记录
	            String user="";
	            if (StringUtils.isNotEmpty(obj.getGroupId())){
	                List ls = sysAccountDao.getData("select account, account_type from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupId()});
	                if(ls != null && ls.size() > 0){
	                    for(int i=0; i < ls.size(); i++){
	                        Map map=(Map)ls.get(i);
	                        user+="'"+(map.get("account")==null?"":map.get("account").toString())+"',";
	                        String accounttype = map.get("account_type") != null ? map.get("account_type").toString().trim() : "1";
	                        if ("1".equals(accounttype)){
	                            mobile_number = map.get("account")==null?"":map.get("account").toString();
	                        }
	                    }
	                }

	                if(user.length()>0){user=user.substring(0,user.length()-1);}
	            }else{
	                user = "'" + obj.getUseraccount() + "'";
	            }
	            
	            //判断是否绑定手机号
	            if (StringUtils.isEmpty(mobile_number)){
	                throw new RuntimeException("未绑定手机号");
	            }
	            
	            sql = "select id from tbl_chongzhi where used_login_id in (" + user + ") and (status = 0 or status = 2) and category = 4 and createtime between '" + startTime + "' and '" + endTime + "'";
	            list = sysAccountDao.getData(sql, new String[]{});
	            if (null != list && !list.isEmpty()){
	                jsondata.put("resultcode", "4");
	                jsondata.put("msg", tips);    
	                respData(jsondata.toString(), request, response);
	                return;
	            }
	            
	            //扣除账户的余额
	            sql = "update tbl_reg_account set flowers = flowers- " + coin + " where account='" + obj.getUseraccount() + "' and flowers =" + score + " and flowers >= " + coin;
	            int rowCount = sysAccountDao.executedUpdateSql(sql, new String[]{});
	            if (rowCount == 0){
	                jsondata.put("resultcode", "4");
	                jsondata.put("msg", "兑换异常，请重试！");    
	                respData(jsondata.toString(), request, response);
	                return;
	            }
	            
	            String partnerOrderNo = java.util.UUID.randomUUID().toString();
	            
	            sql = "INSERT INTO tbl_chongzhi (out_trade_id, uid, used_login_id, category, operators, target, size, score, createtime, status) values(?,?,?,?,?,?,?,?,NOW(),?)";
	            sysAccountDao.executedSql(sql, new String[]{partnerOrderNo, obj.getUid(), obj.getUseraccount(), "4", operators, mobile_number, size+"", coin+"", "2"});
	            
	            resultcode = "0";
	            msg = "24小时内审核通过后到账";
	            
	        }catch(Exception e){
	            logger.error(e);
	            resultcode = "-1";
	            msg = e.getMessage();
	        }
	        
	        jsondata.put("resultcode", resultcode);
	        jsondata.put("msg", msg);
	        jsondata.put("function", obj.getPara_function());
	        
	        respData(jsondata.toString(), request, response);
	        long response_start=System.currentTimeMillis();
	        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
	                "response 255:"+jsondata.toString()); 
	}
	
	public void func256(){
	    func256Req obj = (func256Req) request.getAttribute(SysParameter.REQUESTBEAN);
	   
        String resultcode = "-99";
        String msg = "parameters error!";
        
	    JSONObject jsondata = new JSONObject();
	    JSONArray jsonarray = new JSONArray();
	    try{
	        String user="";
	        if (StringUtils.isNotEmpty(obj.getGroupId())){
	            List ls = sysAccountDao.getData("select account from tbl_reg_account where groupid=? and status=0 limit 5",new String[]{obj.getGroupId()});
	            if(ls != null && ls.size() > 0){
	                for(int i=0; i < ls.size(); i++){
	                    Map map=(Map)ls.get(i);
	                    user+="'"+(map.get("account")==null?"":map.get("account").toString())+"',";
	                }
	            }

	            if(user.length()>0){user=user.substring(0,user.length()-1);}
	        }else{
	            user = "'" + obj.getUseraccount() + "'";
	        }
	        
	        String page = obj.getPage();
	        String size = obj.getSize();
	        
	        int start = Integer.valueOf(page) * Integer.valueOf(size);
	        
	        String sql = "select category, operators, price, size, score, date_format(createtime, '%Y-%m-%d %H:%i:%s') as createtime, status, refund, remark from tbl_chongzhi where used_login_id in (" + user + ") order by createtime desc limit " + start + ", "+ size ;
	        
	        List list = sysAccountDao.getData(sql, new String[]{});
	        if (list == null || list.isEmpty()){
	            jsondata.put("resultcode", "0");
	            jsondata.put("msg", "success");
	            jsondata.put("function", "256");
	            jsondata.put("data", jsonarray);
	            respData(jsondata.toString(), request, response);
	            return;
	        }
	        
	        for (int i = 0; i < list.size(); i++){
	            
	            Map mp = (Map)list.get(i);
	            String category = mp.get("category")==null ? "" : mp.get("category").toString();
	            String createtime = mp.get("createtime")==null ? "" : mp.get("createtime").toString();
	            String status = mp.get("status")==null ? "" : mp.get("status").toString();
	            int price = mp.get("price") == null ? 0 : Integer.valueOf(mp.get("price").toString()) / 100;
	            String refund = mp.get("refund")==null ? "" : mp.get("refund").toString();

	            
	            if ("1".equals(category)){
	                JSONObject json = new JSONObject();
	                
                    if ("1".equals(status) && StringUtils.isNotEmpty(refund)){
                        json.put("datetime", createtime);
                        json.put("status", "3");
                        json.put("type", "兑换Q币");
                        json.put("qty", price + "个");
                        json.put("coin", "+" + (price*100));
                        jsonarray.add(json);
                    }
                    
                    json = new JSONObject();
	                json.put("datetime", createtime);
	                json.put("status", status);
	                json.put("type", "兑换Q币");
	                json.put("qty",  price + "个");
	                json.put("coin", "-" + (price*100));
	                jsonarray.add(json);
	                

	                
	            }else if ("2".equals(category)){
	                JSONObject json = new JSONObject();
	                
                    if ("1".equals(status) && StringUtils.isNotEmpty(refund)){
                        
                        json.put("datetime", createtime);
                        json.put("status", "3");
                        json.put("type", "兑换话费");
                        json.put("qty", price+"元");
                        json.put("coin", "+" + (price*100));
                        json.put("datetime", createtime);
                        jsonarray.add(json);
                    }
                    
                    json = new JSONObject();
	                json.put("datetime", createtime);
	                json.put("status", status);
                    json.put("type", "兑换话费");
                    json.put("qty", price+"元");
                    json.put("coin", "-" + (price*100));
                    json.put("datetime", createtime);
                    jsonarray.add(json);
                    

	            }else if ("3".equals(category)){
	                JSONObject json = new JSONObject();
	                String score = mp.get("score") != null && !"".equals(mp.get("score").toString().trim()) ? mp.get("score").toString() : "0";
	                String remark = mp.get("remark") == null ? "" : mp.get("remark").toString();
	                
	                if ("1".equals(status) && StringUtils.isNotEmpty(refund)){
                        json.put("datetime", createtime);
                        json.put("status", "3");
                        json.put("type", "兑换现金");
                        json.put("qty", price+"元");
                        json.put("coin", "+" + score);
                        jsonarray.add(json);
                    }
                    
	                json = new JSONObject();
	                json.put("datetime", createtime);
	                json.put("status", status);	                
                    json.put("type", "兑换现金");
                    json.put("qty", price+"元");
                    json.put("coin", "-" + score);
                    json.put("msg", remark);
                    jsonarray.add(json);
                    

	            }else if ("4".equals(category)){
                       JSONObject json = new JSONObject();
                       String score = mp.get("score") == null ? "" : mp.get("score").toString();
                       String operators = mp.get("operators") != null ? mp.get("operators").toString() : ""; 
                       String sizes = mp.get("size") != null && !"".equals(mp.get("size").toString().trim()) ? mp.get("size").toString() : "0";
                       String remark = mp.get("remark") == null ? "" : mp.get("remark").toString();
                        if ("1".equals(status) && StringUtils.isNotEmpty(refund)){
                            
                            json.put("datetime", createtime);
                            json.put("status", "3");
                            json.put("type", "兑换流量");
                            json.put("qty", operators+sizes+"MB");
                            json.put("coin", "+" + score);
                            json.put("datetime", createtime);
                            
                            jsonarray.add(json);
                        }
                        
                        json = new JSONObject();
                        json.put("datetime", createtime);
                        json.put("status", status);
                        json.put("type", "兑换流量");
                        json.put("qty", operators+sizes+"MB");
                        json.put("coin", "-" + score);
                        json.put("datetime", createtime);
                        json.put("msg", remark);
                        jsonarray.add(json);
	            }
	        }
	        
	        resultcode = "0";
	        msg = "success";
	    }catch(Exception e){
	        logger.error(e);
	        msg = e.getMessage();
	    }
	    
        jsondata.put("resultcode", resultcode);
        jsondata.put("msg", msg);
        jsondata.put("data", jsonarray);
        jsondata.put("function", obj.getPara_function());
        
        long response_start=System.currentTimeMillis();
        respData(jsondata.toString(),request,response);
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 256:"+jsondata.toString());   
        
	}
	
	public void func257(){
	    func257Req obj = (func257Req) request.getAttribute(SysParameter.REQUESTBEAN);
	    
	    String resultcode = "-1";
	    String msg = "System error";
	    
	    try{
	        if (StringUtils.isEmpty(obj.getAlipay_account()) || StringUtils.isEmpty(obj.getChi_name())){
	            throw new RuntimeException("支付宝账号或名称不能为空");
	        }
	        
	        List ls = null;
            if(StringUtils.isNotEmpty(obj.getGroupId())){
                ls=this.sysAccountDao.getData("select id from tbl_reg_account where groupid!=? and alipay_account=?",new String[]{obj.getGroupId(),obj.getAlipay_account()});                            
            }else if (StringUtils.isNotEmpty(obj.getUseraccount())){
                ls=this.sysAccountDao.getData("select id from tbl_reg_account where account!=? and alipay_account=?",new String[]{obj.getUseraccount(),obj.getAlipay_account()});
            }
            
            if(ls!=null && ls.size()>0){
                resultcode="2";
                msg="该支付宝账号已被绑定";
            }else {
                String sql="";
                if (StringUtils.isNotEmpty(obj.getGroupId())){
                    sql = "update tbl_reg_account set alipay_account='" + obj.getAlipay_account() + "', chi_name='" + obj.getChi_name() + "' where groupid='" + obj.getGroupId() + "'";
                } else if (StringUtils.isNotEmpty(obj.getUseraccount())){
                    sql = "update tbl_reg_account set alipay_account='" + obj.getAlipay_account() + "', chi_name='" + obj.getChi_name() + "' where account='" + obj.getUseraccount() + "'";;
                }
                
                if (StringUtils.isNotEmpty(sql)){
                    sysAccountDao.executedSql(sql, new String[]{});
                }
                
                resultcode = "0";
                msg = "绑定成功";
            }
	    }catch(Exception e){
	        logger.error("func257 bind alipay failed.", e);
	        msg = e.getMessage();
	    }
	    
	    JSONObject jsondata = new JSONObject();
        jsondata.put("resultcode", resultcode);
        jsondata.put("msg", msg);
        jsondata.put("function", obj.getPara_function());
        
        long response_start=System.currentTimeMillis();
        respData(jsondata.toString(),request,response);
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 257:"+jsondata.toString()); 
	}
	
	
	public void func260(){
        String resultcode = "-1";
        String msg = "System error";
        
        JSONObject jsondata = new JSONObject();
        JSONArray jsonarray = new JSONArray();
	        
	    func260Req obj = (func260Req) request.getAttribute(SysParameter.REQUESTBEAN);
	    logger.info("ffunc260...");
	    try {
	        
            String sql = "select title, imgURL, linkURL, category, date_format(createtime, '%Y-%m-%d %H:%i:%s') as createtime from tbl_recommend where status = 0";
            List ls = sysAccountDao.getData(sql,new String[]{});
            if(ls!=null && ls.size()>0){
                for (int i = 0; i < ls.size(); i++){
                    Map map = (Map)ls.get(i);
                    String title = map.get("title")==null ? "" : map.get("title").toString();
                    String imgURL = map.get("imgURL")==null ? "" : map.get("imgURL").toString();
                    String linkURL = map.get("linkURL")==null ? "" : map.get("linkURL").toString();
                    String createtime = map.get("createtime")==null ? "" : map.get("createtime").toString();
                    String category = map.get("category")==null ? "" : map.get("category").toString();
                    
                    JSONObject json = new JSONObject();
                    json.put("title", title);
                    json.put("imgURL", (map.get("imgURL")==null?"":(map.get("imgURL").toString().startsWith("http")?map.get("imgURL").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/recommend/"+map.get("imgURL").toString())));
                    json.put("linkURL", linkURL);
                    json.put("createtime", createtime);
                    json.put("category", category);
                    
                    jsonarray.add(json);
                }
            }
            
            resultcode = "0";
            msg = "success";
        } catch (Exception e) {
            logger.error("func260 failed.", e);
        }
        
        jsondata.put("resultcode", resultcode);
        jsondata.put("msg", msg);
        jsondata.put("data", jsonarray);
        jsondata.put("function", obj.getPara_function());
        
        long response_start=System.currentTimeMillis();
        respData(jsondata.toString(),request,response);
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 260:"+jsondata.toString()); 
	}
	
	   public void func261(){
	        String resultcode = "-1";
	        String msg = "System error";
	        String moreJokes = "";
	        
	        JSONObject jsondata = new JSONObject();
	        JSONArray jsonarray = new JSONArray();
	            
	        func261Req obj = (func261Req) request.getAttribute(SysParameter.REQUESTBEAN);
	        logger.info("ffunc261...");
	        try {
	            
	            Map<String, Object> params = SysParameter.getInstatnce().getParams();
	            if (params.containsKey("MORE_JOKES") && params.get("MORE_JOKES") != null){
	                moreJokes = params.get("MORE_JOKES").toString();
	            } else {
	                List ls = sysAccountDao.getData("select icon from tbl_wifi_parameter where name = 'find08_joke'" ,new String[]{});
	                if (ls != null && !ls.isEmpty()){
	                    Map map = (Map)ls.get(0);
	                    moreJokes = map.get("icon")==null ? "" : map.get("icon").toString();
	                    if (moreJokes.length() > 1){
	                        params.put("MORE_JOKES", moreJokes);
	                    }
	                }
	            }
	            
	            String sql = "select title, status from tbl_find8_joke where status = 1 limit 10";
	            List ls = sysAccountDao.getData(sql,new String[]{});
	            if(ls!=null && !ls.isEmpty()){
	                for (int i = 0; i < ls.size(); i++){
	                    Map map = (Map)ls.get(i);
	                    String title = map.get("title")==null ? "" : map.get("title").toString();
	                    
	                    if (StringUtils.isEmpty(title)){
	                        continue;
	                    }
	                    
	                    JSONObject json = new JSONObject();
	                    json.put("title", title);
	                    
	                    jsonarray.add(json);
	                }
	            }
	            
	            resultcode = "0";
	            msg = "success";
	        } catch (Exception e) {
	            logger.error("func261", e);
	        }
	        
	        jsondata.put("resultcode", resultcode);
	        jsondata.put("moreJokes", moreJokes);
	        jsondata.put("msg", msg);
	        jsondata.put("data", jsonarray);
	        jsondata.put("function", obj.getPara_function());
	        
	        long response_start=System.currentTimeMillis();
	        respData(jsondata.toString(),request,response);
	        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
	                "response 261:"+jsondata.toString()); 
	    }
	   
	   
	   
	  public void func262(){

	       func262Req obj = (func262Req) request.getAttribute(SysParameter.REQUESTBEAN);
	       
	       JSONObject res = new JSONObject();
	       JSONArray moreItems = new JSONArray();
           String resultcode = "-1";
           String msg = "System error";
           
           
	       try {
	           String topicId = "";
                List ls = sysAccountDao.getData("select topicId, count(*) from (  SELECT r.useraccount, r.topicId  FROM  tbl_reply r LEFT JOIN tbl_topic t ON r.topicId = t.id  WHERE r. STATUS = 0  AND t. STATUS = 0 GROUP BY r.topicId, r.useraccount  ) a group by topicid ORDER BY count(*) DESC limit 1", new String[]{});
                if (ls != null && !ls.isEmpty()){
                    Map map = (Map)ls.get(0);
                    topicId = map.get("topicId") == null ? null : map.get("topicId").toString().trim();
                    
                    String num = "0";
                    List rs = sysAccountDao.getData("SELECT count(*) as num from tbl_reply WHERE topicId = " + topicId, new String[]{});
                    if (rs != null && !rs.isEmpty()){
                        map = (Map)rs.get(0);
                        num =  map.get("num") == null ? "0" : map.get("num").toString();
                    }
                    
                    if (StringUtils.isNotEmpty(topicId)){
                    
                        ls = sysAccountDao.getData("select a.account, a.username, a.image as userimage, t.content, t.ssid, t.smallimage  ,date_format(t.createtime, '%Y-%m-%d %H:%i:%s') as createtime from tbl_topic t left join tbl_reg_account a on t.useraccount = a.account where t.id = '" + topicId + "'", new String[]{});
                        if (ls != null && !ls.isEmpty()){
                            map = (Map)ls.get(0);
                            String account = map.get("account") == null ? "" : map.get("account").toString().trim();
                            String username = map.get("username") == null ? "" : map.get("username").toString().trim();
                            String userimage = (map.get("userimage")==null?"":(map.get("userimage").toString().startsWith("http")?map.get("userimage").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+ map.get("userimage").toString()));
                            String content = map.get("content") == null ? "" : map.get("content").toString().trim();
                            String ssid = map.get("ssid") == null ? "" : map.get("ssid").toString().trim();
                            String smallimage = map.get("smallimage") == null || "".equals(map.get("smallimage").toString()) ? "" : PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/topic/" + map.get("smallimage").toString();
                            String createtime = map.get("createtime") == null || "".equals(map.get("createtime").toString()) ? "" : map.get("createtime").toString();
                            
                            JSONObject item = new JSONObject();
                            item.put("replyCount", num);
                            item.put("topicId", topicId);
                            item.put("smallimage", smallimage);
                            item.put("content", content);
                            item.put("useraccount", account);
                            item.put("datetime", createtime);
                            item.put("username", username);
                            item.put("userimage", userimage);
                            item.put("ssid", ssid);
                            res.put("hot", item);
                        }
                }
            }
            
            String recommendTopicId = "";
            ls = sysAccountDao.getData("select (select count(*) from tbl_reply where status = 0 and topicId = t.id) as num, a.account, a.username, a.image as userimage, t.id, t.content, t.ssid, t.smallimage,date_format(t.createtime, '%Y-%m-%d %H:%i:%s') as createtime from tbl_topic t left join tbl_reg_account a on t.useraccount = a.account where t.isrecommand = 0 limit 1", new String[]{});
            if (ls != null && !ls.isEmpty()){
                Map map = (Map)ls.get(0);
                String tid = map.get("id") == null ? "" : map.get("id").toString().trim();
                if (!tid.equals(topicId)){
                    recommendTopicId = tid;
                    String num = map.get("num") == null ? "0" : map.get("num").toString();
                    String account = map.get("account") == null ? "" : map.get("account").toString().trim();
                    
                    String username = map.get("username") == null ? "" : map.get("username").toString().trim();
                    String userimage = (map.get("userimage")==null?"":(map.get("userimage").toString().startsWith("http")?map.get("userimage").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+ map.get("userimage").toString()));
                    String content = map.get("content") == null ? "" : map.get("content").toString().trim();
                    String ssid = map.get("ssid") == null ? "" : map.get("ssid").toString().trim();
                    String smallimage = map.get("smallimage") == null || "".equals(map.get("smallimage").toString()) ? "" : PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/topic/" + map.get("smallimage").toString();
                    String createtime = map.get("createtime") == null || "".equals(map.get("createtime").toString()) ? "" : map.get("createtime").toString();

                    JSONObject item = new JSONObject();
                    item.put("replyCount", num);
                    item.put("topicId", tid);
                    item.put("smallimage", smallimage);
                    item.put("content", content);
                    item.put("useraccount", account);
                    item.put("datetime", createtime);
                    item.put("username", username);
                    item.put("userimage", userimage);
                    item.put("ssid", ssid);
                    res.put("recommand", item); 
                }
            }
            
            double radius = Double.valueOf(SysParameter.getInstatnce().getParams().get("topic_radius").toString());
            radius = radius * 1.1;
            com.aora.wifi.tools.LatitudeLontitudeUtil.Point[] locations = LatitudeLontitudeUtil.getRectangle4Point_v2(obj.getLat(), obj.getLng(), radius);
            
            String sql="select (select count(*) from tbl_reply where status = 0 and topicId = t.id) as num, a.account, a.username, a.image as userimage, t.id, t.content, t.ssid, t.smallimage, date_format(t.createtime, '%Y-%m-%d %H:%i:%s') as createtime from tbl_topic t left join tbl_reg_account a on t.useraccount = a.account where length(a.image) > 1 and t.lat > " + locations[2].x + " and t.lat < " + locations[0].x + " and t.lng > " + locations[3].y + " and t.lng < " + locations[1].y + " order by t.createtime desc limit 10";
            logger.info("ff262:" + sql);
            ls = sysAccountDao.getData(sql, new String[]{});
            if (ls != null && !ls.isEmpty()){
                for (int i = 0; i < ls.size(); i++){
                    Map map = (Map)ls.get(i);
                    String id = map.get("id") == null ? "" : map.get("id").toString().trim();
                    
                    if (id.equals(topicId) || id.equals(recommendTopicId)){
                        continue;
                    }
                    
                    String num = map.get("num") == null ? "0" : map.get("num").toString();
                    String account = map.get("account") == null ? "" : map.get("account").toString().trim();
                    String username = map.get("username") == null ? "" : map.get("username").toString().trim();
                    String userimage = (map.get("userimage")==null?"":(map.get("userimage").toString().startsWith("http")?map.get("userimage").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/user/"+ map.get("userimage").toString()));
                    if (StringUtils.isEmpty(userimage)){
                        continue;
                    }
                    
                    String content = map.get("content") == null ? "" : map.get("content").toString().trim();
                    String ssid = map.get("ssid") == null ? "" : map.get("ssid").toString().trim();
                    String smallimage = map.get("smallimage") == null || "".equals(map.get("smallimage").toString()) ? "" : PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/topic/" + map.get("smallimage").toString();
                    String createtime = map.get("createtime") == null || "".equals(map.get("createtime").toString()) ? "" : map.get("createtime").toString();

                    JSONObject items = new JSONObject();
                    items.put("replyCount", num);
                    items.put("topicId", id);
                    items.put("smallimage", smallimage);
                    items.put("content", content);
                    items.put("useraccount", account);
                    items.put("datetime", createtime);
                    items.put("username", username);
                    items.put("userimage", userimage);
                    items.put("ssid", ssid);
                    
                    moreItems.add(items);
                }
            }
            
            resultcode = "0";
            msg = "success";
        } catch (Exception e) {
            logger.error("func262", e);
        }
        
        res.put("resultcode", resultcode);
        res.put("msg", msg);
        res.put("data", moreItems);
        res.put("function", obj.getPara_function());
        
        long response_start=System.currentTimeMillis();
        respData(res.toString(),request,response);
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 262:" + res.toString()); 
	  }
	  	  
}
