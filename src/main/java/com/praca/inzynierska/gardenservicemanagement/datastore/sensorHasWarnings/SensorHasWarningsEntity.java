package com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "SENSORS_HAS_WARNINGS")

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SensorHasWarningsEntity {

    @EmbeddedId
    private SensorWarningsId id;

}
