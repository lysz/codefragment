package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class func352Req extends requestBean {

    private String uid;
    private String mac;
    private String useraccount;
    private String image;
    private String content;
    private String smallimage;
    private double lng;
    private double lat;
    private String ssid;
    private String gps_addr;

    public String getGps_addr() {
        return gps_addr;
    }

    public void setGps_addr(String gpsAddr) {
        gps_addr = gpsAddr;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getSmallimage() {
        return smallimage;
    }

    public void setSmallimage(String smallimage) {
        this.smallimage = smallimage;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void parse(JSONObject json) {
        if (json != null && !json.isEmpty()){
            setPara_function("352");
            uid = json.getString("uid");
            setPara_uid(uid);
            
            mac = json.containsKey("mac") ? json.getString("mac") : "";
            useraccount = json.containsKey("useraccount") ? json.getString("useraccount") : "";
            content = json.containsKey("content") ? json.getString("content") : "";
            image = json.containsKey("image") ? json.getString("image"): "";
            smallimage = json.containsKey("smallimage") ? json.getString("smallimage") : "";
            ssid = json.containsKey("ssid") ? json.getString("ssid") : "";
            lng = json.containsKey("lng") ? json.getDouble("lng") : 0;
            lat = json.containsKey("lat") ? json.getDouble("lat") : 0;
            gps_addr = json.containsKey("gps_addr") ? json.getString("gps_addr") : "";
        }
    }
}
