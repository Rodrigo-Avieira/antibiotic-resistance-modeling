package com.capydevs.arm_modeling.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capydevs.arm_modeling.model.PathogenProfile;
import com.capydevs.arm_modeling.repository.PathogenProfileRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pathogens")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PathogenController {

    private final PathogenProfileRepository repository;

    // Retorna a lista de nomes únicos de bactérias para o seletor principal
    @GetMapping("/unique")
    public List<String> getUniqueBacteria() {
        return repository.findAll().stream()
                .map(PathogenProfile::getBacteria)
                .distinct()
                .collect(Collectors.toList());
    }

    // Retorna os perfis de antibióticos de uma bactéria específica
    @GetMapping("/by-bacteria")
    public List<PathogenProfile> getProfilesByBacteria(@RequestParam String name) {
        return repository.findByBacteria(name);
    }
}