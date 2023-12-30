package com.praca.inzynierska.gardenservicemanagement.datastore.warnings.model;

import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementType;
import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.Criterion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Warning {

    private Long id;
    private String name;
    private Long threshold;
    private Criterion criterion;
    private MeasurementType measurementType;
    private Boolean belowThreshold;

}
