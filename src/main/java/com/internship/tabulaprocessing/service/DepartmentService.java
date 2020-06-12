package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Department;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {

    List<Department> findAll(Pageable pageable);

    Department findById(int id);

    Department findByName(String name);

    Department persist(Department department);

    Department update(Department department, int id);

    void delete(int id);
}
