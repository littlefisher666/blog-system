package com.github.littlefisher.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.littlefisher.blog.controller.dto.PostDto;
import com.github.littlefisher.blog.controller.dto.SimplePostDto;
import com.github.littlefisher.blog.controller.dto.base.BaseResponseDto;
import com.github.littlefisher.blog.controller.dto.request.InsertPostRequestDto;
import com.github.littlefisher.blog.controller.dto.request.UpdatePostRequestDto;
import com.github.littlefisher.blog.service.PostService;
import com.github.littlefisher.mybatis.pagehelper.PageInfo;
import com.github.littlefisher.mybatis.pagehelper.PageParam;

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
     * @param pageNum 分页开始页，从0开始
     * @param size 每页数量
     * @return 博文
     */
    @GetMapping("/author/{authorId}")
    public BaseResponseDto<PageInfo<SimplePostDto>> queryPostByAuthorId(@PathVariable Integer authorId,
        @RequestParam Integer pageNum, @RequestParam Integer size) {
        PageInfo<SimplePostDto> postPage = postService.queryPostByAuthorId(authorId, null, PageParam.builder()
            .pageNum(pageNum)
            .pageSize(size)
            .build());
        return BaseResponseDto.success(postPage);
    }

    /**
     * 分页查询作者名下所有博文简介
     *
     * @param authorId 作者id
     * @param tagId 标签id
     * @param pageNum 分页开始页，从0开始
     * @param size 每页数量
     * @return 博文
     */
    @GetMapping("/author/{authorId}/tag/{tagId}")
    public BaseResponseDto<PageInfo<SimplePostDto>> queryPostByAuthorIdAndTag(@PathVariable Integer authorId,
        @PathVariable Integer tagId, @RequestParam Integer pageNum, @RequestParam Integer size) {
        PageInfo<SimplePostDto> postPage = postService.queryPostByAuthorId(authorId, tagId, PageParam.builder()
            .pageNum(pageNum)
            .pageSize(size)
            .build());
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

    /**
     * 增加阅读数
     *
     * @param postId 博文id
     * @return 结果
     */
    @PostMapping("/{postId}/read")
    public BaseResponseDto<Void> read(@PathVariable("postId") Integer postId) {
        postService.read(postId);
        return BaseResponseDto.success();
    }

    /**
     * 增加博文
     *
     * @param request 请求入参
     * @return 结果
     */
    @PostMapping
    public BaseResponseDto<Void> insertPost(@RequestBody InsertPostRequestDto request) {
        postService.insertPost(request);
        return BaseResponseDto.success();
    }

    /**
     * 修改博文
     *
     * @param postId 博文编号
     * @param request 请求入参
     * @return 结果
     */
    @PutMapping("{postId}")
    public BaseResponseDto<Void> updatePost(@PathVariable Integer postId, @RequestBody UpdatePostRequestDto request) {
        postService.updatePost(postId, request);
        return BaseResponseDto.success();
    }

    /**
     * 博文从sftp迁移到七牛云
     *
     * @return 结果
     */
    @GetMapping("/convert")
    public BaseResponseDto<Void> convert2Qiniu() {
        postService.convert2Qiniu();
        return BaseResponseDto.success();
    }

}
