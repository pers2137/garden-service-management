package com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class WarningInformation {

    Long timestamp;
    List<String> sensorAddressList;
}
