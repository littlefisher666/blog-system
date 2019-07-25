package com.github.littlefisher.blog.controller;

import com.github.littlefisher.blog.controller.dto.base.BaseResponseDto;
import com.github.littlefisher.blog.controller.dto.CurrentAuthorDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jinyanan
 * @since 2019-07-25 14:52
 */
@RestController
@RequestMapping("/blog/api/v1/author")
public class AuthorController {

    @GetMapping("/{id}")
    public BaseResponseDto<CurrentAuthorDto> queryCurrentAuthor(@PathVariable("id") String id) {
        return null;
    }
}
