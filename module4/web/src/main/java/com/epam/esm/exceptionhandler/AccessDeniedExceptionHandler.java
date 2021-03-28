package com.epam.esm.exceptionhandler;

import com.epam.esm.service.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RestControllerAdvice
public class AccessDeniedExceptionHandler {

    private final MessageSource resourceBundle;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException e,
                                                                         HttpServletRequest request,
                                                                         Authentication authentication) {
        String errorCode = ExceptionCode.ACCESS_IS_DENIED.getErrorCode();
        if (authentication == null) {
            errorCode = ExceptionCode.UNAUTHORIZED.getErrorCode();
        }

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(errorCode);

        exceptionResponse.setErrorMessage(resourceBundle.getMessage(errorCode, new Object[]{e.getMessage()},
                request.getLocale())
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }
}
