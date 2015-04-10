package com.aora.sales.partner.dao.impl;

public class Hotspot implements java.io.Serializable {

    private static final long serialVersionUID = -7377255137798517411L;

    private int               id;

    private String            hotspotId;

    private String            hotname;

    private String            address;

    private String            province;

    private String            city;

    private String            coverarea;

    private double            longitude;

    private double            latitude;

    private int               status;

    private String            image;

    // 分类: CMCC/ChinaNet
    private String            type;

    private int               isUpdate;

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotspotId() {
        return hotspotId;
    }

    public void setHotspotId(String hotspotId) {
        this.hotspotId = hotspotId;
    }

    public String getHotname() {
        return hotname;
    }

    public void setHotname(String hotname) {
        this.hotname = hotname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCoverarea() {
        return coverarea;
    }

    public void setCoverarea(String coverarea) {
        this.coverarea = coverarea;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
