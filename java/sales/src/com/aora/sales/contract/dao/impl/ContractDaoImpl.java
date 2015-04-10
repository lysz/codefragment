package com.aora.sales.contract.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aora.sales.common.bean.Account;
import com.aora.sales.common.bean.Contract;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.contract.dao.ContractDao;

@Repository
public class ContractDaoImpl implements ContractDao {

    private SqlSession sqlSession;
    
    public SqlSession getSqlSession() {
        return sqlSession;
    }

    @Autowired
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    
    public int addOrUpdateContract(Contract contract){
        int primaryKey = 0;
        
        if (contract.getContractId() == 0){            
            getSqlSession().insert("contract.createContract", contract);
            primaryKey = contract.getContractId();
        }else {
            getSqlSession().update("contract.updateContract", contract);
            primaryKey = contract.getContractId();
        }
        
        return primaryKey;
    }

    public void addOrUpdateAccount(Account account) {
       if (account.getAccountId() == 0){           
           getSqlSession().insert("contract.createAccount", account);
       }else {
           getSqlSession().update("contract.updateAccount", account);
       }
    }

    public List<Contract> getAllContracts(Contract contract, Account acc, Page page) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contractId", contract.getContractId());
        map.put("contractCode", contract.getContractCode());
        map.put("companyId", contract.getCompanyId());
        map.put("startTime", contract.getStartTime());
        map.put("endTime", contract.getEndTime());
        
        map.put("accountNumber", acc.getAccountNumber());
        map.put("bank", acc.getBank());
        map.put("userName", acc.getUserName());
        
        
        map.put("start", page.getCurrentRecord());
        map.put("pageSize", page.getPageSize());
        map.put("getTotalSize", page.getTotalRecord());
        
        List<Contract> list = getSqlSession().selectList("contract.getAllContract", map);
        
        if (null == list){
            list = new ArrayList<Contract>();
        }
        
        return list;
    }

    public List<Account> getAllAccounts(List<Contract> contracts) {
        List<Account> accounts = getSqlSession().selectList("contract.getAccounts", contracts);
        
        if (null == accounts){
            accounts = new ArrayList<Account>();
        }
        
        return accounts;
    }

    public Contract getContractById(int contractId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("contractId", contractId);
        
        
        //--------- 因为xml里面需要这些参数, 如果不设置这些参数, mybatis会传这些参数, 又因为map里面没有这些参数, 导致sql错误
        //因此, 这些参数即使不需要, 也必须传null
        map.put("contractCode", null);
        map.put("companyId", 0);
        map.put("startTime", null);
        map.put("endTime", null);
        
        map.put("accountNumber", null);
        map.put("bank", 0);
        map.put("userName", null);
        
        map.put("start", 0);
        map.put("pageSize", 10);
        map.put("getTotalSize", 1);
        
        Contract result = getSqlSession().selectOne("contract.getAllContract", map);
        return result;
    }

    public List<Dict> getAllAccountTypeDict() {
        
        List<Dict> dicts = getSqlSession().selectList("contract.getAllAccountTypeDict");
        
        if (null == dicts){
            dicts = new ArrayList<Dict>();
        }
        
        return dicts;
    }
    
    public List<Dict> getAllBanks(){
        List<Dict> dicts = getSqlSession().selectList("contract.getAllBanks");
        
        if (null == dicts){
            dicts = new ArrayList<Dict>();
        }
        
        return dicts;
    }
    
    public static void main(String[] args) {
        String filepath = "F:\\wifi\\广东_深圳.csv";
        
        File file = new File(filepath);
        System.out.println(file.getName().split("\\.")[0].split("_")[1]);
    }
}
