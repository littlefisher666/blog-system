package com.github.littlefisher.blog.configuration.qiniu.client;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.littlefisher.blog.configuration.qiniu.dto.QiniuDownloadResponseDto;
import com.github.littlefisher.blog.configuration.qiniu.dto.QiniuUploadResponseDto;
import com.github.littlefisher.blog.exception.QiniuServerException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author jinyanan
 * @since 2020/5/9 16:23
 */
@Data
@SuperBuilder
public class QiniuClientImpl implements QiniuClient {

    private UploadManager uploadManager;

    private String upToken;

    private ObjectMapper objectMapper;

    private RestTemplate restTemplate;

    private String domain;

    private Auth auth;

    @Override
    public QiniuUploadResponseDto upload(byte[] data, String fileName) {
        try {
            Response response = uploadManager.put(data, fileName, upToken);
            DefaultPutRet putRet = objectMapper.readValue(response.bodyString(), DefaultPutRet.class);
            return QiniuUploadResponseDto.builder()
                .hash(putRet.hash)
                .filePath(putRet.key)
                .build();
        } catch (IOException e) {
            throw new QiniuServerException();
        }
    }

    @Override
    public QiniuDownloadResponseDto download(String fileName) {
        String publicUrl = String.format("%s/%s", domain, fileName);
        String generatedUrl = auth.privateDownloadUrl(publicUrl);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<byte[]> response = restTemplate.exchange(generatedUrl, HttpMethod.GET, new HttpEntity<>(headers),
            byte[].class);
        return QiniuDownloadResponseDto.builder()
            .data(response.getBody())
            .build();
    }
}
