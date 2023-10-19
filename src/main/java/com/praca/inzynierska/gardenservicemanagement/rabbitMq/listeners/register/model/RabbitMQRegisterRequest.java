package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.model;

import lombok.Data;

@Data
public class RabbitMQRegisterRequest {
    int ip;
    int mac;
    int sv;
}
