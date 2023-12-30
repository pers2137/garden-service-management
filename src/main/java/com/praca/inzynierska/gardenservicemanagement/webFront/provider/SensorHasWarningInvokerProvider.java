package com.praca.inzynierska.gardenservicemanagement.webFront.provider;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings.SensorHasWarningsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings.SensorHasWarningsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SensorHasWarningInvokerProvider implements SensorHasWarningProvider{


    private final SensorHasWarningsRepository sensorHasWarningsRepository;

    @Autowired
    public SensorHasWarningInvokerProvider(SensorHasWarningsRepository sensorHasWarningsRepository) {
        this.sensorHasWarningsRepository = sensorHasWarningsRepository;
    }

    @Override
    public List<SensorHasWarningsEntity> getAllForSensors(List<Long> sensorIds) {
        return sensorHasWarningsRepository.findByIdSensorIdIn(sensorIds);
    }
}
