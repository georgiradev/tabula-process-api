package com.internship.tabulaprocessing.service;

import com.internship.tabulacore.entity.Account;
import com.internship.tabulacore.repository.AccountRepository;
import com.internship.tabulaprocessing.controller.PaginationUtil;
import com.internship.tabulaprocessing.dto.EmployeeDto;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.DepartmentRepository;
import com.internship.tabulaprocessing.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.function.Function;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private AccountRepository accountRepository;
    private DepartmentRepository departmentRepository;
    private Mapper mapper;

    public EmployeeService(EmployeeRepository employeeRepository, AccountRepository accountRepository, DepartmentRepository departmentRepository, Mapper mapper) {
        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
    }

    public ResponseEntity<Page<EmployeeDto>> getAll(Pageable pageable){

        Page<Employee> page = employeeRepository.findAll(pageable);

        Page<EmployeeDto> dtoPage = page.map(new Function<Employee, EmployeeDto>() {
            @Override
            public EmployeeDto apply(Employee entity) {
                EmployeeDto employeeDto = mapper.convertToEmployeeDTO(entity);
                Optional<Account> account = accountRepository.findById(entity.getAccountId());
                employeeDto.setAccountDto(mapper.convertToAccountDto(account.get()));
                employeeDto.setDepartmentDto(mapper.convertToDepartmentDTO(entity.getDepartment()));
                employeeDto.setDepartmentId(String.valueOf(entity.getDepartment().getId()));
                return employeeDto;
            }
        });

        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(page,"http://localhost:8080/employees"))
                .body(dtoPage);
    }

    public ResponseEntity<EmployeeDto> getOne(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if(!employee.isPresent()){
            throw new EntityNotFoundException("The employee was not found.");
        }

        Account account =accountRepository.findById(employee.get().getAccountId()).get();
        EmployeeDto employeeDto = mapper.convertToEmployeeDTO(employee.get());
        employeeDto.setDepartmentDto(mapper.convertToDepartmentDTO(employee.get().getDepartment()));
        employeeDto.setAccountDto(mapper.convertToAccountDto(account));
        employeeDto.setDepartmentId(String.valueOf(employee.get().getDepartment().getId()));
        return  ResponseEntity.ok(employeeDto);
    }

    public ResponseEntity<EmployeeDto> create(EmployeeDto employeeDto) {

        Optional<Account> account = accountRepository.findById(Integer.parseInt(employeeDto.getAccountId()));
        if(!account.isPresent()){
            throw new EntityNotFoundException("Account was not found.");
        }
        Optional<Department> department = departmentRepository.findById(Integer.parseInt(employeeDto.getDepartmentId()));
        if(!department.isPresent()){
            throw new EntityNotFoundException("Department was not found.");
        }

        Employee employee = mapper.convertToEmployeeEntity(employeeDto);
        employee.setDepartment(department.get());
        employeeRepository.save(employee);

        employeeDto = mapper.convertToEmployeeDTO(employee);
        employeeDto.setDepartmentDto(mapper.convertToDepartmentDTO(department.get()));
        employeeDto.setDepartmentId(String.valueOf(department.get().getId()));
        employeeDto.setAccountDto(mapper.convertToAccountDto(account.get()));
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteById(int id) {

        Optional<Employee> optional = employeeRepository.findById(id);

        if(!optional.isPresent()){
            throw new EntityNotFoundException(String.format("Employee with id of %s was not found!", id));
        }

        employeeRepository.deleteById(id);
        return ResponseEntity.ok(String.format("Employee with id of %s was deleted successfully!", id));
    }

    public ResponseEntity<EmployeeDto> update(int id, EmployeeDto employeeDto)  {

        Optional<Employee> optional = employeeRepository.findById(id);
        if(!optional.isPresent()){
            throw new EntityNotFoundException(String.format("Employee with id of %s was not found!", id));
        }

        Optional<Department> department = departmentRepository.findById(Integer.parseInt(employeeDto.getDepartmentId()));
        if(!department.isPresent()){
            throw new EntityNotFoundException("Department not found.");
        }

        Optional<Account> account = accountRepository.findById(Integer.parseInt(employeeDto.getAccountId()));
        if(!account.isPresent()){
            throw new EntityNotFoundException("Account not found.");
        }

        Employee employee = mapper.convertToEmployeeEntity(employeeDto);
        employee.setDepartment(department.get());
        employee.setId(id);
        employeeRepository.save(employee);

        employeeDto = mapper.convertToEmployeeDTO(employee);
        employeeDto.setDepartmentId(String.valueOf(department.get().getId()));
        employeeDto.setDepartmentDto(mapper.convertToDepartmentDTO(department.get()));
        employeeDto.setAccountDto(mapper.convertToAccountDto(account.get()));
        return ResponseEntity.ok(employeeDto);
    }
}
