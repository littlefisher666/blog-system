package com.github.littlefisher.blog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.littlefisher.blog.configuration.dao.AbstractBaseDaoImpl;
import com.github.littlefisher.blog.dao.PostTagRelationDao;
import com.github.littlefisher.blog.dao.example.PostTagRelationDoExample;
import com.github.littlefisher.blog.dao.mapper.PostTagRelationDoMapper;
import com.github.littlefisher.blog.dao.model.PostTagRelationDo;
import com.github.littlefisher.blog.dao.model.ext.PostTagRelationWithNameDo;

/**
 * @author jinyanan
 * @since 2019/11/26 10:11
 */
@Repository
public class PostTagRelationDaoImpl extends AbstractBaseDaoImpl<PostTagRelationDo, PostTagRelationDoMapper>
    implements PostTagRelationDao {
    @Override
    public List<PostTagRelationDo> findByPostIdIn(List<Integer> postIdList) {
        PostTagRelationDoExample example = new PostTagRelationDoExample();
        example.createCriteria()
            .andPostIdIn(postIdList);
        return getMapper().selectByExample(example);
    }

    @Override
    public List<PostTagRelationWithNameDo> findAllTagRelationByPostId(Integer postId) {
        return getMapper().findAllTagRelationByPostId(postId);
    }
}
