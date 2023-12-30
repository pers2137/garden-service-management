package com.praca.inzynierska.gardenservicemanagement.webFront.service;

import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings.StationWarningsListResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings.WarningsAddRequest;

public interface WarningsService {

    void addNewWarnings(WarningsAddRequest request);

    StationWarningsListResponse getWarningsForStation(Long id);
}
