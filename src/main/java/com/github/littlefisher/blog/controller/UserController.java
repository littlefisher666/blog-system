package com.github.littlefisher.blog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.littlefisher.blog.controller.dto.base.BaseResponseDto;
import com.github.littlefisher.blog.controller.dto.request.LoginRequestDto;
import com.github.littlefisher.blog.controller.dto.response.LoginResponseDto;
import com.github.littlefisher.blog.service.UserService;

/**
 * @author jinyanan
 * @since 2019/11/27 09:52
 */
@RestController
@RequestMapping("/blog/api/v1/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param request 入参
     * @return 出参
     */
    @PostMapping("/session")
    public BaseResponseDto<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        return BaseResponseDto.success(userService.login(request));
    }
}
