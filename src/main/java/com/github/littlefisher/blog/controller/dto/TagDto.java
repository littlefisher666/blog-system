package com.github.littlefisher.blog.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

/**
 * @author jinyanan
 * @since 2019-07-25 17:19
 */
@Data
@Builder
public class TagDto {

    /** tag名称 */
    @NotNull
    @NotBlank
    private String name;

    /** tag编码 */
    private Integer code;
}
