package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {

  Optional<Process> findByName(String name);
}
