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
    private int page;

    private int pageSize;

    private Set<String> sortFields;

    private Sort sort;
}
