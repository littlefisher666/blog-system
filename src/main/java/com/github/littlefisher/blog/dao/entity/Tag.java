package com.github.littlefisher.blog.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 标签表
 *
 * @author littlefisher
 * @since 2019-07-26 10:18
 */
@Entity
@Table(name = "tag")
@Data
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标签编码
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, name = "code", nullable = false)
    private Integer code;

    /**
     * 标签名
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

}