package com.github.littlefisher.blog.configuration.sftp.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jinyanan
 * @since 2019-08-22 16:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SftpClientTest {

    @Autowired
    private SftpClient sftpClient;

    @Test
    public void getFile() {
        byte[] file = sftpClient.getFile("/upload/20190923", "text.txt");
        Assert.assertEquals("text", new String(file));
    }

    @Test
    public void uploadFile() {
        String text = "text";
        String path = sftpClient.uploadFile("/upload/20190923", "text.txt", text.getBytes());
        Assert.assertNotNull(path);
    }
}