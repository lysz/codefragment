package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func114Req  extends requestBean{
	
	private String UID=null;
	private String mac=null;
	private String name=null;
	
	
	@Override
	public void parse(JSONObject json) {
		
		if(json!=null && json.size()>0){		
			this.setPara_function("114");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			mac=json.getString("mac");
			name=json.getString("name");

		}
		
	}


	public String getUID() {
		return UID;
	}


	public void setUID(String uid) {
		UID = uid;
	}


	public String getMac() {
		return mac;
	}


	public void setMac(String mac) {
		this.mac = mac;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	

}
