package com.audience.booking.server.dao;

import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudienceDAO extends CrudRepository<Audience, Integer> {

}
