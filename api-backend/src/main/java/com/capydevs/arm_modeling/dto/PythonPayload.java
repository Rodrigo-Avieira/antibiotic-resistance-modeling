package com.capydevs.arm_modeling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PythonPayload {
    private double initialBacterialLoad;
    private double mutationRate;
    private int treatmentDays;
    private double growthRate;
    private double antibioticEfficacy;
}