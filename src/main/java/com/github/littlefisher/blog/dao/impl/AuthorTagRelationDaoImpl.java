package com.github.littlefisher.blog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.littlefisher.blog.configuration.dao.AbstractBaseDaoImpl;
import com.github.littlefisher.blog.dao.AuthorTagRelationDao;
import com.github.littlefisher.blog.dao.example.AuthorTagRelationDoExample;
import com.github.littlefisher.blog.dao.mapper.AuthorTagRelationDoMapper;
import com.github.littlefisher.blog.dao.model.AuthorTagRelationDo;

/**
 * @author jinyanan
 * @since 2019/11/26 10:11
 */
@Repository
public class AuthorTagRelationDaoImpl extends AbstractBaseDaoImpl<AuthorTagRelationDo, AuthorTagRelationDoMapper>
    implements AuthorTagRelationDao {

    @Override
    public List<AuthorTagRelationDo> getByAuthorId(Integer authorId) {
        AuthorTagRelationDoExample example = new AuthorTagRelationDoExample();
        example.createCriteria()
            .andAuthorIdEqualTo(authorId);
        return getMapper().selectByExample(example);
    }
}
