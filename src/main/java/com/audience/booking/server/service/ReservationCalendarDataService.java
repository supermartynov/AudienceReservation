package com.audience.booking.server.service;

import com.audience.booking.server.dao.ReservationCalendarDAO;
import com.audience.booking.server.entity.Audience;
import com.audience.booking.server.entity.Client;
import com.audience.booking.server.entity.ReservationCalendar;
import com.audience.booking.server.exceptions.*;
import com.audience.booking.server.help_classes.ReservationClientAudience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
    public ReservationCalendar saveReservationCalendar(ReservationClientAudience reservationClientAudience) {

        if (reservationClientAudience.getStart() == null || reservationClientAudience.getEnd() == null
                || reservationClientAudience.getClient() == 0 || reservationClientAudience.getAudience() == 0) {
            throw new InvalidRequestFieldsException();
        }

        Audience audienceId = null;
        Client clientId = null;
        LocalDateTime startTime = reservationClientAudience.getStart();
        LocalDateTime endTime = reservationClientAudience.getEnd();
        Audience audience = audienceDataService.getAudience(reservationClientAudience.getAudience());

        List<ReservationCalendar> reservationCalendarList = reservationService.getAllReservationCalendarByIntervalAndAudience(reservationClientAudience.getStart(),
                reservationClientAudience.getEnd(), audience); //находим все записи в заданный интервал и по заданной аудитории
        //следующие проверки предназначены для возможности непрерывного бронирования (Бронь1: 17:00-18:00; Бронь2: 18:00-19:00)
        if ((reservationCalendarList.size() > 1)) {
            if (!reservationCalendarList.get(reservationCalendarList.size() - 1).getStart().equals(endTime)) {
                System.out.println("Попал в 1 проверку");
                throw new AlreadyBookedException(reservationClientAudience);
            }

            if (!reservationCalendarList.get(0).getEnd().equals(startTime)) {
                System.out.println("попал во вторую проврку");
                throw new AlreadyBookedException(reservationClientAudience);
            }
        } else if (reservationCalendarList.size() == 1) {
            if (!(reservationCalendarList.get(0).getStart().isEqual(endTime) || reservationCalendarList.get(0).getEnd().isEqual(startTime))) {
                System.out.println("Попал в 3 проверку");
                System.out.println("То что имеется в таблице");
                throw new AlreadyBookedException(reservationClientAudience);
            }
        }

        if (startTime.isAfter(endTime)) {  //начальное время позже конечного
            throw new InvalidTimeException(startTime, endTime);
        }

        if (startTime.getDayOfYear() != endTime.getDayOfYear()) { //попытка брони в разные дни
            throw new DifferentDayException(startTime, endTime);
        }

        try {
            audienceId = audienceDataService.getAudience(reservationClientAudience.getAudience());
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(reservationClientAudience.getAudience(), Audience.class.getSimpleName());
        }

        if (audienceId.getTemplate().isAvailavle()) { //работает ли аудитория
            throw new AudienceAvailableException(audience.getId());
        }

        try {
            clientId = clientDataService.getClient(reservationClientAudience.getClient());
        } catch (NoSuchElementException exception) {
            throw new MyEntityNotFoundException(reservationClientAudience.getClient(), Client.class.getSimpleName());
        }

        ReservationCalendar reservationCalendar = new ReservationCalendar(startTime, endTime, clientId, audienceId);

        if (!ReservationCalendar.isValidTime(reservationCalendar)) { //проверка удовлетворяет ли временному шаблону запрос
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
    public void deleteReservationCalendar(int id) {
        reservationCalendarCrudRepository.deleteById(id);
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
