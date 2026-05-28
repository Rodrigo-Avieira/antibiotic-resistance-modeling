package com.capydevs.arm_modeling.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capydevs.arm_modeling.dto.ComparisonRequest;
import com.capydevs.arm_modeling.dto.PythonPayload;
import com.capydevs.arm_modeling.dto.PythonResponse;
import com.capydevs.arm_modeling.model.AntibioticSimulationResult;
import com.capydevs.arm_modeling.model.PathogenProfile;
import com.capydevs.arm_modeling.model.SimulationHistoryRecord;
import com.capydevs.arm_modeling.repository.PathogenProfileRepository;
import com.capydevs.arm_modeling.repository.SimulationHistoryRecordRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/simulations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SimulationController {

    // É crucial ter estes dois repositórios declarados aqui para o Spring injetá-los
    private final PathogenProfileRepository pathogenRepository;
    private final SimulationHistoryRecordRepository historyRepository;
    
    // Instância do RestTemplate para fazer a chamada HTTP para o Python
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/compare")
    public ResponseEntity<?> compareTreatments(@RequestBody ComparisonRequest request) {
    // 1. Busca todos os antibióticos cadastrados para a bactéria escolhida
    List<PathogenProfile> profiles = pathogenRepository.findByBacteria(request.getBacteriaName());
    
    List<AntibioticSimulationResult> comparisonResults = new ArrayList<>();

    // 2. Loop para rodar a EDO no Python para cada antibiótico
    for (PathogenProfile profile : profiles) {
        // Monta o payload para enviar ao Python
        PythonPayload payload = new PythonPayload(
            request.getInitialBacterialLoad(),
            profile.getBaseMutationRate(),
            request.getTreatmentDays(),
            profile.getGrowthRate(),
            profile.getAntibioticEfficacy()
        );

        // Faz a chamada HTTP para o Motor em Python (porta 8000)
        PythonResponse pythonResponse = restTemplate.postForObject("http://localhost:8000/calculate", payload, PythonResponse.class);

        if (pythonResponse != null && "success".equals(pythonResponse.getStatus())) {
            // Adiciona o resultado deste antibiótico específico na lista
            comparisonResults.add(new AntibioticSimulationResult(
                profile.getAntibiotic(),
                profile.getGrowthRate(),
                profile.getAntibioticEfficacy(),
                profile.getBaseMutationRate(),
                pythonResponse.getData()
            ));
        }
    }

    // 3. Monta o registro de histórico consolidado para salvar no MongoDB
    SimulationHistoryRecord history = new SimulationHistoryRecord();
    history.setBacteriaName(request.getBacteriaName());
    history.setInitialBacterialLoad(request.getInitialBacterialLoad());
    history.setTreatmentDays(request.getTreatmentDays());
    history.setResults(comparisonResults);
    
    historyRepository.save(history); // Persiste o caso completo no banco NoSQL

    return ResponseEntity.ok(history);
}

    @GetMapping
    public List<SimulationHistoryRecord> getAll() {
        // Busca todos os históricos de comparações em lote salvos no banco
        return historyRepository.findAll();
    }

    @GetMapping("/{id}")
    public SimulationHistoryRecord getById(@PathVariable String id) {
        // Busca uma comparação específica pelo ID
        return historyRepository.findById(id).orElse(null);
    }
}