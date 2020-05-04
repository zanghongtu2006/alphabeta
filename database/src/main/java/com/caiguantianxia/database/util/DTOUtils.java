package com.caiguantianxia.database.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午7:16 19-5-30
 */
public class DTOUtils {
    public static <T> Page<T> list2Page(List<T> modles, Pageable pageable, long total) {
        return new PageImpl<>(modles, pageable, total);
    }
}
