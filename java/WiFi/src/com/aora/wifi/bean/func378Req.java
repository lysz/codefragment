package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func378Req extends requestBean {

    private String uid;
    private String myaccount;
    private String page;
    private String size;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMyaccount() {
        return myaccount;
    }

    public void setMyaccount(String myaccount) {
        this.myaccount = myaccount;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void parse(JSONObject json) {
        if (null != json && !json.isEmpty()) {

            setPara_function("378");
            uid = json.getString("uid");
            setPara_uid(uid);

            myaccount = json.containsKey("myaccount") ? json.getString("myaccount") : "";
            page = json.containsKey("page") ? json.getString("page") : "0";
            size = json.containsKey("size") ? json.getString("size") : "20";
            
        }
    }

}
