package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.services;

import com.praca.inzynierska.gardenservicemanagement.datastore.stations.StationsEntity;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.model.RabbitMQRegisterRequest;

public interface RabbitMQRegisterProcessor {
    boolean registerStation(RabbitMQRegisterRequest request);
}
