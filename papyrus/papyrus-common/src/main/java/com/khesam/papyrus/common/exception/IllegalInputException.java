package com.khesam.papyrus.common.exception;

public class IllegalInputException extends RuntimeException {

    public IllegalInputException(String message) {
        super(message);
    }

    public IllegalInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
