package com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher;

import com.google.gson.Gson;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers.model.DeviceConfigurationRequest;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MosquittoPublisherProcessorImpl implements MosquittoPublisherProcessor {

    @Autowired
    MqttClient mqttClient;

    @Override
    public void sendConfigurationMessageToTopic(String topic, DeviceConfigurationRequest response) {
        MqttMessage mqttMessage = new MqttMessage();
        Gson g = new Gson();

        var message = g.toJson(response).getBytes(StandardCharsets.UTF_8);
        mqttMessage.setPayload(message);

        try {
            mqttClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageToTopic(String topic, String message) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes(StandardCharsets.UTF_8));

        try {
            mqttClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
