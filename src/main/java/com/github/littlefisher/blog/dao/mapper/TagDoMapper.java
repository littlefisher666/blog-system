package com.github.littlefisher.blog.dao.mapper;

import java.util.List;

import com.github.littlefisher.blog.dao.model.TagDo;
import com.github.littlefisher.mybatis.common.CommonMapper;

/**
 * tag Mapper 接口<br>
 *
 * Created on 2019年11月25日
 *
 * @author littlefisher
 * @version 1.0
 * @since v1.0
 */
public interface TagDoMapper extends CommonMapper<TagDo> {

    /**
     * 根据作者id查询都有哪些博文标签
     *
     * @param authorId 作者id
     * @return 博文标签code
     */
    // @Query(value =
    //     "select distinct b.code, b.name, b.create_time, b.update_time from post_tag_relation a left join tag b on a.tag_id = b.code "
    //         + "left join post c on a.post_id = c.post_id where c.author_id = :authorId  order by b.name",
    //     nativeQuery = true)
    List<TagDo> queryPostTagByAuthorId(Integer authorId);

}