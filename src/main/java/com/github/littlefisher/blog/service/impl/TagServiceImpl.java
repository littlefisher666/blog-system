package com.github.littlefisher.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.littlefisher.blog.controller.dto.TagDto;
import com.github.littlefisher.blog.dao.TagDao;
import com.github.littlefisher.blog.dao.model.TagDo;
import com.github.littlefisher.blog.service.TagService;
import com.github.littlefisher.mybatis.pagehelper.PageInfo;
import com.github.littlefisher.mybatis.pagehelper.PageParam;

/**
 * @author jinyanan
 * @since 2019/11/26 15:48
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public PageInfo<TagDto> queryTagList(String name, PageParam page) {
        PageInfo<TagDo> pageInfo = tagDao.queryTagByName(name, page);
        return new PageInfo<>(pageInfo, tag -> TagDto.builder()
            .name(tag.getName())
            .code(tag.getCode())
            .build());
    }
}
