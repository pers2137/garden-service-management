package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.SensorsRepository;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.mapper.SensorMapper;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorInvokerUpdater implements SensorUpdater{

    SensorsRepository sensorsRepository;

    @Autowired
    public SensorInvokerUpdater(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Override
    public Sensor saveNewSensor(Sensor sensor) {
        var savedSensor = sensorsRepository.save(SensorMapper.toSensorEntity(sensor));
        return SensorMapper.toSensor(savedSensor);
    }
}
