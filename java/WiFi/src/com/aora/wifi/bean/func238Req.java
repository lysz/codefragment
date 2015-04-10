package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func238Req extends requestBean{
	
	private String UID=null;
	
	private String account=null;

    private String groupid=null;
	private String login_type=null;
	
	private Double lng=null;
	private Double lat=null;
	
	@Override
	public void parse(JSONObject json) {
		String latitude="0";
		String longitude="0";
		if(json!=null && json.size()>0){		
			this.setPara_function("238");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("username")?"":json.getString("username"));
			
			lat=Double.valueOf(!json.containsKey("latitude")?"0":json.getString("latitude"));
			lng=Double.valueOf(!json.containsKey("longitude")?"0":json.getString("longitude"));
			
			if(account.length()>0 && account.split(":").length==3){
				groupid=account.split(":")[0];
				login_type=account.split(":")[2];
				account=account.split(":")[1];
				
			}else{
				groupid="";
				login_type="";
			}
			
			
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

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getLogin_type() {
		return login_type;
	}

	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}




}
