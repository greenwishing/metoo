package com.metoo.exception;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
public class MetooLoginException extends MetooException {

    public MetooLoginException() {
    }

    public MetooLoginException(String message) {
        super(message);
    }

    public MetooLoginException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MetooLoginException(ErrorMap error) {
        super(error);
    }

    public MetooLoginException(ErrorMap error, Throwable throwable) {
        super(error, throwable);
    }

    public MetooLoginException(Throwable throwable) {
        super(throwable);
    }
}
