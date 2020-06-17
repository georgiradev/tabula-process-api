package com.internship.tabulaprocessing.service;

import com.internship.tabulacore.entity.Account;
import com.internship.tabulacore.repository.AccountRepository;
import com.internship.tabulaprocessing.dto.EmployeeDto;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.DepartmentRepository;
import com.internship.tabulaprocessing.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private AccountRepository accountRepository;
    private DepartmentRepository departmentRepository;
    @Autowired
    private Mapper mapper;

    public EmployeeService(EmployeeRepository employeeRepository, AccountRepository accountRepository,
                           DepartmentRepository departmentRepository) {

        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
        this.departmentRepository = departmentRepository;
    }

    public ResponseEntity<List<EmployeeDto>> getAll(int num){
        Pageable pageable = PageRequest.of(num, 10);
        Page<Employee> page = employeeRepository.findAll(pageable);
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for(Employee employee:page.toList()){
            employeeDtoList.add(mapper.convertToEmployeeDTO(employee));
        }
        return new ResponseEntity<>(employeeDtoList, HttpStatus.OK);
    }

    public ResponseEntity<EmployeeDto> getOne(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if(!employee.isPresent()){
            throw new EntityNotFoundException("The employee was not found.");
        }

        EmployeeDto employeeDto = mapper.convertToEmployeeDTO(employee.get());
        employeeDto.setDepartmentDTO(mapper.coventToDepartmentDTO(employee.get().getDepartment()));
        employeeDto.setAccountDto(mapper.convertToAccountDTOView(employee.get().getAccount()));
        employeeDto.setAccountId(String.valueOf(employee.get().getAccount().getId()));
        employeeDto.setDepartmentId(String.valueOf(employee.get().getDepartment().getId()));
        return  ResponseEntity.ok(employeeDto);
    }

    public ResponseEntity<EmployeeDto> create(EmployeeDto employeeDto) {

        Optional<Account> account = accountRepository.findById(Integer.parseInt(employeeDto.getAccountId()));
        if(!account.isPresent()){
            throw new EntityNotFoundException("Account was not found.");
        }
        Optional<Department> department = departmentRepository.findById(Integer.parseInt(employeeDto.getDepartmentId()));
        if(!account.isPresent()){
            throw new EntityNotFoundException("Department was not found.");
        }

        Employee employee = mapper.convertToEmployeeEntity(employeeDto);
        employee.setAccount(account.get());
        employee.setDepartment(department.get());
        employeeRepository.save(employee);
        employeeDto = mapper.convertToEmployeeDTO(employee);
        employeeDto.setDepartmentDTO(mapper.coventToDepartmentDTO(department.get()));
        employeeDto.setAccountDto(mapper.convertToAccountDTOView(account.get()));
        employeeDto.setAccountId(String.valueOf(account.get().getId()));
        employeeDto.setDepartmentId(String.valueOf(department.get().getId()));
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
        employee.setAccount(account.get());
        employee.setId(id);
        employeeRepository.save(employee);
        return ResponseEntity.ok(mapper.convertToEmployeeDTO(employee));
    }
}
