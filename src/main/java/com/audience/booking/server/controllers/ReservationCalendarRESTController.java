package com.audience.booking.server.controllers;



import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;
import com.audience.booking.server.entity.ReservationCalendar;
import com.audience.booking.server.exceptions.MyEntityNotFoundException;
import com.audience.booking.server.helpClasses.ReservationClientAudience;
import com.audience.booking.server.service.AudienceDataService;
import com.audience.booking.server.service.ClientDataService;
import com.audience.booking.server.service.ReservationCalendarDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reservation_calendar")
public class ReservationCalendarRESTController {

    @Autowired
    private ReservationCalendarDataService reservationService;

    @Autowired
    private AudienceDataService audienceDataService;

    @Autowired
    private ClientDataService clientDataService;

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
    public ReservationCalendar addEmployee(@RequestBody ReservationClientAudience reservationClientAudience) {
        //ReservationClientAudience - вспомогательный объект, содержащий тело запроса.
        LocalDateTime startTime = reservationClientAudience.getStart();
        LocalDateTime endTime = reservationClientAudience.getEnd();
        Audience audienceId = audienceDataService.getAudience(reservationClientAudience.getAudience());
        Client clientId = clientDataService.getClient(reservationClientAudience.getClient());
        //ниже создаем объект типа ReservationCalendar, на основании тела запроса
        ReservationCalendar reservationCalendar = new ReservationCalendar(startTime, endTime, clientId, audienceId);

        reservationService.saveReservationCalendar(reservationCalendar);
        return reservationCalendar;
    }

    @PutMapping("/")
    public ReservationCalendar updateEmployee(@RequestBody ReservationClientAudience reservationClientAudience) {
        //ReservationClientAudience - вспомогательный объект, содержащий тело запроса.
        LocalDateTime startTime = reservationClientAudience.getStart();
        LocalDateTime endTime = reservationClientAudience.getEnd();
        Audience audienceId = audienceDataService.getAudience(reservationClientAudience.getAudience());
        Client clientId = clientDataService.getClient(reservationClientAudience.getClient());
        //ниже создаем объект типа ReservationCalendar, на основании тела запроса
        ReservationCalendar reservationCalendar = new ReservationCalendar(startTime, endTime, clientId, audienceId);
        //ниже создаем объект типа ReservationCalendar, на основании тела запроса
        reservationService.saveReservationCalendar(reservationCalendar);
        return reservationCalendar;;
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        reservationService.deleteReservationCalendar(id);
        return "reservation with id = " + id + "was deleted";
    }

}
