package com.audience.booking.server.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reservation_calendar")
public class ReservationCalendar {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "client_id")
    private int clientId;

    @Column(name = "audience_id")
    private int audienceId;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

}
