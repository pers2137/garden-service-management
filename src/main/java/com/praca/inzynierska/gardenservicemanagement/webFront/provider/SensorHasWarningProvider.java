package com.praca.inzynierska.gardenservicemanagement.webFront.provider;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings.SensorHasWarningsEntity;

import java.util.List;

public interface SensorHasWarningProvider {

    List<SensorHasWarningsEntity> getAllForSensors(List<Long> sensorIds);

}
