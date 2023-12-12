package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.model.Sensor;

import java.util.List;

public interface SensorUpdater {

    Sensor saveNewSensor(Sensor sensor);

    void updateAllSensor(List<Sensor> sensorToUpdate);
}
