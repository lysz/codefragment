package com.aora.sales.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aora.sales.common.bean.Account;
import com.aora.sales.common.bean.Contract;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.contract.dao.ContractDao;
import com.aora.sales.contract.service.ContractService;


@Service
public class ContractServiceImpl implements ContractService {
    
    @Autowired
    private ContractDao contractDao;

    public ContractDao getContractDao() {
        return contractDao;
    }

    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
    }
    
    public void addOrUpdateContract(Contract contract, Account publicAccount, Account privateAccount){
        int primaryKey = getContractDao().addOrUpdateContract(contract);
        
        
        publicAccount.setContractId(primaryKey);
        publicAccount.setCompanyId(contract.getCompanyId());
        
        privateAccount.setContractId(primaryKey);
        privateAccount.setCompanyId(contract.getCompanyId());
        
        List<Dict> dicts = getContractDao().getAllAccountTypeDict();
        for (Dict dict : dicts){
            if ("公有".equals(dict.getDictName())){
                publicAccount.setAccountType(dict.getDictId());
            }else if ("私有".equals(dict.getDictName())){
                privateAccount.setAccountType(dict.getDictId());
            }
        }
        
        getContractDao().addOrUpdateAccount(publicAccount);
        getContractDao().addOrUpdateAccount(privateAccount);
    }

    public List<Contract> getAllContracts(Contract contract, Account acc, Page p) {
        
        return getContractDao().getAllContracts(contract, acc, p);
    }

    public List<Account> getAllAccounts(List<Contract> contracts) {
        
        return getContractDao().getAllAccounts(contracts);
    }

    public Contract getContractById(int contractId) {
       
        return getContractDao().getContractById(contractId);
    } 
    
    public List<Dict> getAllBanks(){
        return getContractDao().getAllBanks();
    }
}
