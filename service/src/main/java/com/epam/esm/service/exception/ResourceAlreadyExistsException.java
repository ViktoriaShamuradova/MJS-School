package com.epam.esm.service.exception;

import java.util.Locale;

public class ResourceAlreadyExistsException extends ServiceException {

    public ResourceAlreadyExistsException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ResourceAlreadyExistsException(String errorCode, Locale locale) {
        super(errorCode, locale);
    }

    public ResourceAlreadyExistsException(String errorCode) {
        super(errorCode);
    }

}
