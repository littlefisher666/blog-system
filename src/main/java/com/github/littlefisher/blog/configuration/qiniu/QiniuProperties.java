package com.github.littlefisher.blog.configuration.qiniu;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author jinyanan
 * @since 2020/5/9 16:17
 */
@Data
@ConfigurationProperties("qiniu")
public class QiniuProperties {

    /** accessKey */
    private String accessKey;

    /** secretKey */
    private String secretKey;

    /** bucket */
    private String bucket;

    /** 域名 */
    private String domain;

}
