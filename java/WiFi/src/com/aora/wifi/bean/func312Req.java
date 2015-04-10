package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func312Req extends requestBean{

	private String UID=null;
	
	private String account=null;	

	private String pageno=null;
	private String pagesize=null;
	
	@Override
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){		
			this.setPara_function("312");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			account=(!json.containsKey("username")?"":json.getString("username"));
			pageno=(!json.containsKey("page_no")?"1":json.getString("page_no"));
			
			pageno=String.valueOf(Integer.parseInt(pageno)-1);
			
			pagesize=(!json.containsKey("page_size")?"20":json.getString("page_size"));

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

	public String getPageno() {
		return pageno;
	}

	public void setPageno(String pageno) {
		this.pageno = pageno;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	

}
