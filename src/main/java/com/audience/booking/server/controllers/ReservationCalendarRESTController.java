package com.audience.booking.server.controllers;



import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;
import com.audience.booking.server.entity.ReservationCalendar;
import com.audience.booking.server.exceptions.AlreadyBookedException;
import com.audience.booking.server.exceptions.InvalidTimeException;
import com.audience.booking.server.exceptions.MyEntityNotFoundException;
import com.audience.booking.server.helpClasses.ReservationClientAudience;
import com.audience.booking.server.service.AudienceDataService;
import com.audience.booking.server.service.ClientDataService;
import com.audience.booking.server.service.ReservationCalendarDataService;
import com.audience.booking.server.service.TemplateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private TemplateDataService templateDataService;

    @GetMapping("/")
    public List<ReservationCalendar> showAllEmployees() {
         return reservationService.getAllReservationCalendar();
    }

    @GetMapping("/{id}")
    public ReservationCalendar getReservationCalendar(@PathVariable int id) {

        ReservationCalendar reservationCalendar = null;

        try {
            reservationCalendar = reservationService.getReservationCalendar(id);
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(id, ReservationCalendar.class.getSimpleName());
        }

        return reservationCalendar;
    }

    @GetMapping("/calendar_list") //параметры: start_time, end_time, audience. Формат dateTime - 2011-10-20T10:23:54
    public List<ReservationCalendar> showCalendarList(HttpServletRequest request) {

        //пример запроса - http://localhost:8080/reservation_calendar/calendar_list?start_time=2012-10-19T13:00:00&end_time=2012-10-20T14:00:00&audience_id=1
        LocalDateTime start_time = LocalDateTime.parse(request.getParameter("start_time"));
        LocalDateTime end_time = LocalDateTime.parse(request.getParameter("end_time"));
        Audience audience = audienceDataService.getAudience(Integer.parseInt(request.getParameter("audience_id")));

        List<ReservationCalendar> list = reservationService.getAllReservationCalendarByIntervalAndAudience(start_time, end_time, audience);

        return list;
    }

    @PostMapping("/")
    public ReservationCalendar addReservationCalendar(@RequestBody ReservationClientAudience reservationClientAudience) {

        Audience audienceId = null;
        Client clientId = null;
        LocalDateTime startTime = reservationClientAudience.getStart();
        LocalDateTime endTime = reservationClientAudience.getEnd();
        Audience audience = audienceDataService.getAudience(reservationClientAudience.getAudience());

        if (!reservationService.getAllReservationCalendarByIntervalAndAudience(reservationClientAudience.getStart(),
                reservationClientAudience.getEnd(), audience).isEmpty()) { //находим все записи в заданный интервал и по заданной
            throw new AlreadyBookedException(reservationClientAudience);  // аудитории
        }

        if (startTime.isAfter(endTime)) {
            throw new InvalidTimeException(startTime, endTime);
        }

        try {
            audienceId = audienceDataService.getAudience(reservationClientAudience.getAudience());
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(reservationClientAudience.getAudience(), Audience.class.getSimpleName());
        }

        try {
            clientId = clientDataService.getClient(reservationClientAudience.getClient());
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(reservationClientAudience.getClient(), Client.class.getSimpleName());
        }

        ReservationCalendar reservationCalendar = new ReservationCalendar(startTime, endTime, clientId, audienceId);
        reservationService.saveReservationCalendar(reservationCalendar);
        return reservationCalendar;
    }

    @PutMapping("/")
    public ReservationCalendar updateReservationCalendar(@RequestBody ReservationClientAudience reservationClientAudience) {
        return addReservationCalendar(reservationClientAudience);
    }

    @DeleteMapping("/{id}")
    public String deleteReservationCalendar(@PathVariable int id) {
        reservationService.deleteReservationCalendar(id);
        return "reservation with id = " + id + "was deleted";
    }

}
