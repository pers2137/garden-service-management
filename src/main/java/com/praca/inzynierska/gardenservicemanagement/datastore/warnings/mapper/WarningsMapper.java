package com.praca.inzynierska.gardenservicemanagement.datastore.warnings.mapper;

import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.WarningEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.model.Warning;

public class WarningsMapper {

    public static Warning toWarnings(WarningEntity warningEntity) {
        return Warning.builder()
                       .id(warningEntity.getId())
                       .threshold(warningEntity.getThreshold())
                       .name(warningEntity.getName())
                       .criterion(warningEntity.getCriterion())
                       .belowThreshold(warningEntity.getBelowThreshold())
                       .measurementType(warningEntity.getMeasurementType())
                       .build();
    }

    public static WarningEntity toWarningsEntity(Warning warning) {
        return WarningEntity.builder()
                             .id(warning.getId())
                             .threshold(warning.getThreshold())
                             .name(warning.getName())
                             .criterion(warning.getCriterion())
                             .belowThreshold(warning.getBelowThreshold())
                             .measurementType(warning.getMeasurementType())
                             .build();
    }

}
