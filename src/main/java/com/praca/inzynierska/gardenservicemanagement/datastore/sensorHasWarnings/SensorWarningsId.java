package com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorWarningsId implements Serializable {

    private Long sensorId;
    private Long warningsId;

}
