package com.praca.inzynierska.gardenservicemanagement.rabbitMq.listeners.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RabbitMQDataRequest {

    int mac;
    WilgotnoscGleby wilgotnoscGleby[];
    Sun sun[];
    Dth11 dth11[];
    Ds18b20 ds18b20[];
    int timestamp;



}
