package com.github.littlefisher.blog.dao;

import java.util.List;
import java.util.Optional;

import com.github.littlefisher.blog.configuration.dao.IBaseDao;
import com.github.littlefisher.blog.dao.model.TagDo;
import com.github.littlefisher.mybatis.pagehelper.PageInfo;
import com.github.littlefisher.mybatis.pagehelper.PageParam;

/**
 * @author jinyanan
 * @since 2019/11/26 10:11
 */
public interface TagDao extends IBaseDao<TagDo> {

    /**
     * 根据id批量查询
     *
     * @param tagIdList tagId列表
     * @return tag列表
     */
    List<TagDo> findAllByIdList(List<Integer> tagIdList);

    /**
     * 根据作者id查询都有哪些博文标签
     *
     * @param authorId 作者id
     * @return 博文标签code
     */
    List<TagDo> queryPostTagByAuthorId(Integer authorId);

    /**
     * 根据tag名称查询
     *
     * @param name tag名称
     * @return tag
     */
    Optional<TagDo> findOneByName(String name);

    /**
     * 分页查询tag列表
     *
     * @param name 名称
     * @param page 分页参数
     * @return tag列表
     */
    PageInfo<TagDo> queryTagByName(String name, PageParam page);
}
