package com.audience.booking.server.entity;

import com.sun.istack.NotNull;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
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
