package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func220Req extends requestBean {
	private String UID=null;
	private String account=null;
	private String version=null;
	private String verifyCode=null;
	
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
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("220");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=json.getString("account");
			version=json.getString("version");
			verifyCode=json.getString("verify_code");
			
		}
		
	}
	
}
