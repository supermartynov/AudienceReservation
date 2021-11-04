package com.audience.booking.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "There is no such user")
public class MinBookingTimeException extends RuntimeException {
    public MinBookingTimeException() {
        super("Минимальное время брони - 1 час, бронь должна быть кратна 30 минутам");
    }
}