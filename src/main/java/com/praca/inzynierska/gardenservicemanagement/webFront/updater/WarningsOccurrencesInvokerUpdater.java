package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences.WarningsOccurrencesEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences.WarningsOccurrencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarningsOccurrencesInvokerUpdater implements WarningsOccurrencesUpdater {

    WarningsOccurrencesRepository warningsOccurrencesRepository;

    @Autowired
    public WarningsOccurrencesInvokerUpdater(WarningsOccurrencesRepository warningsOccurrencesRepository) {
        this.warningsOccurrencesRepository = warningsOccurrencesRepository;
    }

    @Override
    public List<WarningsOccurrencesEntity> saveAll(List<WarningsOccurrencesEntity> entityList) {
        return warningsOccurrencesRepository.saveAll(entityList);
    }
}
