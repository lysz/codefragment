package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func354Req extends requestBean {

    private String uid;

    private String mac;

    private String useraccount;

    private String type;

    private String to;

    private String content;

    private String tousername;

    private String touseraccount;

    private String topicId;

    public String getTouseraccount() {
        return touseraccount;
    }

    public void setTouseraccount(String touseraccount) {
        this.touseraccount = touseraccount;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTousername() {
        return tousername;
    }

    public void setTousername(String tousername) {
        this.tousername = tousername;
    }

    @Override
    public void parse(JSONObject json) {
        if (null != json && !json.isEmpty()) {

            setPara_function("354");
            uid = json.getString("uid");
            setPara_uid(uid);
            
            mac = json.containsKey("mac") ? json.getString("mac") : "";
            useraccount = json.containsKey("useraccount") ? json.getString("useraccount") : "";
            type = json.containsKey("type") ? json.getString("type") : "";
            to = json.containsKey("to") ? json.getString("to") : "";
            content = json.containsKey("content") ? json.getString("content") : "";
            tousername = json.containsKey("tousername") ? json.getString("tousername") : "";
            touseraccount = json.containsKey("touseraccount") ? json.getString("touseraccount") : "";
            topicId = json.containsKey("topicId") ? json.getString("topicId") : "";
            
        }
    }
}
