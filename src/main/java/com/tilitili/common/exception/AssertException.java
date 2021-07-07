package com.tilitili.common.exception;

public class AssertException extends RuntimeException {
    public AssertException() {
        super();
    }

    public AssertException(String s) {
        super(s);
    }

    public AssertException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertException(Throwable cause) {
        super(cause);
    }

}
