package com.praca.inzynierska.gardenservicemanagement.rabbitMq.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {


    @Value("${rabbitmq.register.queue}")
    private String registerRoutingKey;

    @Value("${rabbitmq.data.queue}")
    private String dataRoutingKey;

    @Bean
    public Queue registerQueue() {
        return new Queue(registerRoutingKey);
    }

    @Bean
    public Queue dataQueue() {
        return new Queue(dataRoutingKey);
    }


}
