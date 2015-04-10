package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func243Req  extends requestBean{

	private String UID=null;
	private String username;
	private String groupid=null;
	private String login_type=null;	    

	private String Tgroupid=null;
	private String Tlogin_type=null;	  
	
	private String talk_id=null;

	
	
	
	public String getUID() {
		return UID;
	}




	public void setUID(String uid) {
		UID = uid;
	}




	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
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




	public String getTgroupid() {
		return Tgroupid;
	}




	public void setTgroupid(String tgroupid) {
		Tgroupid = tgroupid;
	}




	public String getTlogin_type() {
		return Tlogin_type;
	}




	public void setTlogin_type(String tlogin_type) {
		Tlogin_type = tlogin_type;
	}




	public String getTalk_id() {
		return talk_id;
	}




	public void setTalk_id(String talk_id) {
		this.talk_id = talk_id;
	}




	@Override
	public void parse(JSONObject json) {
		// TODO Auto-generated method stub
		
		if(json!=null && json.size()>0){		
			this.setPara_function("243");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			username = json.getString("username");
			talk_id=(!json.containsKey("talk_id")?"":json.getString("talk_id"));
			if(username.length()>0 && username.split(":").length==3){
				groupid=username.split(":")[0];
				login_type=username.split(":")[2];
				username=username.split(":")[1];
				
			}else{
				login_type="";
				groupid="";
				
			}      		
			if(talk_id.length()>0 && talk_id.split(":").length==3){
				Tgroupid=talk_id.split(":")[0];
				Tlogin_type=talk_id.split(":")[2];
				talk_id=talk_id.split(":")[1];
				
			}else{
				Tlogin_type="";
				Tgroupid="";
				
			}      		
			
			
		}		
		
	}
		

}

