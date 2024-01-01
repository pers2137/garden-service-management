package com.praca.inzynierska.gardenservicemanagement.webFront.utils;

import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.OperationMode;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.MosquitoConfigValves;
import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.station.Valves;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;


public class DefaultValves {

//TODO -> ZROBIENIE TEOG NA PARAMETRACH BO COŚ NIE DZIAŁA.
//    @Value("${app.valves.default.operationMode}")
//    private String operationMode;
//
//    @Value("${app.valves.default.enableHigh}")
//    private int enableHigh;


    public static MosquitoConfigValves defaultConfiguration(final int pin) {
        return new MosquitoConfigValves(pin, OperationMode.OFF.value, 1, null);
    }

    public static Valves defaultValvesForWWW(final int pin) {
        return new Valves(pin, OperationMode.OFF, true, Collections.emptyList());
    }

}
