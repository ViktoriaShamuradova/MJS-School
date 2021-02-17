package com.epam.esm.service.exception;

import java.util.Locale;

public class NoSuchResourceException extends ServiceException {

    public NoSuchResourceException(String errorCode, String message) {
        super(errorCode, message);
    }

    public NoSuchResourceException(String errorCode, Locale locale) {

        super(errorCode, locale);
    }

    public NoSuchResourceException(String errorCode) {
        super(errorCode);
    }

}
