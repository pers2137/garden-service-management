package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.data.services;

import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.data.model.RabbitMQDataRequest;

public interface RabbitMQDataProcessor {
    void saveMeasurementData(RabbitMQDataRequest request);
}
