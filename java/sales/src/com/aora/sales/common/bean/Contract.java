package com.aora.sales.common.bean;

import java.util.Date;

/**
 * 合同
 * 
 * @author Administrator
 * 
 */
public class Contract implements java.io.Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6275129636164574914L;

    /**
     * 合同Id
     */
    private int contractId;

    /**
     * 合同编号
     */
    private String contractCode;

    /**
     * 所属公司Id
     */
    private int companyId;
    
    private String companyName;

    /**
     * 合同签订时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private int createrId;

    /**
     * 合同附件地址
     */
    private String attachmentPath;

    /**
     * 合同生效时间
     */
    private Date startTime;

    /**
     * 合同结束时间
     */
    private Date endTime;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 查询后台
     */
    private String searchURL;

    /**
     * 查询密码
     */
    private String searchpwd;
    
    /**
     * 最后修改时间
     */
    private Date lastChangeDate;

    /**
     * 合同状态
     */
    private int status;

    /**
     * 备注
     */
    private String description;

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCreaterId() {
        return createrId;
    }

    public void setCreaterId(int createrId) {
        this.createrId = createrId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSearchURL() {
        return searchURL;
    }

    public void setSearchURL(String searchURL) {
        this.searchURL = searchURL;
    }

    public String getSearchpwd() {
        return searchpwd;
    }

    public void setSearchpwd(String searchpwd) {
        this.searchpwd = searchpwd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
