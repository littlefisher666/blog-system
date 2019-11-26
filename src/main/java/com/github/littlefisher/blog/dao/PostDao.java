package com.github.littlefisher.blog.dao;

import com.github.littlefisher.blog.configuration.dao.IBaseDao;
import com.github.littlefisher.blog.dao.model.PostDo;
import com.github.littlefisher.mybatis.pagehelper.PageInfo;
import com.github.littlefisher.mybatis.pagehelper.PageParam;

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
     * @param page 分页参数
     * @return 博文
     */
    PageInfo<PostDo> findByAuthorIdAndTag(Integer authorId, Integer tagId, PageParam page);

    /**
     * 阅读数+1
     *
     * @param postId 博文id
     */
    void updateReadTimes(Integer postId);
}
