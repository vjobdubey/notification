package com.ca.election.notification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

     // Handle validation errors for Spring WebFlux

    @ExceptionHandler(WebExchangeBindException.class)
    public ProblemDetail handleValidationError(WebExchangeBindException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Validation Failed");

        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }

        problem.setProperty("errors", errors);
        return problem;
    }

     //Handle custom corporate action errors

    @ExceptionHandler(CAException.class)
    public ProblemDetail handleCAException(CAException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Corporate Action Error");
        problem.setDetail(ex.getMessage());
        return problem;
    }

     // Fallback for unexpected exceptions

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Unexpected Error");
        problem.setDetail(ex.getMessage());
        return problem;
    }
}
