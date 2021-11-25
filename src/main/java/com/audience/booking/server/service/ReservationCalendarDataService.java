package com.audience.booking.server.service;

import com.audience.booking.server.dao.ReservationCalendarDAO;
import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;
import com.audience.booking.server.entity.ReservationCalendar;
import com.audience.booking.server.exceptions.*;
import com.audience.booking.server.request_bodies.ReservationCalendarRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        //проверка полей запроса
        checkValidRequestFields(reservationCalendarRequestBody);

        Audience audience = getAudience(reservationCalendarRequestBody);
        Client client = getClient(reservationCalendarRequestBody);
        LocalDateTime startTime = reservationCalendarRequestBody.getStart();
        LocalDateTime endTime = reservationCalendarRequestBody.getEnd();

        //находим все записи в заданный интервал и по заданной аудитории
        List<ReservationCalendar> reservationCalendarList = reservationService.getAllReservationCalendarByIntervalAndAudience(reservationCalendarRequestBody.getStart(),
                reservationCalendarRequestBody.getEnd(), audience);
        ReservationCalendar reservationCalendar = new ReservationCalendar(startTime, endTime, client, audience);

        //проверка времени бронирования на валидность
        timeValidation(startTime, endTime, reservationCalendar);
        //проверка доступности аудитории
        checkAudienceAvailability(audience);
        //проверка на занятость аудитории в данное время
        checkBookingAvailability(reservationCalendarList, startTime, endTime, reservationCalendarRequestBody);

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
    public List<ReservationCalendar> getAllReservationCalendarByIntervalAndAudience(String start, String end, int audienceId) {
        LocalDateTime start_time = LocalDateTime.parse(start);
        LocalDateTime end_time = LocalDateTime.parse(end);

        if (start_time.isAfter(end_time)) {
            throw new InvalidTimeException(start_time, end_time);
        }

        Audience audience = audienceDataService.getAudience(audienceId);

        return reservationCalendarCrudRepository.getAllReservationCalendarInterval(start_time, end_time, audience);
    }

    @Transactional
    public List<ReservationCalendar> getAllReservationCalendarByIntervalAndAudience(LocalDateTime start_time, LocalDateTime end_time, Audience audience) {
        return reservationCalendarCrudRepository.getAllReservationCalendarInterval(start_time, end_time, audience);
    }

    @Transactional
    public void deleteAllBookings() {
        reservationCalendarCrudRepository.deleteAll();
    }

    private void checkValidRequestFields(ReservationCalendarRequestBody reservationCalendarRequestBody) {
        if (reservationCalendarRequestBody.getStart() == null || reservationCalendarRequestBody.getEnd() == null
                || reservationCalendarRequestBody.getClient() <= 0 || reservationCalendarRequestBody.getAudience() <= 0) {
            throw new InvalidRequestFieldsException();
        }
    }

    private Client getClient(ReservationCalendarRequestBody reservationCalendarRequestBody) {
        try {
            return clientDataService.getClient(reservationCalendarRequestBody.getClient());
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(reservationCalendarRequestBody.getClient(), Client.class.getSimpleName());
        }
    }

    private Audience getAudience(ReservationCalendarRequestBody reservationCalendarRequestBody) {
        try {
            return audienceDataService.getAudience(reservationCalendarRequestBody.getAudience());
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(reservationCalendarRequestBody.getAudience(), Audience.class.getSimpleName());
        }
    }

    private void checkAudienceAvailability(Audience audience) {
        if (!audience.getTemplate().isAvailavle()) {
            throw new AudienceAvailableException(audience.getId());
        }
    }

    private void checkBookingAvailability(List<ReservationCalendar> reservationCalendarList, LocalDateTime startTime,
                                          LocalDateTime endTime, ReservationCalendarRequestBody reservationCalendarRequestBody)
    {
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

    }

    private void timeValidation(LocalDateTime startTime, LocalDateTime endTime, ReservationCalendar reservationCalendar ) {
        //начальное время позже конечного
        if (startTime.isAfter(endTime)) {
            throw new InvalidTimeException(startTime, endTime);
        }

        //проверка того, что время бронирования больше 60 минутам и запись возможно только во время кратное 30 минутам
        if (getMinutesFromTime(endTime) - getMinutesFromTime(startTime) < 60
                || (getMinutesFromTime(endTime) - getMinutesFromTime(startTime)) % 30 != 0
                || getMinutesFromTime(endTime) % 30 != 0)
        {
            throw new MinBookingTimeException();
        }

        //попытка брони в разные дни
        if (startTime.getDayOfYear() != endTime.getDayOfYear()) {
            throw new DifferentDayException(startTime, endTime);
        }

        //попытка бронирования в прошлое или за 90 дней от сегодня
        if (startTime.isBefore(LocalDateTime.now()) || startTime.isAfter(LocalDateTime.now().plusDays(90))) {
            throw new SoonerOrLaterException(startTime);
        }

        //время не соответствует времени работы аудитории, прописанное в шаблоне
        if (!templateTimeValidation(reservationCalendar)) {
            throw new TimeSutisfyTemplateException(startTime, endTime, reservationCalendar.getAudience().getTemplate());
        }
    }

    private boolean templateTimeValidation(ReservationCalendar reservationCalendar) {
        LocalTime reservationStartTime = reservationCalendar.getStart().toLocalTime();
        LocalTime reservationEndTime = reservationCalendar.getEnd().toLocalTime();
        LocalTime validStartTime = reservationCalendar.getAudience().getTemplate().getStartTime();
        LocalTime validEndTime = reservationCalendar.getAudience().getTemplate().getEndTime();

        if (reservationStartTime.isBefore(validStartTime) || reservationStartTime.isAfter(validEndTime)) {
            return false;
        }

        if (reservationEndTime.isBefore(validStartTime) || reservationEndTime.isAfter(validEndTime)) {
            return false;
        }

        return true;
    }

    private int getMinutesFromTime(LocalDateTime localDateTime) {
        return localDateTime.getHour() * 60 + localDateTime.getMinute() + localDateTime.getSecond() / 60;
    }

}
