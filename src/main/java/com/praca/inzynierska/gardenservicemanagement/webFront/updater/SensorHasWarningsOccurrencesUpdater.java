package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres.SensorHasWarningsOccurrenceEntity;

import java.util.List;

public interface SensorHasWarningsOccurrencesUpdater {

    List<SensorHasWarningsOccurrenceEntity> saveAll(List<SensorHasWarningsOccurrenceEntity> entityList);
}
