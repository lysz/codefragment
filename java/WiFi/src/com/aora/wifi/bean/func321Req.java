package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func321Req extends requestBean{

	private String UID=null;
	
	private String account=null;	
	private String ptype=null;
	private String did=null;


	
	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("321");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("username")?"":json.getString("username"));
			ptype=(!json.containsKey("ptype")?"":json.getString("ptype"));
			did=(!json.containsKey("did")?"":json.getString("did"));
			
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
