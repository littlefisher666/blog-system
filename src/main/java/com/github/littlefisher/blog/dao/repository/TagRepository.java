package com.github.littlefisher.blog.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.github.littlefisher.blog.dao.entity.Tag;

/**
 * @author littlefisher
 * @since 2019-07-26 10:18
 */
public interface TagRepository extends JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {

    /**
     * 根据tag名称查询
     *
     * @param tagName tag名称
     * @return tag
     */
    Optional<Tag> findOneByName(String tagName);

    /**
     * 根据作者id查询都有哪些博文标签
     *
     * @param authorId 作者id
     * @return 博文标签code
     */
    @Query(value =
        "select distinct b.code, b.name, b.create_time, b.update_time from post_tag_relation a left join tag b on a.tag_id = b.code "
            + "left join post c on a.post_id = c.post_id where c.author_id = :authorId  order by b.name",
        nativeQuery = true)
    List<Tag> queryPostTagByAuthorId(Integer authorId);
}