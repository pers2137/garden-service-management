package com.praca.inzynierska.gardenservicemanagement.webFront.service;

import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.StationInformationResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.StationListResponse;

public interface StationService {
    StationListResponse getStationList();

    StationInformationResponse getStationInformation(Long id);
}
