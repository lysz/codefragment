package com.aora.sales.partner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aora.sales.common.bean.Company;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.partner.dao.PartnerDao;
import com.aora.sales.partner.service.PartnerService;

@Service(value = "partnerService")
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerDao partnerDao;
    
    public PartnerDao getPartnerDao() {
        return partnerDao;
    }
    
    public void setPartnerDao(PartnerDao partnerDao) {
        this.partnerDao = partnerDao;
    }
    
    public List<Dict> getAllCategorys() {
        
        return getPartnerDao().getAllCategorys();
    }

    public List<Company> getAllPartners(Company company, Page page) {
        
        return getPartnerDao().getAllPartners(company, page);
    }

    public void addPartner(Company company) {
        getPartnerDao().addPartner(company);
    }

    public Company getPartner(int companyId) {

        return getPartnerDao().getPartner(companyId);
    }

    public void updatePartner(Company company) {
        getPartnerDao().updatePartner(company);
    }
}
