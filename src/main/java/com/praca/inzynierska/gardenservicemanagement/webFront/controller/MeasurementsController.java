package com.praca.inzynierska.gardenservicemanagement.webFront.controller;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.SensorType;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.api.measurements.MeasurementsApi;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.measurements.*;
import com.praca.inzynierska.gardenservicemanagement.webFront.service.MeasurementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MeasurementsController implements MeasurementsApi {

    MeasurementsService measurementsService;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;
    }

    @Override
    public ResponseEntity<MeasurementsDataResponse> getMeasurementsData(Long id, MeasurementsDataRequest request) {
        return new ResponseEntity<>(measurementsService.getMeasurementsData(id, request), HttpStatus.OK);
    }
}
