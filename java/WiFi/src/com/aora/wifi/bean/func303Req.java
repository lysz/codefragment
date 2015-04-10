package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func303Req extends requestBean{

	private String UID=null;
	
	private String account=null;	
	private String country=null;
	private String country_code=null;
	private String order_id=null;
	private String alipay_account=null;
	private String price=null;
	private String qty=null;
	private String amount=null;
	
	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("303");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("username")?"":json.getString("username"));
			country=(!json.containsKey("country")?"":json.getString("country"));
			country_code=(!json.containsKey("country_code")?"":json.getString("country_code"));
			order_id=(!json.containsKey("order_id")?"":json.getString("order_id"));
			alipay_account=(!json.containsKey("alipay_account")?"":json.getString("alipay_account"));
			price=(!json.containsKey("price")?"0":json.getString("price"));
			qty=(!json.containsKey("qty")?"0":json.getString("qty"));
			amount=(!json.containsKey("amount")?"0":json.getString("amount"));
			
			account=this.trimUserName(account);
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
	
	
}
