package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.EmployeeUpdateRequestDto;
import com.internship.tabulaprocessing.dto.EmployeeRequestDto;
import com.internship.tabulaprocessing.dto.EmployeeResponseDto;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.service.DepartmentService;
import com.internship.tabulaprocessing.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final Mapper mapper;
  private final DepartmentService departmentService;
  private final PatchMapper patchMapper;

  public EmployeeController(EmployeeService employeeService, Mapper mapper, DepartmentService departmentService, PatchMapper patchMapper) {
    this.employeeService = employeeService;
    this.mapper = mapper;
    this.departmentService = departmentService;
    this.patchMapper = patchMapper;
  }

  @GetMapping
  public PagedResult<EmployeeResponseDto> getAll(QueryParameter queryParameter) {
    return employeeService.getAll(queryParameter.getPageable());
  }

  @PostMapping
  public ResponseEntity<EmployeeResponseDto> create(
      @Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
    return new ResponseEntity<>(employeeService.create(employeeRequestDto), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeResponseDto> getOne(@PathVariable int id) {
    return ResponseEntity.ok(employeeService.getOne(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    int num = Integer.parseInt(id);
    employeeService.deleteById(num);
    return ResponseEntity.ok(String.format("Employee with id of %s was deleted successfully!", id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<EmployeeResponseDto> update(
      @PathVariable int id, @Valid @RequestBody EmployeeUpdateRequestDto employeeUpdateRequestDto) {

    Employee employee = employeeService.findById(id);
    Department department = departmentService.findById(employeeUpdateRequestDto.getDepartmentId());

    employee.setDepartment(department);
    employee.setRatePerHour(employeeUpdateRequestDto.getRatePerHour());

    return ResponseEntity.ok(employeeService.update(employee));
  }

  @PatchMapping(path = "/{id}", consumes = {"application/merge-patch+json"})
  public ResponseEntity<EmployeeResponseDto> patch(
          @PathVariable int id, @RequestBody EmployeeUpdateRequestDto employeePatchRequestDto) {

    Employee employee = employeeService.findById(id);

    Employee employeePatch = patchMapper.patchEmployee(employeePatchRequestDto, employee);

    return ResponseEntity.ok(employeeService.update(employeePatch));
  }
}
