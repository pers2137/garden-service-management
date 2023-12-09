package com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.measurements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class SensorData {
    String sensorId;
    List<MeasurementsForSensor> measurementsForSensorList;
}
