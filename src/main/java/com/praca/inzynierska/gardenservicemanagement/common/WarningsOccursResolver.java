package com.praca.inzynierska.gardenservicemanagement.common;

import com.praca.inzynierska.gardenservicemanagement.common.model.WarningAndErrorSensors;
import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarnings.SensorHasWarningsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.warnings.model.Warning;
import com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences.WarningsOccurrencesEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class WarningsOccursResolver {

    public static List<WarningAndErrorSensors> resolveWarningOccur(List<Warning> warningsList,
                                                                   List<SensorHasWarningsEntity> sensorHasWarningList,
                                                                   List<MeasurementsEntity> measurementToSaveFiltered) {

        return warningsList.stream().map(it -> resolveWarning(it, sensorHasWarningList, measurementToSaveFiltered))
                                    .filter(Objects::nonNull)
                                    .toList();
    }

    private static WarningAndErrorSensors resolveWarning(Warning warning,
                                                         List<SensorHasWarningsEntity> sensorHasWarningList,
                                                         List<MeasurementsEntity> measurementList) {

        var belongSensorIds = sensorHasWarningList.stream().filter(it -> it.getId().getWarningsId().equals(warning.getId()))
                                                           .map(it-> it.getId().getSensorId())
                                                           .collect(Collectors.toSet());


        var measurementToCheck = measurementList.stream().filter(it -> belongSensorIds.contains(it.getSensorId()))
                                                         .filter(it -> it.getType().equals(warning.getMeasurementType()))
                                                         .toList();

        if(measurementToCheck.isEmpty()) return null;

        WarningAndErrorSensors warningsObject = null;
        switch (warning.getCriterion()) {
            case MIN, MAX ->  warningsObject = checkForMinOrMax(warning, measurementToCheck);
            case AVG ->  warningsObject = checkForAvg(warning, measurementToCheck);
        }

        return warningsObject;


    }

    private static WarningAndErrorSensors checkForMinOrMax(Warning warning, List<MeasurementsEntity> measurementToCheck) {
        var measurementsNotMeetingCondition = measurementToCheck.stream().filter(it -> checkCondition(it, warning)).toList();
        if(measurementsNotMeetingCondition.isEmpty()) return null;
        log.info("Warnings {} occur {}", warning.getId(), warning.getCriterion());
        return WarningAndErrorSensors.builder()
                                     .warningsOccurrencesEntity(WarningsOccurrencesEntity.builder()
                                                                                          .id(null)
                                                                                          .warningsId(warning.getId())
                                                                                          .timestamp(measurementsNotMeetingCondition.stream()
                                                                                                                                    .findFirst()
                                                                                                                                    .get()
                                                                                                                                    .getTimestamp())
                                                                                          .build())
                                     .sensorLink(measurementsNotMeetingCondition.stream().map(MeasurementsEntity::getSensorId).toList())
                                     .build();
    }

    private static boolean checkCondition(MeasurementsEntity measurement, Warning warning) {
        if(warning.getBelowThreshold()) {
            return measurement.getValue() < warning.getThreshold();
        } else {
            return measurement.getValue() > warning.getThreshold();
        }
    }

    private static WarningAndErrorSensors checkForAvg(Warning warning, List<MeasurementsEntity> measurementToCheck) {
        var countedAverage = measurementToCheck.stream().map(MeasurementsEntity::getValue).reduce(0D, Double::sum);
        var checkFlag = checkCondition(countedAverage/measurementToCheck.size(), warning);
        if(!checkFlag) return null;
        log.info("Warnings {} occur {}", warning.getId(), warning.getCriterion());
        return WarningAndErrorSensors.builder()
                                     .warningsOccurrencesEntity(WarningsOccurrencesEntity.builder()
                                                                                         .id(null)
                                                                                         .warningsId(warning.getId())
                                                                                         .timestamp(measurementToCheck.stream()
                                                                                                                      .findFirst()
                                                                                                                      .get()
                                                                                                                      .getTimestamp())
                                                                                        .build())
                                     .sensorLink(measurementToCheck.stream().map(MeasurementsEntity::getSensorId).toList())
                                     .build();

    }

    private static boolean checkCondition(Double countedAverage, Warning warning) {
        if(warning.getBelowThreshold()) {
            return countedAverage < warning.getThreshold();
        } else {
            return countedAverage > warning.getThreshold();
        }
    }

}
