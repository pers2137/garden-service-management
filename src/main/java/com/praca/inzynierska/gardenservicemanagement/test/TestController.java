package com.praca.inzynierska.gardenservicemanagement.test;

import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.MosquittoPublisherProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    MosquittoPublisherProcessor mosquitoPublisher;

    @GetMapping("/")
    public void test() {
        mosquitoPublisher.sendMessageToTopic("register", "test");
        mosquitoPublisher.sendMessageToTopic("data", "test");
        mosquitoPublisher.sendMessageToTopic("xxxx", "test");
    }
}
