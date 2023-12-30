package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings.SensorHasWarningsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings.SensorHasWarningsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorHasWarningsInvokerUpdater implements SensorHasWarningsUpdater {

    @Autowired
    private SensorHasWarningsRepository sensorHasWarningsRepository;


    @Override
    public void addNewSensorHasWarnings(List<SensorHasWarningsEntity> sensorWarningsList) {
        sensorHasWarningsRepository.saveAll(sensorWarningsList);
    }
}
