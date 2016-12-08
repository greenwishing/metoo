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

    public MetooException(ErrorMap error) {
        super(error.getMessage());
        this.error = error;
    }

    public MetooException(ErrorMap error, Throwable throwable) {
        super(error.getMessage(), throwable);
        this.error = error;
    }

    public MetooException(Throwable throwable) {
        super(throwable);
    }

    protected MetooException(ErrorMap error, Throwable throwable, boolean b, boolean b1) {
        super(error.getMessage(), throwable, b, b1);
        this.error = error;
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
}
