package com.github.littlefisher.blog.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * @author jinyanan
 * @since 2019-07-29 10:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PostDto extends SimplePostDto {

    /** 博文id */
    private Integer postId;

    /** 博文内容 */
    private String content;
}
