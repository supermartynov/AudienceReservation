package com.audience.booking.server.controllers;

import com.audience.booking.server.entity.ReservationCalendar;
import com.audience.booking.server.help_classes.ReservationCalendarRequestBody;
import com.audience.booking.server.service.ReservationCalendarDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/reservation_calendar")
public class ReservationCalendarRESTController {

    @Autowired
    private ReservationCalendarDataService reservationService;

    @GetMapping
    public List<ReservationCalendar> showAllEmployees() {
         return reservationService.getAllReservationCalendar();
    }

    @GetMapping("/{id}")
    public ReservationCalendar getReservationCalendar(@PathVariable int id) {
        return reservationService.getReservationCalendar(id);
    }

    @GetMapping("/calendar_list") //параметры: start_time, end_time, audience. Формат dateTime - 2011-10-20T10:23:54
    public List<ReservationCalendar> showCalendarList(HttpServletRequest request) {

        String start_time = request.getParameter("start_time");
        String end_time = request.getParameter("end_time");
        int audience = Integer.parseInt(request.getParameter("audience_id"));

        return reservationService.getAllReservationCalendarByIntervalAndAudience(start_time, end_time, audience);
    }

    @PostMapping
    public ReservationCalendar addReservationCalendar(@RequestBody ReservationCalendarRequestBody reservationCalendarRequestBody) {
        return reservationService.saveReservationCalendar(reservationCalendarRequestBody);
    }

    @PutMapping("")
    public ReservationCalendar updateReservationCalendar(@RequestBody ReservationCalendarRequestBody reservationCalendarRequestBody) {
        return addReservationCalendar(reservationCalendarRequestBody);
    }

    @DeleteMapping("/{id}")
    public String deleteReservationCalendar(@PathVariable int id) {
        reservationService.deleteReservationCalendar(id);
        return "reservation with id = " + id + "was deleted";
    }

    @DeleteMapping
    public String deleteReservationCalendar() {
        reservationService.deleteAllBookings();
        return "all bookings were deleted";
    }
}
