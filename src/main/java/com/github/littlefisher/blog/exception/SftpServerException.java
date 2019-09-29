package com.github.littlefisher.blog.exception;

/**
 * @author jinyanan
 * @since 2019-08-19 11:34
 */
public class SftpServerException extends RuntimeException {

    public SftpServerException() {
        super();
    }

    public SftpServerException(String message) {
        super(message);
    }

    public SftpServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SftpServerException(Throwable cause) {
        super(cause);
    }

    protected SftpServerException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
