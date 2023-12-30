package com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StationSensorListResponse {
    List<String> shList;

    @JsonProperty("sList")
    List<String> sList;
    List<String> dhtList;
    List<String> dsList;
}
