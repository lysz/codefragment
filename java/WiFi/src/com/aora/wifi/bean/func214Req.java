package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func214Req extends requestBean {
	private String UID=null;
	private String account=null;
	private String alipay_account=null;
	private String chi_name=null;
	private int money=0;
	
	private String groupid=null;
	private String login_type=null;		
	
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

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}

	public String getChi_name() {
		return chi_name;
	}

	public void setChi_name(String chi_name) {
		this.chi_name = chi_name;
	}
	

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
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
		if(json!=null && json.size()>0){		
			this.setPara_function("214");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=json.getString("username");
			alipay_account=json.getString("alipay_account");
			chi_name=json.getString("chi_name");
			
			Double tmp_money=Double.parseDouble(json.containsKey("money")?json.get("money").toString():"0");
			
			
			money= new java.math.BigDecimal(tmp_money).multiply(new java.math.BigDecimal(100D)).setScale(0, java.math.BigDecimal.ROUND_HALF_UP).intValue();

			if(account.length()>0 && account.split(":").length==3){
				groupid=account.split(":")[0];
				login_type=account.split(":")[2];
				account=account.split(":")[1];
				
			}else{
				login_type="";
				groupid="";
				
			}		
		}
		
	}

}
