package com.github.littlefisher.blog.dao;

import java.util.List;

import com.github.littlefisher.blog.configuration.dao.IBaseDao;
import com.github.littlefisher.blog.dao.model.AuthorTagRelationDo;

/**
 * @author jinyanan
 * @since 2019/11/26 10:10
 */
public interface AuthorTagRelationDao extends IBaseDao<AuthorTagRelationDo> {

    /**
     * 根据作者id查询标签
     *
     * @param authorId 作者id
     * @return 作者标签关联关系
     */
    List<AuthorTagRelationDo> getByAuthorId(Integer authorId);
}
