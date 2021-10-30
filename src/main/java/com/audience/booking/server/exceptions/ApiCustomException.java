package com.audience.booking.server.exceptions;

import org.springframework.http.HttpStatus;

public class ApiCustomException {
    private final String message;
    private final HttpStatus httpStatus;

    public ApiCustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
