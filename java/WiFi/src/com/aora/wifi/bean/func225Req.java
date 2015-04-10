package com.aora.wifi.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class func225Req extends requestBean{
	
	private String UID=null;

	private String account=null;
	private String longitude=null;
	private String latitude=null;
	private String city=null;
	private List lsdata=new ArrayList();

	
	
	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("225");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("account")?"":json.getString("account"));
			longitude=(!json.containsKey("longitude")?"":json.getString("longitude"));
			latitude=(!json.containsKey("latitude")?"":json.getString("latitude"));
			city=(!json.containsKey("city")?"":json.getString("city"));
			JSONArray data=(JSONArray)json.get("data");
			if(data!=null && data.size()>0){
				
				for(int i=0;i<data.size();i++){
					JSONObject item=(JSONObject)data.get(i);
					Map mp=new HashMap();
					mp.put("wifi_mac", item.get("wifi_mac"));
					mp.put("wifi_name", item.get("wifi_name"));
					mp.put("wifi_pass", item.get("wifi_pass"));
					mp.put("wifi_encrypt_method", (!item.containsKey("wifi_encrypt_method")?"":item.getString("wifi_encrypt_method")));
					lsdata.add(mp);
				}
				

			}
			
		}
		
	}



	public String getUID() {
		return UID;
	}



	public void setUID(String uid) {
		UID = uid;
	}



	public String getAccount() {
		return account;
	}



	public void setAccount(String account) {
		this.account = account;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	public String getLatitude() {
		return latitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public List getLsdata() {
		return lsdata;
	}



	public void setLsdata(List lsdata) {
		this.lsdata = lsdata;
	}

}