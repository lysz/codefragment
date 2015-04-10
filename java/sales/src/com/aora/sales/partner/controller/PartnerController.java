package com.aora.sales.partner.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aora.sales.common.bean.Company;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.partner.service.PartnerService;


@Controller
@RequestMapping("/partner")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;
    
    public PartnerService getPartnerService() {
        return partnerService;
    }
    
    public void setPartnerService(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    /**
     * 获取所有公司类别
     * @return
     */
    @RequestMapping(value="/getAllCategorys", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllCategorys(){
        List<Dict> dictList = getPartnerService().getAllCategorys();
        
        List<Map<String, String>> depts = new ArrayList<Map<String, String>>();
        for (Dict dict : dictList){
            Map<String, String> deptMap = new HashMap<String, String>();
            deptMap.put("categoryId", dict.getDictId() + "");
            deptMap.put("categoryName", dict.getDictName());
            depts.add(deptMap);
        }
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("root", depts);
        data.put("tocalCount", depts.size());
        
        JSONObject jsonArray = JSONObject.fromObject(data);
        return jsonArray.toString();
    }
    
    /**
     * 创建推广合作方
     * @return
     */
    @RequestMapping(value="/create", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addPartner(Company company, HttpSession session){
        
        String result = null;
        if (null != company){ 
            getPartnerService().addPartner(company);
            result = "{success:true}";
        }else{
            result = "{success:false}";
        }
        
        return result;
    }
    
    @RequestMapping(value="/getPartner", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getPartner(@RequestParam("companyId") int companyId){
        
        Company company = getPartnerService().getPartner(companyId);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", company);
        
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }
    
    @RequestMapping(value="/update", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updatePartner(Company company){
        String result = null;
        if (null != company){ 
            getPartnerService().updatePartner(company);
            result = "{success:true}";
        }else{
            result = "{success:false}";
        }
        
        return result;
    }
    
    /**
     * 获取所有推广合作方(grid)
     * @return
     */
    @RequestMapping(value="/getAllPartners", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllPartners(
            @RequestParam("companyName") String companyName, 
            @RequestParam("categoryId") String categoryId,
            @RequestParam("page") int page, 
            @RequestParam("start") int start, 
            @RequestParam("limit") int limit){
        
        Company company = new Company();
        company.setCompanyName(null == companyName || "".equals(companyName.trim()) ? null : companyName.trim());
        company.setCategoryId(null == categoryId || "".equals(categoryId.trim()) ? 0 : Integer.valueOf(categoryId.trim()));
        
        Page p = new Page();
        p.setCurrentRecord(start);
        p.setPageSize(limit);
        p.setTotalRecord(0);
        
        List<Company> companyList = getPartnerService().getAllPartners(company, p);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("root", companyList);
        
        p.setTotalRecord(1);
        List<Company> allCompanyList = getPartnerService().getAllPartners(company, p);
        data.put("totalCount", allCompanyList.size());
        
        JSONObject jsonObject = JSONObject.fromObject(data);
        return jsonObject.toString();
    }
    
    /**
     * 获取所有的推广合作商(合同管理-> 新增合同)
     * @return
     */
    @RequestMapping(value="/getPartners", method={RequestMethod.POST}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllPartners(){
        List<Company> allCompanyList = getPartnerService().getAllPartners(new Company(), null);
        
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("root", allCompanyList);
        data.put("tocalCount", allCompanyList.size());
        
        JSONObject jsonObj = JSONObject.fromObject(data);
        return jsonObj.toString();
    }
}
