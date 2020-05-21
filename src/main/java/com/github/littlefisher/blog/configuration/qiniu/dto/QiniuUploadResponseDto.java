package com.github.littlefisher.blog.configuration.qiniu.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author jinyanan
 * @since 2020/5/9 16:22
 */
@Data
@SuperBuilder
public class QiniuUploadResponseDto {

    private String hash;

    private String filePath;
}
