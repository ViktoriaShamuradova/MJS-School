package com.epam.esm.service.exception;

public class NoSuchResourceException extends RuntimeException {

    private final int errorCode = 4000;

    public NoSuchResourceException(String message) {
        super(message);
    }

    public NoSuchResourceException() {}

    public int getErrorCode() {
        return errorCode;
    }
}
