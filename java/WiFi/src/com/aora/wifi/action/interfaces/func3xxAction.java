package com.aora.wifi.action.interfaces;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.aora.wifi.bean.func301Req;
import com.aora.wifi.bean.func302Req;
import com.aora.wifi.bean.func303Req;
import com.aora.wifi.bean.func304Req;
import com.aora.wifi.bean.func310Req;
import com.aora.wifi.bean.func311Req;
import com.aora.wifi.bean.func312Req;
import com.aora.wifi.bean.func320Req;
import com.aora.wifi.bean.func321Req;
import com.aora.wifi.bean.func322Req;
import com.aora.wifi.bean.func351Req;
import com.aora.wifi.bean.func352Req;
import com.aora.wifi.bean.func353Req;
import com.aora.wifi.bean.func354Req;
import com.aora.wifi.bean.func355Req;
import com.aora.wifi.bean.func356Req;
import com.aora.wifi.bean.func357Req;
import com.aora.wifi.bean.func370Req;
import com.aora.wifi.bean.func375Req;
import com.aora.wifi.bean.func378Req;
import com.aora.wifi.bean.func380Req;
import com.aora.wifi.bean.requestBean;
import com.aora.wifi.dao.impl.SysAccountDaoImpl;
import com.aora.wifi.servlet.SysParameter;
import com.aora.wifi.tools.LatitudeLontitudeUtil;
import com.aora.wifi.tools.PropertyInfo;
import com.aora.wifi.tools.LatitudeLontitudeUtil.Location;
import com.aora.wifi.util.Tools;

public class func3xxAction extends BaseBean {

	private static SysAccountDaoImpl sysAccountDao=new SysAccountDaoImpl();
	

	private final static Logger logger = Logger.getLogger(func3xxAction.class);	
	

	private HttpServletRequest request;
	private HttpServletResponse response;

	public func3xxAction(HttpServletRequest request,HttpServletResponse response){
		this.request=request;
		this.response=response;
	}

	public void process(requestBean obj) throws Exception{
		switch(Integer.parseInt(obj.getPara_function())){
		case 301:
			this.func301();	
			break;
		case 302:
			this.func302();	
			break;
		case 303:
			this.func303();	
			break;
		case 304:
			this.func304();	
			break;
		case 310:
			this.func310();	
			break;
		case 311:
			this.func311();	
			break;
		case 312:
			this.func312();	
			break;
		case 320:
			this.func320();	
			break;
		case 321:
			this.func321();	
			break;
		case 322:
			this.func322();	
			break;			
		case 351:
		    this.func351();
		    break;
        case 352:
            this.func352();
            break;
        case 353:
            this.func353();
            break;
        case 354:
            this.func354();
            break;
        case 355:
            this.func355();
            break;
        case 356:
            this.func356();
            break;
        case 357:
            this.func357();
            break;
        case 370:
            this.func370();
            break;
        case 375:
            this.func375();
            break;
        case 378:
            this.func378();
            break;
        case 380:
            this.func380();
            break;
		}
	}
	
	public void func301() throws Exception{
	    
        func301Req obj=(func301Req)request.getAttribute(SysParameter.REQUESTBEAN);

		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		List ls=null;
		int collectcount=0;
		try{


					String uid=obj.getUID();
					String account=obj.getAccount();
					String country_code="";
					String country="";
					String tips="";
					String price="";
					String image="";

					DecimalFormat df = new DecimalFormat("0.00");  
						ls=this.sysAccountDao.getData("select * from tbl_roaming_country where status=1", new String[]{});
						if(ls!=null || ls.size()>0){
							for(int i=0;i<ls.size();i++){
								Map map=(Map)ls.get(i);
								price=(map.get("price")==null?"0":map.get("price").toString());
								country_code=(map.get("country_code")==null?"0":map.get("country_code").toString());
								country=(map.get("country")==null?"0":map.get("country").toString());
								tips=(map.get("remark")==null?"0":map.get("remark").toString());
								image=(map.get("icon")==null?"0":map.get("icon").toString());
								
								image=(image.length()>7?PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/"+image:PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/icon/roaming.jpg");								
								
								
								JSONObject item=new JSONObject();
								item.put("price",		df.format((Double.valueOf(price))/100).toString());
								item.put("country_code",country_code);
								item.put("country",country);
								item.put("tips",tips);
								item.put("image",image);
								jsonarray.add(item);																
							}
						}
					resultcode="0";
					msg="success";						

		}catch(Exception e){
			e.printStackTrace();
			
						
		}
		


		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("data", jsonarray);
		jsondata.put("function", obj.getPara_function());

		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 301:"+jsondata.toString());
		
   
	}	
			
