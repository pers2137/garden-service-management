package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.data.services;

import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.data.model.RabbitMQDataRequest;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQDataProcessorImpl implements RabbitMQDataProcessor{


    @Override
    public void saveMeasurementData(RabbitMQDataRequest request) {

    }


}
