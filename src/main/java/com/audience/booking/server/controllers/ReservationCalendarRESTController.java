package com.audience.booking.server.controllers;



import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.ReservationCalendar;
import com.audience.booking.server.exceptions.MyEntityNotFoundException;
import com.audience.booking.server.service.AudienceDataService;
import com.audience.booking.server.service.ReservationCalendarDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reservation_calendar")
public class ReservationCalendarRESTController {

    @Autowired
    private ReservationCalendarDataService reservationService;

    @GetMapping("/")
    public List<ReservationCalendar> showAllEmployees() {
         return reservationService.getAllReservationCalendars();
    }

    @GetMapping("/{id}")
    public ReservationCalendar getEmployee(@PathVariable int id) {
        ReservationCalendar reservationCalendar = null;
        try {
            reservationCalendar = reservationService.getReservationCalendar(id);
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(id);
        }
        return reservationCalendar;
    }

    @PostMapping("/")
    public ReservationCalendar addEmployee(@RequestBody ReservationCalendar reservationCalendar) {
        reservationService.saveReservationCalendar(reservationCalendar);
        return reservationCalendar;
    }

    @PutMapping("/")
    public ReservationCalendar updateEmployee(@RequestBody ReservationCalendar reservationCalendar) {
        reservationService.saveReservationCalendar(reservationCalendar);
        return reservationCalendar;
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        reservationService.deleteReservationCalendar(id);
        return "reservation with id = " + id + "was deleted";
    }

}
