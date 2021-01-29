package com.epam.esm.service.exception;

import java.util.Locale;

public class TagAlreadyExistsException extends ServiceException {

    public TagAlreadyExistsException(String messageKey, String message) {
        super("40901", messageKey, message);
    }

    public TagAlreadyExistsException(String messageKey, Locale locale) {
        super("40901", messageKey, locale);
    }

    public TagAlreadyExistsException(String messageKey) {
        super("40901", messageKey);
    }

}
