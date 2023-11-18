package com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class Station  implements Serializable {
    int measurementPeriod;
    Valves values[];
}