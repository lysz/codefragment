package com.aora.wifi.util;
 
import java.io.*;   
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.*;   
import java.net.*;   

import sun.misc.BASE64Encoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import net.sf.json.JSONSerializer;

 
public class httppost {
	
	private final String posturl="http://www.myaora.net:810/api.php";  //game center

	
	
	
	public static void main(String[] arg) throws Exception{
		
////		int aa=10;
////		DecimalFormat df = new DecimalFormat("0.00");  
////		double d = (double)aa;    
//
//		System.out.println(df.format(d/100).toString())	;
//		System.out.println(aa/100);
		
//		double a1=0;
//		
//		if(a1>0){
//			System.out.println(">0");
//		}else if(a1==0){
//			System.out.println("==0");
//		}else {
//			System.out.println("<0");
//		}
//		
		httppost c1=new httppost();
		
		//System.out.println(java.net.URLEncoder.encode("itunes.apple.com/cn/app/wifi-chang-you/id910400294?mt=8", "UTF-8"));
		
		//c1.test2();
		//c1.sendsms();
		c1.wifi_api();
	}
	
	
	public void wifi_api() throws Exception{
		HashMap header=new HashMap();
		//header.put("Cookie", "JSESSIONID=12JpPVtQ83Ny2tmSWFXRZFSVFxq6lfZ2mw3dmtX1QvJX0VvXXK1c!1247219512!-791961461");
		header.put("Cookie", "WIFI_CLIENT_INFO=|00000000-674c-ea86-ffff-ffff8a92e6ef|862967024378058|460077108232338|13510281094|测试");
		header.put("content-type", "text/json;charset=UTF-8");
		header.put("WIFI_ENCRIPTION", "false");
		header.put("CHANNEL", "cywifi-taobao");
		header.put("CLIENT_INFO", "|00000000-674c-ea86-ffff-ffff8a92e6ef|862967024378058|460077108232338|13510281094|测试"+Tools.URLEncode("测试", "UTF-8"));
		String data="";
		BASE64Encoder base64 = new BASE64Encoder();
		FileOutputStream os=null;
		byte[]buf;
		//wifi ��ͼ
			JSONObject json=new JSONObject();
			JSONArray jarry=new JSONArray();
		String api="";
		String url="http://localhost:8080/WIFI2/system/_interface.do";
				//url="http://192.168.0.216:8078/WIFI/system/_interface.do";
				url="http://test.myaora.net:8000/WIFI2/system/_interface.do";
				url="http://www.wifienjoy.com/WIFI2/system/_interface.do";
				// url="http://www.wifienjoy.com/WIFI2/system/_interface.do?init=true"
				 //url="https://sandbox.itunes.apple.com/verifyReceipt";
				// url="https://buy.itunes.apple.com/verifyReceipt";
				//url="http://localhost:8080/WIFI/testServlet.sh";
		api="101";
		
		if(api.equalsIgnoreCase("100")){
			json.put("uid", "ffffffff-eb37-f19f-ffff-ffffdb0b8822");
			json.put("function", "100");
			data=json.toString();
	 	}else	if(api.equalsIgnoreCase("101")){
			json.put("uid", "00000000-635b-5554-9ab7-1dea35e6d525");
			json.put("function", "101");
			json.put("username", "");
			
			JSONObject item=new JSONObject();
				item.put("mac", "sdfsdfs");
				//item.put("name", "CTM-WIFI");
				item.put("name", "CMCC");
				item.put("priority", "7");
			jarry.add(item);
			
			json.put("data", jarry);
			
			data=json.toString();
			
			//data="{\"data\":[{\"priority\":\"86\",\"mac\":\"1c:78:39:03:2b:46\",\"name\":\"CTM-WIFI\"}],\"uid\":\"00000000-021d-54c6-ffff-ffff8a92e6ef\",\"username\":\"\",\"version\":\"3.0.0\",\"function\":\"101\"}";
			//data="{\"uid\":\"ffffffff-8c06-fc3d-07e0-564830cb4571\",\"username\":\"\",\"imei\":\"0x00000000\",\"data\":[{\"wifi_priority\":2,\"wifi_name\":\"CMCC\",\"wifi_mac\":\"00:23:89:21:ca:40\"}],\"imsi\":\"460036021478277\",\"password\":\"\",\"version\":\"1.3.0\",\"function\":\"101\"}";
			//data="{\"data\":[{\"wifi_ priority\":4,\"wifi_name\":\"AORA_YF1\",\"wifi_mac\":\"bc:d1:77:fc:df:2e\"}],\"uid\":\"00000000-4a80-584f-ffff-ffffeb71ecc6\",\"username\":\"\",\"imsi\":\"460002571388217\",\"imei\":\"860918021706678\",\"version\":\"1.0.0.0.1050\",\"function\":\"101\"}";
			
		}else if(api.equalsIgnoreCase("102")){
			json.put("uid", "00000000-635b-5554-9ab7-1dea35e6d525");
			json.put("function", "102");
			json.put("mac", "wifi_mac.......");
			json.put("name", "CTM");
			json.put("acc", "62071827");
			json.put("type", "CMCC");
			json.put("cookie", "cookie_sssssssssss");
			json.put("postdata", "postdata_sssssssss");
			json.put("sessionid", "sessionid_ssssssss");
			json.put("logout_url", "logout_url_ssssssss");
			data=json.toString();	

		}else if(api.equalsIgnoreCase("103")){
			json.put("uid", "00000000-635b-5554-9ab7-1dea35e6d525");
			json.put("function", "103");
			json.put("username", "");
			json.put("mac", "wifi_mac.......");
			json.put("name", "CTM");
			json.put("acc", "62071827");
			
			data=json.toString();	
			//data="{\"uid\":\"ffffffff-c80f-e585-ff50-5d544a79c32c\",\"mac\":\"00:23:89:21:ff:60\",\"username\":\"\",\"acc\":\"18257015354\",\"name\":\"CMCC\",\"function\":\"103\"}";
		}else if(api.equalsIgnoreCase("104")){
			data="{\"uid\":\"00000000-635b-5554-9ab7-1dea35e6d525\",\"username\":\"\",\"mac\":\"c8:3a:35:10:8d:00\",\"city\":\"长春市\",\"type\":\"3\",\"address\":\"\",\"gps_address\":\"吉林省长春市宽城区菜市北街78号\",\"action_type\":0,\"latitude\":\"43.953908\",\"account\":\"18686435503\",\"name\":\"jiayi001\",\"longitude\":\"125.315375\",\"pass\":\"80921903.\",\"function\":\"104\",\"maptype\":\"gaode\"}";
			//data="{\"uid\":\"ffffffff-d928-915b-ffff-ffffd8928a5e\",\"action_type\":\"0\",\"gps_address\":\"广东省深圳市福田区深南大道6007\",\"address\":\"\",\"longitude\":\"114.038002\",\"mac\":\"b3:b4:2f:1d:c9:36\",\"latitude\":\"22.541791\",\"type\":\"3\",\"acc\":\"google\",\"city\":\"深圳市\",\"pass\":\"20100518\",\"function\":\"104\"}";
			//data="{\"uid\":\"ffffffff-d928-915b-ffff-ffffd8928a5e\",\"username\":\"\",\"action_type\":\"0\",\"address\":\"\",\"gps_address\":\"广东省深圳市福田区深南大道6007\",\"name\":\"will.gu\",\"longitude\":\"114.038069\",\"mac\":\"00:36:76:33:9d:f9\",\"latitude\":\"22.541821\",\"type\":\"3\",\"city\":\"深圳市\",\"pass\":\"8888888888\",\"function\":\"104\"}";
			//data="{\"uid\":\"00000000-095f-d80a-1847-9e8f7dd16bdb\",\"username\":\"\",\"action_type\":\"0\",\"address\":\"\",\"gps_address\":\"广东省深圳市福田区深南大道6007\",\"name\":\"AORA_YW3\",\"longitude\":\"114.038062\",\"mac\":\"d8:24:bd:77:38:b9\",\"latitude\":\"22.541795\",\"type\":\"3\",\"city\":\"深圳市\",\"pass\":\"20100518\",\"function\":\"104\"}";
			//data="{\"uid\":\"ffffffff-dd32-7dfe-ffff-ffffb5e018cf\",\"username\":\"\",\"action_type\":\"0\",\"address\":\"\",\"gps_address\":\"\",\"name\":\"AORA_YW1\",\"longitude\":\"114.038061\",\"mac\":\"d8:24:bd:77:ba:ac\",\"latitude\":\"22.54183\",\"type\":\"3\",\"city\":\"深圳市\",\"pass\":\"20100518\",\"function\":\"104\"}";
		}else if(api.equalsIgnoreCase("105")){
			data="{\"leftTopLong\":\"113.930866\",\"uid\":\"00000000-6777-82d5-ffff-ffff9847a981\",\"myLong\":\"114.037739\",\"myLat\":\"22.542482\",\"leftTopLat\":\"22.533866\",\"leftBottomLat\":\"22.527941\",\"rightTopLong\":\"113.935366\",\"function\":\"105\"}";			
			data="{\"leftTopLong\":\"121.699115\",\"uid\":\"ffffffff-8164-2440-ffff-ffff9847a99f\",\"myLong\":\"114.038028\",\"myLat\":\"22.541799\",\"leftTopLat\":\"25.209926\",\"leftBottomLat\":\"25.206967\",\"rightTopLong\":\"121.701406\",\"function\":\"105\"}";
			data="{\"leftTopLong\":\"114.038339\",\"uid\":\"00000000-06bd-2834-ffff-ffff9847a981\",\"myLong\":\"114.038056\",\"myLat\":\"22.541799\",\"leftTopLat\":\"22.545174\",\"leftBottomLat\":\"22.531772\",\"rightTopLong\":\"114.048526\",\"function\":\"105\",\"maptype\":\"baidu\"}";
		}else if(api.equalsIgnoreCase("106")){
			json.put("uid", "ffffffff-8180-0234-ffff-ffffeb71ecc6");
			json.put("function", "106");
			json.put("mac", "wifi_mac.......");
			json.put("name", "KKKK");

			data=json.toString();	
			data="{\"uid\":\"00000000-595e-96c4-ffff-ffffda4203bb\",\"name\":\"CMCC\",\"mac\":\"00:23:89:21:ff:60\",\"function\":\"106\"}";

		}else if(api.equalsIgnoreCase("107")){
			json.put("uid", "1122334455");
			json.put("function", "107");
			json.put("username", "wifi_mac.......");

			data=json.toString();	
			data="{\"uid\":\"ffffffff-8164-2440-ffff-ffff9847a99f\",\"username\":\"\",\"function\":\"107\"}";
		}else if(api.equalsIgnoreCase("108") ){
			json.put("uid", "1122334455");
			json.put("function", api);

			JSONObject item=new JSONObject();
			item.put("mac", "9c:d2:1e:e6:59:6b");
			item.put("name", "CMCC");
			
			jarry.add(item);
			
			item=new JSONObject();
			item.put("mac", "78:54:2e:56:e0:16");
			item.put("name", "Shared");
			
			jarry.add(item);	
			
			item=new JSONObject();
			item.put("mac", "9c:d2:1e:f9:48:e9");
			item.put("name", "CMCC");
			
			jarry.add(item);
			
			item=new JSONObject();
			item.put("mac", "e0:05:c5:30:4a:18");
			item.put("name", "Shared");
			
			jarry.add(item);			
		
			json.put("data", jarry);
		
		data=json.toString();
		data="{\"data\":[{\"mac\":\"d8:24:bd:77:38:b9\",\"name\":\"AORA_YW3\"},{\"mac\":\"c8:e7:d8:3c:dd:15\",\"name\":\"\"},{\"mac\":\"00:23:89:21:ff:62\",\"name\":\"CMCC-FREE\"},{\"mac\":\"00:23:89:21:ff:61\",\"name\":\"CMCC-AUTO\"},{\"mac\":\"bc:d1:77:fc:df:2e\",\"name\":\"AORA_XST\"},{\"mac\":\"00:23:89:21:ff:60\",\"name\":\"CMCC\"},{\"mac\":\"00:36:76:08:a3:15\",\"name\":\"GGT\"},{\"mac\":\"70:62:b8:6f:24:c8\",\"name\":\"LIANG  909\"},{\"mac\":\"90:94:e4:30:a8:b0\",\"name\":\"AORA_XZ\"},{\"mac\":\"00:23:89:21:ca:41\",\"name\":\"CMCC-AUTO\"},{\"mac\":\"00:23:89:21:ca:40\",\"name\":\"CMCC\"},{\"mac\":\"00:26:5a:f1:ff:13\",\"name\":\"Triumph\"},{\"mac\":\"2c:b0:5d:27:b5:3f\",\"name\":\"fairtek\"},{\"mac\":\"94:0c:6d:49:b2:2a\",\"name\":\"TPSW\"},{\"mac\":\"00:22:aa:cf:2c:04\",\"name\":\"THINK\"},{\"mac\":\"cc:53:b5:e2:ea:14\",\"name\":\"ChinaNet-fmvV\"},{\"mac\":\"34:e0:cf:12:f9:48\",\"name\":\"ChinaNet-LTKW\"},{\"mac\":\"20:e5:2a:65:01:c8\",\"name\":\"CAROL-FTK\"}],\"uid\":\"ffffffff-eb37-f19f-ffff-ffffdb0b88ed\",\"function\":\"108\"}";
		//data=new test_3des().desEncrypt(data);
		//header.put("WIFI_ENCRIPTION", "true");		
		
		//data="{\"data\":[{\"wifi_ priority\":4,\"wifi_name\":\"AORA_YW3\",\"wifi_mac\":\"d8:24:bd:77:38:b9\"},{\"wifi_ priority\":3,\"wifi_name\":\"AORA_YW2\",\"wifi_mac\":\"90:94:e4:30:a8:b0\"},{\"wifi_ priority\":4,\"wifi_name\":\"AORA_YF1\",\"wifi_mac\":\"bc:d1:77:fc:df:2e\"},{\"wifi_ priority\":4,\"wifi_name\":\"Triumph\",\"wifi_mac\":\"00:26:5a:f1:ff:13\"},{\"wifi_ priority\":4,\"wifi_name\":\"erik\",\"wifi_mac\":\"7c:c3:a1:b8:2d:b2\"},{\"wifi_ priority\":4,\"wifi_name\":\"AORA_YW1\",\"wifi_mac\":\"d8:24:bd:77:ba:ac\"},{\"wifi_ priority\":3,\"wifi_name\":\"liang\",\"wifi_mac\":\"a8:15:4d:f4:f6:b8\"},{\"wifi_ priority\":2,\"wifi_name\":\"AORA_YF2\",\"wifi_mac\":\"00:18:e7:cf:bc:6c\"},{\"wifi_ priority\":3,\"wifi_name\":\"ChinaNet-VWPE\",\"wifi_mac\":\"34:e0:cf:12:d5:40\"},{\"wifi_ priority\":4,\"wifi_name\":\"DUNHILL\",\"wifi_mac\":\"b8:55:10:b6:18:7a\"},{\"wifi_ priority\":4,\"wifi_name\":\"CMCC-AUTO\",\"wifi_mac\":\"00:23:89:21:ca:41\"},{\"wifi_ priority\":4,\"wifi_name\":\"CMCC\",\"wifi_mac\":\"00:23:89:21:ca:40\"},{\"wifi_ priority\":4,\"wifi_name\":\"CMCC-FREE\",\"wifi_mac\":\"00:23:89:21:ca:42\"},{\"wifi_ priority\":1,\"wifi_name\":\"THINK\",\"wifi_mac\":\"00:22:aa:cf:2c:04\"},{\"wifi_ priority\":1,\"wifi_name\":\"SYNTAX-SZ\",\"wifi_mac\":\"d8:5d:4c:47:da:56\"}],\"uid\":\"ffffffff-8180-0234-ffff-ffffeb71ecc6\",\"function\":\"108\"}";		
		//data=new test_3des().desEncrypt(data);
		//header.put("WIFI_ENCRIPTION", "true");
		
		}else if(api.equalsIgnoreCase("109")){
			json.put("uid", "1122334455");
			json.put("function", "109");
			json.put("city", "������");

			data=json.toString();
			
		}else if(api.equalsIgnoreCase("111")){
			json.put("uid", "1122334455");
			json.put("function", "111");
			
			JSONObject item=new JSONObject();
			item.put("mac", "23424:234234");
			jarry.add(item);			
		
			json.put("data", jarry);			

			data=json.toString();			
		}else if(api.equalsIgnoreCase("112")){
			json.put("uid", "1122334455");
			json.put("function", "112");
			json.put("city", "SZ");
			json.put("longitude", "324234");
			json.put("latitude", "11111");
			json.put("mac", "23424:234234");
			json.put("name", "11111");
			json.put("password", "12345678");
			
	

			data=json.toString();
			
			
		}else if(api.equalsIgnoreCase("113")){
			json.put("uid", "1122334455");
			json.put("function", "113");
			json.put("city", "SZ");
			json.put("longitude", "324234");
			json.put("latitude", "11111");
			json.put("mac", "23424:234234");
			json.put("name", "11111");
			json.put("password", "12345678");

			data=json.toString();	
		}else if(api.equalsIgnoreCase("114")){
			json.put("uid", "1122334455");
			json.put("function", "114");

			json.put("mac", "d8:24:bd:77:38:b9");
			json.put("name", "11111");

			data=json.toString();				

		}else if(api.equalsIgnoreCase("201")){
			json.put("uid", "1122334455");
			json.put("function", "201");
			json.put("account", "13620986524");

			data=json.toString();						
		}else if(api.equalsIgnoreCase("202")){
			json.put("uid", "1122334455");
			json.put("function", "202");
			json.put("reg_account", "13620986524");
			json.put("reg_password", "123456");
			json.put("reg_code", "092362");
			data=json.toString();
			data="{\"code\":\"123456\",\"password\":\"123456\",\"account\":\"13798570323\",\"function\":\"202\",\"uid\":\"1BB89894-B747-4E69-B2CA-86CCCEB7FF4A\"}";
		}else if(api.equalsIgnoreCase("203")){
			json.put("uid", "00000000-4855-65a7-07e0-564830cb4573");
			json.put("function", "203");
			json.put("username", "");
			json.put("password", "");
			
			data=json.toString();	
		}else if(api.equalsIgnoreCase("204")){
			json.put("uid", "1122334455");
			json.put("function", "204");
			json.put("account", "13620986524");
			data=json.toString();				
		}else if(api.equalsIgnoreCase("205")){
			json.put("uid", "1122334455");
			json.put("function", "205");
			json.put("account", "13620986524");
			json.put("password", "123456");
			json.put("code", "045362545");
			data=json.toString();				
		}else if(api.equalsIgnoreCase("206")){
			json.put("uid", "1122334455");
			json.put("function", "206");
			json.put("account", "13620986524");
			json.put("password", "12345655");

			data=json.toString();						
		}else if(api.equalsIgnoreCase("207")){
			
			buf=this.readFromFile("C:/Documents and Settings/Administrator/����/2.jpg");
			json.put("uid", "1122334455");
			json.put("function", "207");
			json.put("account", "13620986524");
			json.put("image", base64.encode(buf) );
			data=json.toString();
			//data="{\"name\":\"ding\",\"account\":\"13798570323\",\"uid\":\"9CED57C5-F185-4A82-B098-F97B438B5E9C\",\"function\":\"207\",\"image\":null}";
			
		}else if(api.equalsIgnoreCase("208")){
			json.put("uid", "1122334455");
			json.put("function", "208");
			json.put("account", "15963617385");
			json.put("effective_days", "30");
			data=json.toString();			
		}else if(api.equalsIgnoreCase("209")){
			json.put("uid", "1122334455");
			json.put("function", "209");


			data=json.toString();
		}else if(api.equalsIgnoreCase("210")){
			json.put("uid", "1122334455");
			json.put("function", "210");
			//json.put("account", "");

			data=json.toString();
			
		}else if(api.equalsIgnoreCase("211")){
			json.put("uid", "1122334455");
			json.put("function", "211");
			json.put("account", "13620986524");
			json.put("ask", "��������ʱЧ�ȱȱȱ�");
			json.put("contact", "qq:ssssss");

			data=json.toString();
			data="{\"uid\":\"web_interface\",\"function\":\"211\",\"account\":\"web_account\",\"ask\":\"12343\",\"contact\":\"234324@ddd.com\"}";
		}else if(api.equalsIgnoreCase("212")){
			json.put("uid", "00000000-635b-5554-9ab7-1dea35e6d525");
			json.put("function", "212");
			json.put("username", "");
			data=json.toString();	
			//data="{\"uid\":\"00000000-4a80-584f-ffff-ffffeb71ecc6\",\"account\":\"18818687815\",\"function\":\"212\"}";

		}else if(api.equalsIgnoreCase("213")){
			json.put("uid", "1122334455");
			json.put("function", "212");
			json.put("account", "13620986524");

			data=json.toString();			
			
		}else if(api.equalsIgnoreCase("215") || api.equalsIgnoreCase("221")){
			json.put("uid", "1122334455");
			json.put("function", api);
			data=json.toString();
		}else if(api.equalsIgnoreCase("216")){
			json.put("uid", "1122334455");
			json.put("function", "216");
			data=json.toString();	
		}else if(api.equalsIgnoreCase("217")){
			json.put("uid", "1122334455");
			json.put("function", "217");
			data=json.toString();
		}else if(api.equalsIgnoreCase("218")){
			json.put("uid", "1122334455");
			json.put("account", "13620986524");
			json.put("ad_name", "����");
			json.put("platform", "Andorid");
			json.put("function", "218");
			data=json.toString();
		}else if(api.equalsIgnoreCase("219")){
			json.put("uid", "1122334455");
			json.put("account", "13620986524");
			json.put("function", "219");
			data=json.toString();
		}else if(api.equalsIgnoreCase("220")){
			String str="ewoJInNpZ25hdHVyZSIgPSAiQXNhUzhCcjdiWjl4cUVlRE14MWh1WFV0WU9QSG1HbEExZkdSTGJueTZLdHA0S3hCd2lpUk5tNG9DTFRZT1BrOUw5anlVV0tVUzVnTXVmRTBNNlphZTVFL0lXYnRVYkFYcCtQL25JOGwza2hvOVF3QnkxNlRGVi9KZllFYmdtS0J5R3pjQ3JPaGIreWR3MTJIb0dFNHByUXFCM0ZzaTVoYjA2SWJFQ0ZScWF0b0FBQURWekNDQTFNd2dnSTdvQU1DQVFJQ0NCdXA0K1BBaG0vTE1BMEdDU3FHU0liM0RRRUJCUVVBTUg4eEN6QUpCZ05WQkFZVEFsVlRNUk13RVFZRFZRUUtEQXBCY0hCc1pTQkpibU11TVNZd0pBWURWUVFMREIxQmNIQnNaU0JEWlhKMGFXWnBZMkYwYVc5dUlFRjFkR2h2Y21sMGVURXpNREVHQTFVRUF3d3FRWEJ3YkdVZ2FWUjFibVZ6SUZOMGIzSmxJRU5sY25ScFptbGpZWFJwYjI0Z1FYVjBhRzl5YVhSNU1CNFhEVEUwTURZd056QXdNREl5TVZvWERURTJNRFV4T0RFNE16RXpNRm93WkRFak1DRUdBMVVFQXd3YVVIVnlZMmhoYzJWU1pXTmxhWEIwUTJWeWRHbG1hV05oZEdVeEd6QVpCZ05WQkFzTUVrRndjR3hsSUdsVWRXNWxjeUJUZEc5eVpURVRNQkVHQTFVRUNnd0tRWEJ3YkdVZ1NXNWpMakVMTUFrR0ExVUVCaE1DVlZNd2daOHdEUVlKS29aSWh2Y05BUUVCQlFBRGdZMEFNSUdKQW9HQkFNbVRFdUxnamltTHdSSnh5MW9FZjBlc1VORFZFSWU2d0Rzbm5hbDE0aE5CdDF2MTk1WDZuOTNZTzdnaTNvclBTdXg5RDU1NFNrTXArU2F5Zzg0bFRjMzYyVXRtWUxwV25iMzRucXlHeDlLQlZUeTVPR1Y0bGpFMU93QytvVG5STStRTFJDbWVOeE1iUFpoUzQ3VCtlWnRERWhWQjl1c2szK0pNMkNvZ2Z3bzdBZ01CQUFHamNqQndNQjBHQTFVZERnUVdCQlNKYUVlTnVxOURmNlpmTjY4RmUrSTJ1MjJzc0RBTUJnTlZIUk1CQWY4RUFqQUFNQjhHQTFVZEl3UVlNQmFBRkRZZDZPS2RndElCR0xVeWF3N1hRd3VSV0VNNk1BNEdBMVVkRHdFQi93UUVBd0lIZ0RBUUJnb3Foa2lHOTJOa0JnVUJCQUlGQURBTkJna3Foa2lHOXcwQkFRVUZBQU9DQVFFQWVhSlYyVTUxcnhmY3FBQWU1QzIvZkVXOEtVbDRpTzRsTXV0YTdONlh6UDFwWkl6MU5ra0N0SUl3ZXlOajVVUllISytIalJLU1U5UkxndU5sMG5rZnhxT2JpTWNrd1J1ZEtTcTY5Tkluclp5Q0Q2NlI0Szc3bmI5bE1UQUJTU1lsc0t0OG9OdGxoZ1IvMWtqU1NSUWNIa3RzRGNTaVFHS01ka1NscDRBeVhmN3ZuSFBCZTR5Q3dZVjJQcFNOMDRrYm9pSjNwQmx4c0d3Vi9abEwyNk0ydWVZSEtZQ3VYaGRxRnd4VmdtNTJoM29lSk9PdC92WTRFY1FxN2VxSG02bTAzWjliN1BSellNMktHWEhEbU9Nazd2RHBlTVZsTERQU0dZejErVTNzRHhKemViU3BiYUptVDdpbXpVS2ZnZ0VZN3h4ZjRjemZIMHlqNXdOelNHVE92UT09IjsKCSJwdXJjaGFzZS1pbmZvIiA9ICJld29KSW05eWFXZHBibUZzTFhCMWNtTm9ZWE5sTFdSaGRHVXRjSE4wSWlBOUlDSXlNREUwTFRBNExUSXhJREl3T2pBek9qVTFJRUZ0WlhKcFkyRXZURzl6WDBGdVoyVnNaWE1pT3dvSkluVnVhWEYxWlMxcFpHVnVkR2xtYVdWeUlpQTlJQ0ppTVRka1pXTXdNell4Wm1SbU16azBNR0poT0RObVpXRTVaVEF4WkdVMk1UTTNPVEF4T0daaElqc0tDU0p2Y21sbmFXNWhiQzEwY21GdWMyRmpkR2x2YmkxcFpDSWdQU0FpTVRBd01EQXdNREV5TURrMU1EZ3hOeUk3Q2draVluWnljeUlnUFNBaU1pNHdJanNLQ1NKMGNtRnVjMkZqZEdsdmJpMXBaQ0lnUFNBaU1UQXdNREF3TURFeU1EazFNRGd4TnlJN0Nna2ljWFZoYm5ScGRIa2lJRDBnSWpFaU93b0pJbTl5YVdkcGJtRnNMWEIxY21Ob1lYTmxMV1JoZEdVdGJYTWlJRDBnSWpFME1EZzJOelkyTXpVME5qTWlPd29KSW5WdWFYRjFaUzEyWlc1a2IzSXRhV1JsYm5ScFptbGxjaUlnUFNBaU5FSXdSRE5DUXpNdE9ESkVPQzAwTmtaRkxUaENRelV0UXpFMU5EVTJOVE5CTWtGRElqc0tDU0p3Y205a2RXTjBMV2xrSWlBOUlDSmpiMjB1ZDJsbWFXVnVhbTk1TG1sdmN5NTNhV1pwYzNWeVptbHVaeTVwWVhBdWRHbGxjakVpT3dvSkltbDBaVzB0YVdRaUlEMGdJamt4TURjM05qTXlPU0k3Q2draVltbGtJaUE5SUNKamIyMHVkMmxtYVdWdWFtOTVMbWx2Y3k1M2FXWnBjM1Z5Wm1sdVp5STdDZ2tpY0hWeVkyaGhjMlV0WkdGMFpTMXRjeUlnUFNBaU1UUXdPRFkzTmpZek5UUTJNeUk3Q2draWNIVnlZMmhoYzJVdFpHRjBaU0lnUFNBaU1qQXhOQzB3T0MweU1pQXdNem93TXpvMU5TQkZkR012UjAxVUlqc0tDU0p3ZFhKamFHRnpaUzFrWVhSbExYQnpkQ0lnUFNBaU1qQXhOQzB3T0MweU1TQXlNRG93TXpvMU5TQkJiV1Z5YVdOaEwweHZjMTlCYm1kbGJHVnpJanNLQ1NKdmNtbG5hVzVoYkMxd2RYSmphR0Z6WlMxa1lYUmxJaUE5SUNJeU1ERTBMVEE0TFRJeUlEQXpPakF6T2pVMUlFVjBZeTlIVFZRaU93cDkiOwoJImVudmlyb25tZW50IiA9ICJTYW5kYm94IjsKCSJwb2QiID0gIjEwMCI7Cgkic2lnbmluZy1zdGF0dXMiID0gIjAiOwp9";
			json.put("uid", "1122334455");
			json.put("account", "13510281094");
			json.put("function", "220");
			json.put("version", "100.1");
			json.put("verify_code", str);
			
			data=json.toString();

		}else if(api.equalsIgnoreCase("222")){
			String str="";
			json.put("uid", "1122334455");
			json.put("function", "222");
			json.put("account", "13510281094");
			json.put("error_description", "xxxxxx");
			json.put("error_function", "101");
			json.put("city", "SZ");
			json.put("longitude", "324234");
			json.put("latitude", "11111");
			json.put("version", "100.1");
			json.put("platform", "android");
			
			data=json.toString();
		}else if(api.equalsIgnoreCase("223")){
			String str="";
			json.put("uid", "1122334455");
			json.put("function", "223");
			json.put("username", "13510281094");
			json.put("collect_description", "xxxxxx");
			json.put("collect_function", "101");
			json.put("city", "SZ");
			json.put("longitude", "324234");
			json.put("latitude", "11111");
			json.put("version", "100.1");
			json.put("platform", "android");
			
			data=json.toString();
		}else if(api.equalsIgnoreCase("224")){
			String str="";
			json.put("uid", "1122334455");
			json.put("function", "224");
			json.put("version", "1.0");
			data=json.toString();
																		
		}else if(api.equalsIgnoreCase("225")){
			String str="";
			json.put("uid", "1122334455665456");
			json.put("function", "225");
			json.put("account", "13510281094");
			json.put("city", "SZ");
			json.put("longitude", "324234");
			json.put("latitude", "11111");
			
			JSONObject item=new JSONObject();
			item.put("wifi_mac", "sdfsdfsdfgdfg");
			item.put("wifi_name", "CMCC");
			item.put("wifi_pass", "734534");
			item.put("wifi_encrypt_method", "7sdfsd");
			jarry.add(item);
			
			item=new JSONObject();
			item.put("wifi_mac", "23424324dfgdfg");
			item.put("wifi_name", "Shared");
			item.put("wifi_pass", "6345345");
			item.put("wifi_encrypt_method", "6sdfsd");
			jarry.add(item);			
		
			json.put("data", jarry);
			
			data=json.toString();
			data=json.toString();		
		}else if(api.equalsIgnoreCase("301")){
			String str="";
			json.put("uid", "1122334455665456");
			json.put("function", "301");
			json.put("account", "hk-123");
			
			JSONObject item=new JSONObject();
			item.put("wifi_mac", "sdfsdfsdfgdfg");
			item.put("wifi_name", "CMCC");
			item.put("wifi_pass", "734534");
			item.put("city", "SZ");
			item.put("longitude", "324234");
			item.put("latitude", "11111");
			
			jarry.add(item);
			
			item=new JSONObject();
			item.put("wifi_mac", "23424324dfgdfg");
			item.put("wifi_name", "Shared");
			item.put("wifi_pass", "6345345");
			item.put("city", "SZ");
			item.put("longitude", "324234");
			item.put("latitude", "11111");
			
			jarry.add(item);			
		
			json.put("data", jarry);
			
			data=json.toString();			
		}else if(api.equalsIgnoreCase("302")){
			String str="";
			json.put("uid", "1122334455665456");
			json.put("function", "302");
			json.put("account", "hk-123");
			json.put("begin_date","2014-10-01");
			json.put("end_date","");
			data=json.toString();
		}else if(api.equalsIgnoreCase("300")){
			String str="ewoJInNpZ25hdHVyZSIgPSAiQXNhUzhCcjdiWjl4cUVlRE14MWh1WFV0WU9QSG1HbEExZkdSTGJueTZLdHA0S3hCd2lpUk5tNG9DTFRZT1BrOUw5anlVV0tVUzVnTXVmRTBNNlphZTVFL0lXYnRVYkFYcCtQL25JOGwza2hvOVF3QnkxNlRGVi9KZllFYmdtS0J5R3pjQ3JPaGIreWR3MTJIb0dFNHByUXFCM0ZzaTVoYjA2SWJFQ0ZScWF0b0FBQURWekNDQTFNd2dnSTdvQU1DQVFJQ0NCdXA0K1BBaG0vTE1BMEdDU3FHU0liM0RRRUJCUVVBTUg4eEN6QUpCZ05WQkFZVEFsVlRNUk13RVFZRFZRUUtEQXBCY0hCc1pTQkpibU11TVNZd0pBWURWUVFMREIxQmNIQnNaU0JEWlhKMGFXWnBZMkYwYVc5dUlFRjFkR2h2Y21sMGVURXpNREVHQTFVRUF3d3FRWEJ3YkdVZ2FWUjFibVZ6SUZOMGIzSmxJRU5sY25ScFptbGpZWFJwYjI0Z1FYVjBhRzl5YVhSNU1CNFhEVEUwTURZd056QXdNREl5TVZvWERURTJNRFV4T0RFNE16RXpNRm93WkRFak1DRUdBMVVFQXd3YVVIVnlZMmhoYzJWU1pXTmxhWEIwUTJWeWRHbG1hV05oZEdVeEd6QVpCZ05WQkFzTUVrRndjR3hsSUdsVWRXNWxjeUJUZEc5eVpURVRNQkVHQTFVRUNnd0tRWEJ3YkdVZ1NXNWpMakVMTUFrR0ExVUVCaE1DVlZNd2daOHdEUVlKS29aSWh2Y05BUUVCQlFBRGdZMEFNSUdKQW9HQkFNbVRFdUxnamltTHdSSnh5MW9FZjBlc1VORFZFSWU2d0Rzbm5hbDE0aE5CdDF2MTk1WDZuOTNZTzdnaTNvclBTdXg5RDU1NFNrTXArU2F5Zzg0bFRjMzYyVXRtWUxwV25iMzRucXlHeDlLQlZUeTVPR1Y0bGpFMU93QytvVG5STStRTFJDbWVOeE1iUFpoUzQ3VCtlWnRERWhWQjl1c2szK0pNMkNvZ2Z3bzdBZ01CQUFHamNqQndNQjBHQTFVZERnUVdCQlNKYUVlTnVxOURmNlpmTjY4RmUrSTJ1MjJzc0RBTUJnTlZIUk1CQWY4RUFqQUFNQjhHQTFVZEl3UVlNQmFBRkRZZDZPS2RndElCR0xVeWF3N1hRd3VSV0VNNk1BNEdBMVVkRHdFQi93UUVBd0lIZ0RBUUJnb3Foa2lHOTJOa0JnVUJCQUlGQURBTkJna3Foa2lHOXcwQkFRVUZBQU9DQVFFQWVhSlYyVTUxcnhmY3FBQWU1QzIvZkVXOEtVbDRpTzRsTXV0YTdONlh6UDFwWkl6MU5ra0N0SUl3ZXlOajVVUllISytIalJLU1U5UkxndU5sMG5rZnhxT2JpTWNrd1J1ZEtTcTY5Tkluclp5Q0Q2NlI0Szc3bmI5bE1UQUJTU1lsc0t0OG9OdGxoZ1IvMWtqU1NSUWNIa3RzRGNTaVFHS01ka1NscDRBeVhmN3ZuSFBCZTR5Q3dZVjJQcFNOMDRrYm9pSjNwQmx4c0d3Vi9abEwyNk0ydWVZSEtZQ3VYaGRxRnd4VmdtNTJoM29lSk9PdC92WTRFY1FxN2VxSG02bTAzWjliN1BSellNMktHWEhEbU9Nazd2RHBlTVZsTERQU0dZejErVTNzRHhKemViU3BiYUptVDdpbXpVS2ZnZ0VZN3h4ZjRjemZIMHlqNXdOelNHVE92UT09IjsKCSJwdXJjaGFzZS1pbmZvIiA9ICJld29KSW05eWFXZHBibUZzTFhCMWNtTm9ZWE5sTFdSaGRHVXRjSE4wSWlBOUlDSXlNREUwTFRBNExUSXhJREl3T2pBek9qVTFJRUZ0WlhKcFkyRXZURzl6WDBGdVoyVnNaWE1pT3dvSkluVnVhWEYxWlMxcFpHVnVkR2xtYVdWeUlpQTlJQ0ppTVRka1pXTXdNell4Wm1SbU16azBNR0poT0RObVpXRTVaVEF4WkdVMk1UTTNPVEF4T0daaElqc0tDU0p2Y21sbmFXNWhiQzEwY21GdWMyRmpkR2x2YmkxcFpDSWdQU0FpTVRBd01EQXdNREV5TURrMU1EZ3hOeUk3Q2draVluWnljeUlnUFNBaU1pNHdJanNLQ1NKMGNtRnVjMkZqZEdsdmJpMXBaQ0lnUFNBaU1UQXdNREF3TURFeU1EazFNRGd4TnlJN0Nna2ljWFZoYm5ScGRIa2lJRDBnSWpFaU93b0pJbTl5YVdkcGJtRnNMWEIxY21Ob1lYTmxMV1JoZEdVdGJYTWlJRDBnSWpFME1EZzJOelkyTXpVME5qTWlPd29KSW5WdWFYRjFaUzEyWlc1a2IzSXRhV1JsYm5ScFptbGxjaUlnUFNBaU5FSXdSRE5DUXpNdE9ESkVPQzAwTmtaRkxUaENRelV0UXpFMU5EVTJOVE5CTWtGRElqc0tDU0p3Y205a2RXTjBMV2xrSWlBOUlDSmpiMjB1ZDJsbWFXVnVhbTk1TG1sdmN5NTNhV1pwYzNWeVptbHVaeTVwWVhBdWRHbGxjakVpT3dvSkltbDBaVzB0YVdRaUlEMGdJamt4TURjM05qTXlPU0k3Q2draVltbGtJaUE5SUNKamIyMHVkMmxtYVdWdWFtOTVMbWx2Y3k1M2FXWnBjM1Z5Wm1sdVp5STdDZ2tpY0hWeVkyaGhjMlV0WkdGMFpTMXRjeUlnUFNBaU1UUXdPRFkzTmpZek5UUTJNeUk3Q2draWNIVnlZMmhoYzJVdFpHRjBaU0lnUFNBaU1qQXhOQzB3T0MweU1pQXdNem93TXpvMU5TQkZkR012UjAxVUlqc0tDU0p3ZFhKamFHRnpaUzFrWVhSbExYQnpkQ0lnUFNBaU1qQXhOQzB3T0MweU1TQXlNRG93TXpvMU5TQkJiV1Z5YVdOaEwweHZjMTlCYm1kbGJHVnpJanNLQ1NKdmNtbG5hVzVoYkMxd2RYSmphR0Z6WlMxa1lYUmxJaUE5SUNJeU1ERTBMVEE0TFRJeUlEQXpPakF6T2pVMUlFVjBZeTlIVFZRaU93cDkiOwoJImVudmlyb25tZW50IiA9ICJTYW5kYm94IjsKCSJwb2QiID0gIjEwMCI7Cgkic2lnbmluZy1zdGF0dXMiID0gIjAiOwp9";
			json.put("receipt-data", str);
			data=json.toString();
		}else if(api.equalsIgnoreCase("311")){
			json.put("uid", "1122334455");
			json.put("function", api);
			data=json.toString();			
		}else{
			
			
		}
				
		System.out.println(data);			
		System.out.println(new String(postdata(url, data, header),"UTF8"));
		
		
	}
	

