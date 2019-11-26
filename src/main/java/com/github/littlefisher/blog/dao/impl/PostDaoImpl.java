package com.github.littlefisher.blog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.littlefisher.blog.configuration.dao.AbstractBaseDaoImpl;
import com.github.littlefisher.blog.dao.PostDao;
import com.github.littlefisher.blog.dao.mapper.PostDoMapper;
import com.github.littlefisher.blog.dao.model.PostDo;
import com.github.littlefisher.mybatis.pagehelper.PageInfo;
import com.github.littlefisher.mybatis.pagehelper.PageParam;
import com.github.pagehelper.PageHelper;

/**
 * @author jinyanan
 * @since 2019/11/26 10:11
 */
@Repository
public class PostDaoImpl extends AbstractBaseDaoImpl<PostDo, PostDoMapper> implements PostDao {

    @Override
    public PageInfo<PostDo> findByAuthorIdAndTag(Integer authorId, Integer tagId, PageParam page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<PostDo> list = getMapper().findByAuthorIdAndTag(authorId, tagId);
        return new PageInfo<>(list);
    }

    @Override
    public void updateReadTimes(Integer postId) {
        getMapper().updateReadTimes(postId);
    }
}
