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
    /** 作者id */
    private Integer authorId;
    /** 名称 */
    private String name;
    /** 头像地址 */
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
    private List<TagDto> authorTags;
    /** 博文标签 */
    private List<TagDto> postTags;
    /** 所处城市 */
    private CityDto city;
    /** 详细地址 */
    private String address;
    /** 手机号 */
    private String phone;
}
