package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.data.model;

import java.io.Serializable;

public class Ds18b20 implements Serializable {
    int romCode;
    double value;
}
