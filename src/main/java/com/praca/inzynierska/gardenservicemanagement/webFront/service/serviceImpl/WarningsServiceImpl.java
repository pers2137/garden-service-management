package com.praca.inzynierska.gardenservicemanagement.webFront.service.serviceImpl;

import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementType;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings.SensorHasWarningsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings.SensorWarningsId;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.SensorType;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.model.Sensor;
import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.model.Warning;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings.StationWarningsListResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings.WarningResponse;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings.WarningsAddRequest;
import com.praca.inzynierska.gardenservicemanagement.webFront.errorHandler.exception.ResponseException;
import com.praca.inzynierska.gardenservicemanagement.webFront.errorHandler.exception.ResponseStatus;
import com.praca.inzynierska.gardenservicemanagement.webFront.provider.SensorHasWarningProvider;
import com.praca.inzynierska.gardenservicemanagement.webFront.provider.SensorProvider;
import com.praca.inzynierska.gardenservicemanagement.webFront.provider.StationProvider;
import com.praca.inzynierska.gardenservicemanagement.webFront.provider.WarningsProvider;
import com.praca.inzynierska.gardenservicemanagement.webFront.service.WarningsService;
import com.praca.inzynierska.gardenservicemanagement.webFront.updater.SensorHasWarningsUpdater;
import com.praca.inzynierska.gardenservicemanagement.webFront.updater.WarningsUpdater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WarningsServiceImpl implements WarningsService {


    private final StationProvider stationProvider;
    private final WarningsUpdater warningsUpdater;
    private final SensorProvider sensorProvider;
    private final SensorHasWarningsUpdater sensorHasWarningsUpdater;
    private final SensorHasWarningProvider sensorHasWarningProvider;
    private final WarningsProvider warningsProvider;

    @Autowired
    public WarningsServiceImpl(
            StationProvider stationProvider,
            WarningsUpdater warningsUpdater,
            WarningsProvider warningsProvider,
            SensorProvider sensorProvider,
            SensorHasWarningsUpdater sensorHasWarningsUpdater,
            SensorHasWarningProvider sensorHasWarningProvider
    ) {
        this.stationProvider = stationProvider;
        this.warningsUpdater = warningsUpdater;
        this.sensorProvider = sensorProvider;
        this.sensorHasWarningsUpdater = sensorHasWarningsUpdater;
        this.sensorHasWarningProvider = sensorHasWarningProvider;
        this.warningsProvider = warningsProvider;
    }

    @Override
    @Transactional
    public void addNewWarnings(final WarningsAddRequest request) {
        log.info(request.toString());
        var station = stationProvider.getStationById(request.getStationId())
                                     .orElseThrow(() -> new ResponseException("station.not-found", ResponseStatus.NOT_FOUND));

        var sensorList = sensorProvider.getAllForStationAndTypesAndAddress(request.getStationId(),
                                                                           resolveTypeList(request.getMeasurementType()),
                                                                           request.getSensorAddress());

        var sensorToLink = resolveSensorToLink(request.getSensorAddress(), sensorList);

        var warning = Warning.builder()
                               .name(request.getName())
                               .belowThreshold(request.getBelowThreshold())
                               .measurementType(request.getMeasurementType())
                               .criterion(request.getCriterion())
                               .threshold(request.getThresholdValue())
                               .build();

        var savedWarnings = warningsUpdater.addNewWarnings(warning);

        var sensorHasWarningsList = createSensorHasWarningList(sensorToLink, savedWarnings);
        sensorHasWarningsUpdater.addNewSensorHasWarnings(sensorHasWarningsList);
    }

    @Override
    public StationWarningsListResponse getWarningsForStation(Long id) {

        var sensorList = sensorProvider.getAllSensorsForStation(id);
        var sensorHasWarningList = sensorHasWarningProvider.getAllForSensors(sensorList.stream().map(Sensor::getId).toList());

        var warningIds = sensorHasWarningList.stream().map(it -> it.getId().getWarningsId()).collect(Collectors.toSet());
        var warningList = warningsProvider.getAllWarningsForIds(warningIds.stream().toList());

        return toStationWarningsListResponse(warningList, sensorHasWarningList, sensorList);
    }

    private StationWarningsListResponse toStationWarningsListResponse(List<Warning> warningList, List<SensorHasWarningsEntity> sensorHasWarningList, List<Sensor> sensorList) {
        var warningResponseList = warningList.stream().map(it -> WarningResponse.builder()
                                                                                .name(it.getName())
                                                                                .criterion(it.getCriterion())
                                                                                .measurementType(it.getMeasurementType())
                                                                                .thresholdValue(it.getThreshold())
                                                                                .belowThreshold(it.getBelowThreshold())
                                                                                .warningsOccuresList(List.of())
                                                                                .sensorAddressList(resolveSensorLinkToWarnings(it.getId(), sensorHasWarningList, sensorList))
                                                                                .build()).toList();

        return StationWarningsListResponse.builder().warningResponseList(warningResponseList).build();
    }

    private List<Long> resolveSensorLinkToWarnings(Long id, List<SensorHasWarningsEntity> sensorHasWarningList, List<Sensor> sensorList) {
        return sensorHasWarningList.stream().filter(it -> it.getId().getWarningsId().equals(id))
                                            .map(it -> it.getId().getSensorId())
                                            .map(it -> sensorList.stream().filter(el -> el.getId().equals(it)).findFirst())
                                            .map(it -> it.get().getAddress())
                                            .toList();
    }

    private List<SensorHasWarningsEntity> createSensorHasWarningList(List<Sensor> sensorToLink, Warning savedWarnings) {
        return sensorToLink.stream().map(it -> SensorHasWarningsEntity.builder().id(SensorWarningsId.builder()
                                                                                                    .warningsId(savedWarnings.getId())
                                                                                                    .sensorId(it.getId())
                                                                                                    .build()).build()).toList();
    }

    private List<Sensor> resolveSensorToLink(List<Long> sensorAddress, List<Sensor> sensorList) {
        var sensorToLink = sensorList.stream().filter(it -> sensorAddress.contains(it.getAddress())).toList();
        if(sensorToLink.size() == sensorAddress.size()) return sensorToLink;
        else throw new ResponseException("warnings.sensor-not-exist", ResponseStatus.BAD_REQUEST);
    }

    private List<SensorType> resolveTypeList(MeasurementType measurementType) {
        if(measurementType.equals(MeasurementType.AIR_TEMP)) return List.of(SensorType.DS, SensorType.DHT);
        else if(measurementType.equals(MeasurementType.INSOLATION)) return List.of(SensorType.S);
        else if(measurementType.equals(MeasurementType.SOIL_HUMIDITY)) return List.of(SensorType.SH);
        else return List.of(SensorType.DHT); //MeasurementType.AIR_HUMIDITY
    }

}
