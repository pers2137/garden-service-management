package com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "SENSORS_HAS_WARNINGS_OCCURRENCES")

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SensorHasWarningsOccurrenceEntity {

    @EmbeddedId
    SensorWarningsOccurrencesId id;
}
