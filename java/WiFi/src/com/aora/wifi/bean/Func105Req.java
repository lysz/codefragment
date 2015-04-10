package com.aora.wifi.bean;

import net.sf.json.JSONObject;

public class Func105Req extends requestBean {

    private String    uid;

    private double leftTopLat;

    private double leftTopLong;
    
    private double rightTopLong;
    
    private double leftBottomLat;
    
    private double myLong;
    
    private double myLat;
    
    private int isIOS;
    
    private int cur_scale;
    
    private String maptype;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    
    public double getLeftTopLat() {
        return leftTopLat;
    }

    public void setLeftTopLat(double leftTopLat) {
        this.leftTopLat = leftTopLat;
    }

    public double getLeftTopLong() {
        return leftTopLong;
    }

    public void setLeftTopLong(double leftTopLong) {
        this.leftTopLong = leftTopLong;
    }

    public double getRightTopLong() {
        return rightTopLong;
    }

    public void setRightTopLong(double rightTopLong) {
        this.rightTopLong = rightTopLong;
    }

    public double getLeftBottomLat() {
        return leftBottomLat;
    }

    public void setLeftBottomLat(double leftBottomLat) {
        this.leftBottomLat = leftBottomLat;
    }
    
    public double getMyLong() {
        return myLong;
    }

    public void setMyLong(double myLong) {
        this.myLong = myLong;
    }

    public double getMyLat() {
        return myLat;
    }

    public void setMyLat(double myLat) {
        this.myLat = myLat;
    }

    public int getIsIOS() {
        return isIOS;
    }

    public void setIsIOS(int isIOS) {
        this.isIOS = isIOS;
    }

    
    
    public int getCur_scale() {
		return cur_scale;
	}

	public void setCur_scale(int cur_scale) {
		this.cur_scale = cur_scale;
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
        uid = json.getString("uid"); 
        this.setPara_uid(uid);        
        String leftTopLatStr = json.getString("leftTopLat");
        String leftTopLongStr = json.getString("leftTopLong");
        String rightTopLongStr = json.getString("rightTopLong");
        String leftBottomLatStr = json.getString("leftBottomLat");
        
        String myLongStr = json.getString("myLong");
        String myLatStr = json.getString("myLat");
        
        
        String tmp_scale=json.containsKey("scale")?json.getString("scale"):"0";
        
        String tmp_maptype=json.containsKey("maptype")?json.getString("maptype"):"baidu";
        
        try{
        	cur_scale=Integer.parseInt(tmp_scale);
        	
        	
        }catch(Exception e){
        	cur_scale=0;
        }
        
        maptype=tmp_maptype;
        
        isIOS = json.get("isIOS") == null ? 0 : json.getInt("isIOS");
        
        myLong = Double.valueOf(myLongStr);
        myLat = Double.valueOf(myLatStr);
        
        leftTopLat = Double.valueOf(leftTopLatStr);
        leftTopLong = Double.valueOf(leftTopLongStr);
        rightTopLong = Double.valueOf(rightTopLongStr);
        leftBottomLat = Double.valueOf(leftBottomLatStr);
    }
    
}
