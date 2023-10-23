package com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class Values  implements Serializable  {
    boolean on;
    Schedules schedules[];
}
