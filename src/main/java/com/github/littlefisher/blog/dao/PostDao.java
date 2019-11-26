package com.github.littlefisher.blog.dao;

import java.util.List;

import com.github.littlefisher.blog.configuration.dao.IBaseDao;
import com.github.littlefisher.blog.dao.model.PostDo;

/**
 * @author jinyanan
 * @since 2019/11/26 10:10
 */
public interface PostDao extends IBaseDao<PostDo> {

    /**
     * 根据作者id和标签id查询博文
     *
     * @param authorId 作者id
     * @param tagId 标签id
     * @return 博文
     */
    List<PostDo> findByAuthorIdAndTag(Integer authorId, Integer tagId);

    /**
     * 阅读数+1
     *
     * @param postId 博文id
     */
    void updateReadTimes(Integer postId);
}
