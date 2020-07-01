package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByAccountId(int accountId);
}
