package com.capydevs.arm_modeling.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capydevs.arm_modeling.model.PathogenProfile;

@Repository
public interface PathogenProfileRepository extends MongoRepository<PathogenProfile, String> {
    // Busca todos os antibióticos disponíveis para uma bactéria específica
    List<PathogenProfile> findByBacteria(String bacteria);
}