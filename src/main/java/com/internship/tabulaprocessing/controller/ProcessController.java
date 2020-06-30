package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.ProcessResponseDto;
import com.internship.tabulaprocessing.dto.ProcessRequestDto;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.service.ProcessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/processes")
public class ProcessController {

  private ProcessServiceImpl processService;

  private Mapper mapper;

  @Autowired
  public ProcessController(ProcessServiceImpl processService, Mapper mapper) {
    this.processService = processService;
    this.mapper = mapper;
  }

  @GetMapping("/{id}")
  public HttpEntity get(@PathVariable(name = "id") Integer id) {
    Process process = processService.getOneById(id);
    return ResponseEntity.ok(mapper.processToProcessGetDTO(process));
  }

  @GetMapping
  public PagedResult<ProcessResponseDto> getAllByPage(QueryParameter queryParameter) {

    Page<Process> allProcesses = processService.findAll(queryParameter.getPageable());
    List<ProcessResponseDto> allToDto = new ArrayList<>();
    for (Process process : allProcesses.toList()) {
      allToDto.add(mapper.processToProcessGetDTO(process));
    }
    return new PagedResult<>(allToDto,
            queryParameter.getPageable().getPageNumber() + 1,
            allProcesses.getTotalPages(),allProcesses.getTotalElements());
  }

  @PostMapping
  public HttpEntity create(@Valid @RequestBody ProcessRequestDto processRequestDto) {

    Process process = mapper.processPostDTOtoProcess(processRequestDto);
    Process createdProcess = processService.create(process);
    return ResponseEntity.ok(mapper.processToProcessGetDTO(createdProcess));
  }

  @PutMapping("/{id}")
  public HttpEntity update(
      @PathVariable("id") Integer id, @Valid @RequestBody ProcessRequestDto processRequestDto) {

    Process process = mapper.processPutDTOtoProcess(processRequestDto);
    Process updatedProcess = processService.update(process, id);
    return ResponseEntity.ok(mapper.processToProcessGetDTO(updatedProcess));
  }

  @DeleteMapping("/{id}")
  public HttpEntity delete(@PathVariable("id") Integer id) {

    processService.delete(id);
    return ResponseEntity.ok("Deleted successfully");
  }
}
