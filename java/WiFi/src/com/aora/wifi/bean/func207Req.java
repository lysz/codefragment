package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func207Req  extends requestBean {

	private String UID=null;
	private String login_account=null;
	private String modify_name=null;
	private String modify_image=null;
	private String modify_city=null;
	private String modify_province=null;
	private String modify_sex=null;
	
	private String groupid=null;
	private String login_type=null;	
	
	public String getUID() {
		return UID;
	}
	public void setUID(String uid) {
		UID = uid;
	}
	public String getLogin_account() {
		return login_account;
	}
	public void setLogin_account(String login_account) {
		this.login_account = login_account;
	}
	public String getModify_name() {
		return modify_name;
	}
	public void setModify_name(String modify_name) {
		this.modify_name = modify_name;
	}

	
	
	
	public String getModify_city() {
		return modify_city;
	}
	public void setModify_city(String modify_city) {
		this.modify_city = modify_city;
	}
	public String getModify_province() {
		return modify_province;
	}
	public void setModify_province(String modify_province) {
		this.modify_province = modify_province;
	}
	public String getModify_sex() {
		return modify_sex;
	}
	public void setModify_sex(String modify_sex) {
		this.modify_sex = modify_sex;
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
	public String getModify_image() {
		return modify_image;
	}
	public void setModify_image(String modify_image) {
		this.modify_image = modify_image;
	}
	@Override
	public void parse(JSONObject json) {
		// TODO Auto-generated method stub
		
		if(json!=null && json.size()>0){		
			this.setPara_function("207");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			login_account=json.getString("username");
			//login_account=this.trimUserName(login_account);			
			
			modify_name=(!json.containsKey("nickname")?"":json.getString("nickname"));
			modify_image=(!json.containsKey("image")?"":json.getString("image"));
			modify_sex=(!json.containsKey("sex")?"":json.getString("sex"));
			modify_city=(!json.containsKey("city")?"":json.getString("city"));
			modify_province=(!json.containsKey("province")?"":json.getString("province"));			
			
			if(login_account.length()>0 && login_account.split(":").length==3){
				groupid=login_account.split(":")[0];
				login_type=login_account.split(":")[2];
				login_account=login_account.split(":")[1];
				
			}else{
				login_type="";
				groupid="";
				
			}						
		}		
		
	}
	
}
