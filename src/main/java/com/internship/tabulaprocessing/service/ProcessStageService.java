package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.controller.PageResponse;
import com.internship.tabulaprocessing.entity.ProcessStage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProcessStageService {

    ProcessStage persist(ProcessStage processStage);

    ProcessStage update(ProcessStage processStage, int id);

    ProcessStage findById(int id);

    Page<ProcessStage> findAll(Pageable pageable);

    ProcessStage findByName(String name);

    void delete(int id);
}
