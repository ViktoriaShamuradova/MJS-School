package com.epam.esm.service.exception;

public class TagAlreadyExistsException extends RuntimeException {

    private final int errorCode = 4000;

    public TagAlreadyExistsException(String message) {
        super(message);
    }

    public TagAlreadyExistsException() {
        super();
    }

    public int getErrorCode() {
        return errorCode;
    }
}
