package com.github.littlefisher.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.github.littlefisher.blog.controller.dto.PostDto;
import com.github.littlefisher.blog.controller.dto.SimplePostDto;

/**
 * @author jinyanan
 * @since 2019-07-29 11:04
 */
public interface PostService {

    /**
     * 分页查询博文信息
     *
     * @param authorId 作者id
     * @param page 分页信息
     * @return 博文信息
     */
    Page<SimplePostDto> queryPostByAuthorId(Integer authorId, PageRequest page);

    /**
     * 查询博文
     *
     * @param postId 博文id
     * @return 博文
     */
    PostDto queryPostContent(Integer postId);

    /**
     * 从本地把原来的markdown存入数据库
     *
     * @param directoryPath markdown文件所在目录
     * @param statisticPath 统计文件地址
     */
    void loanFromDisk(String directoryPath, String statisticPath);
}
