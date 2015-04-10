package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func113Req extends requestBean{
	
	private String UID=null;

	private String name=null;
	private String longitude=null;
	private String latitude=null;
	private String city=null;
	private String password=null;
	private String mac =null;
	
	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("113");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			mac=(!json.containsKey("mac")?"":json.getString("mac"));
			name=(!json.containsKey("name")?"":json.getString("name"));
			password=(!json.containsKey("pass")?"":json.getString("pass"));
			longitude=(!json.containsKey("longitude")?"":json.getString("longitude"));
			latitude=(!json.containsKey("latitude")?"":json.getString("latitude"));
			city=(!json.containsKey("city")?"":json.getString("city"));

		}
		
	}



	public String getMac() {
		return mac;
	}



	public void setMac(String mac) {
		this.mac = mac;
	}



	public String getUID() {
		return UID;
	}



	public void setUID(String uid) {
		UID = uid;
	}






	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
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


}
