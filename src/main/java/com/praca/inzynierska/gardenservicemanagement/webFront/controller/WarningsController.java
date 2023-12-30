package com.praca.inzynierska.gardenservicemanagement.webFront.controller;

import com.praca.inzynierska.gardenservicemanagement.webFront.controller.api.warnings.WarningsApi;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings.StationWarningsListResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings.WarningsAddRequest;
import com.praca.inzynierska.gardenservicemanagement.webFront.service.WarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WarningsController implements WarningsApi {

    @Autowired
    private WarningsService warningsService;

    @Override
    public void saveNewWarnings(WarningsAddRequest request) {
        warningsService.addNewWarnings(request);
    }

    @Override
    public ResponseEntity<StationWarningsListResponse> getWarningsForStations(Long id) {
        return new ResponseEntity<>(warningsService.getWarningsForStation(id), HttpStatus.OK);
    }
}
