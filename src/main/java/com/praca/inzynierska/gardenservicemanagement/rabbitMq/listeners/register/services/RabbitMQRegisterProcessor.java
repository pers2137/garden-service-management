package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.services;

import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.model.RabbitMQRegisterRequest;

public interface RabbitMQRegisterProcessor {
    void registerStation(RabbitMQRegisterRequest request);
}
