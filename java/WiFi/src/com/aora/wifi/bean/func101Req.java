package com.aora.wifi.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class func101Req extends requestBean{

	
	private String UID=null;

	private String Username=null;

	
	private List lsdata=new ArrayList();
	
	private Map Cmcc=null;
	private Map Chinanet=null;
	private String ssid="";
	private String smac="";
	
	private String mac_string="";
	
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){
	
			this.setPara_function("101");
			UID=json.getString("uid");
			this.setPara_uid(UID);

			Username=json.getString("username");
			Username=this.trimUserName(Username);
			
			
			
			JSONArray data=(JSONArray)json.get("data");
			
			if(data!=null && data.size()>0){
				
				for(int i=0;i<data.size();i++){
					JSONObject item=(JSONObject)data.get(i);
					Map mp=new HashMap();
					mp.put("wifi_mac", item.get("mac"));
					mp.put("wifi_name", item.get("name"));
					mp.put("wifi_priority", item.get("priority"));
					ssid=item.get("name").toString();
					smac=item.get("mac").toString();
						
					if(mp.get("wifi_name").toString().equalsIgnoreCase("CMCC")||mp.get("wifi_name").toString().equalsIgnoreCase("CMCC-Web")){
						Cmcc=mp;
						//mp.put("wifi_name","CMCC");
					}else if(mp.get("wifi_name").toString().equalsIgnoreCase("ChinaNet")){
						Chinanet=mp;
					}else{
						mac_string+=(mp.get("wifi_name")==null?"":"'"+mp.get("wifi_mac").toString()+"',");
						lsdata.add(mp);
					}
				}
				
				if(mac_string.length()>0){
					mac_string=mac_string.substring(0,mac_string.length()-1);
				}
				
			}
		}
	}

	public void setMac_string(String mac_string) {
		this.mac_string = mac_string;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uid) {
		UID = uid;
	}



	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	

	public List getLsdata() {
		return lsdata;
	}

	public void setLsdata(List lsdata) {
		this.lsdata = lsdata;
	}

	public Map getCmcc() {
		return Cmcc;
	}

	public void setCmcc(Map cmcc) {
		Cmcc = cmcc;
	}

	public Map getChinanet() {
		return Chinanet;
	}

	public void setChinanet(Map chinanet) {
		Chinanet = chinanet;
	}

	public String getMac_string() {
		return mac_string;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getSmac() {
		return smac;
	}

	public void setSmac(String smac) {
		this.smac = smac;
	}
    

		
	
	
}

