package com.github.littlefisher.blog.service;

import com.github.littlefisher.blog.controller.dto.request.LoginRequestDto;
import com.github.littlefisher.blog.controller.dto.response.LoginResponseDto;

/**
 * @author jinyanan
 * @since 2019/11/27 09:58
 */
public interface UserService {
    /**
     * 登录
     *
     * @param request 入参
     * @return 出参
     */
    LoginResponseDto login(LoginRequestDto request);
}
