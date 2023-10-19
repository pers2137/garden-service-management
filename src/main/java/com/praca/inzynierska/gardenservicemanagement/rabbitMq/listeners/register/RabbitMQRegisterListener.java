package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register;

import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.model.RabbitMQRegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQRegisterListener {

    @RabbitListener(queues = {"${rabbitmq.register.queue}"})
    public void consume(RabbitMQRegisterRequest request) {
       log.info(request.toString());
    }


}
