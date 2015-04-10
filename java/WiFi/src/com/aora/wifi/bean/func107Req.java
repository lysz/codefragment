package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func107Req extends requestBean{



	private String UID=null;
	private String Username=null;				
	
	@Override
	public void parse(JSONObject json) {
		// TODO Auto-generated method stub
		if(json!=null && json.size()>0){
			this.setPara_function(json.getString("function"));
			
			UID=json.getString("uid");
			 this.setPara_uid(UID);
			Username=json.getString("username");
			Username=this.trimUserName(Username); 

		}			
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uid) {
		UID = uid;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	
	
}
