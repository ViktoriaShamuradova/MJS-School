package com.epam.esm.exceptionhandler;

import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.ResourceAlreadyExistsException;
import com.epam.esm.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource resourceBundle;

    private final static String SQL_ERROR_CODE = "5001";

    @Autowired
    public GlobalExceptionHandler(@Qualifier("messageSource") MessageSource resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @ExceptionHandler({ResourceAlreadyExistsException.class})
    public ResponseEntity<ExceptionResponse> handleException(ResourceAlreadyExistsException e, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(e.getErrorCode());
        exceptionResponse.setErrorMessage(resourceBundle.getMessage(e.getErrorCode(), new Object[]{e.getMessage()},
                request.getLocale())
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NoSuchResourceException.class})
    public ResponseEntity<ExceptionResponse> handleException(NoSuchResourceException e, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(e.getErrorCode());
        exceptionResponse.setErrorMessage(resourceBundle.getMessage(e.getErrorCode(), new Object[]{e.getMessage()},
                request.getLocale())
        );
        e.printStackTrace();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse> handleValidatorException(ValidationException e, HttpServletRequest request) {
        String localizedMessage = resourceBundle.getMessage(
                e.getMessage(), new Object[]{}, request.getLocale());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(e.getErrorCode());
        exceptionResponse.setErrorMessage(localizedMessage);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        e.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(SQL_ERROR_CODE);
        exceptionResponse.setErrorMessage(resourceBundle.getMessage(SQL_ERROR_CODE, null, Locale.getDefault()));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
