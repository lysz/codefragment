package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func216Req extends requestBean {

    private String username;
    private int    qty;
    private int    type;
    private String active_account;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getActive_account() {
        return active_account;
    }

    public void setActive_account(String activeAccount) {
        active_account = activeAccount;
    }

    private String UID = null;

    public String getUID() {
        return UID;
    }

    public void setUID(String uid) {
        UID = uid;
    }

    @Override
    public void parse(JSONObject json) {
        if (json != null && json.size() > 0) {
            this.setPara_function("216");
            UID = json.getString("uid");
            this.setPara_uid(UID);
            
            username = json.getString("username");
            type = json.getInt("type");
            qty = json.getInt("qty");
            active_account = json.getString("active_account");
            username=this.trimUserName(username);
        }

    }

}
