package com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StationListResponse {
    private List<StationListElement> stationListElement;
}
