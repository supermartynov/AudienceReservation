package com.audience.booking.server.helpClasses;

import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Template;
import com.audience.booking.server.exceptions.MyEntityNotFoundException;
import com.audience.booking.server.service.TemplateDataService;

import java.util.NoSuchElementException;

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

    public static Audience convertAudienceTemplateToAudience(TemplateDataService templateDataService, AudienceTemplate audience) {
        int capacity = audience.getCapacity();
        String description = audience.getDescription();
        Template template = null;

        try {
            template = templateDataService.getTemplates(audience.getTemplate());
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(audience.getTemplate(), Template.class.getSimpleName());
        }

        Audience finalAudience = new Audience(capacity, description, template);
        template.addAudienceToTemplate(finalAudience);

        return finalAudience;
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
