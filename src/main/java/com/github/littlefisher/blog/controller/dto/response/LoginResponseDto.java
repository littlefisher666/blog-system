package com.github.littlefisher.blog.controller.dto.response;

import com.github.littlefisher.blog.controller.dto.UserDto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author jinyanan
 * @since 2019/11/27 09:55
 */
@Data
@SuperBuilder
public class LoginResponseDto {

    /** 是否登录成功 */
    private Boolean success;

    /**
     * 用户类型
     * {@link com.github.littlefisher.blog.enums.UserTypeEnum}
     */
    private String userType;

    /** 用户类型 */
    private UserDto userInfo;
}
