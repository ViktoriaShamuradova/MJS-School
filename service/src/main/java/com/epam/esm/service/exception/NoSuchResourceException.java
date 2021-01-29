package com.epam.esm.service.exception;

import java.util.Locale;

public class NoSuchResourceException extends ServiceException {

    public NoSuchResourceException(String messageKey, String message) {
        super("40401", messageKey, message);
    }

    public NoSuchResourceException(String messageKey, Locale locale) {
        super("40401", messageKey, locale);
    }

    public NoSuchResourceException(String messageKey) {
        super("40401", messageKey);
    }

}
