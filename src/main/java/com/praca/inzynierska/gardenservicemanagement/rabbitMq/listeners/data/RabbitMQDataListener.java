package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.data;

import com.praca.inzynierska.gardenservicemanagement.common.BinaryParser;
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

    //TODO ZROBIENIE PORZADKU Z NAZWAMI, ORAZ Z API, TABELKA NA BAZIE
    @RabbitListener(queues = {"${rabbitmq.data.queue}"})
    public void consume(RabbitMQDataRequest request) {
        log.info(String.format("Received register request: %s", request.toString()));
        registerProcessor.saveMeasurementData(request);
        log.info(String.format("Data request from %s was handled.", BinaryParser.getMacAddressFromInt64(request.getMac())));
    }
}
