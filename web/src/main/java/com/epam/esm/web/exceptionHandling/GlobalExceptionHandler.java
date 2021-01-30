package com.epam.esm.web.exceptionHandling;

import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.ServiceException;
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

    private final ReloadableResourceBundleMessageSource resourceBundle;

    @Autowired
    public GlobalExceptionHandler(ReloadableResourceBundleMessageSource resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @ExceptionHandler({NoSuchResourceException.class, TagAlreadyExistsException.class})
    public ResponseEntity<ExceptionResponse> handleException(ServiceException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        String key = e.getErrorCode() + e.getMessageKey();
        exceptionResponse.setErrorCode(e.getErrorCode());
        exceptionResponse.setErrorMessage(resourceBundle.getMessage(
                key, new Object[]{e.getMessage()},
                e.getLocale())
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("5000");
        exceptionResponse.setErrorMessage(resourceBundle.getMessage("5000", null, Locale.getDefault()));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
