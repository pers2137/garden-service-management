package com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.data.services;


import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementType;
import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.measurements.MeasurementsRepository;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.SensorType;
import com.praca.inzynierska.gardenservicemanagement.datastore.sensors.model.Sensor;
import com.praca.inzynierska.gardenservicemanagement.datastore.stations.StationsRepository;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.data.model.*;
import com.praca.inzynierska.gardenservicemanagement.webFront.provider.SensorProvider;
import com.praca.inzynierska.gardenservicemanagement.webFront.updater.SensorUpdater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Component
@Slf4j
public class MosquittoDataProcessorImpl implements MosquittoDataProcessor {

    private final MeasurementsRepository measurementsRepository;
    private final StationsRepository stationsRepository;
    private final SensorProvider sensorProvider;
    private final SensorUpdater sensorUpdater;

    @Autowired
    public MosquittoDataProcessorImpl(StationsRepository stationsRepository,
                                      MeasurementsRepository measurementsRepository,
                                      SensorProvider sensorProvider,
                                      SensorUpdater sensorUpdater) {
        this.measurementsRepository = measurementsRepository;
        this.stationsRepository = stationsRepository;
        this.sensorProvider = sensorProvider;
        this.sensorUpdater = sensorUpdater;
    }

    @Override
    @Transactional
    public void saveMeasurementData(MosquittoDataRequest request) {
        var macAddress = request.getMac();
        var station = stationsRepository.findByMacAddress(macAddress);
        if(station.isEmpty()) {
            log.error("Station {} doest not exist!", macAddress);
            return;
        }
        var stationId = station.get().getId();

        var sensorList = sensorProvider.getAllSensorsForStation(stationId);
        var measurementToSave = new ArrayList<MeasurementsEntity>();

        if(request.getAnalog() != null) {
            AtomicLong counter = new AtomicLong(0);
            Arrays.stream(request.getAnalog())
                                 .forEach(it -> measurementToSave.add(analogMeasurementToEntity(it,
                                                                                                request.getTimestamp(),
                                                                                                counter.getAndIncrement(),
                                                                                                sensorList,
                                                                                                stationId)));
        }

        if(request.getDth11() != null) {
            Arrays.stream(request.getDth11())
                  .forEach(it -> measurementToSave.addAll(dth11MeasurementToEntity(it, request.getTimestamp(), sensorList, stationId)));
        }
        if(request.getDs18b20() != null) {
            Arrays.stream(request.getDs18b20()).forEach(it -> measurementToSave.add(dscosMeasurementToEntity(it, request.getTimestamp(), sensorList, stationId)));
        }

        measurementsRepository.saveAll(measurementToSave.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        log.info("Saved all measurements from device {}", macAddress);

    }

    private MeasurementsEntity analogMeasurementToEntity(Integer value, long timestamp, Long line, List<Sensor> sensors, Long stationId) {

        var optionalSensor = sensors.stream().filter(el -> el.getAddress().equals(line)).findFirst();
        var shouldUpdate = false;
        Sensor sensor;
        MeasurementsEntity measurementsEntity = null;

        if(optionalSensor.isPresent()) {
            sensor = optionalSensor.get();
        } else {
            sensor = Sensor.builder()
                    .address(line)
                    .isActive(false)
                    .sensorType(SensorType.SH)
                    .stationId(stationId)
                    .build();
            shouldUpdate = true;
        }


        if(value < 1000) {
                    if(sensor.isActive()) {
                        sensor.setActive(false);
                        shouldUpdate = true;
                    }
            log.info("Analog line: " + line + " - value < 1000 - sensor off");
        } else if (value < 2500) {
                    if(!sensor.isActive()) {
                        sensor.setActive(true);
                        shouldUpdate = true;
                    }
                    if(!sensor.getSensorType().equals(SensorType.SH)) {
                        sensor.setSensorType(SensorType.SH);
                        shouldUpdate = true;
                    }
                    measurementsEntity = MeasurementsEntity.builder()
                                                           .type(MeasurementType.SOIL_HUMIDITY)
                                                           .value(countSoilHumidityValue(value))
                                                           .timestamp(timestamp)
                                                           .build();

        } else if (value < 3000) {
            log.info("Analog line: " + line + " - not supported value 2500-3000");
            if(sensor.isActive()) {
                sensor.setActive(false);
                shouldUpdate = true;
            }
        } else if (value < 4000) {
                    if(!sensor.isActive()) {
                        sensor.setActive(true);
                        shouldUpdate = true;
                    }
                    if(!sensor.getSensorType().equals(SensorType.S)) {
                        sensor.setSensorType(SensorType.S);
                        shouldUpdate = true;
                    }
                    measurementsEntity = MeasurementsEntity.builder()
                                                            .type(MeasurementType.INSOLATION)
                                                            .value(countInsolationHumidityValue(value))
                                                            .timestamp(timestamp)
                                                            .build();
        } else if (value < 4600) {
            log.info("Analog line: " + line + " - not supported value 4000-4600");
            if(sensor.isActive()) {
                sensor.setActive(false);
                shouldUpdate = true;
            }
        } else {
            log.info("Analog line: " + line + " - broken - 4600+");
            if(sensor.isActive()) {
                sensor.setActive(false);
                shouldUpdate = true;
            }
        }

        if(shouldUpdate) {
            sensor = sensorUpdater.saveNewSensor(sensor);
        }

        if(measurementsEntity != null) {
            measurementsEntity.setSensorId(sensor.getId());
            return measurementsEntity;
        } else {
            return null;
        }

    }

    private List<MeasurementsEntity> dth11MeasurementToEntity(Dth11 dth11, long timestamp, List<Sensor> sensorList, Long stationId) {

        var optionalSensor = sensorList.stream().filter(el -> el.getAddress().equals((long)dth11.getInputLine())).findFirst();
        var sensor = optionalSensor.orElseGet(() -> Sensor.builder()
                                                          .sensorType(SensorType.DHT)
                                                          .address((long)dth11.getInputLine())
                                                          .isActive(true)
                                                          .stationId(stationId)
                                                          .build());

        var savedSensor = updateSensorData(sensor, optionalSensor);

        var humidityObject = MeasurementsEntity.builder()
                                               .sensorId(savedSensor.getId())
                                               .value(dth11.getValueHumidity())
                                               .type(MeasurementType.AIR_HUMIDITY)
                                               .timestamp(timestamp)
                                               .build();

        var tempObject = MeasurementsEntity.builder()
                                           .sensorId(savedSensor.getId())
                                           .value(dth11.getValueTemp())
                                           .type(MeasurementType.AIR_TEMP)
                                           .timestamp(timestamp)
                                           .build();

        return List.of(humidityObject, tempObject);
    }

    private MeasurementsEntity dscosMeasurementToEntity(Ds18b20 ds18b20, long timestamp, List<Sensor> sensorList, Long stationId) {
        var optionalSensor = sensorList.stream().filter(el -> el.getAddress().equals((long)ds18b20.getRomCode())).findFirst();
        var sensor = optionalSensor.orElseGet(() -> Sensor.builder()
                                                          .sensorType(SensorType.DS)
                                                          .address((long)ds18b20.getRomCode())
                                                          .isActive(true)
                                                          .stationId(stationId)
                                                          .build());
        var savedSensor = updateSensorData(sensor, optionalSensor);


        return MeasurementsEntity.builder()
                                 .sensorId(savedSensor.getId())
                                 .value(ds18b20.getValue())
                                 .type(MeasurementType.AIR_TEMP)
                                 .timestamp(timestamp)
                                 .build();
    }

    private Sensor updateSensorData(Sensor sensor, Optional<Sensor> optionalSensor) {
        if(optionalSensor.isEmpty()) {
            return sensorUpdater.saveNewSensor(sensor);
        } else {
            if(!optionalSensor.get().isActive()) {
                var sensorToSave = optionalSensor.get();
                sensorToSave.setActive(true);
                return sensorUpdater.saveNewSensor(sensorToSave);
            }
        }
        return sensor;
    }

    private double countSoilHumidityValue(Integer value) {
        var countedValue = -100.0 / 1350.0 * value + 189;
        if(countedValue > 100) return 100;
        if(countedValue < 0) return 0;
        return (int)countedValue;
    }

    private double countInsolationHumidityValue(Integer value) {
        var countedValue = -100.0 / 1350.0 * value + 189;
        //if(countedValue > 100) return 100.0;
        return (int)countedValue;
    }


}