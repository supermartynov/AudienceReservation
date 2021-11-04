package com.audience.booking.server.service;


import com.audience.booking.server.dao.AudienceDAO;
import com.audience.booking.server.dao.TemplatesDAO;
import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Template;
import com.audience.booking.server.exceptions.MyEntityNotFoundException;
import com.audience.booking.server.help_classes.AudienceRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AudienceDataService {

    @Autowired
    private AudienceDAO audienceCrudRepository;

    @Autowired
    private TemplatesDAO templatesCRUDRepository;

    @Transactional
    public List<Audience> getAllAudiences() {
        return (List<Audience>) audienceCrudRepository.findAll();
    }

    @Transactional
    public Audience saveAudience(AudienceRequestBody audienceRequestBody) {

        int capacity = audienceRequestBody.getCapacity();
        String description = audienceRequestBody.getDescription();
        Template template = templatesCRUDRepository.findById(audienceRequestBody.getTemplate()).get();

        Audience audience = new Audience(capacity, description, template);
        audienceCrudRepository.save(audience);
        return audience;
    }

    @Transactional
    public Audience getAudience(int id) {
        Audience audience = null;

        try {
            audience = audienceCrudRepository.findById(id).get();
        } catch (NoSuchElementException err) {
            throw new MyEntityNotFoundException(id, Audience.class.getSimpleName());
        }
        return audience;
    }

    @Transactional
    public void deleteAudience(int id) {
        audienceCrudRepository.deleteById(id);
    }

}