	public void test(){
		HashMap header=new HashMap();
		header.put("Cookie", "JSESSIONID=12JpPVtQ83Ny2tmSWFXRZFSVFxq6lfZ2mw3dmtX1QvJX0VvXXK1c!1247219512!-791961461");
		String data="";

		System.out.println(new String(postdata(posturl, data, header)));
		
		
	}
	
	//**** payserver  ***//
	public void test2(){
		HashMap header=new HashMap();
		header.put("Cookie", "JSESSIONID=XRYpSGqXbh8K295np8hVtb1sh47ZpYXzhHRYhnGynpgshNfBsQJs!-3668315!1380362839666");
		header.put("Referer", "http://www.gpsspg.com/apps/maps/baidu_140802.htm");
		//header.put("Cookie", "mytest=this is a another cookie value;JSESSIONID=12JpPVtQ83Ny2tmSWFXRZFSVFxq6lfZ2mw3dmtX1QvJX0VvXXK1c!1247219512!-791961461");
		//System.out.println(new String(postdata("http://192.168.0.132/mypj/test/test1.php?jj=23&pp=24", "test1=11&test2=22", header)));
		//System.out.println(new String(postdata("http://127.0.0.1:9000/notify", "{\"pay_order_id\":\"5674da35-c9fb-4d75-80f9-207280fd2ccd\",\"type\":\"6\"}", header)));
		//System.out.println(new String(postdata("http://test.myaora.net:9000/notify", "{\"pay_order_id\":\"5674da35-c9fb-4d75-80f9-207280fd2ccd\",\"type\":\"2\"}", header)));
		//System.out.println(new String(postdata("http://localhost:8080/queryData/pctools/doLoginService.action", "username=pct����2&userpwd=123456", header)));
		System.out.println(new String(postdata("http://www.gpsspg.com/ajax/maps_get.aspx?lat=22.541817&lng=114.038079&type=0", "", header)));
		
	}
	
	
	
