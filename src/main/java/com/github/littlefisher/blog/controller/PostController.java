package com.github.littlefisher.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.littlefisher.blog.controller.dto.PostDto;
import com.github.littlefisher.blog.controller.dto.SimplePostDto;
import com.github.littlefisher.blog.controller.dto.base.BaseResponseDto;
import com.github.littlefisher.blog.service.PostService;

/**
 * @author jinyanan
 * @since 2019-07-29 10:09
 */
@RestController
@RequestMapping("/blog/api/v1/post")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * 分页查询作者名下所有博文简介
     *
     * @param authorId 作者id
     * @param pageNum 分页开始页
     * @param size 每页数量
     * @return 博文
     */
    @GetMapping
    public BaseResponseDto<Page<SimplePostDto>> queryPostByAuthorId(@RequestParam Integer authorId,
        @RequestParam Integer pageNum, @RequestParam Integer size) {
        Page<SimplePostDto> postPage = postService.queryPostByAuthorId(authorId, PageRequest.of(pageNum, size));
        return BaseResponseDto.success(postPage);
    }

    /**
     * 查询博文
     *
     * @param postId 博文id
     * @return 博文
     */
    @GetMapping("/{postId}/content")
    public BaseResponseDto<PostDto> queryPostContent(@PathVariable("postId") Integer postId) {
        return BaseResponseDto.success(postService.queryPostContent(postId));
    }
}
