package com.epam.esm.security.exceptionhandler;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Data
@RequiredArgsConstructor
public class JwtTokenExceptionHandler {

    private final MessageSource resourceBundle;
    private final static String DEFAULT_ERROR_CODE = "50100";

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException e,
                                                                           HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(e.getMessage());

        exceptionResponse.setErrorMessage(resourceBundle.getMessage(e.getMessage(), new Object[]{request.getServletPath()},
                request.getLocale())
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

}
