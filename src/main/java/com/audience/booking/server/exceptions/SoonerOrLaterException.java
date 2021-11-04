package com.audience.booking.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Invalid time to book")
public class SoonerOrLaterException extends RuntimeException {
    public SoonerOrLaterException(LocalDateTime start_time) {
        super("Максимальный диапазон, в который можно забронировать аудиторию - 90 дней, начиная с "
                + LocalDateTime.now().toString() + ". Вы пытаетесь забронировать аудиторию на " + start_time.toString());
    }
}