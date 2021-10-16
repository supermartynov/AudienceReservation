package com.audience.booking.server.dao;

import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudienceDAO {
    public List<Audience> getAllAudiences();

    public void saveAudience(Audience audience);

    public Audience getAudience(int id);

    public void deleteAudience(int id);

}
