package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func380Req extends requestBean {

    private String uid;
    private String myuseraccount;
    private double mylat;
    private double mylng;
    private String page;
    private String size;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMyuseraccount() {
        return myuseraccount;
    }

    public void setMyuseraccount(String myuseraccount) {
        this.myuseraccount = myuseraccount;
    }

    public double getMylat() {
        return mylat;
    }

    public void setMylat(double mylat) {
        this.mylat = mylat;
    }

    public double getMylng() {
        return mylng;
    }

    public void setMylng(double mylng) {
        this.mylng = mylng;
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

            setPara_function("380");
            uid = json.getString("uid");
            setPara_uid(uid);

            myuseraccount = json.containsKey("myuseraccount") ? json.getString("myuseraccount") : "";
            mylat = json.containsKey("mylat") ? json.getDouble("mylat") : 0;
            mylng = json.containsKey("mylng") ? json.getDouble("mylng") : 0;
            page = json.containsKey("page") ? json.getString("page") : "0";
            size = json.containsKey("size") ? json.getString("size") : "20";
            
        }
    }

}
