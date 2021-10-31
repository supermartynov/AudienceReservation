package com.audience.booking.server.exceptions;

import com.audience.booking.server.entity.Template;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Невалидное время")
public class TimeSutisfyTemplateException extends RuntimeException {
    public TimeSutisfyTemplateException(LocalDateTime start_time, LocalDateTime end_time, Template template) {
        super("Время в запросе не удовлетворяет расписанию, по которому работает аудитория" +
                " Указанное начальное время - " + start_time.getHour() +
                " Указанное конечное время - " + end_time.getHour() +
                "Время по которому работает аудитория - с " + template.getStartTime() + " по " + template.getEndTime() +
                " ч.");
    }
}

