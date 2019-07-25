package com.github.littlefisher.blog.controller.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author jinyanan
 * @since 2019-07-25 17:21
 */
@Data
@Builder
public class CityDto {

    /** 城市名称 */
    private String name;

    /** 城市编码 */
    private String code;
}
