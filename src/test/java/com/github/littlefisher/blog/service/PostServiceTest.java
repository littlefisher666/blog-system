package com.github.littlefisher.blog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
            "/Users/littlefisher/Documents/LittleFisher/Workspaces/myself/CI_LitterFisher/source/_posts", "/Users/littlefisher/Downloads/statistic.csv");
    }
}