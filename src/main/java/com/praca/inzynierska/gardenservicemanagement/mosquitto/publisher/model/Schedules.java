package com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class Schedules implements Serializable {

    @JsonProperty("day")
    int dayOfWeek;

    @JsonProperty("minute_start")
    int minuteOn;

    @JsonProperty("minute_stop")
    int minuteOff;

    @JsonProperty("hour_start")
    int hourOn;

    @JsonProperty("hour_stop")
    int hourOf;
}

