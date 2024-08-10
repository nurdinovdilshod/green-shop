package com.company.config.handler;


import com.company.payload.res.GenericError;
import com.company.payload.res.GenericResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoResourceFoundException.class)
    public void handleNoResourceFoundException(NoResourceFoundException ex) {
        ex.printStackTrace();
    }
    @ExceptionHandler(Exception.class)
    public GenericResponse<Void> handleUnknownExceptions(Exception e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        GenericError error = new GenericError(request.getRequestURI(), e.getMessage(), 500);
        return new GenericResponse<>(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public GenericResponse<Void> handleRuntimeExceptions(RuntimeException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        GenericError error = new GenericError(request.getRequestURI(), e.getMessage(), 400);
        return new GenericResponse<>(error);
    }

    @ExceptionHandler(DisabledException.class)
    public GenericResponse<Void> handleDisabledException(DisabledException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        GenericError error = new GenericError(request.getRequestURI(), e.getMessage(), 403);
        return new GenericResponse<>(error);
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public GenericResponse<Void> handleCredentialsExpiredException(CredentialsExpiredException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        GenericError error = new GenericError(request.getRequestURI(), e.getMessage(), 400);
        return new GenericResponse<>(error);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public GenericResponse<Void> handleInsufficientAuthenticationException(InsufficientAuthenticationException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        GenericError error = new GenericError(request.getRequestURI(), e.getMessage(), 403);
        return new GenericResponse<>(error);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public GenericResponse<Void> handleExpiredJwtException(ExpiredJwtException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        GenericError error = new GenericError(request.getRequestURI(), e.getMessage(), 403);
        return new GenericResponse<>(error);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String errorMessage = "Input is not valid";
        Map<String, List<String>> errorBody = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorBody.compute(field, (s, values) -> {
                if (!Objects.isNull(values))
                    values.add(message);
                else
                    values = new ArrayList<>(Collections.singleton(message));
                return values;
            });
        }
        String errorPath = request.getRequestURI();
        log.error(e.getMessage(), e);
        GenericError error = new GenericError(errorPath, errorMessage, errorBody, 400);
        return new GenericResponse<>(error);
    }

}
