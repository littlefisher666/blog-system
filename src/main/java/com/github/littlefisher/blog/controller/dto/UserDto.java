package com.github.littlefisher.blog.controller.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author jinyanan
 * @since 2019/11/27 10:21
 */
@Data
@SuperBuilder
public class UserDto {

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 账号
     */
    private String accountNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
