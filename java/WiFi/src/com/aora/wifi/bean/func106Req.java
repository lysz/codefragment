package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func106Req extends requestBean{

	private String uid;
	private String wifi_mac;
	private String wifi_name;
	
	@Override
	public void parse(JSONObject json) {
		// TODO Auto-generated method stub
		if(json!=null && json.size()>0){
			this.setPara_function(json.getString("function"));
			
			uid=json.getString("uid");
			 this.setPara_uid(uid);
			wifi_mac=json.getString("mac");
			wifi_name=json.getString("name");

		}		
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getWifi_mac() {
		return wifi_mac;
	}

	public void setWifi_mac(String wifi_mac) {
		this.wifi_mac = wifi_mac;
	}

	public String getWifi_name() {
		return wifi_name;
	}

	public void setWifi_name(String wifi_name) {
		this.wifi_name = wifi_name;
	}

	
	
	
}
