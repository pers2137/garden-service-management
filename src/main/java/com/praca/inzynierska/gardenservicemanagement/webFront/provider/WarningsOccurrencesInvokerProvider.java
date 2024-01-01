package com.praca.inzynierska.gardenservicemanagement.webFront.provider;

import com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences.WarningsOccurrencesEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences.WarningsOccurrencesRepository;
import com.praca.inzynierska.gardenservicemanagement.webFront.utils.Constraint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WarningsOccurrencesInvokerProvider implements WarningsOccurrencesProvider {

    WarningsOccurrencesRepository warningsOccurrencesRepository;

    @Autowired
    public WarningsOccurrencesInvokerProvider(WarningsOccurrencesRepository warningsOccurrencesRepository) {
        this.warningsOccurrencesRepository = warningsOccurrencesRepository;
    }


    @Override
    public List<WarningsOccurrencesEntity> getLastTenMeasurementsForWarning(Long id) {
        return warningsOccurrencesRepository.getLastTenMeasurementsForWarning(id);
    }

    @Override
    public List<WarningsOccurrencesEntity> getAllByWarningIdAndPage(Long id, Integer page) {
        return warningsOccurrencesRepository.getAllMeasurementsForWarning(id, PageRequest.of(page, Constraint.WARNINGS_OCCURRENCES_PAGE_SIZE));
    }

    @Override
    public Long countAllByWarning(Long id) {
        return warningsOccurrencesRepository.countByWarningsId(id);
    }
}
