package com.audience.booking.server.exceptions;

import com.audience.booking.server.entity.ReservationCalendar;
import com.audience.booking.server.helpClasses.ReservationClientAudience;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Invalid time exception")
public class AlreadyBookedException extends RuntimeException {
    public AlreadyBookedException(ReservationClientAudience reservationCalendar) {
        super("Аудитория уже занята в интервале от " +
                reservationCalendar.getStart().getHour() + "до " + reservationCalendar.getEnd().getHour());
    }
}