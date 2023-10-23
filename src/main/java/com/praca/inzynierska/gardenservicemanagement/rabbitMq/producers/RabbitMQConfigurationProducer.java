package com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers;

import com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers.model.DeviceConfigurationRequest;

public interface RabbitMQConfigurationProducer {
    void createNewQueueAndSendJsonMessage(DeviceConfigurationRequest config, Long mac);
    void sendJsonMessage(DeviceConfigurationRequest config, Long mac);
}
