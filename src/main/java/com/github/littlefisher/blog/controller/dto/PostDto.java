package com.github.littlefisher.blog.controller.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author jinyanan
 * @since 2019-07-29 10:25
 */
@Data
@Builder
public class PostDto {

    /** 博文id */
    private Integer postId;

    /** 博文内容 */
    private String content;
}
