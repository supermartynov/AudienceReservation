package com.audience.booking.server.exceptions;

import com.audience.booking.server.help_classes.ReservationClientAudience;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Already booked exception")
public class AlreadyBookedException extends RuntimeException {
    public AlreadyBookedException(ReservationClientAudience reservationCalendar) {
        super("Аудитория уже занята в промежуток времени от " +
                reservationCalendar.getStart().getHour() + " до " + reservationCalendar.getEnd().getHour());
    }
}