package com.github.littlefisher.blog.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * 根据作者id和标签id查询博文
     *
     * @param authorId 作者id
     * @param tagId 标签id
     * @param pageable 分页参数
     * @return 博文
     */
    @Query(value =
        "select a.post_id, a.title, a.preview, a.content_url, a.author_id, a.read_times, a.liked_times, a.create_time, a.update_time "
            + "from post a LEFT JOIN post_tag_relation b on a.post_id = b.post_id where a.author_id = :authorId and b.tag_id = :tagId ",
        countQuery = "select count(*) from post a LEFT JOIN post_tag_relation b on a.post_id = b.post_id where a.author_id = :authorId and b.tag_id = :tagId",
        nativeQuery = true)
    Page<Post> findByAuthorIdAndTag(Integer authorId, Integer tagId, Pageable pageable);
}