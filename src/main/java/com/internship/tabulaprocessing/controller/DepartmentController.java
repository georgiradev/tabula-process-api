package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.DepartmentDTO;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    public ResponseEntity<PageResponse<DepartmentDTO>> getAllDepartments(QueryParameter queryParameter) {

        Page<Department> departmentPage = departmentService.findAll(queryParameter.getPageable());
        List<DepartmentDTO> responseList = departmentPage.stream()
                .map(department -> mapper.convertToDepartmentDTO(department))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageResponse<>(
                responseList, departmentPage.getTotalPages(), queryParameter.getPage()));
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(
            @Valid @RequestBody DepartmentDTO departmentDTO) {

        Department departmentEntity = mapper.convertToDepartmentEntity(departmentDTO);
        departmentService.persist(departmentEntity);

        return ResponseEntity.ok(mapper.convertToDepartmentDTO(departmentEntity));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DepartmentDTO> getSingleDepartment(@PathVariable @Min(1) int id) {

        return ResponseEntity.ok(mapper.convertToDepartmentDTO(departmentService.findById(id)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @PathVariable @Min(1) int id, @Valid @RequestBody DepartmentDTO departmentDTO) {

        Department updatedDepartment = mapper.convertToDepartmentEntity(departmentDTO);
        departmentService.update(updatedDepartment, id);

        return ResponseEntity.ok(mapper.convertToDepartmentDTO(updatedDepartment));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable @Min(1) int id) {

        departmentService.delete(id);
        return ResponseEntity.ok(String.format("Department with id of %s, sucessfully deleted!", id));
    }
}
