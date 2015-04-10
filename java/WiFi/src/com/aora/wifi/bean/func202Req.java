package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func202Req extends requestBean{

	private String reg_account=null;
	private String UID=null;
	private String reg_password=null;
	private String reg_code=null;
	public String getReg_account() {
		return reg_account;
	}
	public void setReg_account(String reg_account) {
		this.reg_account = reg_account;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uid) {
		UID = uid;
	}
	public String getReg_password() {
		return reg_password;
	}
	public void setReg_password(String reg_password) {
		this.reg_password = reg_password;
	}
	public String getReg_code() {
		return reg_code;
	}
	public void setReg_code(String reg_code) {
		this.reg_code = reg_code;
	}
	@Override
	public void parse(JSONObject json) {
		// TODO Auto-generated method stub
		
		if(json!=null && json.size()>0){		
			this.setPara_function("202");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			reg_account=json.getString("username");
			reg_password=json.getString("password");
			reg_code=json.getString("code");
		}		
	}
	
	
}
