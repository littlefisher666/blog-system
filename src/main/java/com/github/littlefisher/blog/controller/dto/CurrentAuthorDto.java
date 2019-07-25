package com.github.littlefisher.blog.controller.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @author jinyanan
 * @since 2019-07-25 14:58
 */
@Data
@Builder
public class CurrentAuthorDto {
    /** 名称 */
    private String name;
    /** 头像 */
    private String avatar;
    /** 邮箱 */
    private String email;
    /** 签名 */
    private String signature;
    /** 职位 */
    private String job;
    /** 团队 */
    private String group;
    /** 标签 */
    private List<TagDto> tags;
    /** 坐标 */
    private GeographicDto geographic;
    /** 详细地址 */
    private String address;
    /** 手机号 */
    private String phone;
}
