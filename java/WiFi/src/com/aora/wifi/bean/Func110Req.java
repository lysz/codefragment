package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class Func110Req extends requestBean {

    private String mac_address;

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String macAddress) {
        mac_address = macAddress;
    }

    @Override
    public void parse(JSONObject json) {
        if (json != null && json.size() > 0) {

            this.setPara_function("110");
            String uid=(!json.containsKey("uid")?"":json.getString("uid"));
          	this.setPara_uid(uid);
            
            mac_address = json.getString("mac");
        }
    }

}
