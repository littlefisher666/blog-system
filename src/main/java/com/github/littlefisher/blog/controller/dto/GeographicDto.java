package com.github.littlefisher.blog.controller.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author jinyanan
 * @since 2019-07-25 17:20
 */
@Data
@Builder
public class GeographicDto {

    /** 省份 */
    private ProvinceDto province;

    /** 城市 */
    private CityDto city;
}
