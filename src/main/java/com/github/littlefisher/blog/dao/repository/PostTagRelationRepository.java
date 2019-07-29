package com.github.littlefisher.blog.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.littlefisher.blog.dao.entity.PostTagRelation;

/**
 * @author littlefisher
 * @since 2019-07-229 11:51
 */
public interface PostTagRelationRepository
    extends JpaRepository<PostTagRelation, Integer>, JpaSpecificationExecutor<PostTagRelation> {

    /**
     * 批量根据博文id查询标签
     *
     * @param postIdList 博文id列表
     * @return 标签关联关系
     */
    List<PostTagRelation> findByPostIdWithin(List<Integer> postIdList);
}