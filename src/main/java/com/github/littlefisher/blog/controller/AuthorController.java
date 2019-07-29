package com.github.littlefisher.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.littlefisher.blog.controller.dto.CurrentAuthorDto;
import com.github.littlefisher.blog.controller.dto.base.BaseResponseDto;
import com.github.littlefisher.blog.service.AuthorService;

/**
 * @author jinyanan
 * @since 2019-07-25 14:52
 */
@RestController
@RequestMapping("/blog/api/v1/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    /**
     * 查询当前作者
     *
     * @return 作者
     */
    @GetMapping("/default")
    public BaseResponseDto<CurrentAuthorDto> queryCurrentAuthor() {
        return BaseResponseDto.success(authorService.queryCurrentAuthor());
    }
}
