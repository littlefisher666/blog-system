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

    /**
     * 成功的返回
     *
     * @param data 数据
     * @return 统一包装的返回
     */
    public static <T> BaseResponseDto<T> success(T data) {
        return BaseResponseDto.<T>builder().success(true)
            .data(data)
            .build();
    }

    /**
     * 失败的返回
     *
     * @param errorCode 失败code
     * @param errorMessage 失败说明
     * @return 统一包装的返回
     */
    public static <T> BaseResponseDto<T> fail(String errorCode, String errorMessage) {
        return BaseResponseDto.<T>builder().success(false)
            .errorCode(errorCode)
            .errorMessage(errorMessage)
            .build();
    }

}
