package com.aora.sales.partner.service;

import java.util.List;

import com.aora.sales.common.bean.Company;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;

public interface PartnerService {

    /**
     * 获取所有推广合作方类别
     * @return
     */
    List<Dict> getAllCategorys();
    
    /**
     * 获取所有推广合作方
     * @param company
     * @return
     */
    List<Company> getAllPartners(Company company, Page page);
    
    /**
     * 新增推广合作方
     * @param company
     */
    void addPartner(Company company);
    
    /**
     * 根据Id获取推广合作方
     * @param companyId
     */
    Company getPartner(int companyId);
    
    void updatePartner(Company company);
}
