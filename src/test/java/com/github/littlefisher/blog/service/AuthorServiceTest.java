package com.github.littlefisher.blog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.littlefisher.blog.controller.dto.CurrentAuthorDto;

import static org.junit.Assert.assertNotNull;

/**
 * @author jinyanan
 * @since 2019/11/21 17:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Test
    public void queryCurrentAuthor() {
        CurrentAuthorDto author = authorService.queryCurrentAuthor();
        assertNotNull(author);
    }
}