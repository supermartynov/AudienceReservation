package com.audience.booking.server.service;

import com.audience.booking.server.entity.Audience;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class AudienceDataServiceTest {

    @Autowired
    private AudienceDataService audienceDataService;

    @Test
    void getAllAudiences() {
        //тест для проверки вывода информации об аудиториях
        //исходные данные, заполненные liquibase запросами :
        /*
        { id: 1, description: "Аудитория подойдет для переговоров, м. Щукинская", capacity: 15, template: 1}
        { id: 2, description: "Аудитория подойдет для переговоров, м. Кантемировская",  capacity: 10,  template: 2}
        { id: 3, description: "Аудитория подойдет для переговоров, м. Кантемировская",  capacity: 10,  template: 3}
         */

        String audience1 = "Audience{id=1, capacity=15, description='Аудитория подойдет для переговоров, " +
                "м. Щукинская', template=Template{startTime=09:00, endTime=21:00, isAvailavle=true}";
        String audience2 = "Audience{id=2, capacity=10, description='Аудитория подойдет для переговоров, " +
                "м. Кантемировская', template=Template{startTime=10:00, endTime=20:00, isAvailavle=true}";
        String audience3 = "Audience{id=3, capacity=10, description='Аудитория подойдет для переговоров " +
                "м. Кантемировская. Сейчас не работает', template=Template{startTime=09:00, endTime=23:00, " +
                "isAvailavle=false}";

        Assert.assertEquals(audienceDataService.getAllAudiences().get(0).toString(), audience1);
        Assert.assertEquals(audienceDataService.getAllAudiences().get(1).toString(), audience2);
        Assert.assertEquals(audienceDataService.getAllAudiences().get(2).toString(), audience3);

        System.out.println("Тест getAllAudiences пройден");
    }
}