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
 * 作者表
 *
 * @author littlefisher
 * @since 2019-07-26 10:18
 */
@Table(name = "author")
@Data
@Entity
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 作者id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", insertable = false, nullable = false)
    private Integer authorId;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 头像地址
     */
    @Column(name = "avatar", nullable = false)
    private String avatar;

    /**
     * 邮箱
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 签名
     */
    @Column(name = "signature", nullable = false)
    private String signature;

    /**
     * 职位
     */
    @Column(name = "job", nullable = false)
    private String job;

    /**
     * 团队
     */
    @Column(name = "group", nullable = false)
    private String group;

    /**
     * 城市编码
     */
    @Column(name = "city_code", nullable = false)
    private Integer cityCode;

    /**
     * 详细地址
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * 手机号
     */
    @Column(name = "phone", nullable = false)
    private String phone;

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