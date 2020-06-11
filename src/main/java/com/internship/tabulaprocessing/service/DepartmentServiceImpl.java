package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable).toList();
    }

    @Override
    public Department findById(int id) {

        Optional<Department> optional = departmentRepository.findById(id);
        if(!optional.isPresent()){
            throw new EntityNotFoundException(String.format("Departmant with id %s, not found.",id));
        }

        return optional.get();
    }

    @Override
    public Department findByName(String name) {

        Optional<Department> optional = departmentRepository.findByName(name);
        if (!optional.isPresent()) {
            throw new RuntimeException(String.format("Department with name %s, not found!",name));
        }
        return optional.get();
    }

    @Override
    public Department persist(Department department) {

        department.setId(0);
        Optional<Department> optional = departmentRepository.findByName(department.getName());
        if (optional.isPresent()) {
            throw new RuntimeException(String.format("Departmant with name %s, already exists",department.getName()));
        }
        return departmentRepository.save(department);
    }

    @Override
    public Department update(Department department,int id) {

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
