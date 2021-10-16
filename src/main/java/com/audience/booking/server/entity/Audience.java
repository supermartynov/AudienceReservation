package com.audience.booking.server.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "audiences")
public class Audience {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "description")
    private String description;

    @Column(name = "template_id")
    private int templateId;

    @OneToMany()
    @JoinTable(name = "audience_id")
    private List<ReservationCalendar> audienceReservations;

    public Audience() {
    }

    public Audience(int capacity, String description, int templateId) {
        this.capacity = capacity;
        this.description = description;
        this.templateId = templateId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public List<ReservationCalendar> getAudienceReservations() {
        return audienceReservations;
    }

    public void setAudienceReservations(List<ReservationCalendar> audienceReservations) {
        this.audienceReservations = audienceReservations;
    }
}
