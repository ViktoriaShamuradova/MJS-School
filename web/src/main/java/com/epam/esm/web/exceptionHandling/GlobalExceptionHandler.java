package com.epam.esm.web.exceptionHandling;

import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.TagAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ReloadableResourceBundleMessageSource resourceBundle;

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(NoSuchResourceException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(e.getErrorCode());
        exceptionResponse.setErrorMessage(resourceBundle.getMessage("ex.message.notFound", null, Locale.getDefault()));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(TagAlreadyExistsException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(e.getErrorCode());
        exceptionResponse.setErrorMessage(resourceBundle.getMessage("ex.message.exist", null, Locale.getDefault()));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse data = new ExceptionResponse();
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
