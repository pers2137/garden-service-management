package com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model;

public enum OperationMode {

    OFF(0),
    ON(1),
    AUTO(2);

    OperationMode(int value) {
        this.value = value;
    }

    public final int value;

}
