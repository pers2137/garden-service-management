package com.praca.inzynierska.gardenservicemanagement.webFront.controller;

import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementType;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.api.measurements.MeasurementsApi;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.measurements.*;
import com.praca.inzynierska.gardenservicemanagement.webFront.service.MeasurementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        var measurementsData = MeasurementsDataResponse.builder()
                                                       .chartDataA(chartData())
                                                       .chartDataB(null)
                                                       .build();

        return new ResponseEntity<>(measurementsData, HttpStatus.OK);
//        return new ResponseEntity<>(measurementsService.getMeasurementsData(id, request), HttpStatus.OK);

    }

    private ChartData chartData() {

        return ChartData.builder()
                        .sensorData(sensorDataList())
                        .measurementType(MeasurementType.AIR_TEMP)
                        .build();
    }

    private List<SensorData> sensorDataList() {
        return List.of(
                SensorData.builder().sensorId("0").measurementsForSensorList(List.of(
                        MeasurementsForSensor.builder().value(10.0).timestamp(1702000250000L).build(),
                        MeasurementsForSensor.builder().value(11.0).timestamp(1702011250000L).build(),
                        MeasurementsForSensor.builder().value(12.0).timestamp(1702036250000L).build(),
                        MeasurementsForSensor.builder().value(13.0).timestamp(1702056250000L).build(),
                        MeasurementsForSensor.builder().value(14.0).timestamp(1702166250000L).build(),
                        MeasurementsForSensor.builder().value(15.0).timestamp(1702386250000L).build(),
                        MeasurementsForSensor.builder().value(16.0).timestamp(1702106250000L).build(),
                        MeasurementsForSensor.builder().value(17.0).timestamp(1702116250000L).build()
                )).build(),
                SensorData.builder().sensorId("1").measurementsForSensorList(List.of(
                        MeasurementsForSensor.builder().value(20.0).timestamp(1702000250000L).build(),
                        MeasurementsForSensor.builder().value(21.0).timestamp(1702011250000L).build(),
                        MeasurementsForSensor.builder().value(22.0).timestamp(1702036250000L).build(),
                        MeasurementsForSensor.builder().value(23.0).timestamp(1702056250000L).build(),
                        MeasurementsForSensor.builder().value(25.0).timestamp(1702086250000L).build(),
                        MeasurementsForSensor.builder().value(26.0).timestamp(1702106250000L).build(),
                        MeasurementsForSensor.builder().value(27.0).timestamp(1702116250000L).build()
                )).build(),
                SensorData.builder().sensorId("2").measurementsForSensorList(List.of(
                        MeasurementsForSensor.builder().value(-10.0).timestamp(1702000250000L).build(),
                        MeasurementsForSensor.builder().value(-11.0).timestamp(1702011250000L).build(),
                        MeasurementsForSensor.builder().value(-16.0).timestamp(1702106250000L).build(),
                        MeasurementsForSensor.builder().value(-17.0).timestamp(1702116250000L).build()
                )).build(),
                SensorData.builder().sensorId("3").measurementsForSensorList(List.of(
                        MeasurementsForSensor.builder().value(-2.0).timestamp(1702036250000L).build(),
                        MeasurementsForSensor.builder().value(-3.0).timestamp(1702056250000L).build(),
                        MeasurementsForSensor.builder().value(-4.0).timestamp(1702066250000L).build(),
                        MeasurementsForSensor.builder().value(-5.0).timestamp(1702086250000L).build()
                )).build()
//                ,
//                SensorData.builder().sensorId("4").measurementsForSensorList(null).build(),
//                SensorData.builder().sensorId("5").measurementsForSensorList(null).build(),
//                SensorData.builder().sensorId("6").measurementsForSensorList(null).build(),
//                SensorData.builder().sensorId("7").measurementsForSensorList(null).build(),
//                SensorData.builder().sensorId("8").measurementsForSensorList(null).build(),
//                SensorData.builder().sensorId("9").measurementsForSensorList(null).build(),
//                SensorData.builder().sensorId("10").measurementsForSensorList(null).build(),
//                SensorData.builder().sensorId("11").measurementsForSensorList(null).build()
        );

    }

}
