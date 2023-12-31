package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences.WarningsOccurrencesEntity;

import java.util.List;

public interface WarningsOccurrencesUpdater {

    List<WarningsOccurrencesEntity> saveAll(List<WarningsOccurrencesEntity> entityList);
}
