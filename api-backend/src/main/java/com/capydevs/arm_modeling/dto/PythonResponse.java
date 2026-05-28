package com.capydevs.arm_modeling.dto;

import java.util.List;

import lombok.Data;

@Data
public class PythonResponse {
    private String status;
    private List<DataPointDTO> data;
}