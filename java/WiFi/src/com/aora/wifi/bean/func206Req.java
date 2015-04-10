package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func206Req  extends requestBean{

	private String UID=null;
	
	private String logout_account=null;
	private String logout_password=null;
	public String getUID() {
		return UID;
	}
	public void setUID(String uid) {
		UID = uid;
	}
	public String getLogout_account() {
		return logout_account;
	}
	public void setLogout_account(String logout_account) {
		this.logout_account = logout_account;
	}
	public String getLogout_password() {
		return logout_password;
	}
	public void setLogout_password(String logout_password) {
		this.logout_password = logout_password;
	}
	
	@Override
	public void parse(JSONObject json) {
		// TODO Auto-generated method stub
		
		if(json!=null && json.size()>0){		
			this.setPara_function("206");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			logout_account=json.getString("username");
			logout_account=this.trimUserName(logout_account)	;					
			logout_password=json.getString("password");
		}		
		
	}
	
	
	
}
