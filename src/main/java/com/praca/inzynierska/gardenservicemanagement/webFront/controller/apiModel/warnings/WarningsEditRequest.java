package com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings;

import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementType;
import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.Criterion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class WarningsEditRequest {
    Long stationId;
    Long warningId;
    String name;
    Criterion criterion;
    MeasurementType measurementType;
    Long thresholdValue;
    Boolean belowThreshold;
    List<Long> sensorAddress;
}
