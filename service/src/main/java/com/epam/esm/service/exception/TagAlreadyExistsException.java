package com.epam.esm.service.exception;

import java.util.Locale;

public class TagAlreadyExistsException extends ServiceException {

    public TagAlreadyExistsException(String message) {
        super("40901", message);
    }

    public TagAlreadyExistsException(Locale locale) {
        super("40901", locale);
    }

    public TagAlreadyExistsException() {
        super("40901");
    }

}
