package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func205Req extends requestBean{

	private String UID=null;
	
	private String reset_account=null;
	private String reset_password=null;
	private String reset_code=null;
	public String getUID() {
		return UID;
	}
	public void setUID(String uid) {
		UID = uid;
	}
	public String getReset_account() {
		return reset_account;
	}
	public void setReset_account(String reset_account) {
		this.reset_account = reset_account;
	}
	public String getReset_password() {
		return reset_password;
	}
	public void setReset_password(String reset_password) {
		this.reset_password = reset_password;
	}
	public String getReset_code() {
		return reset_code;
	}
	public void setReset_code(String reset_code) {
		this.reset_code = reset_code;
	}
	
	@Override
	public void parse(JSONObject json) {
		// TODO Auto-generated method stub
		
		if(json!=null && json.size()>0){		
			this.setPara_function("205");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			reset_account=json.getString("username");
						
			
			reset_password=json.getString("password");
			reset_code=json.getString("code");
		}		
		
	}
	
	
	
}
