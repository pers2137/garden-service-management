package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register;

import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.model.RabbitMQRegisterRequest;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.services.RabbitMQRegisterProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQRegisterListener {

    private final RabbitMQRegisterProcessor registerProcessor;

    public RabbitMQRegisterListener(RabbitMQRegisterProcessor registerProcessor) {
        this.registerProcessor = registerProcessor;
    }

    @RabbitListener(queues = {"${rabbitmq.register.queue}"})
    public void consume(RabbitMQRegisterRequest request) {
       log.info(String.format("Received register request: %s", request.toString()));
       registerProcessor.registerStation(request);
    }


}
