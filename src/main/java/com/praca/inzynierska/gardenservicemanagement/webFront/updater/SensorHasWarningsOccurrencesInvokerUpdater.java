package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres.SensorHasWarningsOccurrenceEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres.SensorHasWarningsOccurrenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorHasWarningsOccurrencesInvokerUpdater implements SensorHasWarningsOccurrencesUpdater {

    SensorHasWarningsOccurrenceRepository sensorHasWarningsOccurrenceRepository;

    @Autowired
    public SensorHasWarningsOccurrencesInvokerUpdater(SensorHasWarningsOccurrenceRepository sensorHasWarningsOccurrenceRepository) {
        this.sensorHasWarningsOccurrenceRepository = sensorHasWarningsOccurrenceRepository;
    }


    @Override
    public List<SensorHasWarningsOccurrenceEntity> saveAll(List<SensorHasWarningsOccurrenceEntity> entityList) {
        return sensorHasWarningsOccurrenceRepository.saveAll(entityList);
    }
}
