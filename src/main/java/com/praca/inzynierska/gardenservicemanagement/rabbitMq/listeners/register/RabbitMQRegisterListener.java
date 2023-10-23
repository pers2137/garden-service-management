package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register;

import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.model.RabbitMQRegisterRequest;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.register.services.RabbitMQRegisterProcessor;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers.RabbitMQConfigurationProducer;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers.model.DeviceConfigurationRequest;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers.model.Station;
import com.praca.inzynierska.gardenservicemanagement.rabbitMq.producers.model.Values;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQRegisterListener {

    private final RabbitMQRegisterProcessor registerProcessor;
    private final RabbitMQConfigurationProducer rabbitMQConfigurationProducer;

    @Autowired
    public RabbitMQRegisterListener(RabbitMQRegisterProcessor registerProcessor, RabbitMQConfigurationProducer rabbitMQConfigurationProducer) {
        this.registerProcessor = registerProcessor;
        this.rabbitMQConfigurationProducer = rabbitMQConfigurationProducer;
    }

    @RabbitListener(queues = {"${rabbitmq.register.queue}"})
    public void consume(RabbitMQRegisterRequest request) {
       log.info(String.format("Received register request: %s", request.toString()));

       //TODO - DO ROZWAZENIA SYTUACJA KIEDY URZADZENIE ISTNIEJE ALE KOLEJKI TAKIEJ NIE MA -> ZROBIC JAKIEGOS CHECKA CZY TAKA KOLEJKA ISTNIEJE
       if(registerProcessor.registerStation(request)) {
           var deviceConfiguration = prepareDeviceConfiguration();
           rabbitMQConfigurationProducer.createNewQueueAndSendJsonMessage(deviceConfiguration, request.getMac());
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
