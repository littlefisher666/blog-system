package com.github.littlefisher.blog.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 *
 * author_tag_relation 实体<br>
 * <br>
 *
 * Created on 2019年11月25日
 * @author littlefisher
 * @version 2.1
 * @since v2.1
 */
@Table(name = "author_tag_relation")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@SuperBuilder
@NoArgsConstructor
public class AuthorTagRelationDo implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * 关联编号
     */
    @Id
    @Column(name = "relation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer relationId;

    /**
     * 作者id
     */
    @Column(name = "author_id")
    private Integer authorId;

    /**
     * 标签id
     */
    @Column(name = "tag_code")
    private Integer tagCode;

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
}