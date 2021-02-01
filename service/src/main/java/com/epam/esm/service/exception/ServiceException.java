package com.epam.esm.service.exception;

import java.util.Locale;

public class ServiceException extends RuntimeException {

    private final String errorCode;
    private final Locale locale = Locale.ENGLISH;

    ServiceException(String errorCode) {
        super();
        this.errorCode = errorCode;
        //this.locale = Locale.getDefault();
    }

    ServiceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        //this.locale = Locale.getDefault();
    }

    ServiceException(String errorCode, Locale locale) {
        super();
        this.errorCode = errorCode;
        //this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
