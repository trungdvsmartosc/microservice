package com.smartosc.accountcommandservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionResponse<T> {

    private int status;

    private String message;

    private T data;

    private Instant time;

    public static <T> ResponseEntity<ApiExceptionResponse<T>> ofNullData(final HttpStatusCode status, final String message) {
        return ResponseEntity.status(status).body(new ApiExceptionResponse<>(status.value(), message, null, Instant.now()));
    }

    public static <T> ResponseEntity<ApiExceptionResponse<T>> conflict(final String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiExceptionResponse<>(HttpStatus.CONFLICT.value(), message, null, Instant.now()));
    }

    public static <T> ResponseEntity<ApiExceptionResponse<T>> notFound(final String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiExceptionResponse<>(HttpStatus.NOT_FOUND.value(), message, null, Instant.now()));
    }

    public static <T> ResponseEntity<ApiExceptionResponse<T>> ofData(final HttpStatusCode status, final String message, final T data) {
        return ResponseEntity.status(status).body(new ApiExceptionResponse<>(status.value(), message, data, Instant.now()));
    }

    public static <T> ResponseEntity<ApiExceptionResponse<T>> created(final String message, final T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiExceptionResponse<>(HttpStatus.CREATED.value(), message, data, Instant.now()));
    }

    public static <T> ResponseEntity<ApiExceptionResponse<T>> ok(final String message, final T data) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiExceptionResponse<>(HttpStatus.OK.value(), message, data, Instant.now()));
    }

    public static <T> ResponseEntity<ApiExceptionResponse<T>> badRequest(final String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiExceptionResponse<>(HttpStatus.BAD_REQUEST.value(), message, null, Instant.now()));
    }

    public static <T> ResponseEntity<ApiExceptionResponse<T>> internalServerError(final String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiExceptionResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null, Instant.now()));
    }
}
