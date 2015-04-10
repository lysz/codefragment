package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func100Req extends requestBean{

	private String UID=null;

	@Override
	public void parse(JSONObject json) {
		// TODO Auto-generated method stub
		
		this.setPara_function("100");
		
		UID=json.getString("uid");
		this.setPara_uid(UID);
		
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uid) {
		UID = uid;
	}

	
}
