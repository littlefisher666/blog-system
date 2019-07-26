package com.github.littlefisher.blog.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 省份表
 *
 * @author littlefisher
 * @since 2019-07-26 10:18
 */
@Data
@Entity
@Table(name = "province")
public class Province implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 省份编码
     */
    @Id
    @Column(insertable = false, name = "code", nullable = false)
    private Integer code;

    /**
     * 省份名称
     */
    @Column(name = "name")
    private String name;


}