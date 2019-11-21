package com.github.littlefisher.blog.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.github.littlefisher.blog.dao.entity.Post;

/**
 * @author littlefisher
 * @since 2019-07-229 11:51
 */
public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    /**
     * 阅读数+1
     *
     * @param postId 博文id
     */
    @Modifying
    @Query("update Post set read_times = read_times + 1 where post_id = :postId")
    void updateReadTimes(Integer postId);
}