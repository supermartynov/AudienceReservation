package com.audience.booking.server.service;


import com.audience.booking.server.dao.AudienceDAO;
import com.audience.booking.server.dao.AudienceDAOImplementation;
import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AudienceServiceImplementation implements AudienceService {

    @Autowired
    private AudienceDAOImplementation employeeDAO;

    @Override
    @Transactional
    public List<Audience> getAllAudiences() {
        return employeeDAO.getAllAudiences();
    }

    @Override
    @Transactional
    public void saveAudience(Audience audience) {
        employeeDAO.saveAudience(audience);
    }

    @Override
    @Transactional
    public Audience getAudience(int id) {
        return employeeDAO.getAudience(id);
    }

    @Override
    @Transactional
    public void deleteAudience(int id) {
        employeeDAO.deleteAudience(id);
    }

}
