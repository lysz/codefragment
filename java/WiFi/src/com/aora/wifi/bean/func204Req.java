package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func204Req extends requestBean {
	private String UID=null;
	private String account=null;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uid) {
		UID = uid;
	}

	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("204");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=json.getString("account");
		}
		
	}
}
