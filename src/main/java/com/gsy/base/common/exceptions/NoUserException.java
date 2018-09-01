package com.gsy.base.common.exceptions;/**
 * Created by mrzsh on 2018/5/21.
 */

/**
 * @program: gsyboot
 * @description:
 * @author: Mr.zsh
 * @create: 2018-05-21 15:15
 **/
public class NoUserException extends RuntimeException {
    public NoUserException() {
    }

    public NoUserException(String message) {
        super(message);
    }

    public NoUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoUserException(Throwable cause) {
        super(cause);
    }

    public NoUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
