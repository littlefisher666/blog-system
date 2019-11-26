package com.github.littlefisher.blog.dao.model.ext;

import com.github.littlefisher.blog.dao.model.PostTagRelationDo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author jinyanan
 * @since 2019/11/25 17:42
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString
public class PostTagRelationWithNameDo extends PostTagRelationDo {

    /** 标签名 */
    private String name;
}
