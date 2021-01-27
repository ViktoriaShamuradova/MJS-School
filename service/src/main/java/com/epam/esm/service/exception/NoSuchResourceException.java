package com.epam.esm.service.exception;

import java.util.Locale;

public class NoSuchResourceException extends ServiceException {

    public NoSuchResourceException() {
        super("40401");
    }

    public NoSuchResourceException(Locale locale) {
        super("40401", locale);
    }

    public NoSuchResourceException(String message) {
        super(message, "40401");
    }

}
