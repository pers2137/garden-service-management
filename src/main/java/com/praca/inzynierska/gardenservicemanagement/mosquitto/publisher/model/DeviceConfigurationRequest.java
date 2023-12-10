package com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DeviceConfigurationRequest implements Serializable {

    @JsonProperty("period")
    int measurementPeriod;

    @JsonProperty("valves")
    List<MosquitoConfigValves> valves;
}

