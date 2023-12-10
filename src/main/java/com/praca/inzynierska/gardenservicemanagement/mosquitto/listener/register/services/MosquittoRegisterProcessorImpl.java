package com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.register.services;

import com.praca.inzynierska.gardenservicemanagement.common.DeviceConfigurationRequestMapper;
import com.praca.inzynierska.gardenservicemanagement.datastore.schedules.SchedulesEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.stations.StationsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.stations.StationsRepository;
import com.praca.inzynierska.gardenservicemanagement.common.BinaryParser;
import com.praca.inzynierska.gardenservicemanagement.datastore.valves.ValvesEntity;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.register.model.MosquittoRegisterRequest;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.DeviceConfigurationRequest;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.MosquitoConfigValves;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.OperationMode;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.Schedules;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.Valves;
import com.praca.inzynierska.gardenservicemanagement.webFront.provider.ValvesProvider;
import com.praca.inzynierska.gardenservicemanagement.webFront.updater.ValvesUpdater;
import com.praca.inzynierska.gardenservicemanagement.webFront.utils.DefaultValves;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MosquittoRegisterProcessorImpl implements MosquittoRegisterProcessor {

    private final StationsRepository stationsRepository;
    private final ValvesUpdater valvesUpdater;
    private final ValvesProvider valvesProvider;

    public MosquittoRegisterProcessorImpl(StationsRepository stationsRepository, ValvesUpdater valvesUpdater, ValvesProvider valvesProvider) {
        this.stationsRepository = stationsRepository;
        this.valvesUpdater = valvesUpdater;
        this.valvesProvider = valvesProvider;
    }


    @Override
    public DeviceConfigurationRequest registerStation(final MosquittoRegisterRequest request) {
        var macAddress = request.getMac();

        if(stationsRepository.existsByMacAddress(macAddress)) {
            log.error(String.format("Station MAC: %s already exist!", macAddress));
            var station = stationsRepository.findByMacAddress(macAddress);
            var valvesList = valvesProvider.getValvesForStation(station.get().getId());
            return DeviceConfigurationRequestMapper.toDeviceConfigurationRequest(valvesList, station.get().getMeasurementPeriod());
        }

        var newStations = StationsEntity.builder()
                                        .name(String.format("Station MAC: %s", macAddress))
                                        .macAddress(macAddress)
                                        .ipAddress(BinaryParser.getIpAddressFromInt32(request.getIp()))
                                        .softwareVersion(String.valueOf(request.getSv()))
                                        .measurementPeriod(1)
                                        .registerDate(LocalDateTime.now())
                                        .build();
        var savedStation = stationsRepository.save(newStations);
        log.info(String.format("Station MAC: %s saved successfully!", macAddress));
        var defaultConfiguration = prepareDefaultStationConfiguration();
        valvesUpdater.saveValves(defaultConfiguration.getValves().stream()
                                                                 .map(el -> fromDefaultConfigToValves(el, savedStation.getId()))
                                                                 .collect(Collectors.toList()));

        return defaultConfiguration;
    }

    private ValvesEntity fromDefaultConfigToValves(final MosquitoConfigValves mosquitoConfigValves, final Long stationId) {
        return ValvesEntity.builder()
                           .pin(mosquitoConfigValves.getPin())
                           .operationMode(getOperationModeFromInt(mosquitoConfigValves.getOperationMode()))
                           .enableHigh(mosquitoConfigValves.getEnableHigh() == 1)
                           .stationId(stationId)
                           .schedulesList(null)
                           .build();
    }


    private DeviceConfigurationRequest prepareDefaultStationConfiguration() {
        var deviceConf = DeviceConfigurationRequest.builder();
        deviceConf.measurementPeriod(1)
                  .valves(initValuesForNewDevice())
                  .build();
        return deviceConf.build();
    }

    private List<MosquitoConfigValves> initValuesForNewDevice() {
        var valuesTab = new ArrayList<MosquitoConfigValves>(16);
        for(int i=0;i<16;i++) {
            valuesTab.add(DefaultValves.defaultConfiguration(i));
        }
        return valuesTab;
    }

    private OperationMode getOperationModeFromInt(final int intValue) {
        if(intValue == 0 ) return OperationMode.OFF;
        if(intValue == 1 ) return OperationMode.ON;
        return OperationMode.AUTO;
    }

}