	public void func302() throws Exception{
	    
        func302Req obj=(func302Req)request.getAttribute(SysParameter.REQUESTBEAN);

		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		List ls=null;
		String price="0";
		String qty="0";
		String ssid_keys="";
		DecimalFormat df = new DecimalFormat("0.00");  
		synchronized (Tools.synchronized_302_obj){ 		
			try{
	
	
						String uid=obj.getUID();
						String account=obj.getAccount();
	
							ls=this.sysAccountDao.getData("select * from tbl_roaming_country where status=1 and country=?", new String[]{obj.getCountry()});
							if(ls!=null || ls.size()>0){	
								for (int i=0;i<ls.size();i++){
									Map map=(Map)ls.get(i);
									price=(map.get("price")==null?"0":map.get("price").toString());
									qty=(map.get("qty")==null?"0":map.get("qty").toString());
									ssid_keys=(map.get("ssid_keys")==null?"0":map.get("ssid_keys").toString());

									ls=this.sysAccountDao.getData("select * from tbl_roaming_account where status=2 and uid=? and country=?", new String[]{obj.getUID(),obj.getCountry()});
									if(ls==null ||ls.size()==0){
										if(Integer.parseInt(qty)>0){
											ls=this.sysAccountDao.getData("select * from tbl_roaming_account where id in ( select min(id)  from tbl_roaming_account where status=0 and country=? group by ssid)", new String[]{obj.getCountry()});
											if(ls.size()==ssid_keys.split(",").length){ 
												for(int j=0;j<ls.size();j++){
													map=(Map)ls.get(j);
													
													String aid=(map.get("id")==null?"":map.get("id").toString());
													String ssid=(map.get("ssid")==null?"":map.get("ssid").toString());
													String acc=(map.get("acc")==null?"":map.get("acc").toString());
													String pass=(map.get("pass")==null?"":map.get("pass").toString());
													String time=(map.get("time")==null?"0":map.get("time").toString());
													
													this.sysAccountDao.executedSql("update tbl_roaming_account set status=2,uid=?,reg_account=?,pdate=now() where id=?", new String[]{obj.getUID(),obj.getAccount(),aid});												
													
	
												}
												this.sysAccountDao.executedSql("update tbl_roaming_country set qty=qty-1 where country=?", new String[]{obj.getCountry()});											
											}									
										
										}
									}else{
										
									}
									
									break;
								}
	
							}
						resultcode="0";
						msg="success";						
	
			}catch(Exception e){
				e.printStackTrace();
				
							
			}
		}

		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("price", df.format((Double.valueOf(price))/100).toString());
		jsondata.put("qty", qty);
		jsondata.put("orderid", String.valueOf(System.currentTimeMillis()));
		jsondata.put("function", obj.getPara_function());
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 302:"+jsondata.toString());
		
   
	}		

public void func303() throws Exception{
	    
        func303Req obj=(func303Req)request.getAttribute(SysParameter.REQUESTBEAN);

		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		List ls=null;
		String price="0";
		String qty="0";
		String ssid_keys="";
		DecimalFormat df = new DecimalFormat("0.00");  
		synchronized (Tools.synchronized_302_obj){ 			
			try{
	
	
						String uid=obj.getUID();
						String account=obj.getAccount();
						String acc_ids="";
						
							ls=this.sysAccountDao.getData("select * from tbl_roaming_country where status=1 and country=?", new String[]{obj.getCountry()});
								if(ls!=null || ls.size()>0){	
									Map map=(Map)ls.get(0);
									price=(map.get("price")==null?"0":map.get("price").toString());
									qty=(map.get("qty")==null?"0":map.get("qty").toString());
									ssid_keys=(map.get("ssid_keys")==null?"0":map.get("ssid_keys").toString());
									
									if(Integer.parseInt(obj.getQty())>0){
									
										ls=this.sysAccountDao.getData("select * from tbl_roaming_account where status=2 and uid=? and country=?", new String[]{obj.getUID(),obj.getCountry()});
										if(ls.size()==ssid_keys.split(",").length){ 
											for(int i=0;i<ls.size();i++){
											map=(Map)ls.get(i);
											
											String aid=(map.get("id")==null?"":map.get("id").toString());
											String ssid=(map.get("ssid")==null?"":map.get("ssid").toString());
											String acc=(map.get("acc")==null?"":map.get("acc").toString());
											String pass=(map.get("pass")==null?"":map.get("pass").toString());
											String time=(map.get("time")==null?"0":map.get("time").toString());
											this.sysAccountDao.executedSql("update tbl_roaming_account set status=1,uid=?,reg_account=?,pdate=now() where id=?", new String[]{obj.getUID(),obj.getAccount(),aid});												
											
											JSONObject item=new JSONObject();
											item.put("ssid",ssid);
											item.put("acc",acc);
											item.put("pass",pass);
											item.put("time",time);
											jsonarray.add(item);													
											acc_ids+=aid+",";
											}
										}
										this.sysAccountDao.executedSql("insert into tbl_roaming_pay_logs(uid,reg_account,order_id,price,qty,amount,roaming_ids,country,country_code,pay_account,indate) values(?,?,?,?,?,?,?,?,?,?,now())",  new String[]{obj.getUID(),obj.getAccount(),obj.getOrder_id(),obj.getPrice(),obj.getQty(),obj.getAmount(),acc_ids,obj.getAccount(),obj.getCountry_code(),obj.getAlipay_account()});									
									}else{
										this.sysAccountDao.executedSql("update tbl_roaming_country a inner join (select country,count(*) 'qty1' from tbl_roaming_account where  status=2 and uid=? and country=? group by country,ssid limit 1 ) b on a.country=b.country set a.qty=a.qty+b.qty1 ", new String[]{obj.getUID(),obj.getCountry()});
										
										this.sysAccountDao.executedSql("update tbl_roaming_account set status=0,uid='',reg_account='' where  uid=? and country=?", new String[]{obj.getUID(),obj.getCountry()});
									}
//									if(Integer.parseInt(qty)>0){
//										
//										ls=this.sysAccountDao.getData("select * from tbl_roaming_account where id in ( select min(id)  from tbl_roaming_account where status=0 and country=? group by ssid)", new String[]{obj.getCountry()});
//										if(ls.size()==ssid_keys.split(",").length){ 
//											for(int i=0;i<ls.size();i++){
//												map=(Map)ls.get(i);
//												
//												String aid=(map.get("id")==null?"":map.get("id").toString());
//												String ssid=(map.get("ssid")==null?"":map.get("ssid").toString());
//												String acc=(map.get("acc")==null?"":map.get("acc").toString());
//												String pass=(map.get("pass")==null?"":map.get("pass").toString());
//												String time=(map.get("time")==null?"0":map.get("time").toString());
//												
//												this.sysAccountDao.executedSql("update tbl_roaming_account set status=1,uid=?,reg_account=?,pdate=now() where id=?", new String[]{obj.getUID(),obj.getAccount(),aid});												
//												
//												JSONObject item=new JSONObject();
//												item.put("ssid",ssid);
//												item.put("acc",acc);
//												item.put("pass",pass);
//												item.put("time",time);
//												jsonarray.add(item);													
//												acc_ids+=aid+",";
//											}
//											this.sysAccountDao.executedSql("update tbl_roaming_country set qty=qty-1 where country=?", new String[]{obj.getCountry()});											
//										}
//										
//										this.sysAccountDao.executedSql("insert into tbl_roaming_pay_logs(uid,reg_account,order_id,price,qty,amount,roaming_ids,country,country_code,pay_account,indate) values(?,?,?,?,?,?,?,?,?,?,now())",  new String[]{obj.getUID(),obj.getAccount(),obj.getOrder_id(),obj.getPrice(),obj.getQty(),obj.getAmount(),acc_ids,obj.getAccount(),obj.getCountry_code(),obj.getAlipay_account()});
//										
//									}
	
							}
						resultcode="0";
						msg="success";						
	
			}catch(Exception e){
				e.printStackTrace();
				
							
			}
		}


		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("timestamp", String.valueOf(System.currentTimeMillis()));
		jsondata.put("country", obj.getCountry());
		jsondata.put("country_code",obj.getCountry_code());
		
		jsondata.put("data", jsonarray);
		
		long response_start=System.currentTimeMillis();
		respData(jsondata.toString(),request,response);
		this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
				"response 303:"+jsondata.toString());
		
   
	}			
	

public void func304() throws Exception{
    
    func304Req obj=(func304Req)request.getAttribute(SysParameter.REQUESTBEAN);

	JSONObject jsondata=new JSONObject();
	JSONArray jsonarray=new JSONArray();
	String resultcode="-99";
	String msg="request error!";
	List ls=null;
	int collectcount=0;
	String url_file="";	
	try{


				String uid=obj.getUID();
				String account=obj.getAccount();



				DecimalFormat df = new DecimalFormat("0.00");  
					ls=this.sysAccountDao.getData("select * from tbl_roaming_map where country=? and city=?", new String[]{obj.getCountry(),obj.getCity()});
					System.out.println(ls.size());
					if(ls!=null && ls.size()>0){
						for(int i=0;i<ls.size();i++){
							Map map=(Map)ls.get(i);
							url_file=(map.get("url_file")==null?"":map.get("url_file").toString());
							url_file=(url_file.length()>5?PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/map/"+url_file:"");								
							break;								
						}
					}
				resultcode="0";
				msg="success";						

	}catch(Exception e){
		e.printStackTrace();
		
					
	}
	


	jsondata.put("resultcode", resultcode);
	jsondata.put("msg", msg);
	jsondata.put("url", url_file);
	jsondata.put("function", obj.getPara_function());

	long response_start=System.currentTimeMillis();
	respData(jsondata.toString(),request,response);
	this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
			"response 304:"+jsondata.toString());
	

	}	


