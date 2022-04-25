package com.standardbank.currencyconversionservice.infra.api.rest;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles exceptions that may be experienced in application.
 */
@AllArgsConstructor
@ControllerAdvice()
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse runtimeException(RuntimeException e) {
        return ErrorResponse.builder()
            .statusCode(HttpStatus.CONFLICT.value())
            .message(e.getLocalizedMessage())
            .build();
    }

    @Generated
    @Data
    @Builder
    public static class ErrorResponse {
        int statusCode;
        String message;
    }

}
