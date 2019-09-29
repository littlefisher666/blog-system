package com.github.littlefisher.blog.configuration.sftp;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author jinyanan
 * @since 2019-08-21 11:25
 */
@Data
@ConfigurationProperties("sftp")
public class SftpProperties {

    /** sftp用户名 */
    private String userName;

    /** sftp密码 */
    private String password;

    /** sftp ip */
    private String ip;

    /** sftp密码 */
    private Integer port = 22;
}
