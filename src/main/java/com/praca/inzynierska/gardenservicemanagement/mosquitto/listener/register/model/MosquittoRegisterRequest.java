package com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.register.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MosquittoRegisterRequest implements Serializable {

    Long ip;

    @JsonProperty("mac")
    String mac;

    int sv;
}
