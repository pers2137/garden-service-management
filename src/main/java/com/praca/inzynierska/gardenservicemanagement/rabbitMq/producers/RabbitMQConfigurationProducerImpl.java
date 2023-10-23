package com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers;

import com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers.model.DeviceConfigurationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.springframework.amqp.core.Binding.DestinationType.EXCHANGE;

@Slf4j
@Service
public class RabbitMQConfigurationProducerImpl implements RabbitMQConfigurationProducer {


    private AmqpAdmin admin;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.producer.exchange}")
    private String exchange;

    @Autowired
    public RabbitMQConfigurationProducerImpl(AmqpAdmin admin, RabbitTemplate rabbitTemplate) {
        this.admin = admin;
        this.rabbitTemplate = rabbitTemplate;
    }


    public void createNewQueueAndSendJsonMessage(DeviceConfigurationRequest config, Long mac) {
        var newQueueName = "configuration_" + mac;
        var startTime = System.currentTimeMillis();

        log.info("Start creating new Queue: " + newQueueName);
        Queue queue = new Queue(newQueueName, true, false, false);
        Binding binding = new Binding(newQueueName, Binding.DestinationType.QUEUE, exchange, newQueueName, null);
        admin.declareQueue(queue);
        admin.declareBinding(binding);
        log.info(String.format("Successfully created new Queue: %s in %d ms", newQueueName, System.currentTimeMillis() - startTime));

        sendJsonMessage(config, mac);
    }

    @Override
    public void sendJsonMessage(DeviceConfigurationRequest config, Long mac) {
        var newQueueName = "configuration_" + mac;
        var startTime = System.currentTimeMillis();
        log.info(String.format("Start sending and creating new queue"));
        rabbitTemplate.convertAndSend(exchange, newQueueName, config);
        log.info(String.format("Json message sent config: %s in %d ms", config, System.currentTimeMillis() - startTime));
    }


}
