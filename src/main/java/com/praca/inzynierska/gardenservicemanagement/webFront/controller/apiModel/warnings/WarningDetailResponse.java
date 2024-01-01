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
public class WarningDetailResponse {

    String stationName;
    String normName;
    MeasurementType measurementType;
    Criterion criterion;
    Long threshold;
    Boolean belowThreshold;
    List<String> sensorAddress;
}
