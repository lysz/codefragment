package com.aora.wifi.bean;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

public class Func104Req extends requestBean {

    private int    id;

    // wifi名称
    private String wifi_name;

    // 共享wifi密码
    private String wifi_pass;

    // MAC地址
    private String wifi_mac;

    private String wifi_address;

    private String wifi_gps_address;

    private String wifi_type;
    
    private String city;

    // 共享帐号经度的座标
    private double wifi_longitude;

    // 共享帐号纬度的座标
    private double wifi_latitude;

    // 记录新增时间
    private Date   indate;

    // 地址
    private String address;

    // 热点名称
    private String hotName;

    // 修改时间
    private Date   modify_date;

    private int    status;
    
    //0:更新所有信息, 1:更新name和pass,  2: 更新name和地址信息
    private Integer action_type;

    //注册帐号
    private String createby_login_id;
    
    //手机设备号
    private String createby_client_id;
    
    //U币
    private int score;
    
    private String maptype;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public int getAction_type() {
        return action_type;
    }

    public void setAction_type(int actionType) {
        action_type = actionType;
    }

    public String getWifi_name() {
        return wifi_name;
    }

    public void setWifi_name(String wifiName) {
        wifi_name = wifiName;
    }

    public String getWifi_pass() {
        return wifi_pass;
    }

    public void setWifi_pass(String wifiPass) {
        wifi_pass = wifiPass;
    }

    public double getWifi_longitude() {
        return wifi_longitude;
    }

    public void setWifi_longitude(double wifiLongitude) {
        wifi_longitude = wifiLongitude;
    }

    public double getWifi_latitude() {
        return wifi_latitude;
    }

    public void setWifi_latitude(double wifiLatitude) {
        wifi_latitude = wifiLatitude;
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modifyDate) {
        modify_date = modifyDate;
    }

    public String getCreateby_login_id() {
        return createby_login_id;
    }

    public void setCreateby_login_id(String createbyLoginId) {
        createby_login_id = createbyLoginId;
    }

    public String getCreateby_client_id() {
        return createby_client_id;
    }

    public void setCreateby_client_id(String createbyClientId) {
        createby_client_id = createbyClientId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHotName() {
        return hotName;
    }

    public void setHotName(String hotName) {
        this.hotName = hotName;
    }

    public String getWifi_mac() {
        return wifi_mac;
    }

    public void setWifi_mac(String wifiMac) {
        wifi_mac = wifiMac;
    }

    public String getWifi_address() {
        return wifi_address;
    }

    public void setWifi_address(String wifiAddress) {
        wifi_address = wifiAddress;
    }

    public String getWifi_gps_address() {
        return wifi_gps_address;
    }

    public void setWifi_gps_address(String wifiGpsAddress) {
        wifi_gps_address = wifiGpsAddress;
    }

    public String getWifi_type() {
        return wifi_type;
    }

    public void setWifi_type(String wifiType) {
        wifi_type = wifiType;
    }
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    

    public String getMaptype() {
		return maptype;
	}

	public void setMaptype(String maptype) {
		this.maptype = maptype;
	}

	@Override
    public void parse(JSONObject json) {

        setPara_function(json.getString("function"));
        
        createby_client_id = json.getString("uid");
		this.setPara_uid(createby_client_id);
        createby_login_id = json.getString("username");
        
        createby_login_id=this.trimUserName(createby_login_id);        
        
        wifi_mac = json.getString("mac");
        wifi_gps_address = (String)json.getString("gps_address");
        wifi_name = json.getString("name");
        wifi_pass = json.getString("pass");
        wifi_address = json.getString("address");//用户手动填写的地址,,,,,,可能为空
        wifi_type = json.getString("type"); //餐饮, 机场,,,,,,可能为空
        city = json.getString("city");
        
        maptype=json.containsKey("maptype")?json.getString("maptype"):"baidu";        
        
        
        //如果不传action_type或action_type=1表示占领该wifi
        //如果action_type=0表示该wifi密码错误上报
        action_type = Integer.parseInt(json.get("action_type").toString());
        
        if (action_type == 0 || action_type == 1){            
            status = 1;
        }
        
        String longitude = (String)json.get("longitude");
        String latitude = (String)json.get("latitude");

        if (StringUtils.isEmpty(longitude) || StringUtils.isEmpty(latitude)) {
            wifi_longitude = 0;
            wifi_latitude = 0;
        } else {
            wifi_longitude = Double.valueOf(longitude);
            wifi_latitude = Double.valueOf(latitude);
        }
    }
    
    public static void main(String[] args) {
        
    }
}
