package com.audience.booking.server.help_classes;

import java.time.LocalDateTime;

public class ReservationCalendarRequestBody {
    private LocalDateTime start;

    private LocalDateTime end;

    private int client;

    private int audience;

    public ReservationCalendarRequestBody(LocalDateTime start, LocalDateTime end, int client, int audience) {
        this.start = start;
        this.end = end;
        this.client = client;
        this.audience = audience;
    }

    public ReservationCalendarRequestBody() {
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

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }

    @Override
    public String toString() {
        return "ReservationClientAudience{" +
                "start=" + start +
                ", end=" + end +
                ", clientId=" + client +
                ", audienceId=" + audience +
                '}';
    }
}
