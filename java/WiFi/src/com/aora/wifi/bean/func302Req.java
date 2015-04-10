package com.aora.wifi.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.aora.wifi.util.Tools;

public class func302Req extends requestBean{
	
	private String UID=null;
	
	private String account=null;

	private String country=null;
	private String country_code=null;

	
	
	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("302");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("username")?"":json.getString("username"));
			country=(!json.containsKey("country")?"":json.getString("country"));
			country_code=(!json.containsKey("country_code")?"":json.getString("country_code"));
			
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

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}


	
	
	

}
