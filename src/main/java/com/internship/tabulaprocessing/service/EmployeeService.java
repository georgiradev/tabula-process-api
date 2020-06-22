package com.internship.tabulaprocessing.service;

import com.internship.tabulacore.dto.AccountDto;
import com.internship.tabulacore.entity.Account;
import com.internship.tabulacore.repository.AccountRepository;
import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.dto.EmployeeRequestDto;
import com.internship.tabulaprocessing.dto.EmployeeResponseDto;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.DepartmentRepository;
import com.internship.tabulaprocessing.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
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

    public PagedResult<EmployeeResponseDto> getAll(QueryParameter queryParameter){

        Page<Employee> page = employeeRepository.findAll(queryParameter.getPageable());

        Page<EmployeeResponseDto> dtoPage = page.map(new Function<Employee, EmployeeResponseDto>() {
            @Override
            public EmployeeResponseDto apply(Employee entity) {
                EmployeeResponseDto employeeResponseDto = mapper.convertToEmployeeResponseDto(entity);
                employeeResponseDto.setAccountDto(mapper.convertToAccountDto(entity.getAccount()));
                employeeResponseDto.setDepartmentDto(mapper.convertToDepartmentDTO(entity.getDepartment()));
                return employeeResponseDto;
            }
        });

        return new PagedResult<>(
                dtoPage.toList(),
                page.getNumber(),
                page.getTotalPages());

    }

    public ResponseEntity<EmployeeResponseDto> getOne(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if(!employee.isPresent()){
            throw new EntityNotFoundException("The employee was not found.");
        }

        EmployeeResponseDto employeeResponseDto = mapper.convertToEmployeeResponseDto(employee.get());
        employeeResponseDto.setDepartmentDto(mapper.convertToDepartmentDTO(employee.get().getDepartment()));
        Optional<Account> account = accountRepository.findById(employee.get().getAccountId());
        employeeResponseDto.setAccountDto(mapper.convertToAccountDto(account.get()));
        return  ResponseEntity.ok(employeeResponseDto);
    }

    public ResponseEntity<EmployeeResponseDto> create(EmployeeRequestDto employeeRequestDto) {

        Optional<Account> account = accountRepository.findById(employeeRequestDto.getAccountId());
        if(!account.isPresent()){
            throw new EntityNotFoundException("Account was not found.");
        }
        Optional<Department> department = departmentRepository.findById(employeeRequestDto.getDepartmentId());
        if(!department.isPresent()){
            throw new EntityNotFoundException("Department was not found.");
        }

        Employee employee = mapper.convertToEmployeeEntity(employeeRequestDto);
        employee.setDepartment(department.get());
        employee.setAccount(account.get());
        employeeRepository.save(employee);

        EmployeeResponseDto employeeResponseDto = mapper.convertToEmployeeResponseDto(employee);
        employeeResponseDto.setDepartmentDto(mapper.convertToDepartmentDTO(department.get()));
        employeeResponseDto.setAccountDto(mapper.convertToAccountDto(account.get()));
        return new ResponseEntity<>(employeeResponseDto, HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteById(int id) {

        Optional<Employee> optional = employeeRepository.findById(id);

        if(!optional.isPresent()){
            throw new EntityNotFoundException(String.format("Employee with id of %s was not found!", id));
        }

        employeeRepository.deleteById(id);
        return ResponseEntity.ok(String.format("Employee with id of %s was deleted successfully!", id));
    }

    public ResponseEntity<EmployeeResponseDto> update(int id, EmployeeRequestDto employeeRequestDto)  {

        Optional<Employee> optional = employeeRepository.findById(id);
        if(!optional.isPresent()){
            throw new EntityNotFoundException(String.format("Employee with id of %s was not found!", id));
        }

        Optional<Department> department = departmentRepository.findById(employeeRequestDto.getDepartmentId());
        if(!department.isPresent()){
            throw new EntityNotFoundException("Department not found.");
        }

        Optional<Account> account = accountRepository.findById(employeeRequestDto.getAccountId());
        if(!account.isPresent()){
            throw new EntityNotFoundException("Account not found.");
        }

        Employee employee = mapper.convertToEmployeeEntity(employeeRequestDto);
        employee.setId(id);
        employeeRepository.save(employee);

        EmployeeResponseDto employeeResponseDto = mapper.convertToEmployeeResponseDto(employee);
        employeeResponseDto.setDepartmentDto(mapper.convertToDepartmentDTO(department.get()));
        employeeResponseDto.setAccountDto(mapper.convertToAccountDto(account.get()));
        return ResponseEntity.ok(employeeResponseDto);
    }
}
