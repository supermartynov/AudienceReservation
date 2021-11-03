package com.audience.booking.server.help_classes;

public class AudienceTemplate {
    private int capacity;
    private String description;
    private int template;

    public AudienceTemplate(int capacity, String description, int template) {
        this.capacity = capacity;
        this.description = description;
        this.template = template;
    }

    public AudienceTemplate() {
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

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "AudienceTemplate{" +
                "capacity=" + capacity +
                ", description='" + description + '\'' +
                ", templateId=" + template +
                '}';
    }
}
