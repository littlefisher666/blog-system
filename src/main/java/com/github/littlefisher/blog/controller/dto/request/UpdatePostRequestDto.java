package com.github.littlefisher.blog.controller.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.github.littlefisher.blog.controller.dto.TagDto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author jinyanan
 * @since 2019/11/25 14:10
 */
@Data
@SuperBuilder
public class UpdatePostRequestDto {

    /** 博文标题 */
    @NotNull
    @NotBlank
    private String title;

    /** 博文全文 */
    @NotNull
    @NotBlank
    private String content;

    /** 作者id */
    @NotNull
    private Integer authorId;

    /** 标签 */
    private List<@Valid TagDto> tagList;
}
