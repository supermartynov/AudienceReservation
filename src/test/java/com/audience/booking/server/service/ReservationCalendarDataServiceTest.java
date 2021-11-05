package com.audience.booking.server.service;

import com.audience.booking.server.entity.ReservationCalendar;
import com.audience.booking.server.exceptions.*;
import com.audience.booking.server.help_classes.ReservationCalendarRequestBody;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReservationCalendarDataServiceTest {
    @Autowired
    private ReservationCalendarDataService reservationCalendarDataService;

    @Autowired
    private AudienceDataService audienceDataService;

    @Autowired
    private ClientDataService clientDataService;

    static final List<ReservationCalendarRequestBody> bookingsForTests = new ArrayList<>() {};

    static {
        //0
        LocalDateTime startTime = LocalDateTime.parse("2021-11-18T11:00:00");
        LocalDateTime endTime = LocalDateTime.parse("2021-11-18T12:00:00");
        int clientId = 1;
        int audienceId = 2;
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, clientId, audienceId));

        // диапазон записи < 60 минут
        //1
        startTime = LocalDateTime.parse("2021-11-18T12:00:00");
        endTime = LocalDateTime.parse("2021-11-18T12:10:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        // диапазон записи не кратен 30 минутам
        //2
        startTime = LocalDateTime.parse("2021-11-18T12:00:00");
        endTime = LocalDateTime.parse("2021-11-18T13:20:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        //3
        startTime = LocalDateTime.parse("2021-11-18T11:30:00");
        endTime = LocalDateTime.parse("2021-11-18T12:30:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        //4
        startTime = LocalDateTime.parse("2021-11-18T12:00:00");
        endTime = LocalDateTime.parse("2021-11-18T13:00:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        //5
        startTime = LocalDateTime.parse("2021-11-18T13:00:00");
        endTime = LocalDateTime.parse("2021-11-18T14:00:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        //Бронирование аудитории, которая не работает (Шаблон, закрепленный за аудиторией 3 имеет поле isAvailable=false
        //6
        startTime = LocalDateTime.parse("2021-11-18T13:00:00");
        endTime = LocalDateTime.parse("2021-11-18T14:00:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 3));

        //Бронирование аудитории в разные дни
        //7
        startTime = LocalDateTime.parse("2021-11-18T13:00:00");
        endTime = LocalDateTime.parse("2021-11-19T14:00:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        //Бронирование в прошлое (1 ноября)
        //8
        startTime = LocalDateTime.parse("2021-11-01T13:00:00");
        endTime = LocalDateTime.parse("2021-11-01T17:30:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        //Бронирование в будущее (17 марта)
        //бронь возможна ближайшие 90 дней, начиная с текущего времени
        //9
        startTime = LocalDateTime.parse("2022-03-17T16:00:00");
        endTime = LocalDateTime.parse("2022-03-17T18:30:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        //Бронирование в нерабочие для аудитории часы
        //Время работы указано в шаблоне, закрепленном за каждой аудиторией. Для аудитории 2: с 10:00 - 20:00
        //10
        startTime = LocalDateTime.parse("2021-12-17T19:00:00");
        endTime = LocalDateTime.parse("2021-12-17T22:30:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        //Время начала бронирования поpже времени окончания
        //11
        startTime = LocalDateTime.parse("2021-12-17T19:00:00");
        endTime = LocalDateTime.parse("2021-12-17T18:00:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        //12
        startTime = LocalDateTime.parse("2021-12-20T12:00:00");
        endTime = LocalDateTime.parse("2021-12-20T13:30:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        //13
        startTime = LocalDateTime.parse("2021-12-20T14:00:00");
        endTime = LocalDateTime.parse("2021-12-20T15:00:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

        //14
        startTime = LocalDateTime.parse("2021-12-20T16:00:00");
        endTime = LocalDateTime.parse("2021-12-20T17:00:00");
        bookingsForTests.add(new ReservationCalendarRequestBody(startTime, endTime, 1, 2));

    }

    @BeforeEach
    void deleteAllBookings() {
        reservationCalendarDataService.deleteAllBookings();
    }

    @Test()
    void testMinBookingTimeExceptionForInvalidTimeLessSixtyMin() {
        //Время бронирование должно быть >= 60 минутам
        //Бронируем с 2021-11-18 12:00:00 по 2021-11-18 12:10:00 аудиторию 2
        assertThrows(MinBookingTimeException.class, () -> {
            ReservationCalendar firstBooking = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(1));
            reservationCalendarDataService.deleteReservationCalendar(firstBooking.getId());
            System.out.println("Выброшено исключение:  " + MinBookingTimeException.class.getSimpleName());
        }, "Попытка сделать запись длинной в 10 минут");

        System.out.println("Тест testMinBookingTimeExceptionForInvalidTimeLessSixtyMin пройден");
    }

    @Test()
    void testMinBookingTimeExceptionForInvalidTimeDontDevideThirtyMin() {
        //Время бронирование должно быть кратно 30 минутам.
        //Бронируем с 2021-11-18 12:00:00 по 2021-11-18 13:20:00 аудиторию 2
        assertThrows(MinBookingTimeException.class, () -> {
            ReservationCalendar reservationCalendar = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(2));
            reservationCalendarDataService.deleteReservationCalendar(reservationCalendar.getId());
            System.out.println("Выброшено исключение:  " + MinBookingTimeException.class.getSimpleName());
        }, "Попытка бронирование времени на диапазон некратный 30 минутам");

        System.out.println("Тест testMinBookingTimeExceptionForInvalidTimeDevideThirtyMin пройден");
    }

    @Test
    void testMinBookingTimeExceptionForValidTime() {
        //Время бронирование должно быть >= 60 минутам и кратно 30 минутам.
        //Бронируем с 2021-11-18 11:00:00 по 2021-11-18 12:00:00 аудиторию 2
        ReservationCalendar booking = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(0));
        int id = booking.getId();

        Assert.assertEquals(booking.toString(), reservationCalendarDataService.getReservationCalendar(id).toString());

        System.out.println(booking);
        reservationCalendarDataService.deleteReservationCalendar(id);
        System.out.println("Тест testMinBookingTimeExceptionForValidTime пройден");
    }

    @Test
    void testAlreadyBookedException() {
        // Тест на попытку брони недоступной даты
        //Бронируем с 2021-11-18 11:00:00 по 2021-11-18 12:00:00 аудиторию 2
        ReservationCalendar booking = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(0));
        int id = booking.getId();
        //попытаемся забронировать аудиторию 2 с 2021-11-18 11:30:00 по 2021-11-18 12:30:00
        assertThrows(AlreadyBookedException.class, () -> {
            ReservationCalendar reservationCalendar = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(3));
            reservationCalendarDataService.deleteReservationCalendar(reservationCalendar.getId());
            System.out.println("Выброшено исключение:  " + AlreadyBookedException.class.getSimpleName());
        }, "Попытка забронировать на недоступное время");

        reservationCalendarDataService.deleteReservationCalendar(id);
        System.out.println("Тест testAlreadyBookedException пройден");
    }

    @Test
    void testPermanentlyBooking() {
        //Тест на проверку возможности непрерывного бронирования
        //Бронируем с 2021-11-18 11:00:00 по 2021-11-18 12:00:00 аудиторию 2
        ReservationCalendar bookingFirst = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(0));
        int idFirst = bookingFirst.getId();
        //Бронируем с 2021-11-18 13:00:00 по 2021-11-18 14:00:00 аудиторию 2
        ReservationCalendar bookingSecond = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(5));
        int idSecond = bookingSecond.getId();
        //попытаемся забронировать аудиторию 2 с 2021-11-18 12:00:00 по 2021-11-18 13:00:00
        ReservationCalendar bookingThird = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(4));
        int idThird = bookingThird.getId();

        Assert.assertEquals(bookingThird.toString(), reservationCalendarDataService.getReservationCalendar(idThird).toString());

        reservationCalendarDataService.deleteReservationCalendar(idFirst);
        reservationCalendarDataService.deleteReservationCalendar(idSecond);
        reservationCalendarDataService.deleteReservationCalendar(idThird);
        System.out.println("Тест testPermanentlyBooking пройден");
    }

    @Test
    void testDifferentDayException() {
        //Тест на проверку запрета брони начинающейся в один день и заканчивающейся в другой
        //Бронируем с 2021-11-18 13:00:00 по 2021-11-19 14:00:00 аудиторию 2
        assertThrows(DifferentDayException.class, () -> {
            ReservationCalendar reservationCalendar = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(7));
            reservationCalendarDataService.deleteReservationCalendar(reservationCalendar.getId());
            System.out.println("Выброшено исключение:  " + DifferentDayException.class.getSimpleName());
        }, "Попытка забронировать в разные дни");

        System.out.println("Тест testDifferentDayException пройден");
    }

    @Test
    void testSoonerOrLaterExceptionBookingInThePast() {
        //Тест на проверку запрета брони в прошлое
        //Бронируем с 2021-11-1 13:00:00 по 2021-11-1 17:30:00 аудиторию 2
        assertThrows(SoonerOrLaterException.class, () -> {
            ReservationCalendar reservationCalendar = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(8));
            reservationCalendarDataService.deleteReservationCalendar(reservationCalendar.getId());
            System.out.println("Выброшено исключение:  " + SoonerOrLaterException.class.getSimpleName());
        }, "Попытка забронировать в прошлое");

        System.out.println("Тест testSoonerOrLaterExceptionBookingInThePast пройден");
    }

    @Test
    void testSoonerOrLaterExceptionBookingInTheFarFuture() {
        //Тест на проверку запрета брони в будущее (бронь возможна ближайшие 90 дней, начиная с текущего времени)
        //Бронируем с 2022-03-17 16:00:00 по 2022-03-17 18:30:00 аудиторию 2
        assertThrows(SoonerOrLaterException.class, () -> {
            ReservationCalendar reservationCalendar = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(9));
            reservationCalendarDataService.deleteReservationCalendar(reservationCalendar.getId());
            System.out.println("Выброшено исключение:  " + SoonerOrLaterException.class.getSimpleName());
        }, "Попытка забронировать в далекое будещее");

        System.out.println("Тест testSoonerOrLaterExceptionBookingInTheFarFuture пройден");
    }

    @Test
    void testAudienceAvailableException() {
        //Тест на проверку запрета брони аудитории, в шаблоне которой указано, что аудитории не работает на данный момент
        //Бронируем с 2021-11-18 13:00:00 по 2021-11-18 13:00:00 аудиторию 3, которая недоступна
        assertThrows(AudienceAvailableException.class, () -> {
            ReservationCalendar reservationCalendar = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(6));
            reservationCalendarDataService.deleteReservationCalendar(reservationCalendar.getId());
            System.out.println("Выброшено исключение:  " + AudienceAvailableException.class.getSimpleName());
        }, "Попытка забронировать в недоступную аудиторию");

        System.out.println("Тест testAudienceAvailableException пройден");
    }

    @Test
    void testTimeSutisfyTemplateException() {
        //Тест на проверку соответствия времени бронирования в зпросе и шаблона, закрепленного за аудиторией
        //Время работы указано в шаблоне, закрепленном за каждой аудиторией. Для аудитории 2: с 10:00 - 20:00
        //Бронируем с 2021-12-17 19:00:00 по 2021-12-17 22:00:00 аудиторию 3, которая недоступна

        assertThrows(TimeSutisfyTemplateException.class, () -> {
            ReservationCalendar reservationCalendar = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(10));
            reservationCalendarDataService.deleteReservationCalendar(reservationCalendar.getId());
            System.out.println("Выброшено исключение:  " + TimeSutisfyTemplateException.class.getSimpleName());
        }, "Попытка забронировать в нерабочее время");

        System.out.println("Тест testTimeSutisfyTemplateException пройден");
    }

    @Test
    void testInvalidTimeException() {
        //Тест на проверку ситуации, когда время начала позже времени окончания
        //Бронируем с 2021-12-17 19:00:00 по 2021-12-17 18:00:00 аудиторию 2

        assertThrows(InvalidTimeException.class, () -> {
            ReservationCalendar reservationCalendar = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(11));
            reservationCalendarDataService.deleteReservationCalendar(reservationCalendar.getId());
            System.out.println("Выброшено исключение:  " + InvalidTimeException.class.getSimpleName());
        }, "Попытка забронировать в нерабочее время");

        System.out.println("Тест testInvalidTimeException пройден");
    }

    @Test
    void testGetAllReservationCalendarByIntervalAndAudience() {
        //Тест на вывод всех броней в заданный интервал
        //Бронируем с 2021-12-20 12:00:00 по 2021-12-20 13:30:00 аудиторию 2
        ReservationCalendar bookingFirst = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(12));
        int idFirst = bookingFirst.getId();

        //Бронируем с 2021-12-20 14:00:00 по 2021-12-20 15:00:00 аудиторию 2
        ReservationCalendar bookingSecond = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(13));
        int idSecond = bookingSecond.getId();

        //Бронируем с 2021-12-20 16:00:00 по 2021-12-20 17:00:00 аудиторию 2
        ReservationCalendar bookingThird = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(14));
        int idThird = bookingThird.getId();

        List<ReservationCalendar> expectedList = new ArrayList<>();
        expectedList.add(bookingFirst);
        expectedList.add(bookingSecond);
        expectedList.add(bookingThird);

        //Запросим все бронирования для второй аудитории , начиная с 2021-12-20T12:00:00 до 2021-12-20T17:00:00"
        List<ReservationCalendar> list = reservationCalendarDataService.getAllReservationCalendarByIntervalAndAudience("2021-12-20T12:00:00",
                "2021-12-20T17:00:00", 2);

        assertEquals(list.toString(), expectedList.toString());

        reservationCalendarDataService.deleteReservationCalendar(idFirst);
        reservationCalendarDataService.deleteReservationCalendar(idSecond);
        reservationCalendarDataService.deleteReservationCalendar(idThird);
        System.out.println("Тест testGetAllReservationCalendarByIntervalAndAudience пройден");
    }

    @Test
    void testGetAllReservationCalendarByIntervalAndAudienceSecond() {
        //Тест на вывод всех броней в заданный интервал
        //Бронируем с 2021-12-20 12:00:00 по 2021-12-20 13:30:00 аудиторию 2
        ReservationCalendar bookingFirst = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(12));
        int idFirst = bookingFirst.getId();

        //Бронируем с 2021-12-20 14:00:00 по 2021-12-20 15:00:00 аудиторию 2
        ReservationCalendar bookingSecond = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(13));
        int idSecond = bookingSecond.getId();

        //Бронируем с 2021-12-20 16:00:00 по 2021-12-20 17:00:00 аудиторию 2
        ReservationCalendar bookingThird = reservationCalendarDataService.saveReservationCalendar(bookingsForTests.get(14));
        int idThird = bookingThird.getId();

        List<ReservationCalendar> expectedList = new ArrayList<>();
        expectedList.add(bookingSecond);
        expectedList.add(bookingThird);

        //Запросим все бронирования для второй аудитории , начиная с 2021-12-20T14:00:00 до 2021-12-20T17:00:00"
        List<ReservationCalendar> list = reservationCalendarDataService.getAllReservationCalendarByIntervalAndAudience("2021-12-20T14:00:00",
                "2021-12-20T17:00:00", 2);

        assertEquals(list.toString(), expectedList.toString());

        /*reservationCalendarDataService.deleteReservationCalendar(idFirst);
        reservationCalendarDataService.deleteReservationCalendar(idSecond);
        reservationCalendarDataService.deleteReservationCalendar(idThird);*/
        System.out.println("Тест testGetAllReservationCalendarByIntervalAndAudience пройден");
    }

}