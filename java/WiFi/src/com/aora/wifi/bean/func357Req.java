package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func357Req extends requestBean {

    private String uid;
    private String mac;
    private String useraccount;
    private String replyId;

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

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    @Override
    public void parse(JSONObject json) {
        if (null != json && !json.isEmpty()) {

            setPara_function("357");
            uid = json.getString("uid");
            setPara_uid(uid);
            
            mac = json.containsKey("mac") ? json.getString("mac") : "";
            useraccount = json.containsKey("useraccount") ? json.getString("useraccount") : "";
            replyId = json.containsKey("replyId") ? json.getString("replyId") : "";
            
        }
    }

}
