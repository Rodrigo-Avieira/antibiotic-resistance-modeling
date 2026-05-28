package com.capydevs.arm_modeling.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.capydevs.arm_modeling.model.PathogenProfile;
import com.capydevs.arm_modeling.repository.PathogenProfileRepository;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(PathogenProfileRepository repository) {
        return args -> {
            repository.deleteAll();
            if (repository.count() == 0) {
                System.out.println("Semeando matriz comparativa de patógenos no MongoDB...");
            repository.saveAll(List.of(
                // Opções para Escherichia coli
                new PathogenProfile("Escherichia coli", "Amoxicilina", 0.7, 1.9, 0.00001),
                new PathogenProfile("Escherichia coli", "Ciprofloxacino", 0.7, 1.4, 0.00005),
                new PathogenProfile("Escherichia coli", "Meropenem", 0.7, 2.5, 0.000001),
                
                // Opções para Staphylococcus aureus (MRSA)
                new PathogenProfile("Staphylococcus aureus (MRSA)", "Meticilina", 0.5, 0.3, 0.0005),
                new PathogenProfile("Staphylococcus aureus (MRSA)", "Vancomicina", 0.5, 1.8, 0.00002),
                new PathogenProfile("Staphylococcus aureus (MRSA)", "Linezolida", 0.5, 1.5, 0.00001),
                
                // Opções para Pseudomonas aeruginosa
                new PathogenProfile("Pseudomonas aeruginosa", "Ciprofloxacino", 0.6, 1.4, 0.00005),
                new PathogenProfile("Pseudomonas aeruginosa", "Piperacilina/Tazobactam", 0.6, 2.0, 0.00001),
                new PathogenProfile("Pseudomonas aeruginosa", "Ceftazidima", 0.6, 1.6, 0.00003)
                ));
            }
        };
    }
}