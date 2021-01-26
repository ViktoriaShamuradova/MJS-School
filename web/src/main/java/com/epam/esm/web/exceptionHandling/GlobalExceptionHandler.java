package com.epam.esm.web.exceptionHandling;

import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.TagAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ResourceBundleMessageSource resourceBundle;

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(NoSuchResourceException e) {
        IncorrectData data = new IncorrectData();
        data.setCode(e.getErrorCode());
        data.setInfo(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(TagAlreadyExistsException e) {
        IncorrectData data = new IncorrectData();
        data.setCode(e.getErrorCode());
        data.setInfo(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(Exception e) {
        IncorrectData data = new IncorrectData();
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
