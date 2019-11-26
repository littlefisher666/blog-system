package com.github.littlefisher.blog.service;

import com.github.littlefisher.blog.controller.dto.TagDto;
import com.github.littlefisher.mybatis.pagehelper.PageInfo;
import com.github.littlefisher.mybatis.pagehelper.PageParam;

/**
 * @author jinyanan
 * @since 2019/11/26 15:48
 */
public interface TagService {

    /**
     * 查询tag列表
     *
     * @param name tag名称，模糊匹配
     * @param page 分页参数
     * @return tag列表
     */
    PageInfo<TagDto> queryTagList(String name, PageParam page);
}
