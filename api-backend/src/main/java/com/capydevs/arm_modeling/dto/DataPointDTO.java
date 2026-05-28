package com.capydevs.arm_modeling.dto;

import lombok.Data;

@Data
public class DataPointDTO {
    private double day;
    private double susceptible;
    private double resistant;
}