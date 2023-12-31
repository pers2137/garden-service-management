package com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres;

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
public class SensorWarningsOccurrencesId implements Serializable {
    private Long sensorId;
    private Long warningsOccurrenceId;
}
