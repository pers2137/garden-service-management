package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.model;

import lombok.Data;

@Data
public class RabbitMQRegisterRequest {
    int ip;
    long mac;
    int sv;
}
