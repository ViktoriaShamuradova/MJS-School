package com.epam.esm.exceptionHandling;

public class NoSuchException extends RuntimeException {
    public NoSuchException(String message) {
        super(message);
    }
}
