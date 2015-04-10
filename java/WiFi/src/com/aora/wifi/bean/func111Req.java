package com.aora.wifi.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class func111Req extends requestBean{
	
	private String UID=null;



	private List lsdata=new ArrayList();
	
	private Map Cmcc=null;
	private Map Chinanet=null;
	
	private String mac_string="";
	
	public void parse(JSONObject json) {
		if(json!=null && json.size()>0){
	
			this.setPara_function("111");
			UID=json.getString("uid");
			this.setPara_uid(UID);
			
			JSONArray data=(JSONArray)json.get("data");
			if(data!=null && data.size()>0){
				
				for(int i=0;i<data.size();i++){
					JSONObject item=(JSONObject)data.get(i);
					Map mp=new HashMap();
					mp.put("wifi_mac", item.get("mac"));
						
					mac_string+=(mp.get("wifi_mac")==null?"":"'"+mp.get("wifi_mac").toString()+"',");
					lsdata.add(mp);

				}
				
				if(mac_string.length()>0){
					mac_string=mac_string.substring(0,mac_string.length()-1);
				}
				
			}
		}
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uid) {
		UID = uid;
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

	public void setMac_string(String mac_string) {
		this.mac_string = mac_string;
	}
	
	
}
