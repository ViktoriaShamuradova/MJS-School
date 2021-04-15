package com.epam.esm.security.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SpringSecurityExceptionHandler implements AuthenticationEntryPoint {
    private static final String CONTENT_TYPE = "application/json";
    private static final String MESSAGE_TITLE = "{ \"Error message\": \"";
    private static final String MESSAGE_TAIL = "\" }";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.getOutputStream().println(MESSAGE_TITLE + authException.getMessage() + MESSAGE_TAIL);
    }
}
