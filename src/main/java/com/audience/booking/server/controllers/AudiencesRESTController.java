package com.audience.booking.server.controllers;



import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.exceptions.MyEntityNotFoundException;
import com.audience.booking.server.service.AudienceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
        Audience audience = null;
        try {
            audience = audienceService.getAudience(id);
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(id);
        }
        return audience;
    }

    @PostMapping("/")
    public Audience addEmployee(@RequestBody Audience audience) {
        System.out.println(audience);
        System.out.println("____________");
        System.out.println("____________");
        System.out.println("____________");
        //audienceService.saveAudience(audience);
        return audience;
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
