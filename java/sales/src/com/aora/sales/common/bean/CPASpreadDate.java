package com.aora.sales.common.bean;

import java.io.Serializable;

public class CPASpreadDate implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7862075232016433461L;
    
    /**
     *  ID
     */
    private int cpasId;
    
    /**
     * 应用ID
     */
    private int appId;
    
    /**
     * 推广日期
     */
    private String spreadDate;
    
    /**
     * 价格
     */
    private String price;

    public int getCpasId() {
        return cpasId;
    }

    public void setCpasId(int cpasId) {
        this.cpasId = cpasId;
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
