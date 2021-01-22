package com.epam.esm.web.exceptionHandling;

public class NoSuchResourceException extends RuntimeException {
    public NoSuchResourceException(String message) {
        super(message);
    }
}
