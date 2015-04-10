package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func109Req extends requestBean{

	private String UID=null;
	private String City=null;

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
			
			this.setPara_function("109");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			City=json.getString("city");
		}
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}
	
	
	
}
