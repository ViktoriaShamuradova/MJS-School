package com.epam.esm.service.exception;

import java.util.Locale;

public class ServiceException extends RuntimeException {

    private final String messageKey;
    private final Locale locale;

    ServiceException(String messageKey) {
        super();
        this.messageKey = messageKey;
        this.locale = Locale.getDefault();
    }

    ServiceException(String message, String messageKey) {
        super(message);
        this.messageKey = messageKey;
        this.locale = Locale.getDefault();
    }

    ServiceException(String messageKey, Locale locale) {
        super();
        this.messageKey = messageKey;
        this.locale = locale;
    }

    ServiceException(String message, String messageKey, Locale locale) {
        super();
        this.messageKey = messageKey;
        this.locale = locale;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Locale getLocale() {
        return locale;
    }
}
