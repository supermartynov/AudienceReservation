package com.audience.booking.server.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reservation_calendar")
public class ReservationCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_time")
    private LocalDateTime start;

    @Column(name = "end_time")
    private LocalDateTime end;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne()
    @JoinColumn(name = "audience_id")
    private Audience audience;

    public ReservationCalendar() {
    }

    public ReservationCalendar(LocalDateTime start, LocalDateTime end, Client client, Audience audience) {
        this.start = start;
        this.end = end;
        this.client = client;
        this.audience = audience;
    }

    public static boolean isValidTime(ReservationCalendar reservationCalendar) {
        LocalTime reservationStartTime = reservationCalendar.getStart().toLocalTime();
        LocalTime reservationEndTime = reservationCalendar.getEnd().toLocalTime();
        LocalTime validStartTime = reservationCalendar.audience.getTemplate().getStartTime();
        LocalTime validEndTime = reservationCalendar.audience.getTemplate().getEndTime();

        if (reservationStartTime.isBefore(validStartTime) || reservationStartTime.isAfter(validEndTime)) {
            return false;
        }

        if (reservationEndTime.isBefore(validStartTime) || reservationEndTime.isAfter(validEndTime)) {
            return false;
        }

        return true;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Client getClient() {
        return client;
    }

    public Audience getAudience() {
        return audience;
    }
}
