package com.internship.tabulaprocessing.service;

import com.internship.tabulacore.entity.Account;
import com.internship.tabulacore.repository.AccountRepository;
import com.internship.tabulaprocessing.dto.EmployeeRequestDto;
import com.internship.tabulaprocessing.dto.EmployeeResponseDto;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.DepartmentRepository;
import com.internship.tabulaprocessing.repository.EmployeeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

  private EmployeeRepository employeeRepository;
  private AccountRepository accountRepository;
  private DepartmentRepository departmentRepository;
  private Mapper mapper;

  public EmployeeService(
      EmployeeRepository employeeRepository,
      AccountRepository accountRepository,
      DepartmentRepository departmentRepository,
      Mapper mapper) {
    this.employeeRepository = employeeRepository;
    this.accountRepository = accountRepository;
    this.departmentRepository = departmentRepository;
    this.mapper = mapper;
  }

  public PagedResult<EmployeeResponseDto> getAll(Pageable pageable) {
    Page<Employee> employees = employeeRepository.findAll(pageable);
    return new PagedResult<>(
        getEmployeeResponseDtoList(employees),
        pageable.getPageNumber() + 1,
        employees.getTotalPages(),
        employees.getTotalElements());
  }

  private List<EmployeeResponseDto> getEmployeeResponseDtoList(Page<Employee> employees) {
    Page<EmployeeResponseDto> dtoPage =
        employees.map(
            entity -> {
              EmployeeResponseDto employeeDto = mapper.convertToEmployeeResponseDto(entity);
              Optional<Account> account = accountRepository.findById(entity.getAccountId());
              employeeDto.setAccount(mapper.convertToAccountDto(account.get()));
              return employeeDto;
            });
    return dtoPage.toList();
  }

  public ResponseEntity<EmployeeResponseDto> getOne(int id) {
    Optional<Employee> employee = employeeRepository.findById(id);

    if (!employee.isPresent()) {
      throw new EntityNotFoundException("The employee was not found.");
    }

    //TODO:!!!
    EmployeeResponseDto employeeResponseDto = mapper.convertToEmployeeResponseDto(employee.get());
    Optional<Account> account = accountRepository.findById(employee.get().getAccountId());
    //employeeResponseDto.setAccount(mapper.convertToAccountDto(account.get()));
    return ResponseEntity.ok(employeeResponseDto);
  }

  public ResponseEntity<EmployeeResponseDto> create(EmployeeRequestDto employeeRequestDto) {

    Optional<Account> account = accountRepository.findById(employeeRequestDto.getAccountId());
    if (!account.isPresent()) {
      throw new EntityNotFoundException("Account was not found.");
    }
    Optional<Department> department =
        departmentRepository.findById(employeeRequestDto.getDepartmentId());
    if (!department.isPresent()) {
      throw new EntityNotFoundException("Department was not found.");
    }

    Employee employee = mapper.convertToEmployeeEntity(employeeRequestDto);
    employee.setDepartment(department.get());
    employee.setAccount(account.get());
    employeeRepository.save(employee);

    EmployeeResponseDto employeeResponseDto = mapper.convertToEmployeeResponseDto(employee);
    employeeResponseDto.setDepartment(mapper.convertToDepartmentDTO(department.get()));
    employeeResponseDto.setAccount(mapper.convertToAccountDto(account.get()));
    return new ResponseEntity<>(employeeResponseDto, HttpStatus.CREATED);
  }

  public ResponseEntity<?> deleteById(int id) {

    try {
      employeeRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException(String.format("Employee with id of %s was not found!", id));
    }
    return ResponseEntity.ok(String.format("Employee with id of %s was deleted successfully!", id));
  }

  public ResponseEntity<EmployeeResponseDto> update(int id, EmployeeRequestDto employeeRequestDto) {

    Optional<Employee> optional = employeeRepository.findById(id);
    if (!optional.isPresent()) {
      throw new EntityNotFoundException(String.format("Employee with id of %s was not found!", id));
    }

    Optional<Department> department =
        departmentRepository.findById(employeeRequestDto.getDepartmentId());
    if (!department.isPresent()) {
      throw new EntityNotFoundException("Department not found.");
    }

    Optional<Account> account = accountRepository.findById(employeeRequestDto.getAccountId());
    if (!account.isPresent()) {
      throw new EntityNotFoundException("Account not found.");
    }

    Employee employee = mapper.convertToEmployeeEntity(employeeRequestDto);
    employee.setAccount(account.get());
    employee.setDepartment(department.get());
    employee.setId(id);
    employeeRepository.save(employee);

    EmployeeResponseDto employeeResponseDto = mapper.convertToEmployeeResponseDto(employee);
    employeeResponseDto.setAccount(mapper.convertToAccountDto(account.get()));
    return ResponseEntity.ok(employeeResponseDto);
  }
}
