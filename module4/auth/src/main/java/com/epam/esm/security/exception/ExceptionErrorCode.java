package com.epam.esm.security.exception;

public enum ExceptionErrorCode {
    ACCESS_IS_DENIED("40100"),
    EMPTY_TOKEN("40101");

    private final String errorCode;

    ExceptionErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
