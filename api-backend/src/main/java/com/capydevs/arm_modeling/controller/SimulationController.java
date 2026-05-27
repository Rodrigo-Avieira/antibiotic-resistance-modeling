package com.capydevs.arm_modeling.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capydevs.arm_modeling.SimulationRecord;
import com.capydevs.arm_modeling.service.SimulationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/simulations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SimulationController {

    private final SimulationService service;

    @PostMapping
    public SimulationRecord createSimulation(@RequestBody SimulationRecord record) {
        return service.saveSimulation(record);
    }

    @GetMapping
    public List<SimulationRecord> getAll() {
        return service.getAllSimulations();
    }

    @GetMapping("/{id}")
    public SimulationRecord getById(@PathVariable String id) {
        return service.getSimulationById(id);
    }
}