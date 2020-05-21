package com.github.littlefisher.blog.configuration.qiniu.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.littlefisher.blog.configuration.qiniu.dto.QiniuDownloadResponseDto;
import com.github.littlefisher.blog.configuration.qiniu.dto.QiniuUploadResponseDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author jinyanan
 * @since 2020/5/21 07:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QiniuClientTest {

    @Autowired
    private QiniuClient qiniuClient;

    @Test
    public void upload() {
        String fileName = "中文.md";
        QiniuUploadResponseDto response = qiniuClient.upload(new String("test").getBytes(), fileName);
        assertNotNull(response);
        assertEquals(response.getFilePath(), fileName);
    }

    @Test
    public void download() {
        QiniuDownloadResponseDto response = qiniuClient.download("中文.md");
        assertNotNull(response);
    }

}