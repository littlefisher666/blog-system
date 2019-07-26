package com.github.littlefisher.blog.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 作者标签关联表
 *
 * @author littlefisher
 * @since 2019-07-26 10:18
 */
@Entity
@Table(name = "author_tag_relation")
@Data
public class AuthorTagRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 关联编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id", insertable = false, nullable = false)
    private Integer relationId;

    /**
     * 作者id
     */
    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    /**
     * 标签id
     */
    @Column(name = "tag_code", nullable = false)
    private Integer tagCode;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;


}