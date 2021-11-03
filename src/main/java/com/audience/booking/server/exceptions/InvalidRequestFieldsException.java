package com.audience.booking.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid request fields exception")
public class InvalidRequestFieldsException extends RuntimeException {
    public InvalidRequestFieldsException() {
        super("Заданы не все нужные поля запроса");
    }
}