package com.github.littlefisher.blog.dao.repository;

import com.github.littlefisher.blog.dao.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author littlefisher
 * @since 2019-07-26 10:18
 */
public interface TagRepository extends JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {

}