package com.capydevs.arm_modeling;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "simulations")
public class SimulationRecord {
    @Id
    private String id;
    private double initialBacterialLoad;
    private double mutationRate;
    private int treatmentDays;

    // Armazena a série temporal calculada pelo Python
    private List<Object> projectionData;
}
