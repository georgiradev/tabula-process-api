package com.internship.tabulaprocessing.employee;


import com.internship.tabulaprocessing.dto.EmployeeDto;
import com.internship.tabulaprocessing.entity.Employee;
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

import javax.persistence.EntityNotFoundException;
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

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void getAll() {
        List<Employee> employees = new ArrayList<>();
        Page<Employee> page = new PageImpl<>(employees);

        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);
        assertEquals(employees, employeeService.getAll(anyInt()).getBody());
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