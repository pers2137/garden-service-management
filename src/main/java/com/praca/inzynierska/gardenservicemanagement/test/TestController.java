package com.praca.inzynierska.gardenservicemanagement.test;

import com.praca.inzynierska.gardenservicemanagement.datastore.sensorHasWarningsOccurres.SensorHasWarningsOccurrenceRepository;
import com.praca.inzynierska.gardenservicemanagement.datastore.valves.ValvesEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.valves.ValvesRepository;
import com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences.WarningsOccurrencesRepository;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.MosquittoPublisherProcessor;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.OperationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    MosquittoPublisherProcessor mosquitoPublisher;
    ValvesRepository valvesRepository;
    WarningsOccurrencesRepository warningsOccurrencesRepository;

    @Autowired
    public TestController(MosquittoPublisherProcessor mosquitoPublisher, ValvesRepository valvesRepository,  WarningsOccurrencesRepository warningsOccurrencesRepository) {
        this.mosquitoPublisher = mosquitoPublisher;
        this.valvesRepository = valvesRepository;
        this.warningsOccurrencesRepository = warningsOccurrencesRepository;
    }

    @PostMapping("/dwa")
    public void test2() {
//        String message = "{\"timestamp\":1703760907,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,2345,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,1882,45],\"dht11\":[{\"line\":3,\"temp\":17.8,\"hum\":53.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":18.00},{\"addr\":319360827203880,\"temp\":20.69}]}";
//        mosquitoPublisher.sendMessageToTopic("data", message);
//        System.out.println(warningsOccurrencesRepository.getLastMeasurementsForSensors(List.of(353L)));

    }

    @PostMapping("/")
    public void test() {


        String message = "{\"timestamp\":1704060534,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,1710,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,1499,45],\"dht11\":[{\"line\":3,\"temp\":17.0,\"hum\":52.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":17.56},{\"addr\":319360827203880,\"temp\":17.50}]}";
//        String message2 = "{\"timestamp\":1703782127,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,1710,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,1499,45],\"dht11\":[{\"line\":3,\"temp\":17.0,\"hum\":52.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":17.56},{\"addr\":319360827203880,\"temp\":17.50}]}";
//        String message2 = "{\"timestamp\":1703766727,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,1714,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,1501,46],\"dht11\":[{\"line\":3,\"temp\":17.0,\"hum\":52.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":17.50},{\"addr\":319360827203880,\"temp\":17.44}]}";
//        String message3 = "{\"timestamp\":1703767027,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,1719,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,46,45,46,46,46,46,1503,46],\"dht11\":[{\"line\":3,\"temp\":17.0,\"hum\":53.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":17.50},{\"addr\":319360827203880,\"temp\":17.44}]}";
//        String message4 = "{\"timestamp\":1703767327,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,1723,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,1504,45],\"dht11\":[{\"line\":3,\"temp\":17.0,\"hum\":53.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":17.50},{\"addr\":319360827203880,\"temp\":17.38}]}";
//        String message5 = "{\"timestamp\":1703767627,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,1726,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,46,46,46,46,46,46,1506,46],\"dht11\":[{\"line\":3,\"temp\":16.6,\"hum\":53.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":17.44},{\"addr\":319360827203880,\"temp\":17.38}]}";
//        String message6 = "{\"timestamp\":1703767927,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,1730,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,1507,46],\"dht11\":[{\"line\":3,\"temp\":16.6,\"hum\":54.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":17.44},{\"addr\":319360827203880,\"temp\":17.31}]}";
//        String message7 = "{\"timestamp\":1703768227,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,1733,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,1508,46],\"dht11\":[{\"line\":3,\"temp\":16.6,\"hum\":54.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":17.38},{\"addr\":319360827203880,\"temp\":17.31}]}";
//        String message8 = "{\"timestamp\":1703768527,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,1737,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,46,45,45,1510,46],\"dht11\":[{\"line\":3,\"temp\":16.6,\"hum\":54.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":17.38},{\"addr\":319360827203880,\"temp\":17.25}]}";
//        String message9 = "{\"timestamp\":1703769127,\"mac\":\"C0-49-EF-6B-F4-2C\",\"analog\":[48,48,48,48,48,48,1692,48,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,1509,46],\"dht11\":[{\"line\":3,\"temp\":17.4,\"hum\":56.0}],\"ds18b20\":[{\"addr\":319361830773288,\"temp\":17.81},{\"addr\":319360827203880,\"temp\":17.69}]}";
        mosquitoPublisher.sendMessageToTopic("data", message);
//        mosquitoPublisher.sendMessageToTopic("data", message2);
//        mosquitoPublisher.sendMessageToTopic("data", message3);
//        mosquitoPublisher.sendMessageToTopic("data", message4);
//        mosquitoPublisher.sendMessageToTopic("data", message5);
//        mosquitoPublisher.sendMessageToTopic("data", message6);
//        mosquitoPublisher.sendMessageToTopic("data", message7);
//        mosquitoPublisher.sendMessageToTopic("data", message8);
//        mosquitoPublisher.sendMessageToTopic("data", message9);


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
