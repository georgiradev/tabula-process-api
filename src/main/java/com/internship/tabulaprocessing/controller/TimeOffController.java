package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.*;
import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.TimeOff;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.service.EmployeeService;
import com.internship.tabulaprocessing.service.TimeOffServiceImpl;
import com.internship.tabulaprocessing.service.TimeOffTypeService;
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

  private final TimeOffTypeService timeOffTypeService;
  private final TimeOffServiceImpl timeOffService;
  private final EmployeeService employeeService;
  private final PatchMapper patchMapper;
  private final Mapper mapper;

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
    PagedResult pagedResult = timeOffService.findAll(queryParameter.getPageable());
    pagedResult.setElements(mapper.convertToTimeOffResponse(pagedResult.getElements()));

    return ResponseEntity.ok(pagedResult);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TimeOffResponse> update(@PathVariable("id") @Min(1) int id,
                                                @Valid @RequestBody TimeOffRequest timeOffRequest) {

    TimeOff updatedTimeOff = timeOffService.update(fetchAndSetEmployeeAndApprover(timeOffRequest), id);
    return ResponseEntity.ok(mapper.convertToTimeOffResponse(updatedTimeOff));
  }

  @DeleteMapping("/manager/{id}")
  public ResponseEntity<String> deleteRequest(@PathVariable("id") @Min(1) int id) {
    timeOffService.deleteByManager(id);
    return ResponseEntity.ok(String.format("TimeOff with id = %s is deleted!", id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable("id") @Min(1) int id) {
    timeOffService.delete(id);
    return ResponseEntity.ok(String.format("TimeOff with id = %s is deleted!", id));
  }

  @PatchMapping(path = "manager/{id}", consumes = {"application/merge-patch+json"})
  public ResponseEntity<TimeOffResponse> patch(@PathVariable int id, @RequestBody TimeOffPatchStatusRequest data) {
    //PatchMapping for updating only status
    //ONLY MANAGER CAN UPDATE STATUS

    Optional<TimeOff> timeOff = timeOffService.findById(id);

    if(timeOff.isPresent() && data!=null) {
      TimeOff patchedTimeOff = patchMapper.mapObjectsToTimeOffEntity(data, timeOff.get());
      return ResponseEntity.ok(mapper.convertToTimeOffResponse(timeOffService.statusUpdate(patchedTimeOff, id)));
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PatchMapping(path = "/{id}", consumes = {"application/merge-patch+json"})
  public ResponseEntity<TimeOffResponse> patch(@PathVariable int id, @RequestBody TimeOffPatchRequest data) {
    //ordinary patch mapping, cannot update status

    Optional<TimeOff> timeOff = timeOffService.findById(id);

    if(timeOff.isPresent() && data!=null) {
      TimeOff patchedTimeOff = patchMapper.mapObjectsToTimeOffEntity(data, timeOff.get());
      return ResponseEntity.ok(mapper.convertToTimeOffResponse(timeOffService.update(patchedTimeOff, id)));
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  private TimeOff fetchAndSetEmployeeAndApprover(@RequestBody @Valid TimeOffRequest timeOffRequest) {

    EmployeeResponseDto employeeResponseDto = employeeService.getOne(timeOffRequest.getEmployeeId()).getBody();
    Employee employee = mapper.convertToEmployeeEntity(employeeResponseDto);

    EmployeeResponseDto approverResponseDto = employeeService.getOne(timeOffRequest.getApproverId()).getBody();
    Employee approver = mapper.convertToEmployeeEntity(approverResponseDto);

    TimeOff timeOff = mapper.convertToTimeOffEntity(timeOffRequest);
    timeOff.setTimeOffType(timeOffTypeService.getOneById(timeOffRequest.getTypeOfTimeOffId()));
    timeOff.setEmployee(employee);
    timeOff.setApprover(approver);

    return timeOff;
  }
}
