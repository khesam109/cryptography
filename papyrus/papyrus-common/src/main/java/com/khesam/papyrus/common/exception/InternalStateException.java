package com.khesam.papyrus.common.exception;

public class InternalStateException extends RuntimeException {

    public InternalStateException(String message) {
        super(message);
    }

    public InternalStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
