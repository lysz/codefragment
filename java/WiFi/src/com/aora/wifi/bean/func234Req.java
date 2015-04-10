package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func234Req extends requestBean{
	
	private String UID=null;
	
	private String account=null;

    private String groupid=null;
	private String login_type=null;
	
	private String mac_address=null;
	
	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("234");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("username")?"":json.getString("username"));
			
			mac_address=(!json.containsKey("mac_address")?"":json.getString("mac_address"));
			
			
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

	public String getMac_address() {
		return mac_address;
	}

	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}

}
