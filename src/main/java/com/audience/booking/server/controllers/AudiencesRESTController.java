package com.audience.booking.server.controllers;



import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.help_classes.AudienceRequestBody;
import com.audience.booking.server.service.AudienceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audiences")
public class AudiencesRESTController {

    @Autowired
    private AudienceDataService audienceService;

    @GetMapping("/")
    public List<Audience> showAllEmployees() {
         return audienceService.getAllAudiences();
    }

    @GetMapping("/{id}")
    public Audience getEmployee(@PathVariable int id) {
        return audienceService.getAudience(id);
    }

    @PostMapping("/")
    public Audience addEmployee(@RequestBody AudienceRequestBody audience) {
        return  audienceService.saveAudience(audience);
    }

    @PutMapping("/")
    public Audience updateEmployee(@RequestBody AudienceRequestBody audience) {
        return  audienceService.saveAudience(audience);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        audienceService.deleteAudience(id);
        return "audience with id = " + id + "was deleted";
    }

}
