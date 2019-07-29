package com.github.littlefisher.blog.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

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

    /** 阅读数 */
    private Integer readTimes;

    /** 点赞数 */
    private Integer likedTimes;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 标签 */
    private List<TagDto> tagList;
}
