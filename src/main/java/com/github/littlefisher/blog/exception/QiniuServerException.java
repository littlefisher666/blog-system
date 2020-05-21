package com.github.littlefisher.blog.exception;

/**
 * @author jinyanan
 * @since 2020/5/9 16:31
 */
public class QiniuServerException extends RuntimeException {

    public QiniuServerException() {
    }

    public QiniuServerException(String message) {
        super(message);
    }

    public QiniuServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public QiniuServerException(Throwable cause) {
        super(cause);
    }

    public QiniuServerException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
