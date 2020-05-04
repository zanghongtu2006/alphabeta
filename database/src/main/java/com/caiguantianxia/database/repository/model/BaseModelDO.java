package com.caiguantianxia.database.repository.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午1:31 19-5-27
 */
@Data
public class BaseModelDO {
    private Long id;

    private Date createTime;

    private Date updateTime;

    private Boolean available;

    private Boolean rev;
}
