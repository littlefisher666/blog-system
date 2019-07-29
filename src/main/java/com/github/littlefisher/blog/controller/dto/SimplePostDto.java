package com.github.littlefisher.blog.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @author jinyanan
 * @since 2019-07-29 10:12
 */
@Data
@Builder
public class SimplePostDto {

    /** 博文id */
    private Integer postId;

    /** 博文标题 */
    private String title;

    /** 博文预览 */
    private String previewContent;

    /** 作者id */
    private Integer authorId;

    /** 作者名称 */
    private String authorName;

    /** 标签 */
    private List<TagDto> tagList;

    /** 阅读数 */
    private Integer readTimes;

    /** 点赞数 */
    private Integer likedTimes;

    /** 创建时间 */
    private LocalDateTime createTime;
}
