package com.epam.esm.service.exception;

import java.util.Locale;

public class TagAlreadyExistsException extends ServiceException {

    public TagAlreadyExistsException() {
        super("40901");
    }

    public TagAlreadyExistsException(Locale locale) {
        super("40901", locale);
    }

}
