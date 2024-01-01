package com.praca.inzynierska.gardenservicemanagement.webFront.provider;

import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.model.Warning;

import java.util.List;
import java.util.Optional;

public interface WarningsProvider {

    List<Warning> getAllWarningsForIds(List<Long> warningsIds);

    Optional<Warning> getWarningById(Long id);

}
