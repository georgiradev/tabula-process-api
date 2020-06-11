package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

  Page<Department> findAll(Pageable pageable);

  Optional<Department> findByName(String name);
}