	public void test1(){
		HashMap header=new HashMap();
		header.put("Cookie", "JSESSIONID=zQVjTH3hWtPhBr46328nlRY2Trn1GV1jdGR74QWFM2bkdR0Xp7c9!1392321345!1405564826362");
		//header.put("Cookie", "mytest=this is a another cookie value;JSESSIONID=12JpPVtQ83Ny2tmSWFXRZFSVFxq6lfZ2mw3dmtX1QvJX0VvXXK1c!1247219512!-791961461");
		//System.out.println(new String(postdata("http://192.168.0.132/mypj/test/test1.php?jj=23&pp=24", "test1=11&test2=22", header)));
		//System.out.println(new String(postdata("http://my3app.three.com.hk/iPhone/iPhone.do?function=req_EnquiryForm&lang=chi&uat=1", "{\"sendername\":\"test by kelun\",\"sender\":\"test@hthk.com\",\"mob_num\":\"65123456\",\"net_type\":\"3G\",\"subject\":\"test mail\",\"content\":\"test content�yԇ\"}", header)));
		//System.out.println(new String(postdata("http://172.20.69.95/iPhone/iPhone.do?function=req_EnquiryForm&lang=chi&uat=1", "{\"sendername\":\"test by kelun\",\"sender\":\"test@hthk.com\",\"mob_num\":\"65123456\",\"net_type\":\"3G\",\"subject\":\"test mail\",\"content\":\"test content�yԇ", header)));
		//System.out.println(new String(postdata("http://my3app.three.com.hk/iPhone/iPhone.do?function=login&lang=chi&uat=1", "username=63354541&password=63354541&versionno=1.02", header)));
		//System.out.println(new String(postdata("http://my3app.three.com.hk/iPhone/iPhone.do?function=login&lang=chi&uat=1", "username=63354541&password=63354541&versionno=1.02", header)));
		//System.out.println(new String(postdata("http://my3app.three.com.hk/iPhone/iPhone.do?function=req_resetWifiPassword&lang=eng", "email=kelun_@hotmail.com&password=4567890", header)));
		//System.out.println(new String(postdata("http://my3app.three.com.hk/iPhone/iPhone.do?function=get_My3ServicePlan&lang=chi&uat=0", "", header)));
//		System.out.println(new String(postdata("http://172.20.69.95:7001/iPhone/iPhone.do?function=login&lang=chi&uat=1", "username=95013914&password=123456&versionno=1.02", header)));
		//System.out.println(new String(postdata("http://172.20.69.93/iPhone/iPhone.do?function=req_getFamilyList&lang=eng&uat=1", "type=All", header)));		
		//System.out.println(new String(postdata("http://172.20.69.95//iPhone/iPhone.do?function=get_UpdateMyProfile&lang=chi&uat=0","",header)));
		//System.out.println(new String(postdata("http://172.20.69.95/iPhone/iPhone.do?function=get_UsageNotYetBilled&lang=chi&uat=1", "msisdn=63357909", header)));
		//System.out.println(new String(postdata("http://my3app.three.com.hk/iPhone/iPhone.do?function=get_faqAllTree&lang=chi&uat=1", "msisdn=51900253", header)));
		//System.out.println(new String(postdata("http://172.20.69.95/iPhone/iPhone.do?function=login&lang=chi&uat=1", "username=94948015&password=123456&versionno=1.02", header)));		
		//System.out.println(new String(postdata("http://172.20.69.95/iPhone/iPhone.do?function=req_getFamilyList&lang=chi&uat=1", "type=All&msisdn=94948015", header)));

		//System.out.println(new String(postdata("http://172.20.73.237:7002/3Usage/iPhone.do?function=login&lang=chi&uat=1", "username=63357909&password=123456&versionno=1.02", header)));  
		//System.out.println(new String(postdata("http://172.20.73.237:7002/iPhone/iPhone.do?function=req_setCallForward&lang=chi","{\"sessionid\":\"jajkf879438976erfkgasjhsedf\",\"cfw\":[{\"type\":\"all\",\"fwdTo\":\"cancel\"},{ \"type\":\"noAns\",\"fwdTo\":\"vm\"},{ \"type\":\"notRec\",\"fwdTo\":\"vvm\"},{ \"type\":\"busy\",\"fwdTo\":\"cancel\"}]}",header)));
		
		//System.out.println(new String(postdata("http://172.20.69.95:7001/3Usage/iPhone.do?function=login&lang=chi&uat=1", "username=63357909&password=123456&versionno=1.02", header)));  
		//System.out.println(new String(postdata("http://172.20.69.95:7001/3Usage/iPhone.do?function=req_getBillUsageMeter&lang=chi","",header)));
		//System.out.println(new String(postdata("http://172.20.69.95:7001/iPhone/iPhone.do?funtion=req_getVASList&lang=chi","",header)));
		System.out.println(new String(postdata("http://172.20.69.95:7001/iPhone/VASList.do","",header)));

	//	System.out.println(new String(postdata("http://172.20.69.95/iPhone/iPhone.do?function=login&lang=chi&uat=1", "username=63357909&password=123456&versionno=1.02", header)));
		//System.out.println(new String(postdata("http://172.20.69.95/iPhone/iPhone.do?function=req_generatedQRCode&lang=chi&uat=1", "type=All&msisdn=94948015", header)));
		
		//System.out.println(new String(postdata("http://my3app.three.com.hk/iPhone/iPhone.do?function=get_UsageNotYetBilled_MM_DUR&lang=chi&uat=1", "msisdn=94947801", header)));
		//System.out.println(new String(postdata("http://my3app.three.com.hk/iPhone/iPhone.do?function=login&lang=chi&uat=1","username=63354541&password=63354541&versionno=1.02", header)));
		//System.out.println(new String(postdata("http://172.20.73.237:7002/iPhone/iPhone.do?function=get_My3ServicePlan&lang=chi&uat=1","", header)));
		//System.out.println(new String(postdata("http://my3app.three.com.hk/iPhone/iPhone.do?function=req_getDataThreshold&lang=chi&uat=1","", header)));
		//System.out.println(new String(postdata("http://172.20.69.95/iPhone/iPhone.do?function=get_BillDetailedCallRec&lang=chi&uat=1", "invoicedate=20120831&membermobileno=63357909&membermobileid=0", header)));
		//System.out.println(new String(postdata("http://172.20.69.95/iPhone/inquireDetailCallRecords.do?invoicedate=20120831&lang=chi&membermobileno=51900253&memberaccountid=", "", header)));		
	}

