package com.zanghongtu.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午5:09 19-4-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {
    /**
     * 传入参赛从1开始，转换为Hibernate2.x后从0开始
     */
    private Integer page;

    private Integer pageSize;

    private Set<String> sortFields;

    private Sort sort;

    public Integer getPage() {
        return page == null ? 0 : page-1;
    }

    public Integer getPageSize() {
        return pageSize == null ? Integer.MAX_VALUE : pageSize;
    }
}
