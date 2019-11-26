package com.github.littlefisher.blog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.littlefisher.blog.configuration.dao.AbstractBaseDaoImpl;
import com.github.littlefisher.blog.dao.PostDao;
import com.github.littlefisher.blog.dao.mapper.PostDoMapper;
import com.github.littlefisher.blog.dao.model.PostDo;

/**
 * @author jinyanan
 * @since 2019/11/26 10:11
 */
@Repository
public class PostDaoImpl extends AbstractBaseDaoImpl<PostDo, PostDoMapper> implements PostDao {

    @Override
    public List<PostDo> findByAuthorIdAndTag(Integer authorId, Integer tagId) {
        return getMapper().findByAuthorIdAndTag(authorId, tagId);
    }

    @Override
    public void updateReadTimes(Integer postId) {
        getMapper().updateReadTimes(postId);
    }
}
