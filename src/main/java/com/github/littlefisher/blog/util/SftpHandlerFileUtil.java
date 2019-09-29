package com.github.littlefisher.blog.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.github.littlefisher.blog.exception.SftpServerException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jinyanan
 * @since 2019-08-19 11:29
 */
@Slf4j
public final class SftpHandlerFileUtil {

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;

    /** FTP服务器IP地址 */
    private String ftpIp;

    /** 端口号 默认为:22 */
    private int port = 22;

    /** session */
    private Session sshSession;

    /** sftp客户端 */
    private ChannelSftp sftpClient = null;

    /**
     * 私有构造函数
     */
    private SftpHandlerFileUtil() {}

    /**
     * 连接ftp获取ChannelSftp
     */
    private ChannelSftp connectFtpServer() {
        ChannelSftp sftp;
        try {
            JSch jsch = new JSch();
            jsch.getSession(userName, ftpIp, port);
            sshSession = jsch.getSession(userName, ftpIp, port);
            log.debug("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            log.debug("Session connected before");
            sshSession.connect(5000);
            log.debug("Session connected.");
            log.debug("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            log.debug("连接SFTP服务器 " + ftpIp + "成功.");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        this.sftpClient = sftp;
        try {
            sftpClient.setFilenameEncoding(StandardCharsets.UTF_8.displayName());
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return sftp;
    }

    /**
     * 断开与对方FTP Server的连接
     */
    private void disconnectFtpServer() {
        try {
            if (sftpClient != null) {
                sftpClient.disconnect();
            }
            if (sshSession != null) {
                sshSession.disconnect();
            }
        } catch (Exception e) {
            log.error("断开SFTP服务器失败", e);
        }
    }

    /**
     * 进入文件目录
     *
     * @param fileDir 要进入的目录
     */
    private boolean changeWorkingDirectory(String fileDir) {
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
                try {
                    mkDir(fileDir);
                } catch (SftpException e1) {
                    log.error("", e);
                    return false;
                }
                return true;
            } else {
                log.error(e.getMessage(), e);
                return false;
            }

        }
    }

    /**
     * 创建目录
     *
     * @param dir 目录
     * @throws SftpException 异常
     */
    private void mkDir(String dir) throws SftpException {
        List<String> subDirList = Arrays.stream(dir.split("/"))
            .filter(input -> !input.isEmpty())
            .collect(Collectors.toList());
        sftpClient.cd("/");
        for (String subDir : subDirList) {
            if (subDir == null || subDir.isEmpty()) {
                continue;
            }
            try {
                sftpClient.cd(subDir);
            } catch (SftpException e) {
                if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                    try {
                        sftpClient.mkdir(subDir);
                        sftpClient.cd(subDir);
                    } catch (SftpException e1) {
                        log.error(e.getMessage(), e1);
                        throw e1;
                    }
                }
            }
        }
    }

    /**
     * 获取文件byte数组
     *
     * @param directory 要进入的目录
     * @param fileName 文件名
     * @return 文件byte数组
     */
    public byte[] getFileBytes(String directory, String fileName) {
        InputStream fis = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // 连接FTP服务器
            connectFtpServer();
            // 进入FTP服务器指定目录
            changeWorkingDirectory(directory);
            fis = sftpClient.get(fileName);

            int bufSize = 1024 * 256;
            byte[] buffer = new byte[bufSize];
            int len;
            if (fis != null) {
                while (-1 != (len = fis.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len);
                }
            }

            return bos.toByteArray();
        } catch (SftpException | IOException e) {
            log.error("获取文件字节流失败" + e.getMessage());
            throw new SftpServerException();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                disconnectFtpServer();
            } catch (IOException e) {
                log.error("FileInputStream 关闭时发生异常：" + e.getMessage(), e);
            }
        }
    }

    /**
     * 将byte[]上传到sftp，作为文件。
     *
     * 注意:从String生成byte[]是，要指定字符集。
     *
     * @param directory 上传到sftp目录
     * @param sftpFileName 文件在sftp端的命名
     * @param byteArr 要上传的字节数组
     * @return 上传路径
     */
    public String upLoadFile(String directory, String sftpFileName, byte[] byteArr) {

        try {
            // 连接FTP服务器
            connectFtpServer();
            // 进入FTP服务器指定目录
            changeWorkingDirectory(directory);
            // 进入FTP服务器服务路径的相对路径
            sftpClient.put(new ByteArrayInputStream(byteArr), sftpFileName);
            return directory + File.separator + sftpFileName;
        } catch (SftpException e) {
            log.error("上传sftp文件异常");
            throw new SftpServerException();
        }
    }

}

