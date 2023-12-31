package com.praca.inzynierska.gardenservicemanagement.webFront.controller.api.warnings;

import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings.StationWarningsListResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings.WarningsAddRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.praca.inzynierska.gardenservicemanagement.webFront.utils.Constraint.APP_ROOT;

@Tag(name = "Warnings", description = "Warnings controller")
public interface WarningsApi {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "station.not-found: Station not fond")})
    @PostMapping(value = APP_ROOT + "/warnings/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    void saveNewWarnings(@RequestBody WarningsAddRequest request);

    @DeleteMapping(value = APP_ROOT + "/warnings/delete/{id}")
    void deleteWarning(@PathVariable Long id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "station.not-found: Station not fond")})
    @GetMapping(value = APP_ROOT + "/warnings/list/station/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StationWarningsListResponse> getWarningsForStations(@PathVariable Long id);

}
