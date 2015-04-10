package com.aora.sales.partner.dao;

import java.util.List;

import com.aora.sales.common.bean.Company;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;

public interface PartnerDao {

    List<Dict> getAllCategorys();
    
    List<Company> getAllPartners(Company company, Page page);
    
    void addPartner(Company company);
    
    /**
     * 根据Id获取推广合作方
     * @param companyId
     */
    Company getPartner(int companyId);
    
    void updatePartner(Company company);
}
