package com.praca.inzynierska.gardenservicemanagement.webFront.service.serviceImpl;

import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementType;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings.SensorHasWarningsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings.SensorWarningsId;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres.SensorHasWarningsOccurrenceEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.SensorType;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.model.Sensor;
import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.model.Warning;
import com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences.WarningsOccurrencesEntity;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings.*;
import com.praca.inzynierska.gardenservicemanagement.webFront.errorHandler.exception.ResponseException;
import com.praca.inzynierska.gardenservicemanagement.webFront.errorHandler.exception.ResponseStatus;
import com.praca.inzynierska.gardenservicemanagement.webFront.provider.*;
import com.praca.inzynierska.gardenservicemanagement.webFront.service.WarningsService;
import com.praca.inzynierska.gardenservicemanagement.webFront.updater.SensorHasWarningsUpdater;
import com.praca.inzynierska.gardenservicemanagement.webFront.updater.WarningsUpdater;
import com.praca.inzynierska.gardenservicemanagement.webFront.utils.Constraint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
    private final WarningsOccurrencesProvider warningsOccurrencesProvider;
    private final SensorHasWarningsOccurrencesProvider sensorHasWarningsOccurrencesProvider;

    @Autowired
    public WarningsServiceImpl(
            StationProvider stationProvider,
            WarningsUpdater warningsUpdater,
            WarningsProvider warningsProvider,
            SensorProvider sensorProvider,
            SensorHasWarningsUpdater sensorHasWarningsUpdater,
            SensorHasWarningProvider sensorHasWarningProvider,
            WarningsOccurrencesProvider warningsOccurrencesProvider,
            SensorHasWarningsOccurrencesProvider sensorHasWarningsOccurrencesProvider
    ) {
        this.stationProvider = stationProvider;
        this.warningsUpdater = warningsUpdater;
        this.sensorProvider = sensorProvider;
        this.sensorHasWarningsUpdater = sensorHasWarningsUpdater;
        this.sensorHasWarningProvider = sensorHasWarningProvider;
        this.warningsProvider = warningsProvider;
        this.warningsOccurrencesProvider = warningsOccurrencesProvider;
        this.sensorHasWarningsOccurrencesProvider = sensorHasWarningsOccurrencesProvider;
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

        var lastWarningOccurencesList = warningIds.stream().map(warningsOccurrencesProvider::getLastTenMeasurementsForWarning)
                                                           .flatMap(Collection::stream)
                                                           .toList();

        var sensorHasWarningOccurrences = sensorHasWarningsOccurrencesProvider.getAllByWarningsOccurrencesIds(lastWarningOccurencesList.stream().map(WarningsOccurrencesEntity::getId).toList());
        var sensorWhenOccurrencesWarning = sensorProvider.getSensorsByIds(sensorHasWarningOccurrences.stream().map(it->it.getId().getSensorId()).collect(Collectors.toList()));


        return toStationWarningsListResponse(warningList, sensorHasWarningList, sensorList, lastWarningOccurencesList, sensorHasWarningOccurrences, sensorWhenOccurrencesWarning);
    }


    @Override
    @Transactional
    public void deleteWarning(Long id) {
        sensorHasWarningsUpdater.deleteByWarningId(id);
        warningsUpdater.deleteWarningById(id);
        log.info("Successfully deleted warnings with id: {}", id);
    }

    @Override
    public WarningDetailResponse getWarningDetail(Long id, Long warningId) {
        var station = stationProvider.getStationById(id)
                .orElseThrow(() -> new ResponseException("station.not-found", ResponseStatus.NOT_FOUND));

        var warning = warningsProvider.getWarningById(warningId)
                .orElseThrow(() -> new ResponseException("warning.not-found", ResponseStatus.NOT_FOUND));

        var sensorHasWarning = sensorHasWarningProvider.getAllSensorIdByWarningId(warningId);
        var sensorList = sensorProvider.getSensorsByIds(sensorHasWarning.stream().map(it -> it.getId().getSensorId()).toList());

        return WarningDetailResponse.builder()
                                    .stationName(station.getName())
                                    .normName(warning.getName())
                                    .measurementType(warning.getMeasurementType())
                                    .belowThreshold(warning.getBelowThreshold())
                                    .criterion(warning.getCriterion())
                                    .sensorAddress(sensorList.stream().map(Sensor::getAddress).map(Objects::toString).toList())
                                    .threshold(warning.getThreshold())
                                    .build();
    }

    @Override
    @Transactional
    public void editWarning(WarningsEditRequest request) {
        log.info(request.toString());
        var warning = warningsProvider.getWarningById(request.getWarningId())
                                      .orElseThrow(() -> new ResponseException("warning.not-found", ResponseStatus.NOT_FOUND));

        var sensorHasWarning = sensorHasWarningProvider.getAllSensorIdByWarningId(warning.getId());
        var sensorList = sensorProvider.getSensorsByIds(sensorHasWarning.stream().map(it -> it.getId().getSensorId()).toList());
        var sensorListAddress = sensorList.stream().map(Sensor::getAddress).toList();

        var deletedSensor = sensorList.stream().filter(it -> !request.getSensorAddress().contains(it.getAddress())).toList();
        var sensorToAddAddress = request.getSensorAddress().stream().filter(it -> !sensorListAddress.contains(it)).toList();

        var sensorHasWarningToAdd = sensorProvider.getAllForStationAndTypesAndAddress(request.getStationId(),
                                                                                      resolveTypeList(request.getMeasurementType()),
                                                                                      sensorToAddAddress);


        warning.setName(request.getName());
        warning.setCriterion(request.getCriterion());
        warning.setThreshold(request.getThresholdValue());
        warning.setBelowThreshold(request.getBelowThreshold());

        sensorHasWarningsUpdater.deleteByWarningIdAndSensorsIds(deletedSensor.stream().map(Sensor::getId).toList(), request.getWarningId());
        sensorHasWarningsUpdater.addNewSensorHasWarnings(sensorHasWarningToAdd.stream()
                                                                              .map(it -> SensorHasWarningsEntity.builder().id(SensorWarningsId.builder()
                                                                                                                                              .warningsId(request.getWarningId())
                                                                                                                                              .sensorId(it.getId())
                                                                                                                                              .build()).build()).toList());
        warningsUpdater.updateWarnings(warning);
    }

    @Override
    public WarningsPageResponse getWarningsOccurrencesListById(Long warningId, Integer page) {

        var warning = warningsProvider.getWarningById(warningId)
                                      .orElseThrow(() -> new ResponseException("warning.not-found", ResponseStatus.NOT_FOUND));

        var occurredWarningList = warningsOccurrencesProvider.getAllByWarningIdAndPage(warning.getId(), page);
        var occurredWarningCount = warningsOccurrencesProvider.countAllByWarning(warning.getId());

        var sensorHasWarningOccurrences = sensorHasWarningsOccurrencesProvider.getAllByWarningsOccurrencesIds(occurredWarningList.stream().map(WarningsOccurrencesEntity::getId).toList());
        var sensorWhenOccurrencesWarning = sensorProvider.getSensorsByIds(sensorHasWarningOccurrences.stream().map(it->it.getId().getSensorId()).collect(Collectors.toList()));

        return WarningsPageResponse.builder()
                                   .recordCount(occurredWarningCount)
                                   .warningInformationList(prepareWarningInformationList(warning.getId(), occurredWarningList, sensorHasWarningOccurrences, sensorWhenOccurrencesWarning))
                                   .pageCount((long)Math.ceil((double) occurredWarningCount.longValue() / Constraint.WARNINGS_OCCURRENCES_PAGE_SIZE))
                                   .build();
    }

    private StationWarningsListResponse toStationWarningsListResponse(List<Warning> warningList,
                                                                      List<SensorHasWarningsEntity> sensorHasWarningList,
                                                                      List<Sensor> sensorList,
                                                                      List<WarningsOccurrencesEntity> lastWarningOccurencesList,
                                                                      List<SensorHasWarningsOccurrenceEntity> sensorHasWarningOccurrences,
                                                                      List<Sensor> sensorWhenOccurrencesWarning) {

        var warningResponseList = warningList.stream().map(it -> WarningResponse.builder()
                                                                                .warningId(it.getId())
                                                                                .name(it.getName())
                                                                                .criterion(it.getCriterion())
                                                                                .measurementType(it.getMeasurementType())
                                                                                .thresholdValue(it.getThreshold())
                                                                                .belowThreshold(it.getBelowThreshold())
                                                                                .warningInformationList(prepareWarningInformationList(it.getId(),
                                                                                                                                      lastWarningOccurencesList,
                                                                                                                                      sensorHasWarningOccurrences,
                                                                                                                                      sensorWhenOccurrencesWarning))
                                                                                .sensorAddressList(resolveSensorLinkToWarnings(it.getId(), sensorHasWarningList, sensorList))
                                                                                .build()).toList();

        return StationWarningsListResponse.builder().warningResponseList(warningResponseList).build();
    }

    private List<WarningInformation> prepareWarningInformationList(Long id, List<WarningsOccurrencesEntity> lastWarningOccurencesList, List<SensorHasWarningsOccurrenceEntity> sensorHasWarningOccurrences, List<Sensor> sensorWhenOccurrencesWarning) {
        return lastWarningOccurencesList.stream()
                .filter(it->it.getWarningsId().equals(id))
                .map(it -> WarningInformation.builder()
                                             .timestamp(it.getTimestamp())
                                             .sensorAddressList(toSensorListForWarningsOccurrenc(it.getId(), sensorHasWarningOccurrences, sensorWhenOccurrencesWarning))
                                             .build()).toList();
    }

    private List<String> toSensorListForWarningsOccurrenc(Long id, List<SensorHasWarningsOccurrenceEntity> sensorHasWarningOccurrences, List<Sensor> sensorList) {
        var sensorHasWarningList = sensorHasWarningOccurrences.stream().filter(it -> it.getId().getWarningsOccurrenceId().equals(id)).toList();
        return sensorHasWarningList.stream()
                                   .map(it -> sensorList.stream().filter(el -> el.getId().equals(it.getId().getSensorId()))
                                                                 .findFirst().get())
                                   .map(it -> it.getAddress().toString()).toList();

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
