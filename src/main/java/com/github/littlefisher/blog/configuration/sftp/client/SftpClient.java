package com.github.littlefisher.blog.configuration.sftp.client;

/**
 * sftp客户端
 *
 * @author jinyanan
 * @since 2019-08-21 11:28
 */
public interface SftpClient {

    /**
     * 下载文件
     *
     * @param directory 文件路径
     * @param fileName 文件名
     * @return 文件byte数组
     */
    byte[] getFile(String directory, String fileName);

    /**
     * 下载文件
     *
     * @param filePath 文件路径，带文件名
     * @return 文件byte数组
     */
    byte[] getFile(String filePath);

    /**
     * 上传文件
     *
     * @param directory 上传文件目录
     * @param sftpFileName 文件名
     * @param file 文件
     * @return 文件路径
     */
    String uploadFile(String directory, String sftpFileName, byte[] file);
}
