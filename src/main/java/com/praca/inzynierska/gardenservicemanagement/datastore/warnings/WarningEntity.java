package com.praca.inzynierska.gardenservicemanagement.datastore.warnings;

import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "WARNINGS")
public class WarningEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "THRESHOLD")
    private Long threshold;

    @Column(name = "CRITERION")
    @Enumerated(EnumType.STRING)
    private Criterion criterion;

    @Column(name = "MEASUREMENT_TYPE")
    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType;

    @Column(name = "BELOW_THRESHOLD")
    private Boolean belowThreshold;
}
