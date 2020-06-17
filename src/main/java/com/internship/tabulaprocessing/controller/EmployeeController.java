package com.internship.tabulaprocessing.controller;


import com.internship.tabulaprocessing.dto.EmployeeDto;
import com.internship.tabulaprocessing.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll(@RequestParam(defaultValue = "0") int page) {
        return employeeService.getAll(page);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@Valid @RequestBody EmployeeDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getOne(@PathVariable String id) {

        int num = Integer.parseInt(id);
        return employeeService.getOne(num);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)  {

        int num = Integer.parseInt(id);
        return employeeService.deleteById(num);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable String id,
                                              @Valid @RequestBody EmployeeDto employeeDto) {
        int num = Integer.parseInt(id);
        return employeeService.update(num, employeeDto);
    }
}