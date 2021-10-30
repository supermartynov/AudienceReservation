package com.audience.booking.server.entity;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "is_available")
    private boolean isAvailavle;

    @OneToMany(mappedBy = "template")
    private List<Audience> audienceList;

    public Template() {
    }

    public Template(LocalTime startTime, LocalTime endTime, boolean isAvailavle) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailavle = isAvailavle;
    }

    public void addAudienceToTemplate(Audience audience) {
        if (audienceList.isEmpty()) {
            audienceList = new ArrayList<>();
        }
        audienceList.add(audience);
        audience.setTemplate(this);
    }


    @Override
    public String toString() {
        return "Template{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", isAvailavle=" + isAvailavle;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAvailavle() {
        return isAvailavle;
    }

    public void setAvailavle(boolean availavle) {
        isAvailavle = availavle;
    }
}
