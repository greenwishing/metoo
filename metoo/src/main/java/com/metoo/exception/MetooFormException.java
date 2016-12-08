package com.metoo.exception;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/24
 */
public class MetooFormException extends MetooException {

    public MetooFormException() {
    }

    public MetooFormException(ErrorMap error) {
        super(error);
    }

    public MetooFormException(ErrorMap error, Throwable throwable) {
        super(error, throwable);
    }

    public MetooFormException(Throwable throwable) {
        super(throwable);
    }

    public MetooFormException(ErrorMap error, Throwable throwable, boolean b, boolean b1) {
        super(error, throwable, b, b1);
    }
}
