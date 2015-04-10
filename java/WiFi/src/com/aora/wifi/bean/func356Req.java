package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func356Req extends requestBean {

    private String uid;
    private String topicId;
    private String mac;
    private String page;
    private String size;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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

            setPara_function("356");
            uid = json.getString("uid");
            setPara_uid(uid);

            mac = json.containsKey("mac") ? json.getString("mac") : "";
            topicId = json.containsKey("topicId") ? json.getString("topicId") : "";
            page = json.containsKey("page") ? json.getString("page") : "0";
            size = json.containsKey("size") ? json.getString("size") : "20";
        }
    }
}
