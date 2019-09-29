package com.github.littlefisher.blog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.littlefisher.blog.controller.dto.PostDto;
import com.github.littlefisher.blog.controller.dto.SimplePostDto;

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
    public void loanFromDisk() {
        postService.loanFromDisk(
            "/Users/littlefisher/Documents/LittleFisher/Workspaces/myself/CI_LitterFisher/source/_posts",
            "/Users/littlefisher/Downloads/statistic.csv");
    }

    @Test
    public void queryPostContent() {
        PostDto post = postService.queryPostContent(238);
        assertNotNull(post);
    }

    @Test
    public void queryPostByAuthorId() {
        Integer authorId = 1;
        Page<SimplePostDto> postPage = postService.queryPostByAuthorId(authorId, PageRequest.of(1, 20, Sort.by(
            Sort.Order.desc("createTime"))));
        assertNotNull(postPage);
    }
}