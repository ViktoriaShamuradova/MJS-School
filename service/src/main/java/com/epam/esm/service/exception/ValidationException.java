package com.epam.esm.service.exception;

import java.util.Locale;

public class ValidationException extends ServiceException {
    public ValidationException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ValidationException(String errorCode, Locale locale) {
        super(errorCode, locale);
    }

    public ValidationException(String errorCode) {
        super(errorCode);
    }
}
