package com.aora.sales.common.bean;

public class Permission implements java.io.Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7100432086929555543L;

    private int permissionId;

    private String permissionName;

    private String url;

    private String description;

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
