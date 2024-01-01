package com.praca.inzynierska.gardenservicemanagement.webFront.provider;

import com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences.WarningsOccurrencesEntity;

import java.util.List;

public interface WarningsOccurrencesProvider {

    List<WarningsOccurrencesEntity> getLastTenMeasurementsForWarning(Long id);

    List<WarningsOccurrencesEntity> getAllByWarningIdAndPage(Long id, Integer page);

    Long countAllByWarning(Long id);
}