public void func310() throws Exception{
    
    func310Req obj=(func310Req)request.getAttribute(SysParameter.REQUESTBEAN);

	JSONObject jsondata=new JSONObject();
	JSONArray jsonarray=new JSONArray();
	String resultcode="-99";
	String msg="request error!";
	List ls=null;
	String price="0";
	String qty="0";
	String ssid_keys="";
	DecimalFormat df = new DecimalFormat("0.00");  
		
	String uid=obj.getUID();
	String account=obj.getAccount();
	String status="";
	String pno="";
	String type="";
	String title="";
	String description="";
	String url="";
	String sdate="";
	String edate="";
	String image="";
	String score="";
	String remark="";	
		try{





					
						ls=this.sysAccountDao.getData("select * from tbl_activity_loading_data where status=1 and sdate<=now() and now()<=edate", new String[]{});
						if(ls!=null && ls.size()>0){
								status="1";
								Map map=(Map)ls.get(0);
								pno=(map.get("pno")==null?"0":map.get("pno").toString());
								type=(map.get("ptype")==null?"0":map.get("ptype").toString());
								title=(map.get("title")==null?"":map.get("title").toString());
								description=(map.get("description")==null?"":map.get("description").toString());
								url=(map.get("url")==null?"":map.get("url").toString());
								sdate=(map.get("sdate")==null?"":map.get("sdate").toString().substring(0,10));
								edate=(map.get("edate")==null?"":map.get("edate").toString().substring(0,10));
								image=(map.get("image")==null?"":(map.get("image").toString().startsWith("http")?map.get("image").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/active/"+map.get("image").toString()));

						}else{
							status="0";
						}
						ls=this.sysAccountDao.getData("select max(pno) 'pno' from tbl_activity_data where status=1 and sdate<=now() and now()<=edate", new String[]{});
						if(ls!=null && ls.size()>0){
							Map map=(Map)ls.get(0);
							pno=(map.get("pno")==null?"0":map.get("pno").toString());	
						}
						
					resultcode="0";
					msg="success";						

		}catch(Exception e){
			e.printStackTrace();
			
						
		}

	jsondata.put("resultcode", resultcode);
	jsondata.put("msg", msg);
	jsondata.put("active_status", status);
	jsondata.put("active_no", pno);
	jsondata.put("active_type", type);
	jsondata.put("active_title", title);
	jsondata.put("active_description", description);
	jsondata.put("active_url", url);
	jsondata.put("active_sdate", sdate);
	jsondata.put("active_edate", edate);
	jsondata.put("active_image", image);
	

	
	long response_start=System.currentTimeMillis();
	respData(jsondata.toString(),request,response);
	this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
			"response 310:"+jsondata.toString());
	

	}			

public void func311() throws Exception{
    
    func311Req obj=(func311Req)request.getAttribute(SysParameter.REQUESTBEAN);

	JSONObject jsondata=new JSONObject();
	JSONArray jsonarray=new JSONArray();
	String resultcode="-99";
	String msg="request error!";
	List ls=null;
	String price="0";
	String qty="0";
	String ssid_keys="";
	DecimalFormat df = new DecimalFormat("0.00");  
		
	String pid="";
	String uid=obj.getUID();
	String account=obj.getAccount();
	String status="";
	String pno="";
	String type="";
	String title="";
	String description="";
	String url="";
	String sdate="";
	String edate="";
	String image="";
	String score="0";
	String remark="";
	
	String display1_1="",display1_2="",display1_3="";
	String display2_1="",display2_2="",display2_3="";
	String display3_1="",display3_2="",display3_3="";
	String display4_1="",display4_2="",display4_3="";
	
	
		try{





					
						ls=this.sysAccountDao.getData("select * from tbl_activity_loading_data where status=1 and sdate<=now() and now()<=edate", new String[]{});
						if(ls!=null || ls.size()>0){
								status="1";
								Map map=(Map)ls.get(0);
								pid=(map.get("id")==null?"0":map.get("id").toString());
								pno=(map.get("pno")==null?"0":map.get("pno").toString());
								type=(map.get("type")==null?"0":map.get("type").toString());
								title=(map.get("title")==null?"":map.get("title").toString());
								description=(map.get("description")==null?"":map.get("description").toString());
								url=(map.get("url")==null?"":map.get("url").toString());
								sdate=(map.get("sdate")==null?"":map.get("sdate").toString().substring(0,10));
								edate=(map.get("edate")==null?"":map.get("edate").toString().substring(0,10));
								image=(map.get("image")==null?"":(map.get("image").toString().startsWith("http")?map.get("image").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/active/"+map.get("image").toString()));
								display1_1=(map.get("display1_1")==null?"":map.get("display1_1").toString());
								display1_2=(map.get("display1_2")==null?"":map.get("display1_2").toString());
								display1_3=(map.get("display1_3")==null?"":map.get("display1_3").toString());
								display2_1=(map.get("display2_1")==null?"":map.get("display2_1").toString());
								display2_2=(map.get("display2_2")==null?"":map.get("display2_2").toString());
								display2_3=(map.get("display2_3")==null?"":map.get("display2_3").toString());
								display3_1=(map.get("display3_1")==null?"":map.get("display3_1").toString());
								display3_2=(map.get("display3_2")==null?"":map.get("display3_2").toString());
								display3_3=(map.get("display3_3")==null?"":map.get("display3_3").toString());
								display4_1=(map.get("display4_1")==null?"":map.get("display4_1").toString());
								display4_2=(map.get("display4_2")==null?"":map.get("display4_2").toString());
								display4_3=(map.get("display4_3")==null?"":map.get("display4_3").toString());
								
								ls=this.sysAccountDao.getData("select * from tbl_activity_draw_logs where uid=? and indate>=current_date()", new String[]{obj.getUID()});
								if(ls==null || ls.size()==0){
									
									ls=this.sysAccountDao.getData("select * from tbl_activity_data_pool where process is null order by rand() limit 1", new String[]{});
									if(ls!=null && ls.size()>0){
										Map mp=(Map)ls.get(0);
										String tid=(mp.get("id")==null?"0":mp.get("id").toString());
										score=(mp.get("score")==null?"0":mp.get("score").toString());
										
										this.sysAccountDao.executedSql("update tbl_activity_data_pool set process=now(),uid=?,account=? where id=?",new String[]{obj.getUID(),obj.getAccount(),tid});
										
									}else{
										score="0";
									}
									
									this.sysAccountDao.executedSql("insert into tbl_activity_draw_logs(act_id,uid,account,score,indate) values(?,?,?,?,now())" , new String[]{pid,obj.getUID(),obj.getAccount(),score});
									
									if(Integer.parseInt(score)>0){
										if(obj.getAccount().trim().length()==11){
											this.sysAccountDao.executedSql("update tbl_reg_account set score=score+"+score+" where account=?" , new String[]{obj.getAccount()});
										}else{
											this.sysAccountDao.executedSql("update tbl_user_account set score=score+"+score+" where uid=?" , new String[]{obj.getUID()});
										}
									}
									
									resultcode="0";
									msg="success";										
									
								}else{
									
									resultcode="2";
									msg="today had draw the data!";									
									
								}
						}else{
							resultcode="1";
							msg="activity is expire!";	
						}
					

		}catch(Exception e){
			e.printStackTrace();
			resultcode="-11";
			msg="interface error!";	
						
		}

	jsondata.put("resultcode", resultcode);
	jsondata.put("msg", msg);
	jsondata.put("active_status", status);
	jsondata.put("active_no", pno);
	jsondata.put("active_type", type);
	jsondata.put("active_title", title);
	jsondata.put("active_description", description);
	jsondata.put("active_url", url);
	jsondata.put("active_sdate", sdate);
	jsondata.put("active_edate", edate);
	jsondata.put("active_image", image);
	jsondata.put("score", df.format((Double.valueOf(score))/100).toString());

//	jsondata.put("display1_1", display1_1);
//	jsondata.put("display1_2", display1_2);
//	jsondata.put("display1_3", display1_3);
//	jsondata.put("display2_1", display2_1);
//	jsondata.put("display2_2", display2_2);
//	jsondata.put("display2_3", display2_3);
//	jsondata.put("display3_1", display3_1);
//	jsondata.put("display3_2", display3_2);
//	jsondata.put("display3_3", display3_3);
//	jsondata.put("display4_1", display4_1);
//	jsondata.put("display4_2", display4_2);
//	jsondata.put("display4_3", display4_3);
	
	long response_start=System.currentTimeMillis();
	respData(jsondata.toString(),request,response);
	this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
			"response 310:"+jsondata.toString());
	

	}			

public void func312() throws Exception{
    
    func312Req obj=(func312Req)request.getAttribute(SysParameter.REQUESTBEAN);

	JSONObject jsondata=new JSONObject();
	JSONArray jsonarray=new JSONArray();
	String resultcode="-99";
	String msg="request error!";
	List ls=null;
	String price="0";
	String qty="0";
	String ssid_keys="";
	DecimalFormat df = new DecimalFormat("0.00");  
		
	String pid="";
	String uid=obj.getUID();
	String account=obj.getAccount();
	String status="";
	String pno="";
	String type="";
	String title="";
	String description="";
	String url="";
	String sdate="";
	String edate="";
	String image="";
	String score="";
	String remark="";

		try{


					ls=this.sysAccountDao.getData("select * from tbl_activity_data where status!=0  order by status, indate desc limit "+obj.getPageno()+","+obj.getPagesize(), new String[]{});
					for(int i=0;i<ls.size();i++){
						Map	map=(Map)ls.get(i);
						pid=(map.get("id")==null?"0":map.get("id").toString());
						pno=(map.get("pno")==null?"0":map.get("pno").toString());
						type=(map.get("type")==null?"0":map.get("type").toString());
						title=(map.get("title")==null?"":map.get("title").toString());
						description=(map.get("description")==null?"":map.get("description").toString());
						url=(map.get("url")==null?"":map.get("url").toString());
						sdate=(map.get("sdate")==null?"":map.get("sdate").toString().substring(0,10));
						edate=(map.get("edate")==null?"":map.get("edate").toString().substring(0,10));
						status=(map.get("status")==null?"2":map.get("status").toString()); //1 active, 2 oldate
						image=(map.get("image")==null?"":(map.get("image").toString().startsWith("http")?map.get("image").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/active/"+map.get("image").toString()));
						
						JSONObject item=new JSONObject();
						//item.put("active_status", status);
						item.put("active_no", pno);
						item.put("active_type", type);
						item.put("active_title", title);
						item.put("active_description", description);
						item.put("active_url", url);
						item.put("active_sdate", sdate);
						item.put("active_edate", edate);
						item.put("active_image", image);
						item.put("active_status", status);
						jsonarray.add(item);													
						}
					resultcode="0";
					msg="success";						

		}catch(Exception e){
			e.printStackTrace();
			
						
		}



	jsondata.put("resultcode", resultcode);
	jsondata.put("msg", msg);
	jsondata.put("data", jsonarray);
	
	long response_start=System.currentTimeMillis();
	respData(jsondata.toString(),request,response);
	this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
			"response 312:"+jsondata.toString());
	

}			


public void func320() throws Exception{
    
    func320Req obj=(func320Req)request.getAttribute(SysParameter.REQUESTBEAN);

	JSONObject jsondata=new JSONObject();
	JSONArray jsonarray=new JSONArray();
	String resultcode="-99";
	String msg="request error!";
	List ls=null;
	String price="0";
	String qty="0";
	String ssid_keys="";
	DecimalFormat df = new DecimalFormat("0.00");  
		
	String name="";
	String uid=obj.getUID();
	String account=obj.getAccount();
	Map[] find=null;
	JSONArray jdata=new JSONArray();

		try{


					ls=this.sysAccountDao.getData("SELECT * FROM tbl_wifi_parameter where name like 'find%' order by name", new String[]{});

					for(int i=0;i<ls.size();i++){
						Map	map=(Map)ls.get(i);
						name=(map.get("name")==null?"":map.get("name").toString());

						if(name.toLowerCase().startsWith("find01")){
							JSONObject item=new JSONObject();
							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
							item.put("ptype", "01");
							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));							
							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find1_banner where status=1 order by sort,id", new String[]{});
							JSONArray darray=new JSONArray();
							if(dt!=null && dt.size()>0){
								for(int j=0;j<dt.size();j++){
									JSONObject ditem=new JSONObject();
									Map	dmap=(Map)dt.get(j);
									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));
									ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
									darray.add(ditem);
								}
								item.put("item_data", darray);
							}else{
								item.put("item_data", darray);
							}

							jdata.add(item);
						}else if(name.toLowerCase().startsWith("find02")){
							JSONObject item=new JSONObject();
							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));
							item.put("ptype", "02");
							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find2_navigate where status=1 order by sort,id", new String[]{});
							JSONArray darray=new JSONArray();
							if(dt!=null && dt.size()>0){
								for(int j=0;j<dt.size();j++){
									JSONObject ditem=new JSONObject();
									Map	dmap=(Map)dt.get(j);
									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
									ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
									ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
									darray.add(ditem);
								}
								item.put("item_data", darray);
							}else{
								item.put("item_data", darray);
							}

							jdata.add(item);
						}else if(name.toLowerCase().startsWith("find03")){
							JSONObject item=new JSONObject();
							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));							
							item.put("ptype", "03");
							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find3_news where status=1 order by sort,id", new String[]{});
							JSONArray darray=new JSONArray();
							if(dt!=null && dt.size()>0){
								for(int j=0;j<dt.size();j++){
									JSONObject ditem=new JSONObject();
									Map	dmap=(Map)dt.get(j);
									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
									ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
									ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
									darray.add(ditem);
								}
								item.put("item_data", darray);
							}else{
								item.put("item_data", darray);
							}

							jdata.add(item);							
						}else if(name.toLowerCase().startsWith("find04")){
							JSONObject item=new JSONObject();
							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));							
							item.put("ptype", "04");
							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find4_video where status=1 order by sort,id", new String[]{});
							JSONArray darray=new JSONArray();
							if(dt!=null && dt.size()>0){
								for(int j=0;j<dt.size();j++){
									JSONObject ditem=new JSONObject();
									Map	dmap=(Map)dt.get(j);
									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
									ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
									ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
									darray.add(ditem);
								}
								item.put("item_data", darray);
							}else{
								item.put("item_data", darray);
							}

							jdata.add(item);														
						}else if(name.toLowerCase().startsWith("find05")){
							JSONObject item=new JSONObject();
							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));							
							item.put("ptype", "05");
							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find5_coupon where status=1 order by sort,id", new String[]{});
							JSONArray darray=new JSONArray();
							if(dt!=null && dt.size()>0){
								for(int j=0;j<dt.size();j++){
									JSONObject ditem=new JSONObject();
									Map	dmap=(Map)dt.get(j);
									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
									ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
									//ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
									darray.add(ditem);
								}
								item.put("item_data", darray);
							}else{
								item.put("item_data", darray);
							}

							jdata.add(item);														
							
						}else if(name.toLowerCase().startsWith("find06")){
							JSONObject item=new JSONObject();
							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
							item.put("ptype", "06");
							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));							
							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find6_beauties where status=1 order by sort,id", new String[]{});
							JSONArray darray=new JSONArray();
							if(dt!=null && dt.size()>0){
								for(int j=0;j<dt.size();j++){
									JSONObject ditem=new JSONObject();
									Map	dmap=(Map)dt.get(j);
									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
									ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
									ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
									darray.add(ditem);
								}
								item.put("item_data", darray);
							}else{
								item.put("item_data", darray);
							}

							jdata.add(item);												
						}else if(name.toLowerCase().startsWith("find07")){
							JSONObject item=new JSONObject();
							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
							item.put("ptype", "07");
							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));							
							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find7_fiction where status=1 order by sort,id", new String[]{});
							JSONArray darray=new JSONArray();
							if(dt!=null && dt.size()>0){
								for(int j=0;j<dt.size();j++){
									JSONObject ditem=new JSONObject();
									Map	dmap=(Map)dt.get(j);
									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
									ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
									ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
									darray.add(ditem);
								}
								item.put("item_data", darray);
							}else{
								item.put("item_data", darray);
							}

							jdata.add(item);													
