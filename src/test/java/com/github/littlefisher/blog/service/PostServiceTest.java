package com.github.littlefisher.blog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.littlefisher.blog.controller.dto.PostDto;
import com.github.littlefisher.blog.controller.dto.SimplePostDto;
import com.github.littlefisher.blog.controller.dto.TagDto;
import com.github.littlefisher.blog.controller.dto.request.InsertPostRequestDto;
import com.github.littlefisher.blog.controller.dto.request.UpdatePostRequestDto;
import com.github.littlefisher.mybatis.pagehelper.PageInfo;
import com.github.littlefisher.mybatis.pagehelper.PageParam;
import com.google.common.collect.Lists;

import static org.junit.Assert.assertNotNull;

/**
 * @author jinyanan
 * @since 2019-08-15 19:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    public void queryPostContent() {
        PostDto post = postService.queryPostContent(238);
        assertNotNull(post);
    }

    @Test
    public void queryPostByAuthorId() {
        Integer authorId = 1;
        PageInfo<SimplePostDto> postPage = postService.queryPostByAuthorId(authorId, null, PageParam.builder()
            .pageNum(1)
            .pageSize(5)
            .build());
        assertNotNull(postPage);
    }

    @Test
    public void read() {
        Integer postId = 229;
        postService.read(postId);
    }

    @Test
    public void insertPost() {
        InsertPostRequestDto request = InsertPostRequestDto.builder()
            .authorId(1)
            .content("before\n <!-- more --> \n after")
            .tagList(Lists.newArrayList(TagDto.builder()
                .code(221)
                .name("Java")
                .build(), TagDto.builder()
                .name("test")
                .build()))
            .title("test-title")
            .build();
        postService.insertPost(request);
    }

    @Test
    public void updatePost() {
        UpdatePostRequestDto request = UpdatePostRequestDto.builder()
            .authorId(1)
            .content("before1\n <!-- more --> \n after1")
            .tagList(Lists.newArrayList(TagDto.builder()
                .code(221)
                .name("Java")
                .build(), TagDto.builder()
                .name("test1")
                .build()))
            .title("test-title1")
            .build();
        postService.updatePost(366, request);
    }

    @Test
    public void convert2Qiniu() {
        postService.convert2Qiniu();
    }
}