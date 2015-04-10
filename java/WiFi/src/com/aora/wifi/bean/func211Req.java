package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func211Req  extends requestBean {
	private String UID=null;
	private String account=null;
	private String ask=null;
	private String contact=null;

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
	
	public String getAsk() {
		return ask;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("211");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("account")?"":json.getString("account"));
			if(account.length()==0){
				account=(!json.containsKey("username")?"":json.getString("username"));
			}
			ask=json.getString("ask");
			contact=json.getString("contact");
			account=this.trimUserName(account);
		}
		
	}

}
