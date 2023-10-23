package com.praca.inzynierska.gardenservicemanagement.datastore.stations;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StationsRepository extends JpaRepository<StationsEntity, Long> {
    boolean existsByMacAddress(String macAddress);
    Optional<StationsEntity> findByMacAddress(String macAddress);
}