	  private void loger(String s){
		  System.out.println(s);
	  }	
	public void sendsms() throws Exception {
		//广告群发通道
		//接口账号：1001@500933530002
		//接口 Key：7548C27E03DDDDC97C0E4A937AA443BD
		//通道组ID: 3134

	      Properties properties = new Properties();
	      properties.load(getClass().getResourceAsStream("/configuration/jdbc.properties") );

		 
	      String driver=properties.getProperty("jdbc.driverClassName"); loger("\t\t srcPath:"+driver);
	      String dbPath=properties.getProperty("jdbc.url");  			 loger("\t\t dbPath:"+dbPath);
	      String dbUser=properties.getProperty("jdbc.username");   	 loger("\t\t dbUser:"+dbUser);
	      String dbPwd=properties.getProperty("jdbc.password");	 	 loger("\t\t dbPwd:"+dbPwd);		
		
			 Connection conn=null;
			 PreparedStatement PreState=null;
			 PreparedStatement state=null;
			 ResultSet rs=null;
			 try{
				 
//				 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//		 	     conn=   ds.getConnection(); 
		         Class.forName(driver);
		         //dbPath="jdbc:mysql://127.0.0.1:3306/wifiapp?useUnicode=true&characterEncoding=utf8";
		         conn=DriverManager.getConnection(dbPath,dbUser,dbPwd);

		         //state=conn.prepareStatement("update wifilog_sum set processdate=now(),password=?,remark=?,city=?  where id=?");
			     PreState=conn.prepareStatement("SELECT * FROM tmp  where 1=1 and  process is null order by tmp1 limit 40000");
			     rs=PreState.executeQuery();
			     System.out.println(new Date().toLocaleString());
			     int i=0;
			     while(rs.next()){	      
		
			    	 String smscontent="亲爱的畅游粉丝们，无门槛上网时代来啦！wifi畅游新版不用U币、不用登陆，永久免费，分享热点还能赚红包。U币变身用户等级，以前有U币的粉丝们全部晋级成为银牌用户，将在下个版本（1月份）中体现，用户等级越高您所享受的福利越大，更多福利请随时";
			    	 	    smscontent="亲爱的畅游粉丝们，无门槛上网时代来啦！wifi畅游新版不用U币、不用登陆，永久免费，分享热点还能赚红包。U币变身用户等级，有U币的粉丝全部晋级为银牌用户，在下个版本（1月份）中体现，等级越高享受的福利越大，更多福利请随时关注wifi畅游。";
			    	 String content1="亲爱的WiFi畅游粉丝们，好消息来啦！WiFi畅游大改版即将推出! 账号登陆？不需要。U币？也不需要。真正的轻松一键联网，永久免费，分享热点还能赚红包。根据您的分享热点成绩，您已经成功晋级为新版WiFi畅游的";
			    	 String content2= "，您现有的热点数变成用户等级，将在下个版本（1月份）中体现，用户等级越高您所享受的福利越大，更多福利请随时关注WiFi畅游。";
			    	 
			    	 String mobile="13570804309";
			    	 
			    	 try{
				    	 mobile=rs.getString("tmp1");
				    	 //mobile="13620986524,13570804309";
				    	 //smscontent=content1+rs.getString("tmp3")+content2;
				    	 String url="http://smsapi.c123.cn/OpenPlatform/OpenApi?action=sendOnce&ac=1001@500933530002&authkey=7548C27E03DDDDC97C0E4A937AA443BD&cgid=3134&csid=101&c="+Tools.URLEncode(smscontent, "UTF-8")+"&m="+mobile;
				    	 byte[] response =postdata(url, "", null);
				    	 String responsestr=(response==null?"":new String(response));
				    	 System.out.println(responsestr);
				    	 System.out.println("****"+mobile +" ***"+String.valueOf(i));
				    	 i++;
			    	 }catch(Exception e){
			    		 e.printStackTrace();
			    	 }
			    	 
			    	// break;
			     }
			     System.out.println(new Date().toLocaleString());
			 }catch(Exception e){
				 e.printStackTrace();
			 }
	}
	
	

