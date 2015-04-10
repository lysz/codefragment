package com.aora.wifi.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class func231Req extends requestBean{
	
	private String UID=null;
	
	private String account=null;

    private String groupid=null;
	private String login_type=null;
	private String bind_id=null;
	private String bind_type=null;
	
	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("231");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("username")?"":json.getString("username"));
			
			bind_id=(!json.containsKey("bind_id")?"":json.getString("bind_id"));
			bind_type=(!json.containsKey("bind_type")?"":json.getString("bind_type"));			
			
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

	public String getBind_id() {
		return bind_id;
	}

	public void setBind_id(String bind_id) {
		this.bind_id = bind_id;
	}

	public String getBind_type() {
		return bind_type;
	}

	public void setBind_type(String bind_type) {
		this.bind_type = bind_type;
	}

}
