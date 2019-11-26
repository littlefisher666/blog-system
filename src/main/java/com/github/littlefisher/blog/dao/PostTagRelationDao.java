package com.github.littlefisher.blog.dao;

import java.util.List;

import com.github.littlefisher.blog.configuration.dao.IBaseDao;
import com.github.littlefisher.blog.dao.model.PostTagRelationDo;
import com.github.littlefisher.blog.dao.model.ext.PostTagRelationWithNameDo;

/**
 * @author jinyanan
 * @since 2019/11/26 10:10
 */
public interface PostTagRelationDao extends IBaseDao<PostTagRelationDo> {

    /**
     * 批量根据博文id查询标签
     *
     * @param postIdList 博文id列表
     * @return 标签关联关系
     */
    List<PostTagRelationDo> findByPostIdIn(List<Integer> postIdList);

    /**
     * 根据博文id查询博文所有tag
     *
     * @param postId 博文id
     * @return tag
     */
    List<PostTagRelationWithNameDo> findAllTagRelationByPostId(Integer postId);
}
