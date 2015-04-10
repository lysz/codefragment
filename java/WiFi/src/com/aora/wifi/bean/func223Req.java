package com.aora.wifi.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class func223Req  extends requestBean{
	
	private String UID=null;

	private String account=null;
	private String collect_description=null;
	private String collect_function=null;
	private String longitude=null;
	private String latitude=null;
	private String city=null;
	private String platform=null;
	private String version=null;
	
	
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



	public String getCollect_description() {
		return collect_description;
	}

	public void setCollect_description(String collect_description) {
		this.collect_description = collect_description;
	}

	public String getCollect_function() {
		return collect_function;
	}

	public void setCollect_function(String collect_function) {
		this.collect_function = collect_function;
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("223");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("account")?"":json.getString("account"));
			if(account!=null && account.length()==0){
				account=(!json.containsKey("username")?"":json.getString("username"));	
			}
			account=this.trimUserName(account);			
			
			collect_description=(!json.containsKey("collect_description")?"":json.getString("collect_description"));
			collect_function=(!json.containsKey("collect_function")?"":json.getString("collect_function"));
			longitude=(!json.containsKey("longitude")?"":json.getString("longitude"));
			latitude=(!json.containsKey("latitude")?"":json.getString("latitude"));
			city=(!json.containsKey("city")?"":json.getString("city"));
			platform=(!json.containsKey("platform")?"":json.getString("platform"));
			version=(!json.containsKey("version")?"":json.getString("version"));
			
		}
		
	}

}
