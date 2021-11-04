package com.audience.booking.server.service;

import com.audience.booking.server.dao.ReservationCalendarDAO;
import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;
import com.audience.booking.server.entity.ReservationCalendar;
import com.audience.booking.server.exceptions.*;
import com.audience.booking.server.help_classes.ReservationCalendarRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationCalendarDataService  {
    @Autowired
    private ReservationCalendarDataService reservationService;

    @Autowired
    private AudienceDataService audienceDataService;

    @Autowired
    private ClientDataService clientDataService;

    @Autowired
    private ReservationCalendarDAO reservationCalendarCrudRepository;

    @Transactional
    public List<ReservationCalendar> getAllReservationCalendar() {
        return (List<ReservationCalendar>) reservationCalendarCrudRepository.findAll();
    }

    @Transactional
    public ReservationCalendar saveReservationCalendar(ReservationCalendarRequestBody reservationCalendarRequestBody) {

        if (reservationCalendarRequestBody.getStart() == null || reservationCalendarRequestBody.getEnd() == null
                || reservationCalendarRequestBody.getClient() <= 0 || reservationCalendarRequestBody.getAudience() <= 0) {
            throw new InvalidRequestFieldsException();
        }

        Audience audience = null;
        Client client = null;
        LocalDateTime startTime = reservationCalendarRequestBody.getStart();
        LocalDateTime endTime = reservationCalendarRequestBody.getEnd();

        if (ReservationCalendar.getMinutesFromTime(endTime) - ReservationCalendar.getMinutesFromTime(startTime) < 60
                ||  (ReservationCalendar.getMinutesFromTime(endTime) - ReservationCalendar.getMinutesFromTime(startTime)) % 30 != 0)
        {
            throw new MinBookingTimeException();
        }

        try {
            audience = audienceDataService.getAudience(reservationCalendarRequestBody.getAudience());
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(reservationCalendarRequestBody.getAudience(), Audience.class.getSimpleName());
        }

        try {
            client = clientDataService.getClient(reservationCalendarRequestBody.getClient());
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(reservationCalendarRequestBody.getClient(), Client.class.getSimpleName());
        }

        //находим все записи в заданный интервал и по заданной аудитории
        List<ReservationCalendar> reservationCalendarList = reservationService.getAllReservationCalendarByIntervalAndAudience(reservationCalendarRequestBody.getStart(),
                reservationCalendarRequestBody.getEnd(), audience);

        //следующие проверки предназначены для возможности непрерывного бронирования (Бронь1: 17:00-18:00; Бронь2: 18:00-19:00)
        if ((reservationCalendarList.size() > 1)) {
            if (!reservationCalendarList.get(reservationCalendarList.size() - 1).getStart().equals(endTime)) {
                throw new AlreadyBookedException(reservationCalendarRequestBody);
            }

            if (!reservationCalendarList.get(0).getEnd().equals(startTime)) {
                throw new AlreadyBookedException(reservationCalendarRequestBody);
            }
        } else if (reservationCalendarList.size() == 1) {
            if (!(reservationCalendarList.get(0).getStart().isEqual(endTime) || reservationCalendarList.get(0).getEnd().isEqual(startTime))) {
                throw new AlreadyBookedException(reservationCalendarRequestBody);
            }
        }

        //начальное время позже конечного
        if (startTime.isAfter(endTime)) {
            throw new InvalidTimeException(startTime, endTime);
        }

        //попытка брони в разные дни
        if (startTime.getDayOfYear() != endTime.getDayOfYear()) {
            throw new DifferentDayException(startTime, endTime);
        }

        //попытка бронирования в прошлое или за 90 дней от сегодня
        if (startTime.isBefore(LocalDateTime.now()) || startTime.isAfter(LocalDateTime.now().plusDays(90))) {
            throw new SoonerOrLaterException(startTime);
        }

        if (!audience.getTemplate().isAvailavle()) {
            throw new AudienceAvailableException(audience.getId());
        }

        ReservationCalendar reservationCalendar = new ReservationCalendar(startTime, endTime, client, audience);

        //проверка удовлетворяет ли временному шаблону запрос
        if (!ReservationCalendar.isValidTime(reservationCalendar)) {
            throw new TimeSutisfyTemplateException(startTime, endTime, reservationCalendar.getAudience().getTemplate());
        }

        reservationCalendarCrudRepository.save(reservationCalendar);

        return reservationCalendar;
    }

    @Transactional
    public ReservationCalendar getReservationCalendar(int id) {

        ReservationCalendar reservationCalendar = null;

        try {
            reservationCalendar = reservationCalendarCrudRepository.findById(id).get();
        } catch (NoSuchElementException err) {
            throw new MyEntityNotFoundException(id, ReservationCalendar.class.getSimpleName());
        }
        return reservationCalendar;
    }

    @Transactional
    public ReservationCalendar deleteReservationCalendar(int id) {
        ReservationCalendar reservationCalendar = null;

        try {
            reservationCalendar = reservationCalendarCrudRepository.findById(id).get();
        } catch (NoSuchElementException err) {
            throw new MyEntityNotFoundException(id, ReservationCalendar.class.getSimpleName());
        }

        reservationCalendarCrudRepository.deleteById(id);
        return reservationCalendar;
    }

    @Transactional
    public List<ReservationCalendar> getAllReservationCalendarByIntervalAndAudience(String start, String end, String audienceId) {
        LocalDateTime start_time = LocalDateTime.parse(start);
        LocalDateTime end_time = LocalDateTime.parse(end);

        if (start_time.isAfter(end_time)) {
            throw new InvalidTimeException(start_time, end_time);
        }

        Audience audience = audienceDataService.getAudience(Integer.parseInt(audienceId));

        return reservationCalendarCrudRepository.getAllReservationCalendarInterval(start_time, end_time, audience);
    }

    @Transactional
    public List<ReservationCalendar> getAllReservationCalendarByIntervalAndAudience(LocalDateTime start_time, LocalDateTime end_time, Audience audience) {
        return reservationCalendarCrudRepository.getAllReservationCalendarInterval(start_time, end_time, audience);
    }

}
