package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func221Req extends requestBean {
	private String UID=null;

	public String getUID() {
		return UID;
	}

	public void setUID(String uid) {
		UID = uid;
	}

	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("221");
			UID=json.getString("uid");
			this.setPara_uid(UID);
		}
		
	}
}
