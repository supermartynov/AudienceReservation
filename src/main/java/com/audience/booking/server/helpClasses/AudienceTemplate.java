package com.audience.booking.server.helpClasses;

public class AudienceTemplate {
    private int capacity;
    private String description;
    private int templateId;

    public AudienceTemplate(int capacity, String description, int templateId) {
        this.capacity = capacity;
        this.description = description;
        this.templateId = templateId;
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

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "AudienceTemplate{" +
                "capacity=" + capacity +
                ", description='" + description + '\'' +
                ", templateId=" + templateId +
                '}';
    }
}
