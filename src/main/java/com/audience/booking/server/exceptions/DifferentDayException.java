package com.audience.booking.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid time exception")
public class DifferentDayException extends RuntimeException {
    public DifferentDayException(LocalDateTime startTime, LocalDateTime endTime) {
        super("Попытка забронировать время в разнцые дни. Первая дата - " + startTime + ". Вторая дата - " + endTime);
    }
}