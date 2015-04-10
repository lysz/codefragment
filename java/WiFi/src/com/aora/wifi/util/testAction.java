package com.aora.wifi.util;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.aora.wifi.action.interfaces.func1xxAction;
import com.aora.wifi.bean.func215Req;

public class testAction {
	
	private final static Logger logger = Logger.getLogger(func1xxAction.class);

	public String func215(func215Req obj) throws Exception{
		
		
		
		JSONObject jsondata=new JSONObject();
		JSONArray jsonarray1=new JSONArray();
		JSONArray jsonarray2=new JSONArray();
		JSONArray jsonarray3=new JSONArray();
		String resultcode="-99";
		String msg="request error!";
		String regcode="";
		String image="";
		List ls=null;			
		try{
			if(true ){
				
				JSONArray jar1=(JSONArray)cachectl.getcontent("215_banner_cywifi-taobao");
				if(jar1==null){

				}else{
					jsonarray1=jar1;
				}
				
				
				
			}else{
				JSONArray jar1=(JSONArray)cachectl.getcontent("215_banner_default");
				if(jar1==null){				

				}else{
					jsonarray1=jar1;
				}
			}
			
			JSONArray jar2=(JSONArray)cachectl.getcontent("215_ad_default");
			if(jar2==null){	

			}else{
				jsonarray2=jar2;
			}
			
			JSONArray jar3=(JSONArray)cachectl.getcontent("215_hotapp_default");
			if(jar3==null){
			

			}else{
				jsonarray3=jar3;
			}
			
			resultcode="0";
			msg="succ";
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		jsondata.put("resultcode", resultcode);
		jsondata.put("msg", msg);
		jsondata.put("promo_data", jsonarray1);
		jsondata.put("ad_data", jsonarray2);
		jsondata.put("game_data", jsonarray3);
		//jsondata.put("function", obj.getPara_function());

		return jsondata.toString();
	}
	
	public static void main(String[] arg){
		String aa="TX_PLANNING_DETAIL_ID,TX_PLANNING_ID,TX_DATE,START_TX_TIME_HR,START_TX_TIME_MIN,END_TX_TIME_HR,END_TX_TIME_MIN,CREATED_DATE,CREATED_BY,UPDATED_DATE,UPDATED_BY,EPISODE_NO,IS_LOCKED,END_TX_TIME_IS_MIDNIGHT,PRE_EMPTED_IS_PRIME,FIRST_TX,PROMO_TIME_SLOT,OVER_DAY_END_TX_TIME_HR,OVER_DAY_END_TX_TIME_MIN,EPISODE_ID";
		String bb="TX_PLANNING_ID,TV_STATION_ID,TV_CHANNEL_ID,PROGRAMME_TYPE_ID,SERIES_ID,IS_PRE_EMPTED,PRE_EMPTED_TITLE,TX_DATE,START_TX_TIME_HR,START_TX_TIME_MIN,END_TX_TIME_HR,END_TX_TIME_MIN,NO_OF_EPISODE,CREATED_DATE,CREATED_BY,UPDATED_DATE,UPDATED_BY,STYLE,IS_WEEKLY,FROM_DAY,TO_DAY,REMARK,PROMO_DURATION_SEC";
		System.out.println(bb.toLowerCase());
	}
}
