package com.epam.esm.service.exception;

public class NoSuchResourceException extends RuntimeException {

    private final int errorCode = 40401;

    public NoSuchResourceException(String message) {
        super(message);
    }

    public NoSuchResourceException() {super();}

    public int getErrorCode() {
        return errorCode;
    }
}
