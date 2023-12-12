package com.praca.inzynierska.gardenservicemanagement.test;

import com.praca.inzynierska.gardenservicemanagement.datastore.valves.ValvesEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.valves.ValvesRepository;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.MosquittoPublisherProcessor;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.OperationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    MosquittoPublisherProcessor mosquitoPublisher;
    ValvesRepository valvesRepository;

    @Autowired
    public TestController(MosquittoPublisherProcessor mosquitoPublisher, ValvesRepository valvesRepository) {
        this.mosquitoPublisher = mosquitoPublisher;
        this.valvesRepository = valvesRepository;
    }

    @PostMapping("/dwa")
    public void test2() {
        String message = "{\"mac\":\"333333\",\"timestamp\":1702335645000,\"analog\":[2600,2961,4900,5400,1300,1250,1111,1999,2020,2200,2500,1600,2800,2900,2333,2444,3050,3333,3400,3500,3600,3700,3800,3900,100,300,400,500,4100,4200,4300,4400],\"dht11\":[{\"line\":2,\"temp\":23.4,\"hum\":44},{\"line\":6,\"temp\":17,\"hum\":40}],\"ds18b20\":[{\"addr\":123412341,\"temp\":23.3}]}";
        mosquitoPublisher.sendMessageToTopic("data", message);
    }

    @PostMapping("/")
    public void test() {

        mosquitoPublisher.sendMessageToTopic("register", "{\"ip\":285255872,\"mac\":\"333333\",\"sv\":0}");
//        valvesRepository.save(ValvesEntity.builder()
//                        .stationId(902L)
//                        .pin(1)
//                        .enableHigh(true)
//                        .operationMode(OperationMode.OFF)
//                        .build());
//
//        valvesRepository.save(ValvesEntity.builder()
//                .stationId(902L)
//                .pin(2)
//                .enableHigh(false)
//                .operationMode(OperationMode.ON)
//                .build());
//
//        valvesRepository.save(ValvesEntity.builder()
//                .stationId(902L)
//                .pin(3)
//                .enableHigh(true)
//                .operationMode(OperationMode.AUTO)
//                .build());
//        mosquitoPublisher.sendMessageToTopic("register", "test");
//        mosquitoPublisher.sendMessageToTopic("data", "test");
//        mosquitoPublisher.sendMessageToTopic("xxxx", "test");
    }
}
