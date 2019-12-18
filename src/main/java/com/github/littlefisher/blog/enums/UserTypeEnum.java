package com.github.littlefisher.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jinyanan
 * @since 2019/11/27 10:11
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {
    /** 访客 */
    VISITOR("1"),
    /** 作者 */
    AUTHOR("2"),
    ;

    private String code;

}
