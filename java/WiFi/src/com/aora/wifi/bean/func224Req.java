package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func224Req extends requestBean {
	private String UID=null;
	private String version=null;
	
	public String getUID() {
		return UID;
	}

	public void setUID(String uid) {
		UID = uid;
	}

	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("224");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			version=(!json.containsKey("version")?"":json.getString("version"));
		}
		
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
