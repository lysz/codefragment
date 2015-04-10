package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func201Req extends requestBean{
	
	private String account=null;
	private String UID=null;

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
		// TODO Auto-generated method stub
		if(json!=null && json.size()>0){		
			this.setPara_function("201");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=json.getString("username");
			try{
				Long.parseLong(account);
			}catch(Exception e){
				account="";
			}
		}
		
	}
	
	
	

}
