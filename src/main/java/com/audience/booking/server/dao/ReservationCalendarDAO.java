package com.audience.booking.server.dao;

import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;
import com.audience.booking.server.entity.ReservationCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationCalendarDAO extends CrudRepository<ReservationCalendar, Integer>{
    @Query(value = "select r from ReservationCalendar r where (r.start >= :start_time and r.start <= :end_time or " +
            "r.end >= :start_time and r.end <= :end_time) " +
            "and r.audience = :audience")
    List<ReservationCalendar> getAllReservationCalendarInterval(@Param("start_time") LocalDateTime startTime,
                                                                @Param("end_time")LocalDateTime endTime,
                                                                @Param("audience") Audience audience);
}
