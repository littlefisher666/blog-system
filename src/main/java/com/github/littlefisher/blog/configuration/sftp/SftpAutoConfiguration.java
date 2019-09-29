package com.github.littlefisher.blog.configuration.sftp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.littlefisher.blog.configuration.sftp.client.SftpClient;
import com.github.littlefisher.blog.configuration.sftp.client.impl.SftpClientImpl;

/**
 * @author jinyanan
 * @since 2019-08-21 11:25
 */
@Configuration
@EnableConfigurationProperties(SftpProperties.class)
public class SftpAutoConfiguration {

    @Autowired
    private SftpProperties sftpProperties;

    @Bean
    public SftpClient sftpClient() {
        SftpClientImpl client = new SftpClientImpl();
        client.setIp(sftpProperties.getIp());
        client.setUserName(sftpProperties.getUserName());
        client.setPassword(sftpProperties.getPassword());
        client.setPort(sftpProperties.getPort());
        return client;
    }
}
