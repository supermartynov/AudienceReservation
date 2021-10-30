package com.audience.booking.server.dao;

import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplatesDAO extends CrudRepository<Template, Integer> {

}
