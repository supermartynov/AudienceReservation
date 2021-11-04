package com.audience.booking.server.service;

import com.audience.booking.server.entity.ReservationCalendar;
import com.audience.booking.server.help_classes.ReservationCalendarRequestBody;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
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

    @Test
    void getAllReservationCalendarBeforeFilling() {
        //тест показывает, что таблица пуста на момент начала тестирования
        List<ReservationCalendar> list = reservationCalendarDataService.getAllReservationCalendar();
        assertEquals(0, list.size());
    }

    @Test
    void saveReservationCalendarWithOneBooking() {
        //в тесте в таблицу добавляется бронирование с 11:00 до 13:00, для аудитории с id=2 и для клиента с id=1
        LocalDateTime startTime = LocalDateTime.parse("2021-11-18T11:00:00");
        LocalDateTime endTime = LocalDateTime.parse("2021-11-18T11:00:00");
        int clientId = 1;
        int audienceId = 2;
        ReservationCalendarRequestBody reservationCalendarRequestBody = new ReservationCalendarRequestBody(startTime, endTime, clientId, audienceId);
        ReservationCalendar firstBooking = reservationCalendarDataService.saveReservationCalendar(reservationCalendarRequestBody);
        int id = firstBooking.getId();

        //проверим, что произошло добавление
        Assert.assertEquals(firstBooking.toString(), reservationCalendarDataService.getReservationCalendar(id).toString());
        //удалим запись
        reservationCalendarDataService.deleteReservationCalendar(id);
    }


    @Test
    void saveReservationCalendar() {
    }

    @Test
    void getReservationCalendar() {
    }

    @Test
    void deleteReservationCalendar() {
    }

    @Test
    void getAllReservationCalendarByIntervalAndAudience() {
    }
}