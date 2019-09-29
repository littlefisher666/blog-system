package com.github.littlefisher.blog.configuration.sftp.client.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.github.littlefisher.blog.configuration.sftp.client.SftpClient;
import com.github.littlefisher.blog.exception.SftpServerException;
import com.google.common.base.Splitter;
import com.google.common.io.ByteStreams;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jinyanan
 * @since 2019-08-21 11:33
 */
@Setter
@Slf4j
public class SftpClientImpl implements SftpClient {

    /** sftp用户名 */
    private String userName;

    /** sftp密码 */
    private String password;

    /** sftp ip */
    private String ip;

    /** sftp密码 */
    private Integer port;

    /** 分隔符 */
    private static final String SEPARATOR = "/";

    @Override
    public byte[] getFile(String directory, String fileName) {
        // 连接FTP服务器
        ChannelSftp channelSftp = connectFtpServer();
        // 进入FTP服务器指定目录
        if (changeWorkingDirectory(channelSftp, directory)) {
            try (InputStream fis = channelSftp.get(fileName)) {
                // InputStream转byte数组
                return ByteStreams.toByteArray(fis);
            } catch (IOException | SftpException e) {
                throw new SftpServerException("下载文件异常", e);
            } finally {
                disconnectFtpServer(channelSftp);
            }
        } else {
            throw new SftpServerException("进入目标目录失败");
        }
    }

    @Override
    public byte[] getFile(String filePath) {
        if (filePath.contains(SEPARATOR)) {
            List<String> pathList = Splitter.on(SEPARATOR)
                .omitEmptyStrings()
                .trimResults()
                .splitToList(filePath);
            String fileDirectory = pathList.stream()
                .limit(pathList.size() - 1)
                .collect(Collectors.joining(SEPARATOR));
            String fileName = pathList.get(pathList.size() - 1);
            return getFile(fileDirectory, fileName);
        } else {
            return getFile(SEPARATOR, filePath);
        }
    }

    @Override
    public String uploadFile(String directory, String sftpFileName, byte[] file) {
        // 连接FTP服务器
        ChannelSftp channelSftp = connectFtpServer();
        try {

            // 进入FTP服务器指定目录
            changeWorkingDirectory(channelSftp, directory);
            // 进入FTP服务器服务路径的相对路径
            channelSftp.put(new ByteArrayInputStream(file), sftpFileName);
            return directory + File.separator + sftpFileName;
        } catch (SftpException e) {
            throw new SftpServerException("上传sftp文件异常");
        }
    }

    /**
     * 连接ftp获取ChannelSftp
     */
    private ChannelSftp connectFtpServer() {
        ChannelSftp sftp;
        try {
            JSch jsch = new JSch();
            Session sshSession = jsch.getSession(userName, ip, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect(5000);
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            sftp.setFilenameEncoding(StandardCharsets.UTF_8.displayName());
            return sftp;
        } catch (JSchException | SftpException e) {
            throw new SftpServerException("连接sftp服务器异常", e);
        }
    }

    /**
     * 断开与对方FTP Server的连接
     */
    private void disconnectFtpServer(ChannelSftp sftpClient) {
        try {
            if (sftpClient != null) {
                sftpClient.disconnect();
                if (sftpClient.getSession() != null) {
                    sftpClient.getSession()
                        .disconnect();
                }
            }
        } catch (JSchException e) {
            throw new SftpServerException("断开sftp连接异常", e);
        }
    }

    /**
     * 进入文件目录
     *
     * @param fileDir 要进入的目录
     */
    private boolean changeWorkingDirectory(ChannelSftp sftpClient, String fileDir) {
        try {
            if (fileDir.trim()
                .length() > 0) {
                sftpClient.cd(fileDir);
            } else {
                sftpClient.cd(sftpClient.getHome());
            }
            return true;
        } catch (SftpException e) {
            if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                mkDir(sftpClient, fileDir);
                return true;
            } else {
                throw new SftpServerException("进入目标目录失败", e);
            }

        }
    }

    /**
     * 创建目录
     *
     * @param dir 目录
     */
    private void mkDir(ChannelSftp sftpClient, String dir) {
        List<String> subDirList = Splitter.on("/")
            .trimResults()
            .omitEmptyStrings()
            .splitToList(dir);
        cdDir(sftpClient, "/");
        for (String subDir : subDirList) {
            try {
                sftpClient.cd(subDir);
            } catch (SftpException e) {
                if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                    mkSingleDir(sftpClient, subDir);
                    cdDir(sftpClient, subDir);
                } else {
                    throw new SftpServerException("进入目标目录失败", e);
                }
            }
        }
    }

    /**
     * 创建目录
     *
     * @param sftpClient 客户端
     * @param singleDir 目录名称
     */
    private void mkSingleDir(ChannelSftp sftpClient, String singleDir) {
        try {
            sftpClient.mkdir(singleDir);
        } catch (SftpException e) {
            throw new SftpServerException("创建目录失败", e);
        }
    }

    /**
     * 进入目录
     *
     * @param sftpClient 客户端
     * @param dir 目录
     */
    private void cdDir(ChannelSftp sftpClient, String dir) {
        try {
            sftpClient.cd(dir);
        } catch (SftpException e) {
            throw new SftpServerException("进入目标目录失败", e);
        }
    }

}
