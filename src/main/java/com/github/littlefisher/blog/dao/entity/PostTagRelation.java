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
 * 博文标签关联表
 *
 * @author littlefisher
 * @since 2019-07-229 11:51
 */
@Data
@Entity
@Table(name = "post_tag_relation")
public class PostTagRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 关联id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id", insertable = false, nullable = false)
    private Integer relationId;

    /**
     * 博文id
     */
    @Column(name = "post_id", nullable = false)
    private Integer postId;

    /**
     * 标签id
     */
    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

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