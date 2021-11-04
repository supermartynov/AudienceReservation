package com.audience.booking.server.exceptions;

import com.audience.booking.server.entity.Template;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Not valid time")
public class TimeSutisfyTemplateException extends RuntimeException {
    public TimeSutisfyTemplateException(LocalDateTime start_time, LocalDateTime end_time, Template template) {
        super("Время в запросе не удовлетворяет расписанию, по которому работает аудитория." +
                " Время начала брони - " + start_time.getHour() +
                ". Время конца брони - " + end_time.getHour() +
                ". Время по которому работает аудитория - с " + template.getStartTime() + " по " + template.getEndTime() +
                " ч.");
    }
}

