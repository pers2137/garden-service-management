package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings.SensorHasWarningsEntity;

import java.util.List;

public interface SensorHasWarningsUpdater {

    void addNewSensorHasWarnings(List<SensorHasWarningsEntity> sensorWarningsList);

    void deleteByWarningId(Long id);
}
