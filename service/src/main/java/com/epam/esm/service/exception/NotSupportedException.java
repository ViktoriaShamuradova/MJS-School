package com.epam.esm.service.exception;

import java.util.Locale;

public class NotSupportedException extends ServiceException {
    public NotSupportedException(String errorCode, String message) {
        super(errorCode, message);
    }

    public NotSupportedException(String errorCode, Locale locale) {
        super(errorCode, locale);
    }

    public NotSupportedException(String errorCode) {
        super(errorCode);
    }
}
