package com.github.littlefisher.blog.service;

import com.github.littlefisher.blog.controller.dto.CurrentAuthorDto;

/**
 * @author jinyanan
 * @since 2019-07-25 17:55
 */
public interface AuthorService {

    /**
     * 查询当前作者
     *
     * @return 当前作者
     */
    CurrentAuthorDto queryCurrentAuthor();
}
