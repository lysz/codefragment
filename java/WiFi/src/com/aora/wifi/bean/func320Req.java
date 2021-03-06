package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func320Req extends requestBean{

	private String UID=null;
	
	private String account=null;	


	
	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("320");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("username")?"":json.getString("username"));
			
			account=this.trimUserName(account);
		}
	}

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


}
