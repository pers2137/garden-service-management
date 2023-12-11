package com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.praca.inzynierska.gardenservicemanagement.common.BinaryParser;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.data.model.MosquittoDataRequest;
import com.praca.inzynierska.gardenservicemanagement.mosquitto.listener.data.services.MosquittoDataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MosquittoDataListener {

    @Value("${mosquitto.topic.data}")
    String dataTopic;

    private final MqttClient mqttClient;
    private final MosquittoDataProcessor registerProcessor;

    @Autowired
    public MosquittoDataListener(MqttClient mqttClient, MosquittoDataProcessor registerProcessor) {
        this.mqttClient = mqttClient;
        this.registerProcessor = registerProcessor;
    }

    @Bean
    public void dataListener() {
        IMqttMessageListener listener = (topicName, mqttMessage) -> {
            String stringRequest = new String(mqttMessage.getPayload());
            log.info("Received register request: {}", stringRequest);

//            Gson gsonParser = new Gson();
            ObjectMapper objectMapper = new ObjectMapper();
            MosquittoDataRequest request;
            try {
                request = objectMapper.readValue(stringRequest, MosquittoDataRequest.class);
//                 request = gsonParser.fromJson(stringRequest, MosquittoDataRequest.class);
            } catch (Exception e) {
                log.error("dataListener - Parser error !");
                log.error(e.toString());
                return;
            }

            registerProcessor.saveMeasurementData(request);
            log.info("Data request from {} was handled.", request.getMac());
        };

        try {
            mqttClient.subscribe(dataTopic, 0, listener);
        } catch (Exception e) {
            log.error("Error during creating dataTopic");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



}
