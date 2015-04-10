package com.aora.sales.common.bean;

public class Page {

    private int totalPage;// 总页数
    private int currentPage;// 当前页
    private int totalRecord;// 总记录数
    private int currentRecord;// 当前记录条数
    private int pageSize = 50;// 每页默认记录

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalRecord, int pageSize) {
        if (totalRecord % pageSize == 0)
            this.totalPage = totalRecord / pageSize;
        else
            this.totalPage = totalRecord / pageSize + 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentRecord, int pageSize) {
        if (currentRecord % pageSize == 0)
            this.currentPage = currentRecord / pageSize;
        else
            this.currentPage = currentRecord / pageSize + 1;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getCurrentRecord() {
        return currentRecord;
    }

    public void setCurrentRecord(int currentRecord) {
        this.currentRecord = currentRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}