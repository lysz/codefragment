package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func355Req extends requestBean {

    private String uid;
    private String mac;
    private String useraccount;
    private String topicId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @Override
    public void parse(JSONObject json) {
        if (null != json && !json.isEmpty()) {

            setPara_function("355");
            uid = json.getString("uid");
            setPara_uid(uid);
            
            mac = json.containsKey("mac") ? json.getString("mac") : "";
            useraccount = json.containsKey("useraccount") ? json.getString("useraccount") : "";
            topicId = json.containsKey("topicId") ? json.getString("topicId") : "";
            
        }
    }

}
