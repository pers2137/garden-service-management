package com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MosquitoConfigValves implements Serializable {

    /**
      0-15 - NUMBER OF LINE VALVES
    */
    @JsonProperty("line")
    int pin;

    /**
     0 - ALWAYS OFF
     1 - ALWAYS ON
     2 - ACCORDING TO SCHEDULE
     */
    @JsonProperty("mode")
    int operationMode;

    /**
      0 - NO CURRENT FLOW CAUSES WATER TO FLOW
      1 - CURRENT FLOW CAUSES WATER TO FLOW
     */
    @JsonProperty("active_state")
    int enableHigh;

    @JsonProperty("schedules")
    List<Schedules> schedules;
}
