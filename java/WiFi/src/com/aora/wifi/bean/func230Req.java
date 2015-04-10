package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func230Req extends requestBean{
	
	private String account=null;
	private String password=null;
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
	private String groupid=null;
	private String bind_id=null;
	private String bind_type=null;
	
	private String action_code=null;
	

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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
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
			this.setPara_function("230");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=json.getString("username");
			password=json.getString("password");
			
			//openid=(!json.containsKey("openid")?"":json.getString("openid"));
			nickname=(!json.containsKey("nickname")?"":json.getString("nickname"));
			image=(!json.containsKey("headimgurl")?"":json.getString("headimgurl"));
			sex=(!json.containsKey("sex")?"":json.getString("sex"));
			city=(!json.containsKey("city")?"":json.getString("city"));
			province=(!json.containsKey("province")?"":json.getString("province"));
			country=(!json.containsKey("country")?"":json.getString("country"));
			privilege=(!json.containsKey("privilege")?"":json.getString("privilege"));			
			unionid=(!json.containsKey("unionid")?"":json.getString("unionid"));
			bind_type=(!json.containsKey("bind_type")?"":json.getString("bind_type"));
			bind_id=(!json.containsKey("bind_id")?"":json.getString("bind_id"));
			action_code=(!json.containsKey("code")?"":json.getString("code"));
			
			
			if(account.length()>0 && account.split(":").length==3){
				groupid=account.split(":")[0];
				login_type=account.split(":")[2];
				account=account.split(":")[1];
				
			}
			
		}		
		
	}
	public String getAction_code() {
		return action_code;
	}
	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	
}
