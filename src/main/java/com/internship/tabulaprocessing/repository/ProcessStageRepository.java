package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.ProcessStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessStageRepository extends JpaRepository<ProcessStage, Integer> {

  Optional<ProcessStage> findByName(String name);

  ProcessStage findByNextStageEntityId(int id);
}
