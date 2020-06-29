package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.ProcessStagePersistDTO;
import com.internship.tabulaprocessing.dto.ProcessStageResponseDTO;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.ProcessStage;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.service.ProcessStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/process_stage")
public class ProcessStageController {

    private Mapper mapper;
    private ProcessStageService service;

    @Autowired
    public ProcessStageController(Mapper mapper, ProcessStageService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProcessStageResponseDTO> createProcessStage(
            @Valid @RequestBody ProcessStagePersistDTO responseDTO) {

        ProcessStage processStage = mapper.convertToProcessStageEntity(responseDTO);
        service.persist(processStage);

        return ResponseEntity.ok(mapper.convertToProcessStageDTO(processStage));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProcessStageResponseDTO> updateProcessStage(
            @Valid @RequestBody ProcessStageResponseDTO processStageResponseDTO, @PathVariable @Min(1) int id) {

        ProcessStage processStage = mapper.convertToProcessStageEntity(processStageResponseDTO);
        service.update(processStage, id);

        return ResponseEntity.ok(mapper.convertToProcessStageDTO(processStage));
    }


    @GetMapping
    public ResponseEntity<PagedResult<ProcessStageResponseDTO>> getAllProcessStages(
            @Valid QueryParameter queryParameter) {

        Page<ProcessStage> page = service.findAll(queryParameter.getPageable());
        List<ProcessStageResponseDTO> responseList = page.stream()
                .map(stage -> mapper.convertToProcessStageDTO(stage)).
                        collect(Collectors.toList());

        return ResponseEntity.ok(new PagedResult<>(
                responseList, queryParameter.getPage(), page.getTotalPages(),
                page.getTotalElements()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProcessStageResponseDTO> findSingleProcessStage(@PathVariable @Min(1) int id) {

        ProcessStage processStage = service.findById(id);

        return ResponseEntity.ok(mapper.convertToProcessStageDTO(processStage));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProcessStage(@PathVariable @Min(1) int id) {

        service.delete(id);

        return ResponseEntity.ok(String.format("ProcessStage with id %s,successfully deleted", id));
    }

}