	public void job(){
		 String posturl="http://m.anzhi.com/interface/index.php?action=showcatagory&channel=6e2f80bbd420c7577e3e602c1ad63e5ce0328cba";
		 System.out.println(new String(postdata(posturl, "", null)));
	}
	
	
	public void wifi_set_password(){
		 String posturl="http://172.20.74.137:8081/hthk-wifi-pg/api";
		 //String posturl="http://172.20.123.160:8081/hthk-wifi-pg/api";
		 String msg="";
		    msg="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
	       // msg+="<request version=\"1.2\" principal=\"root\" credentials=\"root\">\r\n";
			msg+="<request>\r\n";
	        msg+="<target name=\"UserAPI\" operation=\"updateUser\">\r\n";
	        msg+="<parameter>\r\n";
	        msg+="    <user>\r\n";
	        msg+="    <name>85262703715</name>\r\n";
	        msg+="     <password><value><![CDATA[123456]]></value></password>\r\n";
	        msg+="    </user>\r\n";
	        msg+="</parameter>\r\n";
	        msg+="</target>\r\n";
	        msg+="</request>\r\n";
	     System.out.println("\t[Post URL]:"+posturl);
	     System.out.println("\t\t[Post Data]:"+ msg);
         System.out.println("\t[Response]:");
		 System.out.println("\t\t"+new String(postdata(posturl, msg, null)));
	}	
	

	
	
	
	public byte[] postdata(String strurl,String data,HashMap header){
		        URL url = null;   
		        byte[] val=null;
		        HttpURLConnection httpConn = null;   
		  
		        InputStream in = null;   
		  
		        FileOutputStream out = null; 
		        
		        byte[] buf=new byte[1024*1]; 
		  
		        try {
		        	 url = new URL(strurl);   
		        	  
		        	 httpConn = (HttpURLConnection) url.openConnection();   
		        	 //methods that result in the connection being established).   
		        	 httpConn.setFollowRedirects(false);   
		        	 //inorder to disable the redirects   
		        	 httpConn.setInstanceFollowRedirects(false);
		        	 httpConn.setDoOutput(true);
		        	 

		        	 if(header!=null){
		        		 Iterator it= header.keySet().iterator();
		        		 while(it!=null && it.hasNext()){
		        			 String strkey=it.next().toString();
		        			 httpConn.setRequestProperty(strkey, header.get(strkey).toString());
		        			 //System.out.println(strkey+":"+header.get(strkey).toString());
		        		 }
		        	 }



		        	 
		        	 java.io.OutputStream os= httpConn.getOutputStream();
		        	 os.write(data.getBytes("UTF-8"));
		        	 os.flush();
		        
		        	 java.io.InputStream is= httpConn.getInputStream();
		        	 java.io.ByteArrayOutputStream bos=new ByteArrayOutputStream();
		        	 int rlength=0;
		        	 while((rlength=is.read(buf))!=-1){
		        		 bos.write(buf, 0, rlength);
		        	 }
		        	 

		        	 
		        	 
		        	 val=bos.toByteArray();
		        	
		        	 
		        	 Map mp=httpConn.getHeaderFields();
		        	 Object[] awob=mp.entrySet().toArray();
		        	 
		        	 for(int i=0;i<awob.length;i++){
		        		 System.out.println(awob[i].toString());
		        	 }
/*		        	 
		        	 Iterator rit= mp.keySet().iterator();
		        	 while(rit.hasNext()){
		        		 Object ob=rit.next();
		        		 if(ob!=null){
		        			 String key=ob.toString();
		        			 String keycontent=httpConn.getHeaderField(key).toString();
		        			 System.out.println("\t\r[header]\t"+key+":"+keycontent);
		        		 }
		        	 }
*/		        	 
		        	 
		        	 os.close(); os=null;
		        	 is.close(); is=null;
		        	 
		        	 
		        	 httpConn.disconnect();
		        	 httpConn=null;
		        	
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		  return val;
	}
	

	 public  byte[] readFromFile(String file_name) throws IOException
     {
           FileInputStream fin = new FileInputStream(file_name);
           byte [] buf = new byte[fin.available()];
           fin.read(buf);
           fin.close();
           return buf;
     }	
	
}
