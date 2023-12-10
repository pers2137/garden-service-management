package com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.register.services;

import com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.register.model.MosquittoRegisterRequest;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.DeviceConfigurationRequest;

public interface MosquittoRegisterProcessor {
    DeviceConfigurationRequest registerStation(MosquittoRegisterRequest request);
}
