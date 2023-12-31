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
        String message = "{\"timestamp\":1703760907,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,2345,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,1882,45],\"dht11\":[{\"line\":3,\"temp\":17.8,\"hum\":53.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":18.00},{\"addr\":319360827203880,\"temp\":20.69}]}";
        mosquitoPublisher.sendMessageToTopic("data", message);
    }

    @PostMapping("/")
    public void test() {


        String message = "{\"timestamp\":1703761027,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,2391,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,46,45,45,45,45,1883,45],\"dht11\":[{\"line\":3,\"temp\":18.2,\"hum\":54.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":25.00},{\"addr\":319360827203880,\"temp\":25.56}]}";
        mosquitoPublisher.sendMessageToTopic("data", message);


//        mosquitoPublisher.sendMessageToTopic("register", "{\"ip\":285255872,\"mac\":\"dd333333\",\"sv\":0}");
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
