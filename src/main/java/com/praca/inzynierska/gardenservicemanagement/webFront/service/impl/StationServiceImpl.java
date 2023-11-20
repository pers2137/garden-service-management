package com.praca.inzynierska.gardenservicemanagement.webFront.service.impl;

import com.praca.inzynierska.gardenservicemanagement.datastore.stations.StationsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.stations.StationsRepository;
import com.praca.inzynierska.gardenservicemanagement.datastore.valves.ValvesEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.valves.ValvesRepository;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.*;
import com.praca.inzynierska.gardenservicemanagement.webFront.errorHandler.exception.ResponseException;
import com.praca.inzynierska.gardenservicemanagement.webFront.errorHandler.exception.ResponseStatus;
import com.praca.inzynierska.gardenservicemanagement.webFront.service.StationService;
import com.praca.inzynierska.gardenservicemanagement.webFront.utils.DefaultValves;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StationServiceImpl implements StationService {

    StationsRepository stationsRepository;
    ValvesRepository valvesRepository;

    @Autowired
    public StationServiceImpl(StationsRepository stationsRepository, ValvesRepository valvesRepository) {
        this.stationsRepository = stationsRepository;
        this.valvesRepository = valvesRepository;
    }


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

    @Override
    public StationSettingsResponse getStationSettings(Long id) {
        var station = stationsRepository.findById(id)
                .orElseThrow(() -> new ResponseException("station.not-found", ResponseStatus.NOT_FOUND));


        var valves = valvesRepository.findAllByStationId(station.getId());

        return StationSettingsResponse.builder()
                                      .id(station.getId())
                                      .name(station.getName())
                                      .macAddress(station.getMacAddress())
                                      .measurementPeriod(station.getMeasurementPeriod())
                                      .valvesList(toValvesList(valves))
                                      .build();

    }

    private List<Valves> toValvesList(List<ValvesEntity> valves) {
        var mappedValves = valves.stream().map(this::toValves).collect(Collectors.toList());
        for(int i=1;i<17;i++) {
            if(!checkValveExist(mappedValves, i)) {
                mappedValves.add(DefaultValves.defaultValvesForWWW(i));
            }
        }

        return mappedValves;
    }

    private boolean checkValveExist(List<Valves> mappedValves, int i) {
        return mappedValves.stream().anyMatch(it -> it.getPin() == i);
    }

    private Valves toValves(ValvesEntity valvesEntity) {
        return Valves.builder()
                     .pin(valvesEntity.getPin())
                     .operationMode(valvesEntity.getOperationMode())
                     .enableHigh(valvesEntity.isEnableHigh() ? 1 : 0)
                     .build();
    }

    private StationListElement toStationElement(StationsEntity stationsEntity) {
        return StationListElement.builder()
                                 .id(stationsEntity.getId())
                                 .name(stationsEntity.getName())
                                 .build();

    }
}
