package com.dream.city.base;

import lombok.Data;

import java.io.Serializable;


/**
 * 分页入参
 * @author
 */
@Data
public class PageReq<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static int DEFAULT_START = 1;//默认开始行

    public static int DEFAULT_LIMIT = 20;//默认每页显示行数

    public static int DEFAULT_PAGE = 1;//默认页

    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private long total;
    private int pages;
    private boolean count;
    private Boolean reasonable;
    private Boolean pageSizeZero;
    private String countColumn;
    private String orderBy;
    private boolean orderByOnly;
    //查询条件
    private T condition;

    @SuppressWarnings("static-access")
    public PageReq()
    {
        this.startRow = DEFAULT_START;//默认开始行
        this.pageSize = DEFAULT_LIMIT;//默认每页显示行数
        this.pageNum = DEFAULT_PAGE;//默认页
    }

    public PageReq(int start,int pageSize)
    {
        this.startRow = start;
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        this.startRow = this.pageSize * (this.pageNum - 1);
        return startRow;
    }

    public int getEndRow() {
        int mod = Integer.parseInt(String.valueOf(this.total)) % this.pageSize;
        if (mod == 0){
            this.endRow = Integer.parseInt(String.valueOf(this.total)) / this.pageSize;
        }else if (mod > 0){
            this.endRow = Integer.parseInt(String.valueOf(this.total)) / this.pageSize + 1;
        }else {
            this.endRow = Integer.parseInt(String.valueOf(this.total)) / this.pageSize - 1;
        }
        return endRow;
    }
}
