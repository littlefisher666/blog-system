package com.github.littlefisher.blog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.littlefisher.blog.configuration.dao.AbstractBaseDaoImpl;
import com.github.littlefisher.blog.dao.AuthorDao;
import com.github.littlefisher.blog.dao.example.AuthorDoExample;
import com.github.littlefisher.blog.dao.mapper.AuthorDoMapper;
import com.github.littlefisher.blog.dao.model.AuthorDo;

/**
 * @author jinyanan
 * @since 2019/11/26 10:11
 */
@Repository
public class AuthorDaoImpl extends AbstractBaseDaoImpl<AuthorDo, AuthorDoMapper> implements AuthorDao {
    @Override
    public List<AuthorDo> findAllByIdList(List<Integer> authorIdList) {
        AuthorDoExample example = new AuthorDoExample();
        example.createCriteria()
            .andAuthorIdIn(authorIdList);
        return getMapper().selectByExample(example);
    }
}
