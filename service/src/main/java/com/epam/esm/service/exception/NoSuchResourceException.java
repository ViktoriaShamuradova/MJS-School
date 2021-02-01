package com.epam.esm.service.exception;

import java.util.Locale;

public class NoSuchResourceException extends ServiceException {

    public NoSuchResourceException(String message) {
        super("40401", message);
    }

    public NoSuchResourceException(Locale locale) {

        super("40401", locale);
    }

    public NoSuchResourceException() {
        super("40401");
    }

}
