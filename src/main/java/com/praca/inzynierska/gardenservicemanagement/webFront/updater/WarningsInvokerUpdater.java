package com.praca.inzynierska.gardenservicemanagement.webFront.updater;

import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.WarningsRepository;
import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.mapper.WarningsMapper;
import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.model.Warning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarningsInvokerUpdater implements WarningsUpdater{

    @Autowired
    WarningsRepository warningsRepository;

    @Override
    public Warning addNewWarnings(Warning warning) {
        var savedWarnings =  warningsRepository.save(WarningsMapper.toWarningsEntity(warning));
        return WarningsMapper.toWarnings(savedWarnings);
    }
}
