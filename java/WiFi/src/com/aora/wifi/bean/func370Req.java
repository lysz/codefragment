package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func370Req extends requestBean {

    private String uid;
    private String useraccount;
    private String page;
    private String size;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    @Override
    public void parse(JSONObject json) {
        if (null != json && !json.isEmpty()) {

            setPara_function("370");
            uid = json.getString("uid");
            setPara_uid(uid);

            useraccount = json.containsKey("useraccount") ? json.getString("useraccount") : "";
            page = json.containsKey("page") ? json.getString("page"): "0";
            size = json.containsKey("size") ? json.getString("size"): "20";

        }
    }

}
