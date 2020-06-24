package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.TimeOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeOffRepository extends JpaRepository<TimeOff, Integer> {
}
