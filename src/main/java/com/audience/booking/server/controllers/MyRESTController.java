package com.audience.booking.server.controllers;



import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;
import com.audience.booking.server.exeption_handling.NoSuchEmployeeException;
import com.audience.booking.server.service.AudienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MyRESTController {

    @Autowired
    private AudienceService audienceService;

    @GetMapping("/audiences")
    public List<Audience> showAllEmployees() {
         return audienceService.getAllAudiences();
    }

    @GetMapping("/audiences/{id}")
    public Audience getEmployee(@PathVariable int id) {
        Audience audience = audienceService.getAudience(id);
        if (audience == null) {
            throw new NoSuchEmployeeException("There is no audience with id = " + id + "in data base");
        }
        return audience;
    }

    @PostMapping("/audiences")
    public Audience addEmployee(@RequestBody Audience audience) {
        audienceService.saveAudience(audience);
        return audience;
    }

    @PutMapping("/audiences")
    public Audience updateEmployee(@RequestBody Audience audience) {
        audienceService.saveAudience(audience);
        return audience;
    }

    @DeleteMapping("/audiences/{id}")
    public String deleteEmployee(@PathVariable int id) {
        audienceService.deleteAudience(id);
        return "audience with id = " + id + "was deleted";
    }

}
