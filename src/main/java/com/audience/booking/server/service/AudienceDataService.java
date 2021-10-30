package com.audience.booking.server.service;


import com.audience.booking.server.dao.AudienceDAO;
import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.exceptions.MyEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AudienceDataService {

    @Autowired
    private AudienceDAO audienceCrudRepository;

    @Transactional
    public List<Audience> getAllAudiences() {
        return (List<Audience>) audienceCrudRepository.findAll();
    }

    @Transactional
    public void saveAudience(Audience audience) {
        audienceCrudRepository.save(audience);
    }

    @Transactional
    public Audience getAudience(int id) {

         return audienceCrudRepository.findById(id).get();
    }

    @Transactional
    public void deleteAudience(int id) {
        audienceCrudRepository.deleteById(id);
    }

}
