package com.aora.sales.partner.dao.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class SaveChinaNetHotspot {

    private static Logger logger = Logger.getLogger(SaveChinaNetHotspot.class);
    
    private String fileDir = "F:\\tmp\\chinanet_all";
    
    private String savedDir = "F:\\tmp\\chinanet_saved";
    
    private static final String URL_PATH = "http://wlan.vnet.cn/hp2?hpId=${HID}&lang=zh&pic=${PIC}";
    
    private static final String TYPE ="ChinaNet";
    private static final int WIFI_STATUS_AVAILABLE = 0;
    
    public List<Hotspot> saveHotspot(){
        List<String> files = new ArrayList<String>();
        findAllFiles(fileDir, files);
        
        List<Hotspot> list =  parseHotspotByFiles(files);
        
        setHotspotDetail(list);
        
        return list;
    }
    
    private List<Hotspot> parseHotspotByFiles(List<String> files){
        
        List<Hotspot> list = new ArrayList<Hotspot>();
        
        if (files == null){
            return list;
        }
        
        logger.info("the size of file is:" + files.size());
        
        int count = 0;
        
        File savedFileDir = new File(savedDir);
        if (!savedFileDir.exists()){
            savedFileDir.mkdirs();
        }
        
        for (String filepath : files){
            
            File file = new File(filepath);
            
            if (!file.exists() || file.length() == 0){
                continue;
            }
            
            count++;
            
            if (count >= 50){
                break;
            }
            
            List<Hotspot>  data = parseFile(file.getAbsolutePath());
            list.addAll(data);
            
            try {
                FileUtils.moveFileToDirectory(file, savedFileDir, true);
            } catch (IOException e) {
                logger.info("move file[" + file.getAbsolutePath() + "] to [" + savedDir + "] failed.", e);
            }
        }
        
        return list;
    }
        
    private List<Hotspot> parseFile(String filepath){
        
        logger.info("file:" + filepath);
        
        List<Hotspot> list = new ArrayList<Hotspot>();
        
        String content = getContent(filepath);
        
        JSONObject jsonObj = JSONObject.fromObject(content);
        
        JSONArray array = jsonObj.getJSONArray("hps");
        
        for (int i = 0; i < array.size(); i++){
            Map<String, Object> map = (Map<String, Object>)array.get(i);
            
            String id = String.valueOf(map.get("id"));
            String latStr = String.valueOf(map.get("lat"));
            String lngStr = String.valueOf(map.get("lng"));
            
            if (StringUtils.isEmpty(latStr) || StringUtils.isEmpty(lngStr)){
                logger.info("The lat or lng is empty, so return.");
                continue;
            }
            
            String image = String.valueOf(map.get("image"));
            
            Hotspot cn = new Hotspot();
            cn.setStatus(WIFI_STATUS_AVAILABLE);
            cn.setType(TYPE);
            cn.setHotspotId(id);
            cn.setLatitude(Double.valueOf(latStr));
            cn.setLongitude(Double.valueOf(lngStr));
            cn.setImage(image);
            
            list.add(cn);
        }
        
        return list;
    }
    
    private String getContent(String filepath){
        StringBuilder builder = new StringBuilder();
        
        String content = null;
        BufferedReader buffReader = null;
        
        try {
            buffReader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
            String line = null;
            
            while((line = buffReader.readLine()) != null){
                builder.append(line);
            }
            
            content = builder.toString();
        } catch (Exception e) {
            logger.error("get file content error from url[" + filepath +"]");
        }finally{
            try{
                buffReader.close();
            }catch(Exception e){}
        }
        
        return content;
    }
    
//    private File[] listFile(){
//        File file = new File(fileDir);
//        
//        File[] files = null;
//        if (file.exists() && file.isDirectory()){
//            files = file.listFiles();
//        }
//        
//        return files;
//    }
    
    private void setHotspotDetail(List<Hotspot> list){
        
        for (Hotspot hotspot : list){
            
            setDetail(hotspot);
        }
    }
    
    private void setDetail(Hotspot hotspot){
        
        String hId = hotspot.getHotspotId();
        String pic = hotspot.getImage();
        
        String url = URL_PATH.replace("${HID}", hId).replace("${PIC}", pic);
        logger.info("url[" + url + "]");
        
        String jsonStr = getJsonByURL(url);
                
        if (null == jsonStr || "".equals(jsonStr.trim())){
            
            try {  Thread.sleep(1500);  } catch (InterruptedException e) {}
            
            jsonStr = getJsonByURL(url);
            if (null == jsonStr || "".equals(jsonStr.trim())){
                logger.error("get json error from url[" + url +"]");
                return;
            }
        }
        
        JSONObject jsonObj = JSONObject.fromObject(jsonStr);
        JSONArray jsonArray = (JSONArray)jsonObj.getJSONArray("hps");
        
        if (jsonArray.isEmpty()){
            logger.error("json is empty:" + jsonStr + ", url:" + url);
            return;
        }
        
        JSONObject obj = (JSONObject)jsonArray.get(0);
        
        String area = obj.getString("area");
        String hotName = obj.getString("hpName");
        String address = obj.getString("address");
        String province = obj.getString("province");
        String city = obj.getString("city");
        
        hotspot.setCoverarea(area);        
        hotspot.setAddress(address);
        hotspot.setProvince(province);
        hotspot.setCity(city);
        hotspot.setHotname(hotName);
        
        logger.info("get json from url[" + url + "]  success");
    }
    
    private String getJsonByURL(String url){
        
        String json = null;
        
        HttpURLConnection conn = null;
        BufferedOutputStream bos = null;
        BufferedReader reader = null;
        
        try {            
            conn = (HttpURLConnection)new URL(url).openConnection();
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("content-type", "text/html");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(50000);
            conn.connect();
            
            bos = new BufferedOutputStream(new DataOutputStream(conn.getOutputStream()));
            bos.flush();

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
            String lines;
            
            StringBuffer sb = new StringBuffer();
            while ((lines = reader.readLine()) != null) {
                sb.append(lines);
            }
            
            json = sb.toString();
            
        } catch (Exception e) {
            logger.error("get json error from url[" + url +"]");
        }finally{
            try {

                if (null != bos)
                    bos.close();

                if (null != reader)
                    reader.close();

                if (null != conn)
                    conn.disconnect();
                
            } catch (Exception e) {}
        }
        
        return json;
    }
    
    private static void findAllFiles(String path, List<String> files) {
        File f = new File(path);
 
        File[] fs = f.listFiles();
 
        if (fs == null) {
            return;
        }
 
        for (File file : fs) {
            if (file.isFile()) {
                files.add(file.getAbsolutePath());
            } else {
                findAllFiles(file.getPath(), files);
            }
        }
    }
    
}
