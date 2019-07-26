package com.github.littlefisher.blog.dao.repository;

import com.github.littlefisher.blog.dao.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author littlefisher
 * @since 2019-07-26 10:18
 */
public interface AuthorRepository extends JpaRepository<Author, Integer>, JpaSpecificationExecutor<Author> {

}