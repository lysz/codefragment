package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func217Req extends requestBean {

    private String UID;
    private String username;
	private String groupid=null;
	private String login_type=null;	    

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

	@Override
    public void parse(JSONObject json) {
        if (json != null && json.size() > 0) {
            this.setPara_function("217");
            UID = json.getString("uid");
            username = json.getString("username");
            this.setPara_uid(UID);
			if(username.length()>0 && username.split(":").length==3){
				groupid=username.split(":")[0];
				login_type=username.split(":")[2];
				username=username.split(":")[1];
				
			}else{
				login_type="";
				groupid="";
				
			}      
        }

    }

}
