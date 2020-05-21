package com.github.littlefisher.blog.configuration.qiniu.client;

import com.github.littlefisher.blog.configuration.qiniu.dto.QiniuDownloadResponseDto;
import com.github.littlefisher.blog.configuration.qiniu.dto.QiniuUploadResponseDto;

/**
 * @author jinyanan
 * @since 2020/5/9 16:21
 */
public interface QiniuClient {

    /**
     * 七牛云上传
     *
     * @param data 文件
     * @param fileName 文件名
     * @return 结果
     */
    QiniuUploadResponseDto upload(byte[] data, String fileName);

    /**
     * 七牛云下载
     *
     * @param fileName 文件名
     * @return 结果
     */
    QiniuDownloadResponseDto download(String fileName);
}
