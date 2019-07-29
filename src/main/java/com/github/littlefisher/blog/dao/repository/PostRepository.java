package com.github.littlefisher.blog.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.littlefisher.blog.dao.entity.Post;

/**
 * @author littlefisher
 * @since 2019-07-229 11:51
 */
public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

}