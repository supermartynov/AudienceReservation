package com.audience.booking.server.dao;

import com.audience.booking.server.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsDAO extends CrudRepository<Client, Integer> {
}
