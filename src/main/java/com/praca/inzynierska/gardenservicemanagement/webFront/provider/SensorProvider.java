package com.praca.inzynierska.gardenservicemanagement.webFront.provider;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.SensorType;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.model.Sensor;

import java.util.List;

public interface SensorProvider {

    List<Sensor> getAllSensorsForStation(Long stationId);
    List<Sensor> getAllForStationAndType(Long stationId, SensorType sensorType);
    List<Sensor> getAllForStationAndTypesAndAddress(Long stationId, List<SensorType> sensorTypes, List<Long> address);
    List<Sensor> getSensorsByIds(List<Long> ids);

}
