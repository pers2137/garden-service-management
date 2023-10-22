package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.services;

import com.praca.inzynierska.gardenservicemanagement.datastore.stations.StationsEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.stations.StationsRepository;
import com.praca.inzynierska.gardenservicemanagement.common.BinaryParser;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.model.RabbitMQRegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQRegisterProcessorImpl implements RabbitMQRegisterProcessor{

    private final StationsRepository stationsRepository;

    public RabbitMQRegisterProcessorImpl(StationsRepository stationsRepository) {
        this.stationsRepository = stationsRepository;
    }


    @Override
    public void registerStation(RabbitMQRegisterRequest request) {
        var macAddress = BinaryParser.getMacAddressFromInt64(request.getMac());

        if(stationsRepository.existsByMacAddress(macAddress)) {
             log.error(String.format("Station MAC: %s already exist!", macAddress));
             return;
        }

        var newStations = StationsEntity.builder()
                                        .name(String.format("Station MAC: %s", macAddress))
                                        .macAddress(macAddress)
                                        .ipAddress(BinaryParser.getIpAddressFromInt32(request.getIp()))
                                        .softwareVersion(String.valueOf(request.getSv()))
                                        .measurementPeriod(1)
                                        .build();
        stationsRepository.save(newStations);
        log.info(String.format("Station MAC: %s saved successfully!", macAddress));
    }
}
