package com.audience.booking.server.exceptions;

import com.audience.booking.server.helpClasses.ReservationClientAudience;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid time exception")
public class InvalidTimeException extends RuntimeException {
    public InvalidTimeException(LocalDateTime startTime, LocalDateTime endTime) {
        super("Начальное время позже конечного: начальное - " + startTime + " конечное - " + endTime);
    }
}