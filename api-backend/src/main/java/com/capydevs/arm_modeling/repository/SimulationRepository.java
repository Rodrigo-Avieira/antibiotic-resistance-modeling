package com.capydevs.arm_modeling.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capydevs.arm_modeling.SimulationRecord;

@Repository
public interface SimulationRepository extends MongoRepository<SimulationRecord, String> {
}