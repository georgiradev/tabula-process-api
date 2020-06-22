package com.internship.tabulaprocessing.controller;


import com.internship.tabulaprocessing.dto.EmployeeRequestDto;
import com.internship.tabulaprocessing.dto.EmployeeResponseDto;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public PagedResult<EmployeeResponseDto> getAll(QueryParameter queryParameter) {
        return employeeService.getAll(queryParameter);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> create(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        return employeeService.create(employeeRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getOne(@PathVariable String id) {

        int num = Integer.parseInt(id);
        return employeeService.getOne(num);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)  {

        int num = Integer.parseInt(id);
        return employeeService.deleteById(num);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> update(@PathVariable String id,
                                                      @RequestBody EmployeeRequestDto employeeRequestDto) {
        int num = Integer.parseInt(id);
        return employeeService.update(num, employeeRequestDto);
    }
}