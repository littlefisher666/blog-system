package com.github.littlefisher.blog.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jinyanan
 * @since 2019/11/12 10:44
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JasyptTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void encrypt() {
        String password = stringEncryptor.encrypt("l7-I7R1J8GFZQHn4dR301jtSGVKOygIKszgi2EeY");
        log.info(password);
    }

    @Test
    public void decrypt() {
        String key = "BuNJTGz262z0IPiPtTp/TB+sjIsv3axh";
        String decrypt = stringEncryptor.decrypt(key);
        log.info(decrypt);
    }
}
