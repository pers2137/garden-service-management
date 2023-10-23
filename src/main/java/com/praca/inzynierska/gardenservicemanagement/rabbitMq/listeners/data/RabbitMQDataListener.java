package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.data;

import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.data.model.RabbitMQDataRequest;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.data.services.RabbitMQDataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQDataListener {

    private final RabbitMQDataProcessor registerProcessor;

    public RabbitMQDataListener(RabbitMQDataProcessor registerProcessor) {
        this.registerProcessor = registerProcessor;
    }

//TODO DODANIE DEKLARACJI TEJ KOLEJKI W KONFIGURACJI DO NASLUCHU
    @RabbitListener(queues = {"${rabbitmq.data.queue}"})
    public void consume(RabbitMQDataRequest request) {
        //TODO ZAPIS DO BAZY DANYCH :)
        System.out.println("odebrano :)");
    }
}
