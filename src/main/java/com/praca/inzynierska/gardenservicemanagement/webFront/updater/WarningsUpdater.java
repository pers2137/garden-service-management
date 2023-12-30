package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.model.Warning;

public interface WarningsUpdater {

    Warning addNewWarnings(Warning warning);

}
