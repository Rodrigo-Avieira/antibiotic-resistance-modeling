package com.capydevs.arm_modeling.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.capydevs.arm_modeling.SimulationRecord;
import com.capydevs.arm_modeling.repository.SimulationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SimulationService {

    private final SimulationRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    public SimulationRecord saveSimulation(SimulationRecord record) {
        String pythonEngineUrl = "http://localhost:8000/calculate";
        
        try {
            // Envia os dados iniciais para o modelo matemático no Python
            ResponseEntity<Map> response = restTemplate.postForEntity(pythonEngineUrl, record, Map.class);
            
            // Extrai o cálculo retornado e anexa ao registro
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<Object> resultData = (List<Object>) response.getBody().get("data");
                record.setProjectionData(resultData);
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha na comunicação com o motor matemático: " + e.getMessage());
        }

        // Persiste o cenário completo (inputs e a curva de resistência) no MongoDB
        return repository.save(record);
    }

    public List<SimulationRecord> getAllSimulations() {
        return repository.findAll();
    }

    public SimulationRecord getSimulationById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Simulação não encontrada."));
    }
}