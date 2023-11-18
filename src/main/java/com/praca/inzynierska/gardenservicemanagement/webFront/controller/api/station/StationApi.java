package com.praca.inzynierska.gardenservicemanagement.webFront.controller.api.station;

import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.StationInformationResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.StationListResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.praca.inzynierska.gardenservicemanagement.webFront.utils.Constraint.APP_ROOT;

public interface StationApi {

    @GetMapping(value = APP_ROOT + "/station/get/list", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StationListResponse> getStationList();

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "station.not-found: Station not find")})
    @GetMapping(value = APP_ROOT + "/station/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StationInformationResponse> getStation(@PathVariable Long id);
}