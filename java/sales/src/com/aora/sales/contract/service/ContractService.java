package com.aora.sales.contract.service;

import java.util.List;

import com.aora.sales.common.bean.Account;
import com.aora.sales.common.bean.Contract;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;

public interface ContractService {

   void addOrUpdateContract(Contract contract, Account publicAccount, Account privateAccount);
   
   List<Contract> getAllContracts(Contract contract, Account acc, Page p);
   
   List<Account> getAllAccounts(List<Contract> contracts);
   
   Contract getContractById(int contractId);
   
   List<Dict> getAllBanks();
}
