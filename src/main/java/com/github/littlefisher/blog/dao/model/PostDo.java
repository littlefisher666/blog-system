package com.github.littlefisher.blog.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import tk.mybatis.mapper.annotation.ColumnType;


/**
 *
 * post 实体<br>
 * <br>
 *
 * Created on 2019年11月25日
 * @author littlefisher
 * @version 2.1
 * @since v2.1
 */
@Table(name = "post")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@SuperBuilder
@NoArgsConstructor
public class PostDo implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * 博文主键
     */
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    /**
     * 博文标题
     */
    private String title;

    /**
     * 博文地址
     */
    @Column(name = "content_url")
    private String contentUrl;

    /**
     * 作者id
     */
    @Column(name = "author_id")
    private Integer authorId;

    /**
     * 阅读数
     */
    @Column(name = "read_times")
    private Integer readTimes;

    /**
     * 点赞数
     */
    @Column(name = "liked_times")
    private Integer likedTimes;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 博文预览
     */
    @ColumnType(jdbcType = JdbcType.LONGVARCHAR)
    private String preview;
}