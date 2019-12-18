package com.github.littlefisher.blog.controller.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author jinyanan
 * @since 2019/11/27 09:54
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class LoginRequestDto {

    /** 账号 */
    @NotNull
    @NotBlank
    private String accountNo;

    /** 密码 */
    @NotNull
    @NotBlank
    private String password;
}
