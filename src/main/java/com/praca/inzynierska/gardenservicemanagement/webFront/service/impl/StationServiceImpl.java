package com.praca.inzynierska.gardenservicemanagement.webFront.service.impl;

import com.praca.inzynierska.gardenservicemanagement.datastore.stations.StationsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.stations.StationsRepository;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.StationInformationResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.StationListElement;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.StationListResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.errorHandler.exception.ResponseException;
import com.praca.inzynierska.gardenservicemanagement.webFront.errorHandler.exception.ResponseStatus;
import com.praca.inzynierska.gardenservicemanagement.webFront.service.StationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StationServiceImpl implements StationService {

    @Autowired
    StationsRepository stationsRepository;

    @Override
    public StationListResponse getStationList() {
        var stationList = stationsRepository.findAll().stream().map(this::toStationElement).toList();
        return StationListResponse.builder().stationListElement(stationList).build();
    }

    @Override
    public StationInformationResponse getStationInformation(Long id) {
        //TODO -> INFO TAKIE STACJA NIE ISTNIEJE + DODAĆ DO API
        var station = stationsRepository.findById(id)
                                        .orElseThrow(() -> new ResponseException("station.not-found", ResponseStatus.NOT_FOUND));

        return StationInformationResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .build();
    }

    private StationListElement toStationElement(StationsEntity stationsEntity) {
        return StationListElement.builder()
                                 .id(stationsEntity.getId())
                                 .name(stationsEntity.getName())
                                 .build();

    }
}
