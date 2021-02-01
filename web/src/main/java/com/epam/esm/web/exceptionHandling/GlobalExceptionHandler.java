package com.epam.esm.web.exceptionHandling;

import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.TagAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ReloadableResourceBundleMessageSource resourceBundle;

    private final static String SQL_ERROR_CODE = "5001";
    @Autowired
    public GlobalExceptionHandler(ReloadableResourceBundleMessageSource resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @ExceptionHandler({TagAlreadyExistsException.class})
    public ResponseEntity<ExceptionResponse> handleException(TagAlreadyExistsException e, HttpServletResponse response) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(e.getErrorCode());
        exceptionResponse.setErrorMessage(resourceBundle.getMessage(e.getErrorCode(), new Object[]{e.getMessage()},
                e.getLocale())
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NoSuchResourceException.class})
    public ResponseEntity<ExceptionResponse> handleException(NoSuchResourceException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(e.getErrorCode());
        exceptionResponse.setErrorMessage(resourceBundle.getMessage(e.getErrorCode(), new Object[]{e.getMessage()},
                e.getLocale())
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(SQL_ERROR_CODE);
        exceptionResponse.setErrorMessage(resourceBundle.getMessage(SQL_ERROR_CODE, null, Locale.getDefault()));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
