package com.audience.booking.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "There is no such user")
public class MyEntityNotFoundException extends RuntimeException {
    public MyEntityNotFoundException(Integer id) {
        super("Entity is not found, id="+id);
    }
}