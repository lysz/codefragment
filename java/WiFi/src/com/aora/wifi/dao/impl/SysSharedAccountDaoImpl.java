package com.aora.wifi.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aora.wifi.bean.Func104Req;
import com.aora.wifi.bean.Hotspot;
import com.aora.wifi.servlet.SysParameter;
import com.aora.wifi.tools.PropertyInfo;
import com.ibatis.sqlmap.client.SqlMapClient;

public class SysSharedAccountDaoImpl extends BaseDao{

    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
    
    /**
     * 新增wifi共享帐号
     * @param bean
     */
    public void addWifiShareAccount(Func104Req bean) throws Exception {
        
        //SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
        
        try {
            // 打开事务
            //sqlMapClient.startTransaction();

            //getSqlMapClientTemplate().insert("system.addWifiShareAccount", bean);
        	if(bean.getMaptype().equalsIgnoreCase("baidu")){
        		this.executedSql("INSERT INTO tbl_wifi_share_account(account,passwd,status,mac_address,longitude,latitude,wifi_address,wifi_type,wifi_gps_address,indate,modify_date,city,createby_login_id,createby_client_id,channel,gaode_longitude,gaode_latitude) VALUES(?,?,?,?,?,?,?,?,?,now(),now(),?,?,?,?,0,0)", 
            			new String[]{bean.getWifi_name(),bean.getWifi_pass(),String.valueOf(bean.getStatus()),bean.getWifi_mac(),String.valueOf(bean.getWifi_longitude()),String.valueOf(bean.getWifi_latitude()),
            			bean.getWifi_address(),bean.getWifi_type(),bean.getWifi_gps_address(),bean.getCity(),bean.getCreateby_login_id(),bean.getCreateby_client_id(),bean.getChannel()});
        	}else{
        		this.executedSql("INSERT INTO tbl_wifi_share_account(account,passwd,status,mac_address,gaode_longitude,gaode_latitude,wifi_address,wifi_type,wifi_gps_address,indate,modify_date,city,createby_login_id,createby_client_id,channel,longitude,latitude) VALUES(?,?,?,?,?,?,?,?,?,now(),now(),?,?,?,?,0,0)", 
            			new String[]{bean.getWifi_name(),bean.getWifi_pass(),String.valueOf(bean.getStatus()),bean.getWifi_mac(),String.valueOf(bean.getWifi_longitude()),String.valueOf(bean.getWifi_latitude()),
            			bean.getWifi_address(),bean.getWifi_type(),bean.getWifi_gps_address(),bean.getCity(),bean.getCreateby_login_id(),bean.getCreateby_client_id(),bean.getChannel()});
        		
        	}
            
            //getSqlMapClientTemplate().insert("system.addWifiShareAccountLog", bean);
            if(bean.getCreateby_login_id().length()>0){
               	this.executedSql("INSERT INTO tbl_wifi_share_account_logs(account,remark,mac_address,wifi_address,wifi_gps_address,wifi_type,indate,score,uid,reg_account) VALUES(?,?,?,?,?,?,now(),?,'',?)",
                    	new String[]{bean.getWifi_name(),bean.getCreateby_client_id(),bean.getWifi_mac(),bean.getWifi_address(),bean.getWifi_gps_address(),bean.getWifi_type(),String.valueOf(bean.getScore()),bean.getCreateby_login_id()});
            	   
             }else{            
            	this.executedSql("INSERT INTO tbl_wifi_share_account_logs(account,uid,mac_address,wifi_address,wifi_gps_address,wifi_type,indate,score) VALUES(?,?,?,?,?,?,now(),?)",
            		new String[]{bean.getWifi_name(),bean.getCreateby_client_id(),bean.getWifi_mac(),bean.getWifi_address(),bean.getWifi_gps_address(),bean.getWifi_type(),String.valueOf(bean.getScore())});
             }
            
            //sqlMapClient.commitTransaction();
        } finally {
            // 事务结束
            //sqlMapClient.endTransaction();
        }
    }
    
