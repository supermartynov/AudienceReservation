package com.audience.booking.server.dao;

import com.audience.booking.server.entity.Audience;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudienceDAO extends CrudRepository<Audience, Integer> {

}
