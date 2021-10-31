package com.audience.booking.server.service;

import com.audience.booking.server.dao.ReservationCalendarDAO;
import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.ReservationCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationCalendarDataService  {

    @Autowired
    private ReservationCalendarDAO reservationCalendarCrudRepository;

    @Transactional
    public List<ReservationCalendar> getAllReservationCalendar() {
        return (List<ReservationCalendar>) reservationCalendarCrudRepository.findAll();
    }

    @Transactional
    public void saveReservationCalendar(ReservationCalendar reservationCalendar) {
        reservationCalendarCrudRepository.save(reservationCalendar);
    }

    @Transactional
    public void deleteReservationCalendar(int id) {
        reservationCalendarCrudRepository.deleteById(id);
    }

    @Transactional
    public ReservationCalendar getReservationCalendar(int id) {
        return reservationCalendarCrudRepository.findById(id).get();
    }

    @Transactional
    public List<ReservationCalendar> getAllReservationCalendarByIntervalAndAudience(LocalDateTime start_time, LocalDateTime end_time, Audience audience) {
        return reservationCalendarCrudRepository.getAllReservationCalendarInterval(start_time, end_time, audience);
    }


    /*@Query(value = "from EntityClassTable t where yourDate BETWEEN :startDate AND :endDate")
    public List<EntityClassTable> getAllBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate);*/

}
