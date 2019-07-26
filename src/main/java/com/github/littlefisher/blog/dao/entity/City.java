package com.github.littlefisher.blog.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 城市表
 *
 * @author littlefisher
 * @since 2019-07-26 10:18
 */
@Data
@Entity
@Table(name = "city")
public class City implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 城市编码
     */
    @Id
    @Column(insertable = false, name = "code", nullable = false)
    private Integer code;

    /**
     * 城市名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 省份编码
     */
    @Column(name = "province_code", nullable = false)
    private Integer provinceCode;


}