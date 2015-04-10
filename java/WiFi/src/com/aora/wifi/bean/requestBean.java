package com.aora.wifi.bean;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


public abstract class requestBean extends baseBean  {
	

	public String getHeader_version() {
		return header_version;
	}
	public void setHeader_version(String header_version) {
		this.header_version = header_version;
	}
	public String getHeader_mac() {
		return header_mac;
	}
	public void setHeader_mac(String header_mac) {
		this.header_mac = header_mac;
	}
	public String getHeader_imei() {
		return header_imei;
	}
	public void setHeader_imei(String header_imei) {
		this.header_imei = header_imei;
	}
	public String getHeader_imsi() {
		return header_imsi;
	}
	public void setHeader_imsi(String header_imsi) {
		this.header_imsi = header_imsi;
	}
	public String getHeader_username() {
		return header_username;
	}
	public void setHeader_username(String header_username) {
		this.header_username = header_username;
	}

	
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	


	public String getHeader_brand() {
		return header_brand;
	}
	public void setHeader_brand(String header_brand) {
		this.header_brand = header_brand;
	}
	public String getHeader_model() {
		return header_model;
	}
	public void setHeader_model(String header_model) {
		this.header_model = header_model;
	}
	public String getHeader_city() {
		return header_city;
	}
	public void setHeader_city(String header_city) {
		this.header_city = header_city;
	}
	public String getHeader_longitude() {
		return header_longitude;
	}
	public void setHeader_longitude(String header_longitude) {
		this.header_longitude = header_longitude;
	}
	public String getHeader_latitude() {
		return header_latitude;
	}
	public void setHeader_latitude(String header_latitude) {
		this.header_latitude = header_latitude;
	}
	public String getReal_ip() {
		return real_ip;
	}
	public void setReal_ip(String real_ip) {
		this.real_ip = real_ip;
	}




	private String header_version;
	private String header_mac;
	private String header_imei;
	private String header_imsi;
	private String header_username;	
	private String header_brand;
	private String header_model;
	private String header_city;
	private String header_longitude;
	private String header_latitude;
	private String channel;
	private String real_ip;
	
	private long request_start=0;
	private long request_forward=0;
	
	public abstract void parse(JSONObject json);
	
	public void parseHeader(String  headerstr){
		try{
				String header=headerstr;
				if(header!=null && header.split("\\|").length>=8){
					this.header_version=header.split("\\|")[0];
					this.header_imei=header.split("\\|")[1];
					this.header_imsi=header.split("\\|")[2];
					this.header_brand=header.split("\\|")[3];
					this.header_model=header.split("\\|")[4];
					this.header_longitude=header.split("\\|")[5];
					this.header_latitude=header.split("\\|")[6];
					this.header_city=header.split("\\|")[7];
					this.header_username="";
					this.header_mac=this.getPara_uid();
				}else{
					this.header_version="";
					this.header_imei="";
					this.header_imsi="";
					this.header_brand="";
					this.header_model="";
					this.header_longitude="";
					this.header_latitude="";
					this.header_city="";					
					this.header_username="";
					
					this.header_mac=this.getPara_uid();
					
				}
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] arg){
		try{
			String requestData="{'aa':'test','hps':[{'id':'210056','lat':'36.615002','lng':'101.758459','image':'red.png'},{'id':'210057','lat':'36.630234','lng':'101.755836','image':'red.png'},{'id':'210058','lat':'36.636124','lng':'101.759402','image':'red.png'}]}";
			JSONObject jsondata = (JSONObject) JSONSerializer.toJSON( requestData ); 
			JSONArray jsonarr=(JSONArray)jsondata.getJSONArray("hps");
			
			System.out.println(jsondata.get("aa"));
			System.out.println(jsonarr.size());
			System.out.println(((JSONObject)jsonarr.get(0)).get("id"));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public long getRequest_start() {
		return request_start;
	}
	public void setRequest_start(long request_start) {
		this.request_start = request_start;
	}
	public long getRequest_forward() {
		return request_forward;
	}
	public void setRequest_forward(long request_forward) {
		this.request_forward = request_forward;
	}
	

	public String trimUserName(String str){
		String value=str;
		if(str.length()>0 && str.split(":").length==3){
			value=str.split(":")[1];
		}
		return value;
	}

}
