package com.github.littlefisher.blog.controller.dto.base;

import lombok.Builder;
import lombok.Data;

/**
 * @author jinyanan
 * @since 2019-07-25 14:55
 */
@Data
@Builder
public class BaseResponseDto<T> {

    /** 是否成功，true-成功，false-失败 */
    private Boolean success;

    /** 失败code */
    private String errorCode;

    /** 失败说明 */
    private String errorMessage;

    /** 返回数据 */
    private T data;
}
