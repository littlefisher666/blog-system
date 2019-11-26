package com.github.littlefisher.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * Spring Boot启动器
 *
 * @author littlefisher
 */
@SpringBootApplication
@MapperScan("com.github.littlefisher.blog.dao.mapper.**")
public class BlogSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogSystemApplication.class, args);
    }

}
