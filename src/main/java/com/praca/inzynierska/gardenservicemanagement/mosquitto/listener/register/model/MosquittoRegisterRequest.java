package com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.register.model;

import lombok.Data;

@Data
public class MosquittoRegisterRequest {
    int ip;
    long mac;
    int sv;
}
