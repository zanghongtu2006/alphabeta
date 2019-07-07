package com.zanghongtu.database.repository.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午1:31 19-5-27
 */
@Data
@MappedSuperclass
public class BaseModelPO {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.zanghongtu.database.util.IDGenerator")
    private Long id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "rev")
    private Boolean rev;
}
