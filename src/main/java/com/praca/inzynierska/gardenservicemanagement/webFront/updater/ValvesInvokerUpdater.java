package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.valves.ValvesEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.valves.ValvesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValvesInvokerUpdater implements ValvesUpdater{

    ValvesRepository valvesRepository;

    @Autowired
    public ValvesInvokerUpdater(ValvesRepository valvesRepository) {
        this.valvesRepository = valvesRepository;
    }

    @Override
    public void saveValves(List<ValvesEntity> valvesList) {
        valvesRepository.saveAll(valvesList);
    }
}