    public void updateSharedWIFIAccountStatus(String mac, int status) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("wifi_mac", mac);
        param.put("status", status);
        
       // getSqlMapClientTemplate().insert("system.updateSharedWIFIAccountStatus", param);
       try{
        this.executedSql("update tbl_wifi_share_account  SET status = ?   WHERE   mac_address =?" , new String[]{String.valueOf(status),mac});
       }catch(Exception e){
    	   e.printStackTrace();
       }
    }
    
    public void updateSharedWIFIAccount(Func104Req bean){
        //getSqlMapClientTemplate().update("system.updateSharedWIFI", func104);
    	try{
    		
    		List param=new ArrayList();
    		String sql=" UPDATE tbl_wifi_share_account set ";
    		if(bean.getWifi_name()!=null && bean.getWifi_name().length()>0){ 
    			sql+="account=?,";
    			param.add(bean.getWifi_name());
    		}	
    		if(bean.getWifi_pass()!=null && bean.getWifi_pass().length()>0){ 
    			sql+="passwd=?,connectfailureNumber=0,";
    			param.add(bean.getWifi_pass());
    		}	
    		if(bean.getWifi_address()!=null && bean.getWifi_address().length()>0){ 
    			sql+="wifi_address=?,";
    			param.add(bean.getWifi_address());
    		}	
    		if(bean.getWifi_gps_address()!=null && bean.getWifi_gps_address().length()>0){ 
    			sql+="wifi_gps_address=?,";
    			param.add(bean.getWifi_gps_address());
    		}	
	
    		if(bean.getWifi_type()!=null && bean.getWifi_type().length()>0){ 
    			sql+="wifi_type=?,";
    			param.add(bean.getWifi_type());
    		}	
    		if(bean.getCreateby_login_id()!=null && bean.getCreateby_login_id().length()>0){ 
    			sql+="createby_login_id=?,";
    			param.add(bean.getCreateby_login_id());
    		}	
    		if(bean.getCreateby_client_id()!=null && bean.getCreateby_client_id().length()>0){ 
    			sql+="createby_client_id=?,";
    			param.add(bean.getCreateby_client_id());
    		}	    		
    		if(bean.getWifi_longitude()>0 ){ 
    			if(bean.getMaptype().equalsIgnoreCase("baidu")){
    				sql+="longitude=?,gaode_longitude=0,";
    			}else{
    				sql+="longitude=0,gaode_longitude=?,";
    			}
    			param.add(bean.getWifi_longitude());    			
    		}	    		
    		if(bean.getWifi_latitude()>0 ){ 
    			if(bean.getMaptype().equalsIgnoreCase("baidu")){    			
    				sql+="latitude=?,gaode_latitude=0,";
    			}else{
    				sql+="latitude=0,gaode_latitude=?,";
    			}
    			param.add(bean.getWifi_latitude());
    		}	
    		if(bean.getAction_type()==0){
    			sql+="status =1,";
    		}
    		
    		if(sql.endsWith(",")){sql=sql.substring(0,sql.length()-1);}
    		
    		sql+=" WHERE mac_address =?";
    		param.add(bean.getWifi_mac());
    			
    		this.executedSql(sql, param.toArray());    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public void addWifiShareAccountLog(Func104Req bean){        
        //getSqlMapClientTemplate().insert("system.addWifiShareAccountLog", func104);
        try{   	
//        	this.executedSql("INSERT INTO tbl_wifi_share_account_logs(account,uid,mac_address,wifi_address,wifi_gps_address,wifi_type,indate,score) VALUES(?,?,?,?,?,?,now(),?)",
//        		new String[]{bean.getCreateby_login_id(),bean.getCreateby_client_id(),bean.getWifi_mac(),bean.getWifi_address(),bean.getWifi_gps_address(),bean.getWifi_type(),String.valueOf(bean.getScore())});
           if(bean.getCreateby_login_id().length()>0){
           	this.executedSql("INSERT INTO tbl_wifi_share_account_logs(account,remark,mac_address,wifi_address,wifi_gps_address,wifi_type,indate,score,uid,reg_account) VALUES(?,?,?,?,?,?,now(),?,'',?)",
                	new String[]{bean.getWifi_name(),bean.getCreateby_client_id(),bean.getWifi_mac(),bean.getWifi_address(),bean.getWifi_gps_address(),bean.getWifi_type(),String.valueOf(bean.getScore()),bean.getCreateby_login_id()});
        	   
           }else{
        	this.executedSql("INSERT INTO tbl_wifi_share_account_logs(account,uid,mac_address,wifi_address,wifi_gps_address,wifi_type,indate,score) VALUES(?,?,?,?,?,?,now(),?)",
            	new String[]{bean.getWifi_name(),bean.getCreateby_client_id(),bean.getWifi_mac(),bean.getWifi_address(),bean.getWifi_gps_address(),bean.getWifi_type(),String.valueOf(bean.getScore())});
           }
        	
        }catch(Exception e){
     	   e.printStackTrace();
        }    	
    	
    }
    
    public List<Map<String, Object>> getShareWifiHotspot(Map<String, Double> params, int isIOS) throws Exception{
        
        List<Map<String, Object>> hotspots = new ArrayList<Map<String, Object>>();
        
        List<Func104Req> shareWifiList = null;
        List<Hotspot> hotspotList = null;
        
        //SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
        try{
            //打开事务
            //sqlMapClient.startTransaction();
            
        	List para=new ArrayList();
        	para.add(params.get("leftBottomLat"));
        	para.add(params.get("leftTopLat"));
        	para.add(params.get("leftTopLong"));
        	para.add(params.get("rightTopLong"));
        	
        	int tmp_scale=((Double)params.get("cur_scale")).intValue();
        	
        	int maptype_baidu=((Double)params.get("maptype_baidu")).intValue();
        	
            if (isIOS != 1){
                //查询共享wifi的数据
               // shareWifiList = getSqlMapClientTemplate().queryForList("system.getShareWifiHotspot", params);
            	
            	if(maptype_baidu>0){
            	
	            	if(tmp_scale>0 && tmp_scale<100){
	            		shareWifiList=this.getData("SELECT id AS id, account AS wifi_name, passwd AS wifi_pass,longitude  AS wifi_longitude, latitude AS wifi_latitude, wifi_address AS wifi_address, wifi_gps_address AS wifi_gps_address, wifi_type  AS wifi_type,icon 'hotName'  FROM tbl_wifi_share_account " +
	                			"WHERE latitude > ?  AND latitude < ?  AND longitude > ?  AND longitude < ? AND status != 0 ", para.toArray(),Func104Req.class);            		
	            	}else{
	            		shareWifiList=this.getData("SELECT id AS id, account AS wifi_name, passwd AS wifi_pass,longitude  AS wifi_longitude, latitude AS wifi_latitude, wifi_address AS wifi_address, wifi_gps_address AS wifi_gps_address, wifi_type  AS wifi_type,icon 'hotName'  FROM tbl_wifi_share_account " +
	            			"WHERE latitude > ?  AND latitude < ?  AND longitude > ?  AND longitude < ? AND status != 0 order by rand() limit 70", para.toArray(),Func104Req.class);
	            	}
            	}else{
                	
	            	if(tmp_scale>0 && tmp_scale<100){
	            		shareWifiList=this.getData("SELECT id AS id, account AS wifi_name, passwd AS wifi_pass,gaode_longitude  AS wifi_longitude, gaode_latitude AS wifi_latitude, wifi_address AS wifi_address, wifi_gps_address AS wifi_gps_address, wifi_type  AS wifi_type,icon 'hotName'  FROM tbl_wifi_share_account " +
	                			"WHERE gaode_latitude > ?  AND gaode_latitude < ?  AND gaode_longitude > ?  AND gaode_longitude < ? AND status != 0 ", para.toArray(),Func104Req.class);            		
	            	}else{
	            		shareWifiList=this.getData("SELECT id AS id, account AS wifi_name, passwd AS wifi_pass,gaode_longitude  AS wifi_longitude, gaode_latitude AS wifi_latitude, wifi_address AS wifi_address, wifi_gps_address AS wifi_gps_address, wifi_type  AS wifi_type,icon 'hotName'  FROM tbl_wifi_share_account " +
	            			"WHERE gaode_latitude > ?  AND gaode_latitude < ?  AND gaode_longitude > ?  AND gaode_longitude < ? AND status != 0 order by rand() limit 70", para.toArray(),Func104Req.class);
	            	}            		            		
            		
            		
            	}
            }
            
            //查询运营商的数据
            //hotspotList = getSqlMapClientTemplate().queryForList("system.getCMCCChinaNetHotspot", params);
        	if(maptype_baidu>0){        	
	            if(tmp_scale>0 && tmp_scale<100){
	        		hotspotList=this.getData("        SELECT id,hotname,address,coverarea,type,longitude,latitude FROM tbl_hotspot WHERE" +
	                		" latitude >? AND latitude < ? AND  longitude > ? AND longitude < ? ",para.toArray(),Hotspot.class);
	        	}else{
	        		hotspotList=this.getData("        SELECT id,hotname,address,coverarea,type,longitude,latitude FROM tbl_hotspot WHERE" +
	            		" latitude >? AND latitude < ? AND  longitude > ? AND longitude < ? order by rand() limit 30",para.toArray(),Hotspot.class);
	        	}
        	}else{
	            if(tmp_scale>0 && tmp_scale<100){
	        		hotspotList=this.getData("        SELECT id,hotname,address,coverarea,type,gaode_longitude as longitude,gaode_latitude as latitude FROM tbl_hotspot WHERE" +
	                		" gaode_latitude >? AND gaode_latitude < ? AND  gaode_longitude > ? AND gaode_longitude < ? ",para.toArray(),Hotspot.class);
	        	}else{
	        		hotspotList=this.getData("        SELECT id,hotname,address,coverarea,type,gaode_longitude as longitude,gaode_latitude as latitude FROM tbl_hotspot WHERE" +
	            		" gaode_latitude >? AND gaode_latitude < ? AND  gaode_longitude > ? AND gaode_longitude < ? order by rand() limit 30",para.toArray(),Hotspot.class);
	        	}        		
        		
        	}
                        
            //sqlMapClient.commitTransaction();
            
        }finally{
            //事务结束
            //sqlMapClient.endTransaction();
        }
        
        double myLong = params.get("myLong");
        double myLat = params.get("myLat");
        
        //遍历共享wifi
        if (null != shareWifiList){
            for (Func104Req shareWifi : shareWifiList){
                
                int distace = calculateDistance(myLat, myLong, shareWifi.getWifi_latitude(), shareWifi.getWifi_longitude());
                
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("tid",        shareWifi.getId() + "");
                data.put("latitude",   shareWifi.getWifi_latitude() + "");
                data.put("longitude",  shareWifi.getWifi_longitude() + "");
                data.put("type",       shareWifi.getWifi_type());
                data.put("typename",   convertToTypename(shareWifi.getWifi_type()));
                data.put("name",       shareWifi.getWifi_name());
                data.put("gpsaddress",    shareWifi.getWifi_gps_address() );
                data.put("address",(shareWifi.getWifi_address() == null ? "" : shareWifi.getWifi_address()));
                data.put("icon",(shareWifi.getHotName() == null ||shareWifi.getHotName().length()==0 ? "" : SysParameter.getInstatnce().getDomain()+"/WIFI2/images/icon/"+shareWifi.getHotName()));
                data.put("distances", distace);
                hotspots.add(data);
            }
        }
        
         //遍历运营商网络
        if (hotspotList != null){
            for (Hotspot hotspot: hotspotList){
                
                int distace = calculateDistance(myLat, myLong, hotspot.getLatitude(), hotspot.getLongitude());
                
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("tid",        hotspot.getId() + "");
                data.put("latitude",   hotspot.getLatitude() + "");
                data.put("longitude",  hotspot.getLongitude() + "");
                data.put("type",       convertHotTypeName(hotspot.getType())[0]);
                data.put("typename",   convertHotTypeName(hotspot.getType())[1]);
                data.put("name",       hotspot.getType());
                data.put("gpsaddress", hotspot.getAddress());
                data.put("address",    hotspot.getHotname());
                data.put("icon",  ""  );
                data.put("distances",  distace);
                hotspots.add(data);
            }
        }
        
        if (null != hotspots && !hotspots.isEmpty()){
            CompareDistances compare = new CompareDistances();
            Collections.sort(hotspots, compare);            
        }
        
        return hotspots;
    }
    
    
    private String[] convertHotTypeName(String type){
    	String value[]={"1","中国移动"};
    	
       
       if(type.equalsIgnoreCase("CMCC")){
    		value[0]="1";value[1]="中国移动";
       }else if(type.equalsIgnoreCase("CHINANET")){
        	value[0]="2";value[1]="中国电信";
       }else if(type.toUpperCase().startsWith("CTM")){
    	   	value[0]="13";value[1]="澳门电讯";
       }else if(type.toUpperCase().startsWith("WIFI-GO")){
      	   	value[0]="14";value[1]="澳门-任我行";
       }else if(type.toUpperCase().startsWith("CHT")){
   	   		value[0]="15";value[1]="CHT中华电讯";
       }else if(type.toUpperCase().startsWith("ITAIWANG")){
    	   value[0]="16";value[1]="ITAIWANG";
       }else if(type.toUpperCase().startsWith("TPE-FREE")){
    	   value[0]="17";value[1]="TPE-FREE";

    	   
       }
    	return value;
    }
    
    
    private String convertToTypename(String type){
        
        if (null == type){
            return "其它";
        }
        int typenumber = Integer.valueOf(type);
        
        String typename = null;
        
        switch(typenumber){
        case 3:
            typename = "其它";
            break;
        case 4:
            typename = "餐饮";
            break;
        case 5:
            typename = "咖啡厅,酒吧";
            break;
        case 6:
            typename = "办公楼";
            break;
        case 7:
            typename = "私人";
            break;
        default:
        	typename = "其它";
        	break;
        }
        
        return typename;
    }
    
    public List getShareWifiLog(String username, String mac_address)throws Exception{
                
        String sql = "SELECT * FROM tbl_wifi_share_account_logs WHERE mac_address='" + mac_address + "' AND (uid='" + username + "' or remark='"+ username + "')";
        List list = getData(sql, new String[]{});
        
        if (null == list)
            list = new ArrayList();
        
        return list;
    }
	
	public List getShareWifiList(String mac_str) throws Exception {
		
		List ls=null;
		try {
		
			ls=this.getData("select * from tbl_wifi_share_account where status!=0 and mac_address in("+mac_str+")",new String[]{});
		}catch(Exception e){
			e.printStackTrace();
			ls=new ArrayList();
		}
		return ls;
	}
	
	private int calculateDistance(double lat1, double lon1, double lat2, double lon2){
	    
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
	    
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        double d = AVERAGE_RADIUS_OF_EARTH * c * 1000; // Distance in km
        
        int dist = (int)Math.round(d);
        
	    return dist;
	}
	
	private class CompareDistances implements Comparator<Object>{
	    
	    public int compare(Object o0, Object o1) {
	        
	        Map<String, Object> map1 = (Map<String, Object>) o0;
	        Map<String, Object> map2 = (Map<String, Object>) o1;
	        
	        int d1 = (Integer)map1.get("distances");
	        int d2 = (Integer)map2.get("distances");
	        
	        if (d1 > d2) {
	            return 1; // 第一个大于第二个
	        } else if (d1 < d2) {
	            return -1;// 第一个小于第二个
	        } else {
	            return 0; // 等于
	        }
	    }
	}
	

	
}
