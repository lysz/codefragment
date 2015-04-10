package com.aora.wifi.servlet;

import java.util.HashMap;
import java.util.Map;

import com.aora.wifi.util.DesTools;

public class SysParameter {

	
	private static SysParameter instance=null;
	
	private SysParameter(){
		System.out.println("init SysParameter...");
	}

	public static SysParameter getInstatnce(){
		
			if(instance==null){
				
				instance=new SysParameter();
			}
		
		return instance;
	}	
	
    private  int SYS_REGISTER_SCORE=100;  //注册送U币
    private  int SYS_TRY_USE_TWICE=3;		//体验次数	
    private  int SYS_SIGN_SCORE_BASCE=5; //签到送的基础U币
    private  int SYS_SIGN_SCORE_PER_DAY=1;//签到送的递增U币
    private  int SYS_TRY_USE_MINUTE=10;   //体验时长
    private  int SYS_USE_SHARE_WIFI_SCORE=10; //用户第一次使用共享WIFI送U币给占领者
    private  int SYS_USE_SCORE_PER_DAY=100; //用户每天上网消费的U币
    private  int SYS_USE_MINUTE_PER_DAY=120; //用户每天消费获得上网时长
    
    private  String domain="http://www.enjoy.com/";
	

	private String strkey=null;

	private String KEYDESC=null;
	
	
	

	public String getStrkey() {
		return strkey;
	}

	public void setStrkey(String strkey) {
		this.strkey = strkey;
	}

	public String getKEYDESC() {
		return KEYDESC;
	}

	public void setKEYDESC(String keydesc) {
		KEYDESC = keydesc;
	}

	public  int getSYS_REGISTER_SCORE() {
		return SYS_REGISTER_SCORE;
	}

	public  void setSYS_REGISTER_SCORE(int sys_register_score) {
		SYS_REGISTER_SCORE = sys_register_score;
	}

	public  int getSYS_TRY_USE_TWICE() {
		return SYS_TRY_USE_TWICE;
	}

	public  void setSYS_TRY_USE_TWICE(int sys_try_use_twice) {
		SYS_TRY_USE_TWICE = sys_try_use_twice;
	}


	
	
	public  int getSYS_SIGN_SCORE_BASCE() {
		return SYS_SIGN_SCORE_BASCE;
	}

	public  void setSYS_SIGN_SCORE_BASCE(int sys_sign_score_basce) {
		SYS_SIGN_SCORE_BASCE = sys_sign_score_basce;
	}

	public  int getSYS_SIGN_SCORE_PER_DAY() {
		return SYS_SIGN_SCORE_PER_DAY;
	}

	public  void setSYS_SIGN_SCORE_PER_DAY(int sys_sign_score_per_day) {
		SYS_SIGN_SCORE_PER_DAY = sys_sign_score_per_day;
	}

	public  int getSYS_TRY_USE_MINUTE() {
		return SYS_TRY_USE_MINUTE;
	}

	public  void setSYS_TRY_USE_MINUTE(int sys_try_use_minute) {
		SYS_TRY_USE_MINUTE = sys_try_use_minute;
	}

	public  int getSYS_USE_SHARE_WIFI_SCORE() {
		return SYS_USE_SHARE_WIFI_SCORE;
	}

	public  void setSYS_USE_SHARE_WIFI_SCORE(int sys_use_share_wifi_score) {
		SYS_USE_SHARE_WIFI_SCORE = sys_use_share_wifi_score;
	}

	public  int getSYS_USE_SCORE_PER_DAY() {
		return SYS_USE_SCORE_PER_DAY;
	}

	public  void setSYS_USE_SCORE_PER_DAY(int sys_use_score_per_day) {
		SYS_USE_SCORE_PER_DAY = sys_use_score_per_day;
	}

	public  int getSYS_USE_MINUTE_PER_DAY() {
		return SYS_USE_MINUTE_PER_DAY;
	}

	public  void setSYS_USE_MINUTE_PER_DAY(int sys_use_minute_per_day) {
		SYS_USE_MINUTE_PER_DAY = sys_use_minute_per_day;
	}


	public final static String ACCOUNTINFO = "accountInfo";
	public final static String FALE="fale";

	public final static String ERROR="error";
	public final static String REQUESTBEAN="RequestBean";
	public final static String WIFI_ENCRIPTION="WIFI_ENCRIPTION";	
	
	//存放U币相关参数
	private   Map<String, Object> params = new HashMap<String, Object>();

	public  Map<String, Object> getParams() {
		
        return params;
    }

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}	
}
