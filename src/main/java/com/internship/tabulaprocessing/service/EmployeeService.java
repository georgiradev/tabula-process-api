package com.internship.tabulaprocessing.service;

import com.internship.tabulacore.entity.Account;
import com.internship.tabulacore.repository.AccountRepository;
import com.internship.tabulaprocessing.dto.EmployeeRequestDto;
import com.internship.tabulaprocessing.dto.EmployeeResponseDto;
import com.internship.tabulaprocessing.entity.Customer;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.exception.IncorrectDataInputException;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.CustomerRepository;
import com.internship.tabulaprocessing.repository.DepartmentRepository;
import com.internship.tabulaprocessing.repository.EmployeeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

  private EmployeeRepository employeeRepository;
  private AccountRepository accountRepository;
  private DepartmentRepository departmentRepository;
  private CustomerRepository customerRepository;
  private Mapper mapper;

  public EmployeeService(
          EmployeeRepository employeeRepository,
          AccountRepository accountRepository,
          DepartmentRepository departmentRepository,
          CustomerRepository customerRepository,
          Mapper mapper) {
    this.employeeRepository = employeeRepository;
    this.accountRepository = accountRepository;
    this.departmentRepository = departmentRepository;
    this.customerRepository = customerRepository;
    this.mapper = mapper;
  }

  public PagedResult<EmployeeResponseDto> getAll(Pageable pageable) {
    Page<Employee> employees = employeeRepository.findAll(pageable);
    return new PagedResult<>(
            getEmployeeResponseDtoList(employees),
            pageable.getPageNumber(),
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

  public EmployeeResponseDto getOne(int id) {
    Optional<Employee> employee = employeeRepository.findById(id);

    if (!employee.isPresent()) {
      throw new EntityNotFoundException("The employee was not found.");
    }

    EmployeeResponseDto employeeResponseDto = mapper.convertToEmployeeResponseDto(employee.get());
    Optional<Account> account = accountRepository.findById(employee.get().getAccountId());

    if (account.isPresent()) {
      employeeResponseDto.setAccount(mapper.convertToAccountDto(account.get()));
    }
    return employeeResponseDto;
  }

  public EmployeeResponseDto create(EmployeeRequestDto employeeRequestDto) {

    Optional<Account> account = accountRepository.findById(employeeRequestDto.getAccountId());
    if (!account.isPresent()) {
      throw new EntityNotFoundException("Account was not found.");
    }
    Optional<Department> department =
            departmentRepository.findById(employeeRequestDto.getDepartmentId());
    if (!department.isPresent()) {
      throw new EntityNotFoundException("Department was not found.");
    }

    isAccountAlreadyTaken(account.get().getId());

    Employee employee = mapper.convertToEmployeeEntity(employeeRequestDto);
    employee.setDepartment(department.get());
    employee.setAccount(account.get());
    employeeRepository.save(employee);

    EmployeeResponseDto employeeResponseDto = mapper.convertToEmployeeResponseDto(employee);
    employeeResponseDto.setDepartment(mapper.convertToDepartmentDTO(department.get()));
    employeeResponseDto.setAccount(mapper.convertToAccountDto(account.get()));
    return employeeResponseDto;
  }

  public void deleteById(int id) {
    try {
      employeeRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException(String.format("Employee with id of %s was not found!", id));
    }
  }

  public EmployeeResponseDto update(Employee employee) {

    try {
      employeeRepository.save(employee);
    }catch(Exception e){
      throw new IncorrectDataInputException("Incorrect data for Employee.");
    }
    EmployeeResponseDto employeeResponseDto = mapper.convertToEmployeeResponseDto(employee);
    employeeResponseDto.setAccount(mapper.convertToAccountDto(employee.getAccount()));
    return employeeResponseDto;
  }

  private void isAccountAlreadyTaken(int accountId){

    Optional<Customer> customer = customerRepository.findByAccountId(accountId);
    if(customer.isPresent()) {
      throw new EntityAlreadyPresentException("This account is used for Customer");
    }

    Optional<Employee> employee = employeeRepository.findByAccountId(accountId);
    if(employee.isPresent()){
      throw new EntityAlreadyPresentException("This account is used for another Employee");
    }
  }

  public Employee findById(int id){
    Optional<Employee> employee = employeeRepository.findById(id);
    if(!employee.isPresent()){
      throw new EntityNotFoundException("Employee not found");
    }
    Optional<Account> account = accountRepository.findById(employee.get().getAccountId());
    employee.get().setAccount(account.get());
    return employee.get();
  }
}

