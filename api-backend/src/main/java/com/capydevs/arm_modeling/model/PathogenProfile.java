package com.capydevs.arm_modeling.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "pathogen_profiles")
public class PathogenProfile {
    @Id
    private String id;
    private String bacteria;
    private String antibiotic;
    private double growthRate;          // O 'r' da equação
    private double antibioticEfficacy;  // O 'a' da equação
    private double baseMutationRate;    // O 'm' da equação
    
    public PathogenProfile(String bacteria, String antibiotic, double growthRate, double antibioticEfficacy, double baseMutationRate) {
        this.bacteria = bacteria;
        this.antibiotic = antibiotic;
        this.growthRate = growthRate;
        this.antibioticEfficacy = antibioticEfficacy;
        this.baseMutationRate = baseMutationRate;
    }
}