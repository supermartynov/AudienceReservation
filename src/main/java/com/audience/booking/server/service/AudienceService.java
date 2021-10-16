package com.audience.booking.server.service;


import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;

import java.util.List;


public interface AudienceService {
    public List<Audience> getAllAudiences();

    public void saveAudience (Audience employee);

    public Audience getAudience (int id);

    public void deleteAudience(int id);

}
