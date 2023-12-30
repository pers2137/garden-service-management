package com.praca.inzynierska.gardenservicemanagement.datastore.warnings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarningsRepository extends JpaRepository<WarningEntity, Long> {
    List<WarningEntity> findByIdIn(List<Long> warningsIds);
}
