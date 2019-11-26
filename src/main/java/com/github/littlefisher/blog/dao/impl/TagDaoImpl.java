package com.github.littlefisher.blog.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.littlefisher.blog.configuration.dao.AbstractBaseDaoImpl;
import com.github.littlefisher.blog.dao.TagDao;
import com.github.littlefisher.blog.dao.example.TagDoExample;
import com.github.littlefisher.blog.dao.mapper.TagDoMapper;
import com.github.littlefisher.blog.dao.model.TagDo;

/**
 * @author jinyanan
 * @since 2019/11/26 10:12
 */
@Repository
public class TagDaoImpl extends AbstractBaseDaoImpl<TagDo, TagDoMapper> implements TagDao {
    @Override
    public List<TagDo> findAllByIdList(List<Integer> tagIdList) {
        TagDoExample example = new TagDoExample();
        example.createCriteria()
            .andCodeIn(tagIdList);
        return getMapper().selectByExample(example);
    }

    @Override
    public List<TagDo> queryPostTagByAuthorId(Integer authorId) {
        return getMapper().queryPostTagByAuthorId(authorId);
    }

    @Override
    public Optional<TagDo> findOneByName(String name) {
        TagDoExample example = new TagDoExample();
        example.createCriteria()
            .andNameEqualTo(name);
        return Optional.ofNullable(getMapper().selectOneByExample(example));
    }
}
