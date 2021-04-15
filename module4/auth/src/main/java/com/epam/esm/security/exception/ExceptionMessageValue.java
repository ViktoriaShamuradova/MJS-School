package com.epam.esm.security.exception;

public enum ExceptionMessageValue {
    ACCESS_IS_DENIED("JWT token is expired or invalid");

    private final String message;

    ExceptionMessageValue(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
