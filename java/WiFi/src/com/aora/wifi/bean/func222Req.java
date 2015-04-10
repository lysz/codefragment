package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func222Req extends requestBean{
	
		private String UID=null;

		private String account=null;
		private String error_description=null;
		private String error_function=null;
		private String longitude=null;
		private String latitude=null;
		private String city=null;
		private String platform=null;
		private String version=null;
		
		
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

		public String getError_description() {
			return error_description;
		}

		public void setError_description(String error_description) {
			this.error_description = error_description;
		}

		public String getError_function() {
			return error_function;
		}

		public void setError_function(String error_function) {
			this.error_function = error_function;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPlatform() {
			return platform;
		}

		public void setPlatform(String platform) {
			this.platform = platform;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		@Override
		public void parse(JSONObject json) {
			if(json!=null && json.size()>0){		
				this.setPara_function("222");
				UID=json.getString("uid");
				this.setPara_uid(UID);
				account=(!json.containsKey("account")?"":json.getString("account"));
				if(account!=null && account.length()==0){
					account=(!json.containsKey("username")?"":json.getString("username"));	
				}
				account=this.trimUserName(account);  				
				error_description=(!json.containsKey("error_description")?"":json.getString("error_description"));
				error_function=(!json.containsKey("error_function")?"":json.getString("error_function"));
				longitude=(!json.containsKey("longitude")?"":json.getString("longitude"));
				latitude=(!json.containsKey("latitude")?"":json.getString("latitude"));
				city=(!json.containsKey("city")?"":json.getString("city"));
				platform=(!json.containsKey("platform")?"":json.getString("platform"));
				version=(!json.containsKey("version")?"":json.getString("version"));
				
			}
			
		}

}
