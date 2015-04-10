package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func218Req extends requestBean{
	private String UID=null;
	private String account=null;
	private String ad_name=null;
	private String platform=null;
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

	public String getAd_name() {
		return ad_name;
	}

	public void setAd_name(String ad_name) {
		this.ad_name = ad_name;
	}
	
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("218");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=json.getString("account");
			  			
			
			ad_name=json.getString("ad_name");
			platform=(!json.containsKey("platform")?"":json.getString("platform"));
		}
		
	}
	
}
