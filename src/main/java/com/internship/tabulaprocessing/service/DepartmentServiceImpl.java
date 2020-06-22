package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Page<Department> findAll(Pageable pageable) {

        return departmentRepository.findAll(pageable);
    }

    @Override
    public Department findById(int id) {

        Optional<Department> optional = departmentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(String.format("Departmеnt with id %s, not found.", id));
        }

        return optional.get();
    }

    @Override
    public Department findByName(String name) {

        Optional<Department> optional = departmentRepository.findByName(name);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(String.format("Department with name %s, not found!", name));
        }
        return optional.get();
    }

    @Override
    public Department persist(Department department) {

        department.setId(0);
        Optional<Department> optional = departmentRepository.findByName(department.getName());
        if (optional.isPresent()) {
            throw new EntityAlreadyPresentException(String.format("Departmеnt with name %s, already exists", department.getName()));
        }
        return departmentRepository.save(department);
    }

    @Override
    public Department update(Department department, int id) {

        findById(id);
        department.setId(id);

        return departmentRepository.save(department);
    }

    @Override
    public void delete(int id) {

        findById(id);
        departmentRepository.deleteById(id);

    }
}