//						}else if(name.toLowerCase().startsWith("find08")){
//							JSONObject item=new JSONObject();
//							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
//							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
//							item.put("ptype", "08");
//							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));							
//							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
//							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find8_joke where status=1 order by sort,id", new String[]{});
//							JSONArray darray=new JSONArray();
//							if(dt!=null && dt.size()>0){
//								for(int j=0;j<dt.size();j++){
//									JSONObject ditem=new JSONObject();
//									Map	dmap=(Map)dt.get(j);
//									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
//									ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
//									ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
//									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
//									darray.add(ditem);
//								}
//								item.put("item_data", darray);
//							}else{
//								item.put("item_data", darray);
//							}
//
//							jdata.add(item);													
						}else if(name.toLowerCase().startsWith("find09")){
							JSONObject item=new JSONObject();
							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
							item.put("ptype", "09");
							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));							
							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find9_app where status=1 order by sort,id", new String[]{});
							JSONArray darray=new JSONArray();
							if(dt!=null && dt.size()>0){
								for(int j=0;j<dt.size();j++){
									JSONObject ditem=new JSONObject();
									Map	dmap=(Map)dt.get(j);
									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
									ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
									ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
									darray.add(ditem);
								}
								item.put("item_data", darray);
							}else{
								item.put("item_data", darray);
							}

							jdata.add(item);																
						}else if(name.toLowerCase().startsWith("find10")){
							JSONObject item=new JSONObject();
							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
							item.put("ptype", "10");
							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));							
							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find10_ad_txt where status=1 order by sort,id", new String[]{});
							JSONArray darray=new JSONArray();
							if(dt!=null && dt.size()>0){
								for(int j=0;j<dt.size();j++){
									JSONObject ditem=new JSONObject();
									Map	dmap=(Map)dt.get(j);
									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
									ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
									//ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
									darray.add(ditem);
								}
								item.put("item_data", darray);
							}else{
								item.put("item_data", darray);
							}

							jdata.add(item);								
						}else if(name.toLowerCase().startsWith("find11")){
							JSONObject item=new JSONObject();
							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
							item.put("ptype", "10");
							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));							
							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find11_ad_img where status=1 order by sort,id", new String[]{});
							JSONArray darray=new JSONArray();
							if(dt!=null && dt.size()>0){
								for(int j=0;j<dt.size();j++){
									JSONObject ditem=new JSONObject();
									Map	dmap=(Map)dt.get(j);
									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
									//ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
									ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
									darray.add(ditem);
								}
								item.put("item_data", darray);
							}else{
								item.put("item_data", darray);
							}

							jdata.add(item);															
						}
						
						
												
						}
					resultcode="0";
					msg="success";						

		}catch(Exception e){
			e.printStackTrace();
			
						
		}

	jsondata.put("resultcode", resultcode);
	jsondata.put("msg", msg);
	jsondata.put("data", jdata);
	
	long response_start=System.currentTimeMillis();
	respData(jsondata.toString(),request,response);
	this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
			"response 320:"+jsondata.toString());
	

}			

