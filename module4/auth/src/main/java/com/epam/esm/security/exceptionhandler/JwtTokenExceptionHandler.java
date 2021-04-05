package com.epam.esm.security.exceptionhandler;

import com.epam.esm.exceptionhandler.ExceptionResponse;
import com.epam.esm.exceptionhandler.GlobalExceptionHandler;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class JwtTokenExceptionHandler extends GlobalExceptionHandler {

    public JwtTokenExceptionHandler(MessageSource resourceBundle) {
        super(resourceBundle);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException e,
                                                                           HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(e.getMessage());

        exceptionResponse.setErrorMessage(super.getResourceBundle().getMessage(e.getMessage(), new Object[]{request.getServletPath()},
                request.getLocale())
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

}
