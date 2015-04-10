package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func102Req  extends requestBean {

	private String uid;
	private String wifi_mac;
	private String wifi_name;
	private String wifi_acc;
	private String wifi_type;
	private String wifi_cookie;
	private String wifi_postdata;
	private String wifi_sessionid;
	private String wifi_logout_url;
	private String longitude;
	private String latitude;
	private String city;
	
	private String groupid;
	private String login_type;
	
	private String username;
	
	
	@Override
	public void parse(JSONObject json) {
		// TODO Auto-generated method stub
		if(json!=null && json.size()>0){
			this.setPara_function(json.getString("function"));
			
			uid=(json.getString("uid")==null?"":json.getString("uid"));
			this.setPara_uid(uid);

			username=(!json.containsKey("username")?"":json.getString("username"));
			//username=this.trimUserName(username);
			
			if(username.length()>0 && username.split(":").length==3){
				groupid=username.split(":")[0];
				login_type=username.split(":")[2];
				username=username.split(":")[1];
				
			}else{
				groupid="";
				login_type="";
			}			

			
			longitude=(!json.containsKey("longitude")?"":json.getString("longitude"));
			latitude=(!json.containsKey("latitude")?"":json.getString("latitude"));
			city=(!json.containsKey("city")?"":json.getString("city"));
			
			wifi_mac=(json.getString("mac")==null?"":json.getString("mac"));
			wifi_name=(json.getString("name")==null?"":json.getString("name"));
			wifi_acc=(json.getString("acc")==null?"":json.getString("acc"));
			wifi_type=(json.getString("type")==null?"":json.getString("type"));
			if(wifi_type.equalsIgnoreCase("cmcc-web")){
				wifi_type="CMCC";
			}
			wifi_cookie=(json.getString("cookie")==null?"":json.getString("cookie"));
			wifi_postdata=(json.getString("postdata")==null?"":json.getString("postdata"));
			wifi_sessionid=(json.getString("sessionid")==null?"":json.getString("sessionid"));
			wifi_logout_url=(json.getString("logout_url")==null?"":json.getString("logout_url"));
		}
		
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


	public String getWifi_cookie() {
		return wifi_cookie;
	}


	public void setWifi_cookie(String wifi_cookie) {
		this.wifi_cookie = wifi_cookie;
	}


	public String getWifi_postdata() {
		return wifi_postdata;
	}


	public void setWifi_postdata(String wifi_postdata) {
		this.wifi_postdata = wifi_postdata;
	}




	public String getWifi_sessionid() {
		return wifi_sessionid;
	}


	public void setWifi_sessionid(String wifi_sessionid) {
		this.wifi_sessionid = wifi_sessionid;
	}


	public String getWifi_logout_url() {
		return wifi_logout_url;
	}


	public void setWifi_logout_url(String wifi_logout_url) {
		this.wifi_logout_url = wifi_logout_url;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getGroupid() {
		return groupid;
	}


	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}


	public String getLogin_type() {
		return login_type;
	}


	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}

	
	
}
