package com.praca.inzynierska.gardenservicemanagement.datastore.warningsOccurrences;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "WARNINGS_OCCURRENCES")
public class WarningsOccurrencesEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "WARNINGS_ID")
    private Long warningsId;

    @Column(name = "TIMESTAMP")
    private Long timestamp;
}