public void func321() throws Exception{
    
	func321Req obj=(func321Req)request.getAttribute(SysParameter.REQUESTBEAN);

	
	
	JSONObject jsondata=new JSONObject();
	JSONArray jsonarray=new JSONArray();
	String resultcode="-99";
	String msg="system error!";
	String regcode="";
	String image="";
	List ls=null;			
	try{

		 
		 
		this.sysAccountDao.executedSql("insert into tbl_find_logs(uid,account,ptype,did,channel,indate) values(?,?,?,?,?,now())",new String[]{obj.getUID(),obj.getAccount(),obj.getPtype(),obj.getDid(),obj.getChannel()});
					
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


public void func322() throws Exception{
    
    func322Req obj=(func322Req)request.getAttribute(SysParameter.REQUESTBEAN);

	JSONObject jsondata=new JSONObject();
	JSONArray jsonarray=new JSONArray();
	String resultcode="-99";
	String msg="request error!";
	List ls=null;
	String price="0";
	String qty="0";
	String ssid_keys="";
	DecimalFormat df = new DecimalFormat("0.00");  
		
	String name="";
	String uid=obj.getUID();
	String account=obj.getAccount();
	Map[] find=null;
	JSONArray jdata=new JSONArray();

		try{


					ls=this.sysAccountDao.getData("SELECT * FROM tbl_wifi_parameter where name like 'find08%' order by name", new String[]{});

					for(int i=0;i<ls.size();i++){
						Map	map=(Map)ls.get(i);
						name=(map.get("name")==null?"":map.get("name").toString());

						if(name.toLowerCase().startsWith("find08")){
							JSONObject item=new JSONObject();
							item.put("state", (map.get("value")==null?"0":map.get("value").toString()));
							item.put("title", (map.get("title")==null?"":map.get("title").toString().substring(3)));
							item.put("ptype", "08");
							item.put("sortid", (map.get("remark")==null?"":map.get("remark").toString()));							
							item.put("link", (map.get("icon")==null?"":map.get("icon").toString()));
							List dt=this.sysAccountDao.getData("SELECT * FROM tbl_find8_joke where status=1 order by sort,id", new String[]{});
							JSONArray darray=new JSONArray();
							if(dt!=null && dt.size()>0){
								for(int j=0;j<dt.size();j++){
									JSONObject ditem=new JSONObject();
									Map	dmap=(Map)dt.get(j);
									ditem.put("did", (dmap.get("id")==null?"":dmap.get("id").toString()));									
									ditem.put("title", (dmap.get("title")==null?"":dmap.get("title").toString()));
									ditem.put("img_url", (dmap.get("img_url")==null?"":(dmap.get("img_url").toString().startsWith("http")?dmap.get("img_url").toString():PropertyInfo.getValue("access.domain", null)+request.getContextPath()+"/images/find/"+dmap.get("img_url").toString())));
									ditem.put("hyper_url", (dmap.get("hyper_url")==null?"":dmap.get("hyper_url").toString()));
									darray.add(ditem);
								}
								item.put("item_data", darray);
							}else{
								item.put("item_data", darray);
							}

							jdata.add(item);													

						
												
						}
					}
					resultcode="0";
					msg="success";						

		}catch(Exception e){
			e.printStackTrace();
			
						
		}

	jsondata.put("resultcode", resultcode);
	jsondata.put("msg", msg);
	jsondata.put("data", jdata);
	
	long response_start=System.currentTimeMillis();
	respData(jsondata.toString(),request,response);
	this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
			"response 322:"+jsondata.toString());
	

}			


public void func351() throws Exception{
    func351Req obj = (func351Req)request.getAttribute(SysParameter.REQUESTBEAN);
    
    JSONObject jsondata = new JSONObject();
    JSONArray array = new JSONArray();
    
    String resultcode = "0";
    String msg = "success";
    
    String mac = obj.getMac();
    String page = obj.getPage();
    String size = obj.getSize();
   
    try{
        
        int start = Integer.valueOf(page) * Integer.valueOf(size);
        
        String sql = "SELECT a.id, a.mac_addr, a.content, a.image, a.praiseQty, a.useraccount, date_format(a.createtime, '%Y-%m-%d %H:%i:%s') as createtime, b.username, b.growth_value, b.image as userimage from tbl_topic a left join tbl_reg_account b on a.useraccount = b.account where a.mac_addr = ? and a.status = 0 order by a.createtime desc limit " + start + ", "+ size;
        
        List list = sysAccountDao.getData(sql, new String[]{mac});
        if (list == null || list.isEmpty()){
            jsondata.put("resultcode", "0");
            jsondata.put("msg", "success");
            jsondata.put("function", "351");
            jsondata.put("data", array);
            jsondata.put("currtime", Tools.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
            respData(jsondata.toString(), request, response);
            return;
        }
        
        for (int i = 0; i < list.size(); i++){
            Map map=(Map)list.get(i);
            
            String topicId = map.get("id") == null || "".equals(map.get("id").toString())? "0" : map.get("id").toString();
            String content = map.get("content") == null || "".equals(map.get("content").toString()) ? "" : map.get("content").toString();
            String image = map.get("image") == null || "".equals(map.get("image").toString()) ? "" : PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/topic/" + map.get("image").toString();
            String praiseQty = map.get("praiseQty") == null || "".equals(map.get("praiseQty").toString()) ? "0" : map.get("praiseQty").toString();
            String useraccount = map.get("useraccount") == null || "".equals(map.get("useraccount").toString()) ?  "" : map.get("useraccount").toString();
            String createtime = map.get("createtime") == null || "".equals(map.get("createtime").toString()) ? "" : map.get("createtime").toString();
            String username =  map.get("username") == null || "".equals(map.get("username").toString()) ? "" : map.get("username").toString();
            String userimage = map.get("userimage") == null || "".equals(map.get("userimage").toString()) ? "" : (map.get("userimage").toString().startsWith("http:") ? map.get("userimage").toString(): PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/" + map.get("userimage").toString());
            String mac_addr = map.get("mac_addr") == null || "".equals(map.get("mac_addr").toString())? "" : map.get("mac_addr").toString();
            String growth_value = map.get("growth_value") == null || "".equals(map.get("growth_value").toString())? "0" : map.get("growth_value").toString();
            
            JSONObject item = new JSONObject();
            item.put("topicId", topicId);
            item.put("image", image);
            item.put("content", content);
            item.put("useraccount", useraccount);
            item.put("praiseQty", praiseQty);
            item.put("datetime", createtime);
            item.put("username", username);
            item.put("userimage", userimage);
            item.put("mac", mac_addr);
            item.put("growthValue", growth_value);
            array.add(item);
        }
    }catch(Exception e){
        logger.error(e);
        e.printStackTrace();
        
        resultcode = "-99";
        msg = "刷新失败";
    }
    

    
    jsondata.put("resultcode", resultcode);
    jsondata.put("msg", msg);
    jsondata.put("currtime", Tools.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    jsondata.put("data", array);
    
    long response_start=System.currentTimeMillis();
    respData(jsondata.toString(),request,response);
    this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
            "response 351:"+jsondata.toString());
}

public void func352() throws Exception{
    func352Req obj = (func352Req)request.getAttribute(SysParameter.REQUESTBEAN);
    JSONObject jsondata = new JSONObject();
    
    String resultcode = "0";
    String msg = "留言成功";
    
    try{
        String content = obj.getContent();
        String mac = obj.getMac();
        String useraccount = obj.getUseraccount();
        if (useraccount.contains(":") && useraccount.split(":").length > 1){
            useraccount = useraccount.split(":")[1];
        }
        
        String ssid = obj.getSsid();
        String image = "";
        String smallimage = "";
        
        double lng = obj.getLng();
        double lat = obj.getLat();
        String gps_addr = obj.getGps_addr();
        String timestamp = Tools.date2String(new Date(), "yyyyMMddHHmmss");
        String uuid = java.util.UUID.randomUUID().toString();
        
        if (StringUtils.isNotEmpty(obj.getImage())){
            BASE64Decoder base64d = new BASE64Decoder();
            byte[] buf = base64d.decodeBuffer(obj.getImage());
            image = useraccount + "_big_" + timestamp + "_" + uuid + ".jpg";
            FileOutputStream fos = null;
            
            try{
                fos = new java.io.FileOutputStream(request.getRealPath("/").replaceAll("\\\\", "/")+"/images/user/topic/"+ image);                
                fos.write(buf);
            }finally{
                if (null != fos){
                    fos.close();
                }
            }
        }
        
        if (StringUtils.isNotEmpty(obj.getSmallimage())){
            
            BASE64Decoder base64d = new BASE64Decoder();
            byte[] buf = base64d.decodeBuffer(obj.getSmallimage());
            smallimage = useraccount + "_small_" + timestamp + "_" + uuid + ".jpg";
            FileOutputStream fos = null;
            
            try{
                fos = new java.io.FileOutputStream(request.getRealPath("/").replaceAll("\\\\", "/")+"/images/user/topic/"+ smallimage);                
                fos.write(buf);
            }finally{
                if (null != fos){
                    fos.close();
                }
            }
        }
        
        String sql = "INSERT INTO tbl_topic(mac_addr, ssid, content, image, smallimage, status, praiseQty, useraccount, createtime, lat, lng, gps_addr) values(?, ?, ?, ?, ?, 0, 0, ?, now(), ?, ?, ?)";
        sysAccountDao.executedSql(sql, new String[]{mac, ssid, content, image, smallimage, useraccount, lat+"", lng+"", gps_addr});
    }catch(Exception e){
        logger.error(e);
        e.printStackTrace();
        
        resultcode="-99";
        msg="留言失败";
    }
    
    jsondata.put("resultcode", resultcode);
    jsondata.put("msg", msg);
    
    long response_start=System.currentTimeMillis();
    respData(jsondata.toString(),request,response);
    this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
            "response 352:"+jsondata.toString());
}

public void func353() throws Exception{
    func353Req obj = (func353Req)request.getAttribute(SysParameter.REQUESTBEAN);
    JSONObject jsondata = new JSONObject();
    JSONArray array = new JSONArray();
    
    String resultcode = "0";
    String msg = "加载成功";
    
    try{
        
        String mac = obj.getMac();
        String topicId = obj.getTopicId();
        
        String topicSQL = "select a.id, a.mac_addr, a.content, a.image, a.praiseQty, a.useraccount, date_format(a.createtime, '%Y-%m-%d %H:%i:%s') as createtime, b.username, b.growth_value, b.image as userimage from tbl_topic a left join tbl_reg_account b on a.useraccount = b.account where a.status = 0 and a.id = ?";
        
        List list = sysAccountDao.getData(topicSQL, new String[]{topicId});
        if (list != null && !list.isEmpty()){            
            Map map = (Map)list.get(0);
            
            String content =  map.get("content") != null && !"".equals(map.get("content").toString()) ? map.get("content").toString() : "";
            String image = map.get("image") != null && !"".equals(map.get("image").toString()) ? PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/topic/" + map.get("image").toString() : "";
            String praiseQty = map.get("praiseQty") != null && !"".equals(map.get("praiseQty").toString()) ? map.get("praiseQty").toString() : "0";
            String useraccount = map.get("useraccount") != null && !"".equals(map.get("useraccount").toString())? map.get("useraccount").toString() : "";
            String createtime = map.get("createtime") != null && !"".equals(map.get("createtime").toString()) ? map.get("createtime").toString(): "";
            String username =  map.get("username") != null && !"".equals(map.get("username").toString()) ? map.get("username").toString(): "";
            String userimage = map.get("userimage") == null || "".equals(map.get("userimage").toString()) ? "" : (map.get("userimage").toString().startsWith("http:") ? map.get("userimage").toString(): PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/" + map.get("userimage").toString());
            String mac_addr =  map.get("mac_addr") != null && !"".equals(map.get("mac_addr").toString()) ? map.get("mac_addr").toString() : "";
            String growth_value =  map.get("growth_value") != null && !"".equals(map.get("growth_value").toString()) ? map.get("growth_value").toString() : "0";
            
            jsondata.put("topicId", topicId);
            jsondata.put("useraccount", useraccount);
            jsondata.put("username", username);
            jsondata.put("content", content);
            jsondata.put("image", image);
            jsondata.put("userimage", userimage);
            jsondata.put("datetime", createtime);
            jsondata.put("praiseQty", praiseQty);
            jsondata.put("mac", mac_addr);
            jsondata.put("growthValue", growth_value);
        } else {
            jsondata.put("resultcode", "99");
            jsondata.put("msg", "request error!");
            jsondata.put("function", "353");
            jsondata.put("currtime", Tools.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
            respData(jsondata.toString(), request, response);
            return;
        }
        
        String page = obj.getPage();
        String size = obj.getSize();
        int start = Integer.valueOf(page) * Integer.valueOf(size);
        
        String replySQL = "select a.id, b.username, a.useraccount, b.image as userimage, b.growth_value, date_format(a.createtime, '%Y-%m-%d %H:%i:%s') as createtime, a.content,  a.type, a.tousername from tbl_reply a left join tbl_reg_account b on a.useraccount = b.account where a.topicId = ? and a.status = 0 order by createtime limit " + start + ", " + size;
        list = sysAccountDao.getData(replySQL, new String[]{topicId});
        if (list != null && !list.isEmpty()){
            for (int i = 0; i < list.size(); i++){
                Map map = (Map)list.get(i);
                String id = map.get("id") != null && !"".equals(map.get("id").toString()) ? map.get("id").toString() : "0";
                String username = map.get("username") != null && !"".equals(map.get("username").toString()) ? map.get("username").toString(): "";
                String useraccount = map.get("useraccount") != null  && !"".equals(map.get("useraccount").toString())? map.get("useraccount").toString() : "";
                String userimage = map.get("userimage") == null || "".equals(map.get("userimage").toString()) ? "" : (map.get("userimage").toString().startsWith("http:") ? map.get("userimage").toString(): PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/" + map.get("userimage").toString());
                String createtime = map.get("createtime") != null && !"".equals(map.get("createtime").toString()) ? map.get("createtime").toString(): "";
                String content =  map.get("content") != null && !"".equals(map.get("content").toString())? map.get("content").toString() : "";
                String type = map.get("type") != null && !"".equals(map.get("type").toString()) ? map.get("type").toString() : "0";
                String tousername = map.get("tousername") != null && !"".equals(map.get("tousername").toString())? map.get("tousername").toString(): "";
                String growth_value =  map.get("growth_value") != null && !"".equals(map.get("growth_value").toString()) ? map.get("growth_value").toString() : "0";
                
                JSONObject item = new JSONObject();
                item.put("replyId", id);
                item.put("username", username);
                item.put("useraccount", useraccount);
                item.put("userimage", userimage);
                item.put("datetime", createtime);
                item.put("content", content);
                item.put("type", type);
                item.put("tousername", tousername);
                item.put("growthValue", growth_value);
                array.add(item);
            }
        }
        
    }catch(Exception e){
        resultcode = "-99";
        msg = "加载失败，请重试";
    }

    
    jsondata.put("resultcode", resultcode);
    jsondata.put("msg", msg);
    jsondata.put("currtime", Tools.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    jsondata.put("data", array);
    
    long response_start=System.currentTimeMillis();
    respData(jsondata.toString(),request,response);
    this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
            "response 353:"+jsondata.toString());
}


public void func354() throws Exception{
    func354Req obj = (func354Req)request.getAttribute(SysParameter.REQUESTBEAN);
    JSONObject jsondata = new JSONObject();
    JSONArray array = new JSONArray();
    
    String resultcode = "0";
    String msg = "回复成功，成长值+0";
    
    try{
        
        String useraccount = obj.getUseraccount();
        if (useraccount.contains(":") && useraccount.split(":").length > 1){
            useraccount = useraccount.split(":")[1];
        }
        
        String type = obj.getType();
        String to = obj.getTo();
        String content = obj.getContent();
        
        
        String tousername = obj.getTousername();
        String touseraccount = obj.getTouseraccount();
        String topicId = obj.getTopicId();
        
        String replySQL = "insert into tbl_reply(content, useraccount, type, topicId, tousername, touseraccount, parentId, status, createtime) values(?, ?, ?, ?, ?, ?, ?, 0, now())";
        
        sysAccountDao.executedSql(replySQL, new String[]{content, useraccount, type, topicId, tousername, touseraccount, to });
    }catch(Exception e){
        e.printStackTrace();
        logger.error(e);
        
        resultcode = "-99";
        msg = "回复失败，请重试";
    }
    
    jsondata.put("resultcode", resultcode);
    jsondata.put("msg", msg);
    
    long response_start=System.currentTimeMillis();
    respData(jsondata.toString(),request,response);
    this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
            "response 354:"+jsondata.toString());
}

public void func355() throws Exception {
    func355Req obj = (func355Req)request.getAttribute(SysParameter.REQUESTBEAN);
    JSONObject jsondata = new JSONObject();
    
    String mac = obj.getMac();
    String useraccount = obj.getUseraccount();
    String topicId = obj.getTopicId();
    
    String deleteSQL = "update tbl_topic set status = 1 where mac_addr = ? and useraccount = ? and id = ?";
    
    try{            
        sysAccountDao.executedSql(deleteSQL, new String[]{mac, useraccount, topicId});
        jsondata.put("resultcode", "0");
        jsondata.put("msg", "success");
    }catch(Exception e){
        e.printStackTrace();
        jsondata.put("resultcode", "99");
        jsondata.put("msg", "request error!");
    }
    
    long response_start=System.currentTimeMillis();
    respData(jsondata.toString(),request,response);
    this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
            "response 355:"+jsondata.toString());
}

public void func356() throws Exception {
    func356Req obj = (func356Req)request.getAttribute(SysParameter.REQUESTBEAN);
    JSONObject jsondata = new JSONObject();
    JSONArray array = new JSONArray();
    
    String topicId = obj.getTopicId();
    String page = obj.getPage();
    String size = obj.getSize();
    
    int start = Integer.valueOf(page) * Integer.valueOf(size);
    String sql = "SELECT a.id, a.content, a.useraccount, date_format(a.createtime, '%Y-%m-%d %H:%i:%s') as createtime, a.tousername, a.type, a.parentId, b.username, b.growth_value, b.image as userimage from tbl_reply a left join tbl_reg_account b on a.useraccount = b.account where a.topicId = ? and a.status = 0 order by a.createtime desc limit " + start + ", " + size;
    
    List list = sysAccountDao.getData(sql, new String[]{topicId});
    if (list == null || list.isEmpty()){
        jsondata.put("resultcode", "0");
        jsondata.put("msg", "success");
        jsondata.put("function", "356");
        jsondata.put("data", array);
        jsondata.put("currtime", Tools.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        respData(jsondata.toString(), request, response);
        return;
    }
    
    for (int i = 0; i < list.size(); i++){
        Map map=(Map)list.get(i);
        
        String replyId = map.get("id") != null && !"".equals(map.get("id").toString())? map.get("id").toString() : "0";
        String username =  map.get("username") != null && !"".equals(map.get("username").toString())? map.get("username").toString(): "";
        String useraccount = map.get("useraccount") != null && !"".equals(map.get("useraccount").toString())? map.get("useraccount").toString() : "";
        String createtime = map.get("createtime") != null && !"".equals(map.get("createtime").toString()) ? map.get("createtime").toString(): "";
        String content = map.get("content") != null && !"".equals(map.get("content").toString())? map.get("content").toString() : "";
        String userimage = map.get("userimage") == null || "".equals(map.get("userimage").toString()) ? "" : (map.get("userimage").toString().startsWith("http:") ? map.get("userimage").toString(): PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/" + map.get("userimage").toString());
        String type = map.get("type") != null && !"".equals(map.get("type").toString())? map.get("type").toString(): "";
        String to = map.get("parentId") != null && !"".equals(map.get("parentId").toString())? map.get("parentId").toString() : "";
        String tousername = map.get("tousername") != null && !"".equals(map.get("tousername").toString())? map.get("tousername").toString() : "";
        String growth_value = map.get("growth_value") != null && !"".equals(map.get("growth_value").toString())? map.get("growth_value").toString() : "0";
        
        JSONObject item = new JSONObject();
        item.put("replyId", replyId);
        item.put("username", username);
        item.put("useraccount", useraccount);
        item.put("datetime", createtime);
        item.put("content", content);
        item.put("type", type);
        item.put("userimage", userimage);
        item.put("to", to);
        item.put("tousername", tousername);
        item.put("growthValue", growth_value);
        array.add(item);
    }
    
    jsondata.put("resultcode", "0");
    jsondata.put("msg", "success");
    jsondata.put("currtime", Tools.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    jsondata.put("data", array);
    
    long response_start=System.currentTimeMillis();
    respData(jsondata.toString(),request,response);
    this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
            "response 356:"+jsondata.toString());
}


public void func357() throws Exception {
    func357Req obj = (func357Req)request.getAttribute(SysParameter.REQUESTBEAN);
    JSONObject jsondata = new JSONObject();
    
    String mac = obj.getMac();
    String useraccount = obj.getUseraccount();
    if (useraccount.contains(":") && useraccount.split(":").length > 1){
        useraccount = useraccount.split(":")[1];
    }
    
    String replyId = obj.getReplyId();
    
    String deleteSQL = "update tbl_reply set status = 1 where useraccount = ? and id = ?";
    
    try{            
        sysAccountDao.executedSql(deleteSQL, new String[]{useraccount, replyId});
        jsondata.put("resultcode", "0");
        jsondata.put("msg", "success");
    }catch(Exception e){
        e.printStackTrace();
        jsondata.put("resultcode", "99");
        jsondata.put("msg", "request error!");
    }
    
    long response_start=System.currentTimeMillis();
    respData(jsondata.toString(),request,response);
    this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
            "response 357:"+jsondata.toString());
    
}

    public void func370() throws Exception {
        func370Req obj = (func370Req)request.getAttribute(SysParameter.REQUESTBEAN);
        JSONObject jsondata = new JSONObject();
        JSONArray array = new JSONArray();
        
        String useraccount = obj.getUseraccount();
        String page = obj.getPage();
        String size = obj.getSize();
        
        int start = Integer.valueOf(page) * Integer.valueOf(size);
        
        String sql = "select a.id, a.ssid, a.mac_addr, b.wifi_gps_address, date_format(a.createtime, '%Y-%m-%d %H:%i:%s') as createtime from tbl_topic a left join tbl_wifi_share_account_logs b on a.mac_addr = b.mac_address where a.useraccount = ? and a.status = 0 order by a.createtime desc limit " + start + ", "+ size;
        
        List list = sysAccountDao.getData(sql, new String[]{useraccount});
        if (list == null || list.isEmpty()){
            jsondata.put("resultcode", "0");
            jsondata.put("msg", "success");
            jsondata.put("function", "356");
            jsondata.put("data", array);
            respData(jsondata.toString(), request, response);
            return;
        }
        
        for (int i = 0; i < list.size(); i++){
            Map map=(Map)list.get(i);
            
            String ssid = map.get("ssid") == null || "".equals(map.get("ssid").toString()) ? "" : map.get("ssid").toString();
            String mac =  map.get("mac_addr") == null || "".equals(map.get("mac_addr").toString()) ? "" : map.get("mac_addr").toString();
            String gps_addr =  map.get("wifi_gps_address") == null || "".equals(map.get("wifi_gps_address").toString()) ? "" : map.get("wifi_gps_address").toString();
            String datetime =  map.get("createtime") == null || "".equals(map.get("createtime").toString()) ? "" : map.get("createtime").toString();
            String topicId =  map.get("id") == null || "".equals(map.get("id").toString()) ? "0" : map.get("id").toString();
            
            JSONObject item = new JSONObject();
            item.put("ssid", ssid);
            item.put("mac", mac);
            item.put("gps_addr", gps_addr);
            item.put("datetime", datetime);
            item.put("topicId", topicId);
            
            array.add(item);
        }
        
        jsondata.put("resultcode", "0");
        jsondata.put("msg", "success");
        
        jsondata.put("data", array);
        
        long response_start=System.currentTimeMillis();
        respData(jsondata.toString(),request,response);
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 370:"+jsondata.toString());
    }
    
    public void func375() throws Exception {
        func375Req obj = (func375Req)request.getAttribute(SysParameter.REQUESTBEAN);
        JSONObject jsondata = new JSONObject();
        JSONArray array = new JSONArray();
        
        String useraccount = obj.getUseraccount();
        if (null == useraccount || "".equals(useraccount)){
            jsondata.put("resultcode", "-99");
            jsondata.put("msg", "服务器正忙，请稍候再试!");
            jsondata.put("function", "375");
            jsondata.put("data", array);
            respData(jsondata.toString(), request, response);
            return;
        }
        
        if (useraccount.contains(":") && useraccount.split(":").length > 1){
            useraccount = useraccount.split(":")[1];
        }

        String myaccount = obj.getMyaccount();
        String isFirst = obj.getIsFirst();
        String page = obj.getPage();
        String size = obj.getSize();
        
        if ("true".equals(isFirst)){
            //查看用户信息
            String selectUserSQL = "select id, username, sex, province, growth_value, city, level, flowers, image as userimage, (select count(*) from tbl_wifi_share_account_logs where reg_account =?) as sharehotspot from tbl_reg_account where account =?";
            List list = sysAccountDao.getData(selectUserSQL, new String[]{useraccount, useraccount});
            if (list == null || list.isEmpty()){
                jsondata.put("resultcode", "-99");
                jsondata.put("msg", "request error!");
                jsondata.put("function", "375");
                jsondata.put("data", array);
                respData(jsondata.toString(), request, response);
                return;
            }
            
            Map map = (Map)list.get(0);
            String uid = map.get("id") != null ? map.get("id").toString() : "";
            String username = map.get("username") != null ? map.get("username").toString() : "";
            String sex = map.get("sex") != null ? map.get("sex").toString() : "";
            String province = map.get("province") != null ? map.get("province").toString() : "";
            String city = map.get("city") != null ? map.get("city").toString() : "";
            String level = map.get("level") != null ? map.get("level").toString() : "1";
            String flowers = map.get("flowers") != null ? map.get("flowers").toString() : "0";
            String userimage = map.get("userimage") == null || "".equals(map.get("userimage").toString()) ? "" : (map.get("userimage").toString().startsWith("http:") ? map.get("userimage").toString(): PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/" + map.get("userimage").toString());
            String sharehotspot = map.get("sharehotspot") != null ? map.get("sharehotspot").toString() : "0";
            String growth_value = map.get("growth_value") != null ? map.get("growth_value").toString() : "0";
            
            jsondata.put("id", uid);
            jsondata.put("nickname", username);
            jsondata.put("userimage", userimage);
            jsondata.put("province", province);
            jsondata.put("city", city);
            jsondata.put("sex", sex);
            jsondata.put("level", level);
            jsondata.put("sharehotspot", sharehotspot);
            jsondata.put("flower", flowers);  
            jsondata.put("growthValue", growth_value);
            jsondata.put("isChat", "true");//是否可私信
            
            if (null == myaccount || "".equals(myaccount) || !useraccount.equals(myaccount)){
                jsondata.put("isDisplayChatBtn", "true");//是否显示私信按钮
            }
            
            if (useraccount.equals(myaccount)){
                jsondata.put("isDisplayChatBtn", "false");//是否显示私信按钮
            }
        }

        int start = Integer.valueOf(page) * Integer.valueOf(size);
        
        String sql = "SELECT id, ssid, content, image, praiseQty, date_format(createtime, '%Y-%m-%d %H:%i:%s') as createtime from tbl_topic where useraccount = ? and status = 0 order by createtime desc limit " + start + ", "+ size;
        
        List list = sysAccountDao.getData(sql, new String[]{useraccount});
        if (list != null && !list.isEmpty()){
            for (int i = 0; i < list.size(); i++){
                Map map=(Map)list.get(i);
                
                String ssid = map.get("ssid") == null || "".equals(map.get("ssid").toString()) ? "" : map.get("ssid").toString();
                String topicId = map.get("id") != null ? map.get("id").toString() : "";
                String content = map.get("content") == null || "".equals(map.get("content").toString()) ? "" : map.get("content").toString();
                String image = map.get("image") == null || "".equals(map.get("image").toString()) ? "" : PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/topic/" + map.get("image").toString();
                String praiseQty = map.get("praiseQty") == null || "".equals(map.get("praiseQty").toString()) ? "0" : map.get("praiseQty").toString();
                String createtime = map.get("createtime") == null || "".equals(map.get("createtime").toString()) ? "" : map.get("createtime").toString();

                JSONObject item = new JSONObject();
                item.put("ssid", ssid);
                item.put("topicId", topicId);
                item.put("image", image);
                item.put("content", content);
                item.put("praiseQty", praiseQty);
                item.put("datetime", createtime);
                array.add(item);
            }
        }
        
        jsondata.put("resultcode", "0");
        jsondata.put("msg", "success");
        jsondata.put("data", array);
        
        long response_start=System.currentTimeMillis();
        respData(jsondata.toString(),request,response);
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 375:"+jsondata.toString());
    }
    
    
    public void func378() throws Exception{
        func378Req obj = (func378Req)request.getAttribute(SysParameter.REQUESTBEAN);
        JSONObject jsondata = new JSONObject();
        JSONArray array = new JSONArray();
        
        String account = obj.getMyaccount();
        String page = obj.getPage();
        String size = obj.getSize();
        
        int start = Integer.valueOf(page) * Integer.valueOf(size);
        
        //String sql = "select u.image as userimage, u.id as userId,  u.username, b.topicId, b.content, date_format(b.createtime, '%Y-%m-%d %H:%i:%s') as createtime from tbl_reply b left join tbl_reg_account u on b.useraccount = u.account  where b.topicId in (select id from tbl_topic where useraccount = '18673356507')";
        //sql = "select   b.topicId, b.content, date_format(b.createtime, '%Y-%m-%d %H:%i:%s') as createtime from tbl_reply b left join tbl_topic t on b.topicId = t.id where t.useraccount = '18673356507'";
        String sql = "select u.image as userimage, u.id as userId,  u.username, b.topicId, b.content, date_format(b.createtime, '%Y-%m-%d %H:%i:%s') as createtime from tbl_reply b left join tbl_reg_account u on b.useraccount = u.account  where b.useraccount = ? order by createtime desc limit " + start + ", "+ size;
        List list = sysAccountDao.getData(sql, new String[]{account});
        if (list == null || list.isEmpty()){
            jsondata.put("resultcode", "0");
            jsondata.put("msg", "success");
            jsondata.put("function", "378");
            jsondata.put("data", array);
            respData(jsondata.toString(), request, response);
            return;
        }
        
        for (int i = 0; i < list.size(); i++){
            
            Map map = (Map)list.get(i);
            String userId = map.get("userId") != null ? map.get("userId").toString() : "";
            String userimage = map.get("userimage") == null || "".equals(map.get("userimage").toString()) ? "" : (map.get("userimage").toString().startsWith("http:") ? map.get("userimage").toString(): PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/" + map.get("userimage").toString());
            String username = map.get("username") != null ? map.get("username").toString() : "";
            String topicId = map.get("topicId") != null ? map.get("topicId").toString() : "";
            String content = map.get("content") != null ? map.get("content").toString() : "";
            String createtime = map.get("createtime") == null || "".equals(map.get("createtime").toString()) ? "" : map.get("createtime").toString();
            
            JSONObject item = new JSONObject();
            item.put("userId", userId);
            item.put("userimage", userimage);
            item.put("username", username);
            item.put("topicId", topicId);
            item.put("content", content);
            item.put("datetime", createtime);
            
            array.add(item);
        }
       
        jsondata.put("resultcode", "0");
        jsondata.put("msg", "success");
        jsondata.put("data", array);
        
        long response_start=System.currentTimeMillis();
        respData(jsondata.toString(),request,response);
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 378:"+jsondata.toString());
        
    }
    
    public void func380() throws Exception{
        func380Req obj = (func380Req)request.getAttribute(SysParameter.REQUESTBEAN);
        JSONObject jsondata = new JSONObject();
        JSONArray array = new JSONArray();
        
        String account = obj.getMyuseraccount();
        String page = obj.getPage();
        String size = obj.getSize();
        
        double mylat = obj.getMylat();
        double mylng = obj.getMylng();
        
        int start = Integer.valueOf(page) * Integer.valueOf(size);
        
        int radius = Integer.valueOf(SysParameter.getInstatnce().getParams().get("topic_radius").toString());
        Location[] locations = LatitudeLontitudeUtil.getRectangle4Point(obj.getMylat(), obj.getMylng(), radius);
        
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("leftTop", locations[2].getLatitude());
//        params.put("rightTop", locations[0].getLatitude());
//        params.put("leftBottom", locations[1].getLongitude());
//        params.put("rightBottom", locations[0].getLongitude());
        
        String sql = "SELECT a.id, a.image, a.content, a.useraccount, date_format(a.createtime, '%Y-%m-%d %H:%i:%s') as createtime, a.ssid, a.gps_addr, b.image as userimage, b.username FROM tbl_topic a  LEFT JOIN tbl_reg_account b on a.useraccount = b.account WHERE a.lat > " + locations[2].getLatitude() + " AND a.lat < " + locations[0].getLatitude() + " AND a.lng > " + locations[1].getLongitude()+ " AND a.lng < " + locations[0].getLongitude() + " AND a.status != 0 order by a.createtime desc limit " + start + ", "+ size;
        List list = sysAccountDao.getData(sql, new String[]{});
        if (list == null || list.isEmpty()){
            jsondata.put("resultcode", "0");
            jsondata.put("msg", "success");
            jsondata.put("function", "380");
            jsondata.put("data", array);
            respData(jsondata.toString(), request, response);
            return;
        }
        
        for (int i = 0; i < list.size(); i++){
            Map map = (Map)list.get(i);
            String topicId = map.get("id") != null ? map.get("id").toString() : "";
            String image = map.get("image") == null || "".equals(map.get("image").toString()) ? "" : PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/topic/" + map.get("image").toString();
            String content = map.get("content") != null ? map.get("content").toString() : "";
            String useraccount = map.get("useraccount") != null && !"".equals(map.get("useraccount").toString())? map.get("useraccount").toString() : "";
            String createtime = map.get("createtime") == null || "".equals(map.get("createtime").toString()) ? "" : map.get("createtime").toString();
            String ssid = map.get("ssid") == null || "".equals(map.get("ssid").toString()) ? "" : map.get("ssid").toString();
            String gps_addr = map.get("gps_addr") == null || "".equals(map.get("gps_addr").toString()) ? "" : map.get("gps_addr").toString();
            String userimage = map.get("userimage") == null || "".equals(map.get("userimage").toString()) ? "" : (map.get("userimage").toString().startsWith("http:") ? map.get("userimage").toString(): PropertyInfo.getValue("access.domain", null) + request.getContextPath() + "/images/user/" + map.get("userimage").toString());
            String username = map.get("username") != null ? map.get("username").toString() : "";
            
            JSONObject item = new JSONObject();
            item.put("userimage", userimage);
            item.put("username", username);
            item.put("useraccount", useraccount);
            item.put("topicId", topicId);
            item.put("ssid", ssid);
            item.put("gps_addr", gps_addr);
            item.put("content", content);
            item.put("datetime", createtime);
            
            array.add(item);
        }
        
        jsondata.put("resultcode", "0");
        jsondata.put("msg", "success");
        jsondata.put("data", array);
        
        long response_start=System.currentTimeMillis();
        respData(jsondata.toString(),request,response);
        this.logger.info("<"+(System.currentTimeMillis()-obj.getRequest_start())+","+(System.currentTimeMillis()-obj.getRequest_forward())+","+(System.currentTimeMillis()-response_start)+">"+
                "response 380:"+jsondata.toString());
    }
}

