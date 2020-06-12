package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.DepartmentDTO;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private Mapper mapper;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(QueryParameter queryParameter) {

        return ResponseEntity.ok(
                departmentService.findAll(queryParameter.getPageable()).stream()
                        .map(department -> mapper.coventToDepartmentDTO(department))
                        .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(
            @Valid @RequestBody DepartmentDTO departmentDTO) {

        Department departmentEntity = mapper.convertToDepartmentEntity(departmentDTO);
        departmentService.persist(departmentEntity);

        return ResponseEntity.ok(mapper.coventToDepartmentDTO(departmentEntity));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DepartmentDTO> getSingleDepartment(@PathVariable int id) {

        return ResponseEntity.ok(mapper.coventToDepartmentDTO(departmentService.findById(id)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @PathVariable int id, @Valid @RequestBody DepartmentDTO departmentDTO) {

        Department updatedDepartment = mapper.convertToDepartmentEntity(departmentDTO);
        departmentService.update(updatedDepartment, id);

        return ResponseEntity.ok(mapper.coventToDepartmentDTO(updatedDepartment));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable int id) {

        departmentService.delete(id);
        return ResponseEntity.ok(String.format("Department with id of %s, sucessfully deleted!", id));
    }
}
