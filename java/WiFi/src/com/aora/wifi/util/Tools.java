package com.aora.wifi.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

public class Tools {
	public static String synchronized_214_obj="synchronized_214_obj";
	public static String synchronized_216_obj="synchronized_216_obj";
	public static String synchronized_302_obj="synchronized_302_obj";
	
	public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
	
	
	public synchronized static String date2String(Date date, String formatStr) {
		String returnStr = "";
		if (date != null && formatStr != null) {
			try{
				SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
				returnStr = sdf.format(date);
			}catch(Exception e){}
		}
		return returnStr;
	}
	

	public synchronized static int calculateDistance(double lat1, double lon1, double lat2, double lon2){
	    
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
	
	public synchronized static Date string2Date(String date, String formatStr) {
		Date returnval = null;
		if (date != null && formatStr != null) {
			try{
				SimpleDateFormat sdf = new SimpleDateFormat(formatStr,Locale.CHINA);
				returnval = sdf.parse(date);
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
		return returnval;
	}
	
	  public synchronized static String asHex(byte[] data)
	  {
	    //double size, two bytes (hex range) for one byte
	    StringBuffer buf = new StringBuffer(data.length * 2);
	    for (int i = 0; i < data.length; i++)
	    {
	      //don't forget the second hex digit
	      if ( ( (int) data[i] & 0xff) < 0x10)
	      {
	        buf.append("0");
	      }
	      buf.append(Long.toString( (int) data[i] & 0xff, 16));
	    }
	    return buf.toString();
	  }
	  
		public synchronized static byte[] MD5(String rStr)
		{
			try
			{
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				md5.update(rStr.getBytes());
				return md5.digest();
			}catch (Exception e)
			{
				return new byte[0];
			}
		}
	
		public synchronized static String URLEncode(String str, String lang) {
			String returnval = "";
			if (str != null) {
				try{
					if(lang!=null && lang.length()>0){
						returnval=java.net.URLEncoder.encode(str,lang);
					}else{
						returnval=java.net.URLEncoder.encode(str,"UTF-8");
					}
				}catch(Exception e){
					
				}
			}
			return returnval;
		}	
		
		
	     public synchronized static String sha1(String data) throws Exception {  

	         MessageDigest md = MessageDigest.getInstance("SHA1");  

	         md.update(data.getBytes());  

	         StringBuffer buf = new StringBuffer();  

	         byte[] bits = md.digest();  

	         for(int i=0;i<bits.length;i++){  

	             int a = bits[i];  

	             if(a<0) a+=256;  

	             if(a<16) buf.append("0");  

	             buf.append(Integer.toHexString(a));  

	         }  

	         return buf.toString();  

	     }  
		
		public synchronized static String URLDecode(String str, String lang) {
			String returnval = "";
			if (str != null) {
				try{
					if(lang!=null && lang.length()>0){
						returnval=java.net.URLDecoder.decode(str,lang);
					}else{
						returnval=java.net.URLDecoder.decode(str,"UTF-8");
					}
				}catch(Exception e){
					
				}
			}
			return returnval;
		}		
		
		public synchronized static String Rtrim(String str,String key){
			if(str==null) return "";
			if(str!=null && str.endsWith(key)){
				return  str.substring(0,str.lastIndexOf(key));
			}else{
				return str;
			}
		}
		
		public synchronized static String getParameter(HttpServletRequest request,String paraName, String default_val){
			try{
				//pro
					return request.getParameter(paraName)==null?default_val:request.getParameter(paraName);
				//testing
					//return request.getParameter(paraName)==null?default_val:new String(request.getParameter(paraName).getBytes("ISO8859-1"),"UTF-8");
			}catch(Exception e){
				e.printStackTrace();
				return default_val;
			}
			
		}	
		

	/**
	 * 
	 * 
	 * @param file
	 * @param savePath
	 * @return
	 */
	public static boolean saveFile(File file, String savePath) {

		boolean b = false;
		InputStream is = null;
		OutputStream os = null;
		try {

			if (file != null && file.exists()) {
				is = new FileInputStream(file);
				os = new FileOutputStream(savePath);
				byte buffer[] = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					os.write(buffer, 0, count);
				}
				b = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
			}
			try {
				os.close();
			} catch (Exception e) {
			}
		}
		return b;
	}
	/**
	 * 
	 * 
	 * @param str
	 * @return 
	 */
	public static boolean IsStringEmpty(String str) {

		if (str != null) {
			str = str.trim();
		}

		return str == null || "".equals(str) || "null".equals(str);
	}		
			
		
		public static void main(String[] arg){
			
			System.out.println(new Date().getHours());
			//cachectl.getcontent("test");
			 
//			System.out.println(date2String(new Date(), "yyyy-MM")+"-01");
//			System.out.println(URLDecode("%E6%93%8D%E4%BD%9C%E5%A4%B1%E8%B4%A5%21%28%E5%B8%90%E5%8F%B7%E6%95%B0%E6%8D%AE%E5%B7%B2%E5%AD%98%E5%9C%A8%29","UTF-8"));
//			System.out.println(string2Date("2013-05-01", "yyyy-MM-dd"));
//			
//			System.out.println(Rtrim("asdfsadf,asdf,",","));
//			System.out.println(date2String(new Date(), "EEE MMM dd HH:mm:ss z yyyy"));//Fri Sep 27 00:00:00 CST 2013
//			System.out.println(date2String(new Date(), "yyyyMMddHH:mm:ss.SSS"));//Fri Sep 27 00:00:00 CST 2013
//			System.out.println(string2Date("Fri Sep 27 00:00:00 CST 2013","EEE MMM dd HH:mm:ss z yyyy"));
//			System.out.println(string2Date("Fri Sep 27 00:00:00 CST 2013","EEE MMM dd HH:mm:ss z yyyy"));
//			
//			System.out.println(asHex(MD5("123456")));
//			
//			System.out.println(lPad("782343453454",10,"0"));
			String mob="13798380672";
										

			String smscontent="发红包啦！快来新版WiFi畅游查收吧。可提现哦！应用内更新或点击以下链接下载最新版本：m.wifienjoy.com";
			String url="http://smsapi.c123.cn/OpenPlatform/OpenApi?action=sendOnce&ac=1001@500933530001&authkey=F9FE80B75E91FD430E0B81FFF5B5191B&cgid=1754&csid=101&c="+Tools.URLEncode(smscontent, "UTF-8")+"&m="+mob;
			byte[] response =Tools.postdata(url, "", null);
			String responsestr=(response==null?"":new String(response));
			
			System.out.println(responsestr);
			
		}
		
		 public static int getDaySub(String beginDateStr,String endDateStr)
		    {
		        int day=0;
		        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");   
		        java.util.Date beginDate;
		        java.util.Date endDate;
		        try
		        {
		            beginDate = format.parse(beginDateStr);
		            endDate= format.parse(endDateStr);   
		            day=(int) ((endDate.getTime()-beginDate.getTime())/(24*60*60*1000)+1);   
		            //System.out.println("相隔的天�?"+day);  
		        } catch (Exception e)
		        {
		            // TODO 自动生成 catch �?
		            e.printStackTrace();
		        }  
		        return day;
		    }
		 public static String getValue(HSSFCell cell) {
			  String value = "";
			  if(cell==null){return value;}
			  switch (cell.getCellType()) {

			  case HSSFCell.CELL_TYPE_NUMERIC: 
				   if (HSSFDateUtil.isCellDateFormatted(cell)) {
					   value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
				   } else {
				    value = String.valueOf(cell.getNumericCellValue());
				    long d=(long)Double.parseDouble(value);
				     value=Long.toString(d);
				   }
				   break;

			  /* 此行表示单元格的内容为string类型 */

			  case HSSFCell.CELL_TYPE_STRING: // 
				   value = cell.getStringCellValue().toString();
				   break;
			  case HSSFCell.CELL_TYPE_FORMULA:
				   value = String.valueOf(cell.getNumericCellValue());
				   if (value.equals("NaN")) {// 
	
				    value = cell.getStringCellValue().toString();
	
				   }
				   break;
			  case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
				  value = " "+ cell.getBooleanCellValue();
				  break;
			  case HSSFCell.CELL_TYPE_BLANK: // 
				  value = "";
				  break;
			  case HSSFCell.CELL_TYPE_ERROR: // 故障
				  value = "";
				  break;
			  default:
				  value = cell.getStringCellValue().toString();
			  }
			  return value;
			 }
		 
		 public static String GetRandomNumber(){
		    	String temp = ""; 
  
		        Set<Integer> set = new HashSet<Integer>();  
 
		        Random random = new Random();  
		          
		        while (set.size() < 4) {  
		           
		            set.add(random.nextInt(10));  
		        }  
 
		        Iterator<Integer> iterator = set.iterator();  
		        while (iterator.hasNext()) {  
		            temp += iterator.next();  
		            // System.out.print(iterator.next());  
		        }  
		    	return temp;
		    }
		 public static String yesterdayString(Date date, String formatStr, int days) {
				String returnStr = "";
				if (date != null && formatStr != null) {
					Calendar ca = Calendar.getInstance();
					ca.setTime(date);
					ca.add(Calendar.DAY_OF_YEAR, -days);
					Date date2 = ca.getTime();
					SimpleDateFormat sdf2 = new SimpleDateFormat(formatStr);
					returnStr = sdf2.format(date2);
				}
				return returnStr;

			}
		 public String getYesterday(){
			 	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -1); 
				Date theDate = calendar.getTime();
				String yesterday = df.format(theDate);
				return yesterday;
		 }
		 
	
		    /**
		     * 字符串替换函�?
		     * @param sAll String   原来的字符串
		     * @param older String  要替换掉的字符串
		     * @param newer String  新的字符�?
		     * @return String       替换后的结果
		     */
		    public synchronized static  String strReplace(String sAll,String sOld, String sNew){
		        int iT=0;
		        String sF = null,sH= null;

		        if(sNew.indexOf(sOld)!= -1)
		            return sAll;

		        if(sAll == null || sOld==null ||sNew==null)
		            return sAll;
		        iT = sAll.indexOf(sOld);
		        int i = 0;
		        while(iT != -1){
		            sF = sAll.substring(0,iT);
		            sH= sAll.substring(iT+sOld.length());
		            sAll = sF+sNew+sH;
		            iT = sAll.indexOf(sOld);
		        }
		        return sAll;
		    }
		    
		    
		    public static String lPad(String iValue, int iLen, String iFill){
		    	   byte ch;
		    	   if(iFill==null|| iFill.length()==0) {
		    	     ch=32;
		    	   }
		    	   else{
		    	     ch=(byte)iFill.charAt(0);
		    	   }
		    	  return lPad(iValue,iLen,ch);
		    	 }

		    public static String lPad(String iValue, int iLen, byte iFill)
		    	 {
		    	     String lFuncName = "lPad";
		    	     String lBuff = iValue;
		    	     if(lBuff == null)
		    	         lBuff = "";
		    	     byte lBytes[] = lBuff.getBytes();
		    	     byte lResult[] = new byte[iLen];
		    	     if(lBytes.length <= iLen)
		    	     {
		    	         
		    	         for(int li = 0; li < iLen-lBytes.length; li++)
		    	             lResult[li] = iFill;
		    	         
		    	         System.arraycopy(lBytes, 0, lResult, iLen-lBytes.length, lBytes.length);		    	         

		    	     } else
		    	     {
		    	         System.arraycopy(lBytes, 0, lResult, 0, iLen);
		    	     }
		    	     return new String(lResult);
		    	 }		    
		
		    public static String rPad(String iValue, int iLen, String iFill){
		    	   byte ch;
		    	   if(iFill==null|| iFill.length()==0) {
		    	     ch=32;
		    	   }
		    	   else{
		    	     ch=(byte)iFill.charAt(0);
		    	   }
		    	  return rPad(iValue,iLen,ch);
		    	 }
		     public static String rPad(String iValue, int iLen, byte iFill)
		    	 {
		    	     String lFuncName = "rPad";
		    	     String lBuff = iValue;
		    	     if(lBuff == null)
		    	         lBuff = "";
		    	     byte lBytes[] = lBuff.getBytes();
		    	     byte lResult[] = new byte[iLen];
		    	     if(lBytes.length <= iLen)
		    	     {
		    	         System.arraycopy(lBytes, 0, lResult, 0, lBytes.length);
		    	         for(int li = lBytes.length; li < iLen; li++)
		    	             lResult[li] = iFill;

		    	     } else
		    	     {
		    	         System.arraycopy(lBytes, 0, lResult, 0, iLen);
		    	     }
		    	     return new String(lResult);
		    	 }		 
		     
	 	public static byte[] postdata(String strurl,String data,HashMap header){
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
		        		// System.out.println(awob[i].toString());
		        	 }
		        	 
		        	 os.close(); os=null;
		        	 is.close(); is=null;
		        	 
		        	 
		        	 httpConn.disconnect();
		        	 httpConn=null;
		        	
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		  return val;
	}
		 public synchronized static byte[] readFromFile(String file_name) throws IOException
	      {
	            FileInputStream fin = new FileInputStream(file_name);
	            byte [] buf = new byte[fin.available()];
	            fin.read(buf);
	            fin.close();
	            return buf;
	      }	 	
}
