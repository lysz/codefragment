package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func103Req extends requestBean{

	
	private String uid;
	
	private String wifi_mac;
	private String wifi_acc;
	private String wifi_type;
	private String username;
	
	
	@Override
	public void parse(JSONObject json) {
		// TODO Auto-generated method stub

		if(json!=null && json.size()>0){
			this.setPara_function(json.getString("function"));
			username=(!json.containsKey("username")?"":json.getString("username"));	
			username=this.trimUserName(username);			
			
			uid=json.getString("uid");
			this.setPara_uid(uid);
			wifi_mac=json.getString("mac");
			wifi_acc=json.getString("acc");
			wifi_type=(!json.containsKey("name")?"":json.getString("name"));
			if(wifi_type==null|| wifi_type.length()==0){
				wifi_type=(!json.containsKey("type")?"":json.getString("type"));
			}
			if(wifi_type.equalsIgnoreCase("cmcc-web")){
				wifi_type="CMCC";
			}
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


	public String getWifi_acc() {
		return wifi_acc;
	}


	public void setWifi_acc(String wifi_acc) {
		this.wifi_acc = wifi_acc;
	}


	public String getWifi_type() {
		return wifi_type;
	}


	public void setWifi_type(String wifi_type) {
		this.wifi_type = wifi_type;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	
	
	
}
