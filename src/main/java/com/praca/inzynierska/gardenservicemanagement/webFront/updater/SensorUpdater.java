package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.model.Sensor;

public interface SensorUpdater {

    Sensor saveNewSensor(Sensor sensor);
}
