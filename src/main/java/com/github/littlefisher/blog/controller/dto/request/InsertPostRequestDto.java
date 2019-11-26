package com.github.littlefisher.blog.controller.dto.request;

import java.util.List;

import com.github.littlefisher.blog.controller.dto.TagDto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author jinyanan
 * @since 2019/11/25 14:03
 */
@Data
@SuperBuilder
public class InsertPostRequestDto {

    /** 博文标题 */
    private String title;

    /** 博文全文 */
    private String content;

    /** 作者id */
    private Integer authorId;

    /** 标签 */
    private List<TagDto> tagList;
}
