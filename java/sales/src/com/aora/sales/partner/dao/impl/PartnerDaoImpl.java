package com.aora.sales.partner.dao.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aora.sales.common.bean.Company;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.partner.dao.PartnerDao;

@Repository
public class PartnerDaoImpl implements PartnerDao {

    private static Logger logger = Logger.getLogger(PartnerDaoImpl.class);
    
    private SqlSession sqlSession;
    
    public SqlSession getSqlSession() {
        return sqlSession;
    }

    @Autowired
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    
    public List<Dict> getAllCategorys() {
        
//        SaveChinaNetHotspot cnHotspot = new SaveChinaNetHotspot();
//        List<Hotspot> data = cnHotspot.saveHotspot();
//        
//        int batchCount = data.size() / 100;
//        
//        for (int i = 1; i <= batchCount; i++){            
//            List<Hotspot> sub = data.subList(0, 100);
//            List<Hotspot> newList = new ArrayList<Hotspot>(sub);
//            
//            getSqlSession().insert("company.saveCMCCHotspot", newList);
//            
//            sub.clear();
//        }
//        
//        getSqlSession().insert("company.saveCMCCHotspot", data);
        
        
        List<String> files = new ArrayList<String>();
        
        findAllFiles("E:\\wifi\\hotspot\\cmcc", files);
        
//        File file = new File("E:\\wifi\\hotspot\\cmcc");
//        File[] files = file.listFiles();
        
        for (String filepath : files){
            File one = new File(filepath);
            
            List<Hotspot> list = new ArrayList<Hotspot>();
            
            BufferedReader br = null;
            try {
                String filename = one.getName();
                String city = filename.split("\\.")[0];
                
                List<Map<String, String>> res = getSqlSession().selectList("company.getProvinceByCity", city);
                if (null == res || res.isEmpty()){
                    log(filename);
                    continue;
                }
                
                String province = res.get(0).get("province");
                city = res.get(0).get("city");
                                
                br = new BufferedReader(new FileReader(one));
                String line = null;
                
                while ((line = br.readLine()) != null){
                    String[] lineArr = line.split(",");
                    
                    if (lineArr.length == 1){
                        continue;
                    }
                    
                    if (null == lineArr[1] || "".equals(lineArr[1].trim()) || null == lineArr[2] || "".equals(lineArr[2].trim())){
                        continue;
                    }
                    
                    Hotspot hp = new Hotspot();
                    hp.setProvince(province);
                    hp.setCity(city);
                    hp.setHotname(lineArr[0]);
                    hp.setType("CMCC");
                    hp.setAddress(lineArr.length == 3 ? "" : lineArr[3]);                    
                    hp.setLongitude(Double.valueOf(lineArr[1]));
                    hp.setLatitude(Double.valueOf(lineArr[2]));
                    hp.setStatus(0);
                    hp.setIsUpdate(0);
                    
                    list.add(hp);
                    
                    if (list.size() == 50){
                        getSqlSession().insert("company.saveCMCCHotspot", list);
                        
                        list.clear();
                    }
                }
                
                if (!list.isEmpty())
                    getSqlSession().insert("company.saveCMCCHotspot", list);
                
            } catch (Exception e) {
                logger.info(one.getName(), e);
                log(one.getName()+ ":failed.");
                e.printStackTrace();
            }finally{
               try{
                   if (null != br) br.close();
               }catch(Exception e){}
            }
        }
        
        
        List<Dict> dictList = getSqlSession().selectList("company.getAllPartnerCategorys");
        
        if (null == dictList){
            dictList = new ArrayList<Dict>();
        }
        
        return dictList;
    }

    public List<Company> getAllPartners(Company company, Page page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyName", company.getCompanyName());
        map.put("categoryId", company.getCategoryId());
        
        if (null == page){
            ////getTotalSize等于1, 则取所有符合条件的数据
            map.put("getTotalSize", 1);
        }else{
            map.put("start", page.getCurrentRecord());
            map.put("pageSize", page.getPageSize());
            map.put("getTotalSize", page.getTotalRecord());
        }
        
        
        List<Company> companyList = getSqlSession().selectList("company.getAllPartners", map);
        
        if (null == companyList){
            companyList = new ArrayList<Company>();
        }
        
        return companyList;
    }

    public void addPartner(Company company) {
        getSqlSession().insert("company.createPartner", company);
    }

    public Company getPartner(int companyId) {
        
        return getSqlSession().selectOne("company.getPartnerById", companyId);
    }

    public void updatePartner(Company company) {
        getSqlSession().update("company.updatePartner", company);
    }
    
    public static void main(String[] args) {
        
    }
    
    private void findAllFiles(String path, List<String> files) {
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
    
    private void log(String s){
        File file = new File("E:\\tmp\\log.log");
        FileOutputStream fis = null;
        BufferedOutputStream bos = null;
        
        try{
            if (!file.exists()){
                file.createNewFile();
            }
            
            fis = new FileOutputStream(file, true);
            bos = new BufferedOutputStream(fis);
            bos.write((s + "\n").getBytes());
            
            bos.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if (bos != null)
                    bos.close();
                if (fis != null)
                    fis.close();
            }catch(Exception e){}
        }
          
      }
}
