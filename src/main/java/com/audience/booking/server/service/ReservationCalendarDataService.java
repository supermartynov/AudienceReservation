package com.audience.booking.server.service;

import com.audience.booking.server.dao.ReservationCalendarDAO;
import com.audience.booking.server.entity.ReservationCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReservationCalendarDataService {

    @Autowired
    private ReservationCalendarDAO ReservationCalendarCrudRepository;

    @Transactional
    public List<ReservationCalendar> getAllReservationCalendars() {
        return (List<ReservationCalendar>) ReservationCalendarCrudRepository.findAll();
    }

    @Transactional
    public void saveReservationCalendar(ReservationCalendar ReservationCalendar) {
        ReservationCalendarCrudRepository.save(ReservationCalendar);
    }

    @Transactional
    public void deleteReservationCalendar(int id) {
        ReservationCalendarCrudRepository.deleteById(id);
    }

    @Transactional
    public ReservationCalendar getReservationCalendar(int id) {
        return ReservationCalendarCrudRepository.findById(id).get();
    }

}