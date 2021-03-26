package com.epam.esm.exceptionhandler;

import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.ResourceAlreadyExistsException;
import com.epam.esm.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource resourceBundle;

    private final static String DEFAULT_ERROR_CODE = "50100";

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

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException e,
                                                                           HttpServletRequest request) {

        String localizedMessage = resourceBundle.getMessage(
                e.getMessage(), new Object[]{}, request.getLocale());

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(e.getMessage());

        exceptionResponse.setErrorMessage(resourceBundle.getMessage(e.getMessage(), new Object[]{request.getServletPath()},
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

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ExceptionResponse> handleException(ConstraintViolationException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("40007");
        exceptionResponse.setErrorMessage(resourceBundle.getMessage("40007", new Object[]{ex.getMessage()},
                request.getLocale())
        );
        ex.printStackTrace();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("40007");
        exceptionResponse.setErrorMessage(resourceBundle.getMessage("40007", new Object[]{"Id must digit and min value=1"},
                request.getLocale())
        );
        ex.printStackTrace();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(BindException exception, HttpServletRequest request) {

        List<ErrorModel> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                .distinct()
                .collect(Collectors.toList());
        return ErrorResponse.builder().errorMessages(errorMessages).build();
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse> handleException(ValidationException e, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(e.getErrorCode());
        exceptionResponse.setErrorMessage(resourceBundle.getMessage(e.getErrorCode(), new Object[]{e.getMessage()},
                request.getLocale())
        );
        e.printStackTrace();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        e.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(DEFAULT_ERROR_CODE);
        exceptionResponse.setErrorMessage(resourceBundle.getMessage(DEFAULT_ERROR_CODE, null, Locale.getDefault()));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
