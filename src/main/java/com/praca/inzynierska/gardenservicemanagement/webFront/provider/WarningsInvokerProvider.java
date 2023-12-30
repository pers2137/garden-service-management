package com.praca.inzynierska.gardenservicemanagement.webFront.provider;

import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.WarningsRepository;
import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.mapper.WarningsMapper;
import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.model.Warning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WarningsInvokerProvider implements WarningsProvider{

    WarningsRepository warningsRepository;

    @Autowired
    public WarningsInvokerProvider(WarningsRepository warningsRepository) {
        this.warningsRepository = warningsRepository;
    }

    @Override
    public List<Warning> getAllWarningsForIds(List<Long> warningsIds) {
        return warningsRepository.findByIdIn(warningsIds).stream()
                                                         .map(WarningsMapper::toWarnings)
                                                         .toList();
    }
}
