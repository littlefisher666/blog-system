package com.github.littlefisher.blog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.littlefisher.blog.controller.dto.TagDto;
import com.github.littlefisher.mybatis.pagehelper.PageInfo;
import com.github.littlefisher.mybatis.pagehelper.PageParam;

import static org.junit.Assert.assertNotNull;

/**
 * @author jinyanan
 * @since 2019/11/26 15:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    public void queryTagList() {
        PageInfo<TagDto> pageInfo = tagService.queryTagList("J", PageParam.builder()
            .pageNum(1)
            .pageSize(5)
            .build());
        assertNotNull(pageInfo);
    }
}