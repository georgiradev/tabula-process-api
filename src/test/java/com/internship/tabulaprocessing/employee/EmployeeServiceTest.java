package com.internship.tabulaprocessing.employee;


import com.internship.tabulacore.entity.Account;
import com.internship.tabulacore.repository.AccountRepository;
import com.internship.tabulaprocessing.dto.EmployeeDto;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.repository.DepartmentRepository;
import com.internship.tabulaprocessing.repository.EmployeeRepository;
import com.internship.tabulaprocessing.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void getAll() {
        List<Employee> employees = new ArrayList<>();
        Page<Employee> page = new PageImpl<>(employees);

//        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);
//        assertEquals(employees, employeeService.getAll(new Pageble);
    }

    @Test
    void CreateIfAccountNotFound(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(7);
        employeeDto.setRatePerHour(BigDecimal.valueOf(23.45));
        employeeDto.setAccountId("2");
        employeeDto.setDepartmentId("2");
        when(accountRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.create(employeeDto));

    }

    @Test
    void CreateIfDepartmentNotFound(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(7);
        employeeDto.setRatePerHour(BigDecimal.valueOf(23.45));
        employeeDto.setAccountId("2");
        employeeDto.setDepartmentId("2");

        Account account = new Account();
        when(accountRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(account));

        when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.create(employeeDto));

    }

    @Test
    void getOne() {
        when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.getOne(Mockito.anyInt()));
    }

    @Test
    void deleteById() {
        Employee employee = new Employee();
        employee.setId(1);
        when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> employeeService.deleteById(employee.getId()));
    }

    @Test
    void update() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(5);
        when(employeeRepository.findById(5)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.update(5, employeeDto));
    }
}