package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func203Req extends requestBean{
	
	private String login_account=null;
	private String login_password=null;
	private String UID=null;
	
	//private String openid=null;
	private String nickname=null;

	private String image=null;
	private String sex=null;
	private String city=null;
	private String province=null;
	private String country=null;
	private String privilege=null;
	private String unionid=null;
	private String login_type=null;	
	
	public String getLogin_account() {
		return login_account;
	}
	public void setLogin_account(String login_account) {
		this.login_account = login_account;
	}
	public String getLogin_password() {
		return login_password;
	}
	public void setLogin_password(String login_password) {
		this.login_password = login_password;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uid) {
		UID = uid;
	}
	
	
	

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	@Override
	public void parse(JSONObject json) {
		// TODO Auto-generated method stub
		
		if(json!=null && json.size()>0){		
			this.setPara_function("203");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			login_account=json.getString("username");
			login_password=json.getString("password");
			
			//openid=(!json.containsKey("openid")?"":json.getString("openid"));
			nickname=(!json.containsKey("nickname")?"":json.getString("nickname"));
			image=(!json.containsKey("headimgurl")?"":json.getString("headimgurl"));
			sex=(!json.containsKey("sex")?"":json.getString("sex"));
			city=(!json.containsKey("city")?"":json.getString("city"));
			province=(!json.containsKey("province")?"":json.getString("province"));
			country=(!json.containsKey("country")?"":json.getString("country"));
			privilege=(!json.containsKey("privilege")?"":json.getString("privilege"));			
			unionid=(!json.containsKey("unionid")?"":json.getString("unionid"));
			login_type=(!json.containsKey("login_type")?"1":json.getString("login_type"));				
			if(login_account.length()>0 && login_account.split(":").length==3){
				login_type=login_account.split(":")[2];
				login_account=login_account.split(":")[1];
				
			}else{
				if(login_type.length()==0){
					login_type="1";
				}
				
			}
			
		}		
		
	}
	
	


}
