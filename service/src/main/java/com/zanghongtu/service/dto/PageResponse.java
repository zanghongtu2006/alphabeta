package com.zanghongtu.service.dto;

import java.util.List;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午5:09 19-4-24
 */
public class PageResponse<T> extends BaseResponse {
    private int page;

    private int pageSize;

    private int size;

    private int total;

    private List<T> results;

    public PageResponse(BaseErrorCode errorCode, int page, int pageSize, int size, int total, List<T> results) {
        super(errorCode);
        this.page = page;
        this.pageSize = pageSize;
        this.size = size;
        this.total = total;
        this.results = results;
    }

    public PageResponse(BaseErrorCode errorCode, String message, int page, int pageSize, int size, int total, List<T> results) {
        super(errorCode.getCode(), message);
        this.page = page;
        this.pageSize = pageSize;
        this.size = size;
        this.total = total;
        this.results = results;
    }

    public PageResponse(Integer code, String message, int page, int pageSize, int size, int total, List<T> results) {
        super(code, message);
        this.page = page;
        this.pageSize = pageSize;
        this.size = size;
        this.total = total;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "PageResponse{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", size=" + size +
                ", total=" + total +
                ", results=" + results +
                "} " + super.toString();
    }
}
