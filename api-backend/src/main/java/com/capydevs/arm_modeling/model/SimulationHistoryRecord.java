package com.capydevs.arm_modeling.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.List;

@Data
@Document(collection = "simulation_history")
public class SimulationHistoryRecord {
    @Id
    private String id;
    private String bacteriaName;
    private double initialBacterialLoad;
    private int treatmentDays;
    private List<AntibioticSimulationResult> results;
}