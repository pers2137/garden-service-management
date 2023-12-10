package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.valves.ValvesEntity;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.Valves;

import java.util.List;

public interface ValvesUpdater {

    void saveValves(List<ValvesEntity> valvesList);

}
