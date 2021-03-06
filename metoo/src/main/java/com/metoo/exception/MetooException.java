package com.metoo.exception;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/24
 */
public class MetooException extends RuntimeException {

    private ErrorMap error = ErrorMap.ERROR;

    public MetooException() {
        super();
    }

    public MetooException(String message) {
        super(message);
    }

    public MetooException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MetooException(ErrorMap error) {
        this(error.getMessage());
        this.error = error;
    }

    public MetooException(ErrorMap error, Throwable throwable) {
        super(error.getMessage(), throwable);
        this.error = error;
    }

    public MetooException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getMessage() {
        return error.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    @Override
    public String toString() {
        return getMessage();
    }

    public ErrorMap getError() {
        return error;
    }
}
