package com.internship.tabulaprocessing.controller;


import com.internship.tabulaprocessing.dto.EmployeeResponseDto;
import com.internship.tabulaprocessing.dto.TimeOffPatchRequest;
import com.internship.tabulaprocessing.dto.TimeOffRequest;
import com.internship.tabulaprocessing.dto.TimeOffResponse;
import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.TimeOff;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.service.EmployeeService;
import com.internship.tabulaprocessing.service.TimeOffService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/timeOffs")
@RequiredArgsConstructor
public class TimeOffController {

  private final TimeOffService timeOffService;
  private final Mapper mapper;
  private final PatchMapper patchMapper;
  private final EmployeeService employeeService;

  @PostMapping
  public ResponseEntity<TimeOffResponse> create(@Valid @RequestBody TimeOffRequest timeOffRequest) {
    TimeOff savedTimeOff = timeOffService.create(fetchAndSetEmployeeAndApprover(timeOffRequest));
    return ResponseEntity.ok(mapper.convertToTimeOffResponse(savedTimeOff));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TimeOffResponse> getOne (@PathVariable @Min(1) int id) {
    return ResponseEntity.ok(mapper.convertToTimeOffResponse(timeOffService.findById(id).get()));
  }

  @GetMapping
  public ResponseEntity<PagedResult<TimeOff>> getAll(QueryParameter queryParameter) {
    return ResponseEntity.ok(timeOffService.findAll(queryParameter.getPageable()));
  }

  @PutMapping("/{id}")
  public ResponseEntity<TimeOffResponse> update(@PathVariable("id") @Min(1) int id,
                                                @Valid @RequestBody TimeOffRequest timeOffRequest) {

    TimeOff updatedTimeOff = timeOffService.update(fetchAndSetEmployeeAndApprover(timeOffRequest), id);
    return ResponseEntity.ok(mapper.convertToTimeOffResponse(updatedTimeOff));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCompany(@PathVariable("id") @Min(1) int id) {
    timeOffService.delete(id);
    return ResponseEntity.ok(String.format("TimeOff with id = %s is deleted!", id));
  }

  @PatchMapping(path = "/{id}", consumes = {"application/merge-patch+json"})
  public ResponseEntity<TimeOffResponse> patch(@PathVariable int id, @RequestBody TimeOffPatchRequest data) {

    Optional<TimeOff> timeOff = timeOffService.findById(id);

    if(timeOff.isPresent() && data!=null) {
      TimeOff patchedTimeOff = patchMapper.mapObjectsToTimeOffEntity(data, timeOff.get());
      return ResponseEntity.ok(mapper.convertToTimeOffResponse(timeOffService.update(patchedTimeOff, id)));
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  private TimeOff fetchAndSetEmployeeAndApprover(@RequestBody @Valid TimeOffRequest timeOffRequest) {

    TimeOff timeOff = mapper.convertToTimeOffEntity(timeOffRequest);

    EmployeeResponseDto employeeResponseDto = employeeService.getOne(timeOffRequest.getEmployeeId()).getBody();
    Employee employee = mapper.convertToEmployeeEntity(employeeResponseDto);

    EmployeeResponseDto approverResponseDto = employeeService.getOne(timeOffRequest.getApproverId()).getBody();
    Employee approver = mapper.convertToEmployeeEntity(approverResponseDto);

    timeOff.setEmployee(employee);
    timeOff.setApprover(approver);

    return timeOff;
  }
}