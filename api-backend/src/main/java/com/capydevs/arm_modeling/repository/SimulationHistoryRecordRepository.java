package com.capydevs.arm_modeling.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capydevs.arm_modeling.model.SimulationHistoryRecord;

@Repository
public interface SimulationHistoryRecordRepository extends MongoRepository<SimulationHistoryRecord, String> {
}