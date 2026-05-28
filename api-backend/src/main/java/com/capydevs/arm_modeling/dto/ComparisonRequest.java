package com.capydevs.arm_modeling.dto;

import lombok.Data;

@Data
public class ComparisonRequest {
    private String bacteriaName;
    private double initialBacterialLoad;
    private int treatmentDays;
}