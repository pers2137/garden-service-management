package com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorHasWarningsRepository extends JpaRepository<SensorHasWarningsEntity, SensorWarningsId>  {


    @Query("SELECT w FROM SensorHasWarningsEntity w " +
            "WHERE w.id.sensorId IN :ids ")
    List<SensorHasWarningsEntity> findByIdSensorIdIn(@Param("ids") List<Long> sensorIds);

    @Modifying
    @Query("DELETE FROM SensorHasWarningsEntity w WHERE w.id.warningsId=:id")
    void deleteByWarningId(@Param("id") Long id);

    @Query("SELECT w FROM SensorHasWarningsEntity w " +
            "WHERE w.id.warningsId= :warningId ")
    List<SensorHasWarningsEntity> findByIdWarningId(@Param("warningId") Long warningId);


    @Modifying
    @Query("DELETE FROM SensorHasWarningsEntity w " +
            "WHERE w.id.warningsId=:warningId " +
              "AND w.id.sensorId IN :sensorIds")
    void deleteByWarningIdAndSensorsIds(@Param("sensorIds") List<Long> sensorIds, @Param("warningId") Long warningId);
}
