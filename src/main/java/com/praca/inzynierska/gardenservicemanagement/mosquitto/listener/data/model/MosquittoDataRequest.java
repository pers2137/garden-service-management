package com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MosquittoDataRequest implements Serializable {

    @JsonProperty("mac")
    String mac;


    @JsonProperty("analog")
    Integer[] analog;

    /**
     * max 8 value - 0-7 line
     */
    @JsonProperty("dht11")
    Dth11 dth11[];

    @JsonProperty("ds18b20")
    Ds18b20 ds18b20[];

    @JsonProperty("timestamp")
    long timestamp;



}
