package com.github.littlefisher.blog.configuration.qiniu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.littlefisher.blog.configuration.qiniu.client.QiniuClient;
import com.github.littlefisher.blog.configuration.qiniu.client.QiniuClientImpl;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * @author jinyanan
 * @since 2020/5/9 16:17
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(QiniuProperties.class)
public class QiniuAutoConfiguration {

    @Autowired
    private QiniuProperties qiniuProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public QiniuClient client() {
        Configuration cfg = new Configuration(Region.huadong());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        String upToken = auth.uploadToken(qiniuProperties.getBucket());
        return QiniuClientImpl.builder()
            .uploadManager(uploadManager)
            .upToken(upToken)
            .objectMapper(objectMapper)
            .restTemplate(new RestTemplate())
            .domain(qiniuProperties.getDomain())
            .auth(auth)
            .build();
    }
}
