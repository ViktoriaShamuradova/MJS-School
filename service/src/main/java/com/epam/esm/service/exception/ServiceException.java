package com.epam.esm.service.exception;

import java.util.Locale;

public class ServiceException extends RuntimeException {

    private final String errorCode;
    private final Locale locale;
    private final String messageKey;

    ServiceException(String errorCode, String messageKey) {
        super();
        this.errorCode = errorCode;
        this.locale = Locale.getDefault();
        this.messageKey = messageKey;
    }

    ServiceException(String errorCode, String messageKey, String message) {
        super(message);
        this.errorCode = errorCode;
        this.messageKey = messageKey;
        this.locale = Locale.getDefault();
    }

    ServiceException(String errorCode, String messageKey, Locale locale) {
        super();
        this.errorCode = errorCode;
        this.locale = locale;
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
