package com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorHasWarningsOccurrenceRepository extends JpaRepository<SensorHasWarningsOccurrenceEntity, SensorWarningsOccurrencesId> {

    @Query("SELECT w FROM SensorHasWarningsOccurrenceEntity w " +
            "WHERE w.id.warningsOccurrenceId IN :ids ")
    List<SensorHasWarningsOccurrenceEntity> findByIdSensorIdIn(@Param("ids") List<Long> sensorIds);

}
