package com.audience.booking.server.dao;

import com.audience.booking.server.entity.Client;
import com.audience.booking.server.entity.ReservationCalendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationCalendarDAO extends CrudRepository<ReservationCalendar, Integer> {
}
