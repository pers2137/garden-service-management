package com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.register;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.praca.inzynierska.gardenservicemanagement.common.BinaryParser;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.register.model.MosquittoRegisterRequest;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.register.services.MosquittoRegisterProcessor;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.MosquittoPublisherProcessor;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers.model.DeviceConfigurationRequest;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers.model.Station;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers.model.Values;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MosquittoRegisterListener {

    @Value("${mosquitto.topic.register}")
    String registerTopic;

    private final MqttClient mqttClient;
    private final MosquittoRegisterProcessor registerProcessor;
    private final MosquittoPublisherProcessor mosquittoPublisherProcessor;

    @Autowired
    public MosquittoRegisterListener(MqttClient mqttClient, MosquittoRegisterProcessor registerProcessor, MosquittoPublisherProcessor mosquittoPublisherProcessor) {
        this.mqttClient = mqttClient;
        this.registerProcessor = registerProcessor;
        this.mosquittoPublisherProcessor = mosquittoPublisherProcessor;
    }

    @Bean
    public void registerListener() {
        IMqttMessageListener register = (topicName, mqttMessage) -> {
            String stringRequest = new String(mqttMessage.getPayload());
            log.info("Received register request: {}", stringRequest);

            Gson gsonParser = new Gson();
            MosquittoRegisterRequest request;

            try {
                 request = gsonParser.fromJson(stringRequest, MosquittoRegisterRequest.class);
            } catch (JsonSyntaxException e) {
                log.info("registerListener - Parser error!");
                return;
            }
            System.out.println("MosquittoRegisterRequest parsed successfully");

           //TODO - DO ROZWAZENIA SYTUACJA KIEDY URZADZENIE ISTNIEJE ALE KOLEJKI TAKIEJ NIE MA -> ZROBIC JAKIEGOS CHECKA CZY TAKA KOLEJKA ISTNIEJE
           if(registerProcessor.registerStation(request)) {
               var deviceConfiguration = prepareDeviceConfiguration();
               mosquittoPublisherProcessor.sendConfigurationMessageToTopic(String.format("configuration_%d",request.getMac()), deviceConfiguration);
           }
           log.info("Register request from {} was handled.", BinaryParser.getMacAddressFromInt64(request.getMac()));

        };

        try {
            mqttClient.subscribe(registerTopic, 0, register);
        } catch (Exception e) {
            log.error("Error during creating registerTopic");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private DeviceConfigurationRequest prepareDeviceConfiguration() {
        var deviceConf = DeviceConfigurationRequest.builder();
        deviceConf.station(Station.builder()
                .measurementPeriod(1)
                .values(initValuesForNewDevice())
                .build()

        ).build();
        return deviceConf.build();
    }

    private Values[] initValuesForNewDevice() {
        var valuesTab = new Values[16];
        for(int i=0;i<16;i++) {
            valuesTab[i] = new Values(false, null);
        }
        return valuesTab;
    }

}
