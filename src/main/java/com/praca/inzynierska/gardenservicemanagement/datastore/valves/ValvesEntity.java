package com.praca.inzynierska.gardenservicemanagement.datastore.valves;


import com.praca.inzynierska.gardenservicemanagement.mosquitto.publisher.model.OperationMode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "VALVES")
public class ValvesEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "STATION_ID")
    private Long stationId;

    @Column(name = "PIN")
    private int pin;

    @Column(name = "OPERATION_MODE")
    @Enumerated(EnumType.STRING)
    private OperationMode operationMode;

    @Column(name = "ENABLE_HIGH")
    private boolean enableHigh;

}
