package com.gsy.base.common.exceptions;

/**
 * Created by mrzsh on 2018/3/26.
 */
public class ParamentErroException extends RuntimeException {


    public ParamentErroException() {
    }

    public ParamentErroException(String message) {
        super(message);
    }

    public ParamentErroException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamentErroException(Throwable cause) {
        super(cause);
    }

    public ParamentErroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
