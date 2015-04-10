package com.aora.sales.contract.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aora.sales.common.bean.Account;
import com.aora.sales.common.bean.Contract;
import com.aora.sales.common.bean.Dict;
import com.aora.sales.common.bean.Page;
import com.aora.sales.common.util.ConfigUtils;
import com.aora.sales.common.util.DateUtils;
import com.aora.sales.contract.service.ContractService;

@Controller
@RequestMapping("/contract")
public class ContractController {

    private static Logger logger = Logger.getLogger(ContractController.class);

    @Autowired
    private ContractService contractService;

    public ContractService getContractService() {
        return contractService;
    }

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }
    
    @RequestMapping(value = "/getAllBanks", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllBanks(){
        
        List<Dict> dict = getContractService().getAllBanks();
        
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        
        for (Dict d : dict){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dictId", d.getDictId());
            map.put("dictName", d.getDictName());
            data.add(map);
        }
        
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("root", data);
        result.put("tocalCount", data.size());
        
        JSONObject jsonObj = JSONObject.fromObject(result);
        return jsonObj.toString();
    }

    @RequestMapping(value = "/saveOrUpdate", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addContract(
            Contract contract,
            HttpServletRequest request,
            @RequestParam("attachment") MultipartFile file,
            
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,

            @RequestParam("contractIdStr") String contractIdStr,
            @RequestParam("publicAccountId") String publicAccountIdStr,
            @RequestParam("publicUserName") String publicUserName,
            @RequestParam("publicBank") int publicBank,
            @RequestParam("publicAccount") String publicAccount,

            @RequestParam("privateAccountId") String privateAccountIdStr,
            @RequestParam("privateUserName") String privateUserName,
            @RequestParam("privateBank") int privateBank,
            @RequestParam("privateAccount") String privateAccount) {
        

        Account publicAct = new Account();
        publicAct.setAccountId(StringUtils.isEmpty(publicAccountIdStr) ? 0 : Integer.valueOf(publicAccountIdStr.trim()));
        publicAct.setAccountNumber(publicAccount);
        publicAct.setUserName(publicUserName);
        publicAct.setBank(publicBank);
        
        Account privateAct = new Account();
        privateAct.setAccountId(StringUtils.isEmpty(privateAccountIdStr) ? 0 : Integer.valueOf(privateAccountIdStr.trim()));
        privateAct.setAccountNumber(privateAccount);
        privateAct.setUserName(privateUserName);
        privateAct.setBank(privateBank);

        String result = "";

        try {

            String fileName = file.getOriginalFilename();// 上传的文件名字
            
            if (!StringUtils.isEmpty(fileName)){
                
                String attachment = ConfigUtils.getProperty("contract.attachment.path");

                File targetFile = new File(attachment, fileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                
                file.transferTo(targetFile);

                contract.setAttachmentPath(targetFile.getAbsolutePath());
            }
            
            
            contract.setContractId(StringUtils.isEmpty(contractIdStr) ? 0 : Integer.valueOf(contractIdStr.trim()));
            contract.setStartTime(startDate);
            contract.setEndTime(endDate);

            getContractService().addOrUpdateContract(contract, publicAct, privateAct);

            result = "{success:true}";
        } catch (Exception e) {
            logger.error(e);
            result = "{success:false}";
        }

        return result;
    }

    @RequestMapping(value = "/getAllContract", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllContract(
            @RequestParam("company") String company,
            @RequestParam("contractCode") String contractCode,
            @RequestParam("userName") String userName,
            @RequestParam("bank") String bank,
            @RequestParam("accountNumber") String accountNumber,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam("page") int page, 
            @RequestParam("start") int start, 
            @RequestParam("limit") int limit
            ) {
        
        Contract contact = new Contract();
        
        contact.setCompanyId(StringUtils.isEmpty(company) ? 0 : Integer.valueOf(company));
        contact.setContractCode(StringUtils.isEmpty(contractCode) ? null : contractCode.trim());
        contact.setStartTime(startDate);
        contact.setEndTime(endDate);
        
        Account acc = new Account();
        acc.setAccountNumber(StringUtils.isEmpty(accountNumber)? null : accountNumber.trim());
        acc.setBank(StringUtils.isEmpty(bank) ? 0 : Integer.valueOf(bank));
        acc.setUserName(StringUtils.isEmpty(userName)? null : userName.trim());
        
        Page p = new Page();
        p.setCurrentRecord(start);
        p.setPageSize(limit);
        p.setTotalRecord(0);
        
        List<Contract> contracts = getContractService().getAllContracts(contact, acc, p);
        List<Map<String, Object>> data = convertListToJson(contracts);
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("root", data);
        
        p.setTotalRecord(1);
        
        contracts = getContractService().getAllContracts(contact, acc, p);
        result.put("totalCount", contracts.size());
        
        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject.toString();
    }
    
    @RequestMapping(value = "/getContract", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getContract(@RequestParam("contractId") int contractId){
        Contract contract = getContractService().getContractById(contractId);
        
        List<Contract> contracts = new ArrayList<Contract>();
        contracts.add(contract);
        
        List<Account> accounts = getContractService().getAllAccounts(contracts);
        
        Map<String, Object> data = convertObjectToJson(contract, accounts);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", data);
        
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }
    
    
    private List<Map<String, Object>> convertListToJson(List<Contract> contracts){
        
        List<Account> accounts = null;
        
        if (!contracts.isEmpty()){
            accounts = getContractService().getAllAccounts(contracts);
        }

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (Contract contract : contracts){
            Map<String, Object> map = convertObjectToJson(contract, accounts);
            data.add(map); 
        }
        
        return data;
    }
    
    private Map<String, Object> convertObjectToJson(Contract contract, List<Account> accounts){
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("contractId", contract.getContractId());
        map.put("contractIdStr", contract.getContractId());
        
        map.put("companyId", contract.getCompanyId());
        map.put("companyName",contract.getCompanyName());
        map.put("contractCode", contract.getContractCode());
       
        map.put("attachmentPath", contract.getAttachmentPath());
        map.put("attachment", contract.getAttachmentPath());
        
        map.put("startTime", DateUtils.formatToDay(contract.getStartTime()));
        map.put("endTime", DateUtils.formatToDay(contract.getEndTime()));
        
        map.put("startDate", DateUtils.formatToDay(contract.getStartTime()));
        map.put("endDate", DateUtils.formatToDay(contract.getEndTime()));
        
        map.put("contact", contract.getContact());
        map.put("telephone", contract.getTelephone());
        map.put("searchURL", contract.getSearchURL());
        map.put("searchpwd", contract.getSearchpwd());
        
        
        map.put("lastChangeDateStr", DateUtils.formatToTime(contract.getLastChangeDate()));
        map.put("description", contract.getDescription());
        
        if (null != accounts){
            for (Account account : accounts){
                if (contract.getContractId() == account.getContractId()){
                    
                    if (account.getAccounttypeName().equals("公有")){
                        
                        map.put("publicAccountId", account.getAccountId());
                        map.put("publicUserName", account.getUserName());
                        map.put("publicBank", account.getBank());
                        map.put("publicBankName", account.getBankName());
                        map.put("publicAccount", account.getAccountNumber());
                        
                    }else if (account.getAccounttypeName().equals("私有")){
                        
                        map.put("privateAccountId", account.getAccountId());
                        map.put("privateUserName", account.getUserName());
                        map.put("privateBank", account.getBank());
                        map.put("privateBankName", account.getBankName());
                        map.put("privateAccount", account.getAccountNumber());
                    }
                }
            }
        }
        
        return map;
    }
    
    @RequestMapping(value = "/downloadContract", method = { RequestMethod.POST })
    public void downloadContract(@RequestParam("fileName") String fileName, HttpServletResponse response){
        
        File file = new File(fileName);
        try{
            
            if (!file.exists()){
                return ;
            }
            
            String extension = FilenameUtils.getExtension(fileName);
            
            response.setContentType("application/force-download");
            response.setContentType("application/" + extension);
            response.setContentLength(Integer.valueOf(file.length() + ""));
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            
            InputStream is = new FileInputStream(file);
            IOUtils.copy(is, response.getOutputStream());
            
            response.flushBuffer();
        }catch(Exception e){
            logger.error("download failed of the file:" + fileName, e);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
}
