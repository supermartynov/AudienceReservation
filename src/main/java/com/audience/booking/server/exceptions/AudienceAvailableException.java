package com.audience.booking.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid request fields exception")
public class AudienceAvailableException extends RuntimeException {
    public AudienceAvailableException(int id) {
        super("Аудитория с id = " + id + " недоступна на данный момент");
    }
}