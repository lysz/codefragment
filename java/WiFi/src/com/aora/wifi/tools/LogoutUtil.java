package com.aora.wifi.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class LogoutUtil{

    private static Logger logger = Logger.getLogger(LogoutUtil.class);
    
    public static void logoutCMCC(final String url, final String cookie, final String postData, final String session)
           {

        logger.info("URL[" + url + "], cookie[" + cookie + "], postData[" + postData + "],session["
                + session + "]");
        if(url==null || (url!=null && url.length()<10)){return ;}
        
        LogoutCMCC logoutcmcc = new LogoutCMCC(url, cookie, postData, session);
        Thread thread = new Thread(logoutcmcc);
        
        thread.start();
    }

    private static class LogoutCMCC implements Runnable{
        
        private String url;
        private String cookie;
        private String postData;
        private String session;
        
        private LogoutCMCC(String url, String cookie, String postData, String session){
            this.url = url;
            this.cookie = cookie;
            this.postData = postData;
            this.session = session;
        }
        
        private String logout(){
            String json = "";
            
            try{
                URL urlObj = new URL(url);

                HttpURLConnection conn = (HttpURLConnection) urlObj
                        .openConnection();

                conn.setUseCaches(false);
                conn.setRequestProperty("Cookie", cookie);
                conn.setRequestProperty("content-type", 
                        "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(50000);
                conn.setReadTimeout(50000);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                
                
                if (null != postData && !"".equals(postData.trim())) {
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));

                    writer.write(postData);
                    writer.flush();
                    writer.close();
                    os.close();
                }

                conn.connect();

                // 读取响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "GBK"));

                String lines;
                StringBuffer sb = new StringBuffer();

                while ((lines = reader.readLine()) != null) {
                    sb.append(lines);
                }

                // 断开连接
                reader.close();
                conn.disconnect();
                
                json = sb.toString();
            }catch(Exception e){
            	System.out.println("\t\t"+url+","+postData);
            	e.printStackTrace(System.out);
                logger.error("connect failed of session[" + session + "].", e);
            }
            
            return json;
        }

        public void run() {
            
            int i = 0;
            for (; i < 3; i++){
                String json = logout();
                logger.info("logout result[" + json + "].");
                
                if (json.contains("下线成功")){
                    logger.info("logout success.");
                    break;
                }
                
                try { Thread.sleep(1500);  } catch (InterruptedException e) { }
            } 
            
            if (i == 3){
                logger.info("logout failed. session[" + session + "]");
            }
        }        
    }     
}
