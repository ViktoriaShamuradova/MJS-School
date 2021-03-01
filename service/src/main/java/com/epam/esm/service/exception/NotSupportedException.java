package com.epam.esm.service.exception;


public class NotSupportedException extends ServiceException {
    public NotSupportedException(String errorCode, String message) {
        super(errorCode, message);
    }

    public NotSupportedException(String errorCode) {
        super(errorCode);
    }

}
