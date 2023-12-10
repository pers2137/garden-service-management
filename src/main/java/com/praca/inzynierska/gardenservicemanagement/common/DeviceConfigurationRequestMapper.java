package com.praca.inzynierska.gardenservicemanagement.common;

import com.praca.inzynierska.gardenservicemanagement.datastore.schedules.SchedulesEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.valves.ValvesEntity;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.DeviceConfigurationRequest;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.MosquitoConfigValves;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.Schedules;

import java.util.List;
import java.util.stream.Collectors;

public class DeviceConfigurationRequestMapper {

    public static DeviceConfigurationRequest toDeviceConfigurationRequest(final List<ValvesEntity> valvesList, final int measurementPeriod) {
        return DeviceConfigurationRequest.builder()
                .measurementPeriod(measurementPeriod)
                .valves(toMosquitoConfigValues(valvesList))
                .build();
    }

    private static List<MosquitoConfigValves> toMosquitoConfigValues(final List<ValvesEntity> valvesList) {
        return valvesList.stream().map(DeviceConfigurationRequestMapper::toMosquitoConfigValves).collect(Collectors.toList());
    }

    private static MosquitoConfigValves toMosquitoConfigValves(final ValvesEntity valvesEntity) {
        return MosquitoConfigValves.builder()
                .pin(valvesEntity.getPin())
                .operationMode(valvesEntity.getOperationMode().value)
                .enableHigh(valvesEntity.getEnableHigh() ? 1 : 0)
                .schedules(valvesEntity.getSchedulesList().stream().map(DeviceConfigurationRequestMapper::toMosquitoValveScheduler).collect(Collectors.toList()))
                .build();
    }

    private static Schedules toMosquitoValveScheduler(final SchedulesEntity schedulesEntity) {
        return Schedules.builder()
                .dayOfWeek(schedulesEntity.getDayOfWeek().intValue())
                .hourOf(schedulesEntity.getHourStop().intValue())
                .hourOn(schedulesEntity.getHourStart().intValue())
                .minuteOff(schedulesEntity.getMinuteStop().intValue())
                .minuteOn(schedulesEntity.getMinuteStart().intValue())
                .build();
    }


}
