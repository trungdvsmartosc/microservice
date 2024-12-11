package com.smartosc.transactioncommandservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public <T> ResponseEntity<ApiExceptionResponse<T>> handleSQLException(SQLException ex) {
        return ApiExceptionResponse.internalServerError(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiExceptionResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ApiExceptionResponse.badRequest(ex.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse<Object>> handleEntityException(ApiException ex) {
        return ApiExceptionResponse.ofNullData(ex.getStatusCode(), ex.getMessage());
    }
}
