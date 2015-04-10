package com.aora.sales.contract.dao;

import java.util.List;

import com.aora.sales.common.bean.Account;
import com.aora.sales.common.bean.Contract;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;

public interface ContractDao {

    int addOrUpdateContract(Contract contract);
    
    void addOrUpdateAccount(Account account);
    
    List<Contract> getAllContracts(Contract contract, Account acc, Page p);
    
    List<Account> getAllAccounts(List<Contract> contracts);
    
    Contract getContractById(int contractId);
    
    List<Dict> getAllAccountTypeDict();
    
    List<Dict> getAllBanks();
}
