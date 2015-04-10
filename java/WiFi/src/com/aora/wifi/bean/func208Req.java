package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func208Req  extends requestBean {
	private String UID=null;
	private String account=null;
	private String effective_days=null;

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

	public String getEffective_days() {
		return effective_days;
	}

	public void setEffective_days(String effective_days) {
		this.effective_days = effective_days;
	}

	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("208");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=json.getString("account");
			effective_days=json.getString("effective_days");
		}
		
	}

}
