package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func304Req extends requestBean{

	private String UID=null;
	
	private String account=null;	
	private String country=null;
	private String country_code=null;
	private String city=null;

	
	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("304");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("username")?"":json.getString("username"));
			country=(!json.containsKey("country")?"":json.getString("country"));
			country_code=(!json.containsKey("country_code")?"":json.getString("country_code"));
			city=(!json.containsKey("city")?"":json.getString("city"));

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

	public String getCountry() {
		return country;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
