package com.smartosc.transaction.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus statusCode;

    public ApiException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
