package com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorHasWarningsOccurrenceRepository extends JpaRepository<SensorHasWarningsOccurrenceEntity, SensorWarningsOccurrencesId> {
}
