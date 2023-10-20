package com.praca.inzynierska.gardenservicemanagement.datastore.stations;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StationsRepository extends JpaRepository<StationsEntity, Long> {
    boolean existsByMacAddress(String macAddress);
}
