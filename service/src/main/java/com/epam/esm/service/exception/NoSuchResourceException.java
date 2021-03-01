package com.epam.esm.service.exception;

public class NoSuchResourceException extends ServiceException {

    public NoSuchResourceException(String errorCode, String message) {
        super(errorCode, message);
    }

    public NoSuchResourceException(String errorCode) {
        super(errorCode);
    }

}
