package com.github.littlefisher.blog.dao.repository;

import com.github.littlefisher.blog.dao.entity.AuthorTagRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author littlefisher
 * @since 2019-07-26 10:18
 */
public interface AuthorTagRelationRepository extends JpaRepository<AuthorTagRelation, Integer>, JpaSpecificationExecutor<AuthorTagRelation> {

    /**
     * 根据作者id查询标签
     *
     * @param authorId 作者id
     * @return 作者标签关联关系
     */
    List<AuthorTagRelation> getByAuthorId(Integer authorId);
}