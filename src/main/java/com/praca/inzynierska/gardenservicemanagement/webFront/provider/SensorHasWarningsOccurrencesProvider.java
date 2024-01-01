package com.praca.inzynierska.gardenservicemanagement.webFront.provider;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres.SensorHasWarningsOccurrenceEntity;

import java.util.List;

public interface SensorHasWarningsOccurrencesProvider {

    List<SensorHasWarningsOccurrenceEntity> getAllByWarningsOccurrencesIds(List<Long> warningOccurrencesIds);
}
