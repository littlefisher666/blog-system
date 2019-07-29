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
 * 博文表
 *
 * @author littlefisher
 * @since 2019-07-229 11:51
 */
@Data
@Entity
@Table(name = "post")
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 博文主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", insertable = false, nullable = false)
    private Integer postId;

    /**
     * 博文标题
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 博文预览
     */
    @Column(name = "preview", nullable = false)
    private String preview;

    /**
     * 博文内容
     */
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * 作者id
     */
    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    /**
     * 阅读数
     */
    @Column(name = "read_times", nullable = false)
    private Integer readTimes;

    /**
     * 点赞数
     */
    @Column(name = "liked_times", nullable = false)
    private Integer likedTimes;

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