package com.github.littlefisher.blog.service;

import com.github.littlefisher.blog.controller.dto.PostDto;
import com.github.littlefisher.blog.controller.dto.SimplePostDto;
import com.github.littlefisher.blog.controller.dto.request.InsertPostRequestDto;
import com.github.littlefisher.blog.controller.dto.request.UpdatePostRequestDto;
import com.github.littlefisher.mybatis.pagehelper.PageInfo;
import com.github.littlefisher.mybatis.pagehelper.PageParam;

/**
 * @author jinyanan
 * @since 2019-07-29 11:04
 */
public interface PostService {

    /**
     * 分页查询博文信息
     *
     * @param authorId 作者id
     * @param tagId 标签id
     * @param page 分页信息
     * @return 博文信息
     */
    PageInfo<SimplePostDto> queryPostByAuthorId(Integer authorId, Integer tagId, PageParam page);

    /**
     * 查询博文
     *
     * @param postId 博文id
     * @return 博文
     */
    PostDto queryPostContent(Integer postId);

    /**
     * 增加阅读数
     *
     * @param postId 博文id
     */
    void read(Integer postId);

    /**
     * 增加博文
     *
     * @param request 请求入参
     */
    void insertPost(InsertPostRequestDto request);

    /**
     * 修改博文
     *
     * @param postId 博文id
     * @param request 请求入参
     */
    void updatePost(Integer postId, UpdatePostRequestDto request);
}
