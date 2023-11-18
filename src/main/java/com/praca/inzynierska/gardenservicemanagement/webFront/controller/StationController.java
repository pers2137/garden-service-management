package com.praca.inzynierska.gardenservicemanagement.webFront.controller;

import com.praca.inzynierska.gardenservicemanagement.webFront.controller.api.station.StationApi;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.StationInformationResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.StationListResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class StationController implements StationApi {

    @Autowired
    StationService stationService;

    @Override
    public ResponseEntity<StationListResponse> getStationList() {
        return new ResponseEntity<>(stationService.getStationList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StationInformationResponse> getStation(Long id) {
        return new ResponseEntity<>(stationService.getStationInformation(id), HttpStatus.OK);
    }

}