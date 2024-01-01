package com.praca.inzynierska.gardenservicemanagement.webFront.provider;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres.SensorHasWarningsOccurrenceEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres.SensorHasWarningsOccurrenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SensorHasWarningsOccurrencesInvokerProvider implements SensorHasWarningsOccurrencesProvider {

    SensorHasWarningsOccurrenceRepository sensorHasWarningsOccurrenceRepository;

    @Autowired
    public SensorHasWarningsOccurrencesInvokerProvider(SensorHasWarningsOccurrenceRepository sensorHasWarningsOccurrenceRepository) {
        this.sensorHasWarningsOccurrenceRepository = sensorHasWarningsOccurrenceRepository;
    }


    @Override
    public List<SensorHasWarningsOccurrenceEntity> getAllByWarningsOccurrencesIds(List<Long> warningOccurrencesIds) {
        return sensorHasWarningsOccurrenceRepository.findByIdSensorIdIn(warningOccurrencesIds);
    }
}
