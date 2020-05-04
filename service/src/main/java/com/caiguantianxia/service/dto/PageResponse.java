package com.caiguantianxia.service.dto;

import lombok.Data;

import java.util.List;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午5:09 19-4-24
 */
@Data
public class PageResponse<T> {
    /**
     * 当前页
     */
    private int page;

    /**
     * 每页个数
     */
    private int pageSize;

    /**
     * 总数
     */
    private long total;

    /**
     * 结果列表
     */
    private List<T> results;

    public PageResponse(int page, int pageSize, long total, List<T> results) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.results = results;
    }

}
