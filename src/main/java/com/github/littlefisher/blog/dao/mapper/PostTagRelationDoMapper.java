package com.github.littlefisher.blog.dao.mapper;

import java.util.List;

import com.github.littlefisher.blog.dao.model.PostTagRelationDo;
import com.github.littlefisher.blog.dao.model.ext.PostTagRelationWithNameDo;
import com.github.littlefisher.mybatis.common.CommonMapper;

/**
 * post_tag_relation Mapper 接口<br>
 *
 * Created on 2019年11月25日
 *
 * @author littlefisher
 * @version 1.0
 * @since v1.0
 */
public interface PostTagRelationDoMapper extends CommonMapper<PostTagRelationDo> {

    /**
     * 根据博文id查询博文所有tag
     *
     * @param postId 博文id
     * @return tag
     */
    List<PostTagRelationWithNameDo> findAllTagRelationByPostId(Integer postId);
}