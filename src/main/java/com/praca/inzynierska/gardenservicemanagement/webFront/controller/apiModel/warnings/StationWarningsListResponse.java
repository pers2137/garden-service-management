package com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class StationWarningsListResponse {

    List<WarningResponse> warningResponseList;

}
