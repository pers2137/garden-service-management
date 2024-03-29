package com.praca.inzynierska.gardenservicemanagement.webFront.controller.api.station;

import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.praca.inzynierska.gardenservicemanagement.webFront.utils.Constraint.APP_ROOT;

@Tag(name = "Station", description = "Station controller")
public interface StationApi {

    @GetMapping(value = APP_ROOT + "/station/list", produces = MediaType.APPLICATION_JSON_VALUE) //get/
    ResponseEntity<StationListResponse> getStationList();

//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "404", description = "station.not-found: Station not fond")})
//    @GetMapping(value = APP_ROOT + "/station/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    ResponseEntity<StationInformationResponse> getStation(@PathVariable Long id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "station.not-found: Station not fond")})
    @GetMapping(value = APP_ROOT + "/station/{id}/settings", produces = MediaType.APPLICATION_JSON_VALUE) //get/
    ResponseEntity<StationSettingsResponse> getStationSettings(@PathVariable Long id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "station.not-found: Station not fond")})
    @PostMapping(value = APP_ROOT + "/station/{id}/settings/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StationSettingsResponse> saveStationSettings(@PathVariable Long id, @RequestBody SaveSettingsRequest request);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "station.not-found: Station not fond")}) //get/
    @GetMapping(value = APP_ROOT + "/station/{id}/details/information", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StationDetailsInformationResponse> getStationInformationDetails(@PathVariable Long id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "station.not-found: Station not fond")}) //get/
    @GetMapping(value = APP_ROOT + "/station/{id}/sensor/list", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StationSensorListResponse> getStationSensorList(@PathVariable Long id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "station.not-found: Station not fond")}) //get/
    @GetMapping(value = APP_ROOT + "/station/{id}/basic/information", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StationDetailsInformationResponse> getStationBasicInformation(@PathVariable Long id);

}
