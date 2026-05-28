package com.capydevs.arm_modeling.model;

import java.util.List;

import com.capydevs.arm_modeling.dto.DataPointDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AntibioticSimulationResult {
    private String antibioticName;
    private double growthRate;
    private double antibioticEfficacy;
    private double baseMutationRate;
    private List<DataPointDTO> projectionData;
}