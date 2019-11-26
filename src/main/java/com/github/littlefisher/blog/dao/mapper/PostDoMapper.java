package com.github.littlefisher.blog.dao.mapper;

import java.util.List;

import com.github.littlefisher.blog.dao.model.PostDo;
import com.github.littlefisher.mybatis.common.CommonMapper;

/**
 * post Mapper 接口<br>
 *
 * Created on 2019年11月25日
 *
 * @author littlefisher
 * @version 1.0
 * @since v1.0
 */
public interface PostDoMapper extends CommonMapper<PostDo> {

    /**
     * 阅读数+1
     *
     * @param postId 博文id
     */
    void updateReadTimes(Integer postId);

    /**
     * 根据作者id和标签id查询博文
     *
     * @param authorId 作者id
     * @param tagId 标签id
     * @return 博文
     */
    List<PostDo> findByAuthorIdAndTag(Integer authorId, Integer tagId);

}