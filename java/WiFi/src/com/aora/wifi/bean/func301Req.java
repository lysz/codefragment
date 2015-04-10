package com.aora.wifi.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aora.wifi.util.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class func301Req extends requestBean{
	
	private String UID=null;
	
	private String account=null;

	private List lsdata=new ArrayList();
	
	
	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("301");
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
