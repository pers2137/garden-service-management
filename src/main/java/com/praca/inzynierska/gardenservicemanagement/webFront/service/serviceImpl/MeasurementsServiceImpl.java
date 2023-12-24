package com.praca.inzynierska.gardenservicemanagement.webFront.service.serviceImpl;

import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementType;
import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.SensorType;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.model.Sensor;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.measurements.*;
import com.praca.inzynierska.gardenservicemanagement.webFront.errorHandler.exception.ResponseException;
import com.praca.inzynierska.gardenservicemanagement.webFront.errorHandler.exception.ResponseStatus;
import com.praca.inzynierska.gardenservicemanagement.webFront.provider.MeasurementsProvider;
import com.praca.inzynierska.gardenservicemanagement.webFront.provider.SensorProvider;
import com.praca.inzynierska.gardenservicemanagement.webFront.provider.StationProvider;
import com.praca.inzynierska.gardenservicemanagement.webFront.service.MeasurementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasurementsServiceImpl implements MeasurementsService {

    StationProvider stationProvider;
    SensorProvider sensorProvider;
    MeasurementsProvider measurementsProvider;

    @Autowired
    public MeasurementsServiceImpl(StationProvider stationProvider, SensorProvider sensorProvider, MeasurementsProvider measurementsProvider) {
        this.stationProvider = stationProvider;
        this.sensorProvider = sensorProvider;
        this.measurementsProvider = measurementsProvider;
    }

    @Override
    public MeasurementsDataResponse getMeasurementsData(final Long id, final MeasurementsDataRequest request) {
        var station = stationProvider.getStationById(id)
                                     .orElseThrow(() -> new ResponseException("station.not-found", ResponseStatus.NOT_FOUND));
        var sensorList = sensorProvider.getAllForStationAndType(station.getId(), request.getSensorType());
        var measurementsList = measurementsProvider.getMeasurementsForSensors(sensorList.stream()
                                                                                        .map(Sensor::getId)
                                                                                        .collect(Collectors.toList()),
                                                                                        request.getStartDate(),
                                                                                        request.getEndDate());

        if(request.getSensorType().equals(SensorType.DHT)) {
            return makeDataForTwoChartDTH(sensorList, measurementsList);
        } else {
            return makeDataForOneChart(sensorList, measurementsList);
        }
    }

    private MeasurementsDataResponse makeDataForTwoChartDTH(List<Sensor> sensorList, List<MeasurementsEntity> measurementsList) {
        return MeasurementsDataResponse.builder()
                                       .chartDataA(makeChartData(sensorList, measurementsList.stream()
                                                                                             .filter(it -> it.getType().equals(MeasurementType.AIR_TEMP))
                                                                                             .collect(Collectors.toList())))
                                       .chartDataB(makeChartData(sensorList, measurementsList.stream()
                                                                                             .filter(it -> it.getType().equals(MeasurementType.AIR_HUMIDITY))
                                                                                             .collect(Collectors.toList())))
                                       .build();
    }

    private MeasurementsDataResponse makeDataForOneChart(final List<Sensor> sensorList, final List<MeasurementsEntity> measurementsList) {
        return MeasurementsDataResponse.builder()
                                       .chartDataA(makeChartData(sensorList, measurementsList))
                                       .chartDataB(null)
                                       .build();
    }

    private ChartData makeChartData(final List<Sensor> sensorList, final List<MeasurementsEntity> measurementsList) {
        return ChartData.builder()
                        .measurementType(measurementsList.size() > 0 ? measurementsList.get(0).getType() : null)
                        .sensorData(sensorList.stream().map(sensor -> toSensorData(sensor, measurementsList)).toList())
                        .build();
    }

    private SensorData toSensorData(final Sensor sensor, final List<MeasurementsEntity> measurementsList) {
        return SensorData.builder()
                         .sensorId(sensor.getAddress().toString())
                         .measurementsForSensorList(measurementsList.stream()
                                                                    .filter(it -> it.getSensorId().equals(sensor.getId()))
                                                                    .map(it -> new MeasurementsForSensor(it.getValue(), it.getTimestamp()))
                                                                    .toList()
                         )
                         .build();
    }
}
