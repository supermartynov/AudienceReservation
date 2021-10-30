package com.audience.booking.server.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "audiences")
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    /*@OneToMany
    @JoinColumn(name = "audience_id")
    private List<ReservationCalendar> audienceReservations;*/

    public Audience() {
    }

    public Audience(int capacity, String description, Template template) {
        this.capacity = capacity;
        this.description = description;
        this.template = template;
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

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "Audience{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", description='" + description + '\'' +
                ", template=" + template +
                '}';
    }
}
