package com.praca.inzynierska.gardenservicemanagement.webFront.provider;

import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.model.Warning;

import java.util.List;

public interface WarningsProvider {

    List<Warning> getAllWarningsForIds(List<Long> warningsIds);

}
