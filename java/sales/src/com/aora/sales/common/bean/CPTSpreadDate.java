package com.aora.sales.common.bean;

import java.io.Serializable;

/**
 * CPT推广日期与价格
 * @author Administrator
 *
 */
public class CPTSpreadDate implements Serializable {
    
    private static final long serialVersionUID = 3987906453257626140L;

    private int cptSpreadId;
    
    private int appId;
    
    private String spreadDate;
    
    private String price;

    public int getCptSpreadId() {
        return cptSpreadId;
    }

    public void setCptSpreadId(int cptSpreadId) {
        this.cptSpreadId = cptSpreadId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getSpreadDate() {
        return spreadDate;
    }

    public void setSpreadDate(String spreadDate) {
        this.spreadDate = spreadDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    
}
