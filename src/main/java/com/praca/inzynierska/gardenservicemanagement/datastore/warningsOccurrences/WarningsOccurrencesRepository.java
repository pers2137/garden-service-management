package com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarningsOccurrencesRepository extends JpaRepository<WarningsOccurrencesEntity, Long> {

    @Query("SELECT w FROM WarningsOccurrencesEntity w " +
            "WHERE w.warningsId=:id " +
            "ORDER BY w.timestamp DESC LIMIT 5") // LIMIT 10 ORDER BY warnings_id ASC LIMIT 10
    List<WarningsOccurrencesEntity> getLastTenMeasurementsForWarning(@Param("id") Long id);

    @Query("SELECT w FROM WarningsOccurrencesEntity w " +
            "WHERE w.warningsId=:id " +
            "ORDER BY w.timestamp DESC") // LIMIT 10 ORDER BY warnings_id ASC LIMIT 10
    List<WarningsOccurrencesEntity> getAllMeasurementsForWarning(@Param("id") Long id, Pageable pageable);

    Long countByWarningsId(Long id);
}
