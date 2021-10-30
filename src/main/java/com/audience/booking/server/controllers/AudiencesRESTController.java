package com.audience.booking.server.controllers;



import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Template;
import com.audience.booking.server.exceptions.MyEntityNotFoundException;
import com.audience.booking.server.helpClasses.AudienceTemplate;
import com.audience.booking.server.service.AudienceDataService;
import com.audience.booking.server.service.TemplateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/audiences")
public class AudiencesRESTController {

    @Autowired
    private AudienceDataService audienceService;

    @Autowired
    private TemplateDataService templateDataService;

    @GetMapping("/")
    public List<Audience> showAllEmployees() {
         return audienceService.getAllAudiences();
    }

    @GetMapping("/{id}")
    public Audience getEmployee(@PathVariable int id) {
        Audience audience = null;
        try {
            audience = audienceService.getAudience(id);
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(id);
        }
        return audience;
    }

    @PostMapping("/")
    public Audience addEmployee(@RequestBody AudienceTemplate audience) {
        int capacity = audience.getCapacity();
        String description = audience.getDescription();
        Template template = templateDataService.getTemplates(audience.getTemplateId());

        Audience finalAudience = new Audience(capacity, description, template);

        template.addAudienceToTemplate(finalAudience);
        audienceService.saveAudience(finalAudience);
        return finalAudience;
    }

    @PutMapping("/")
    public Audience updateEmployee(@RequestBody Audience audience) {
        audienceService.saveAudience(audience);
        return audience;
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        audienceService.deleteAudience(id);
        return "audience with id = " + id + "was deleted";
    }

}
