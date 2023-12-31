package com.praca.inzynierska.gardenservicemanagement.common.model;


import com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences.WarningsOccurrencesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarningAndErrorSensors {

    WarningsOccurrencesEntity warningsOccurrencesEntity;
    List<Long> sensorLink;

}
