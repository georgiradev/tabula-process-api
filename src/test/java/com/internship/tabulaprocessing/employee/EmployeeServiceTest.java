package com.internship.tabulaprocessing.employee;


import com.internship.tabulacore.entity.Account;
import com.internship.tabulacore.repository.AccountRepository;
import com.internship.tabulaprocessing.dto.EmployeeRequestDto;
import com.internship.tabulaprocessing.dto.EmployeeResponseDto;
import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void CreateIfAccountNotFound(){

        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();

        employeeRequestDto.setRatePerHour(BigDecimal.valueOf(23.45));
        employeeRequestDto.setAccountId(2);
        employeeRequestDto.setDepartmentId(2);
        when(accountRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.create(employeeRequestDto));

    }

    @Test
    void getAll() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setRatePerHour(BigDecimal.valueOf(5));
        employee.setAccountId(1);

        Employee employee2 = new Employee();
        employee2.setId(2);
        employee2.setRatePerHour(BigDecimal.valueOf(55));
        employee2.setAccountId(1);

        List<Employee> expectedEmployee = new ArrayList<>();
        expectedEmployee.add(employee);
        expectedEmployee.add(employee2);

        Pageable pageable = PageRequest.of(1,2);
        Page<Employee> expectedPage = new PageImpl<>(expectedEmployee, pageable, expectedEmployee.size());

        doReturn(expectedPage).when(employeeRepository).findAll(pageable);
        PagedResult<EmployeeResponseDto> actual = employeeService.getAll(pageable);

        assertNotNull(actual);
        assertEquals(mapper.convertToEmployeeResponseDtoList(expectedPage.getContent()), actual.getElements());
        assertEquals(expectedPage.getContent().get(0), employee);
        assertEquals(expectedPage.getTotalPages(), actual.getNumOfTotalPages());
    }

    @Test
    void DeleteById(){

        employeeService.deleteById(89);
        verify(employeeRepository, times(1)).deleteById(eq(89));
    }

    @Test
    void CreateIfDepartmentNotFound(){

        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setRatePerHour(BigDecimal.valueOf(23.45));
        employeeRequestDto.setAccountId(2);
        employeeRequestDto.setDepartmentId(2);

        Account account = new Account();
        when(accountRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(account));

        when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.create(employeeRequestDto));

    }

    @Test
    void getOne() {
        when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.getOne(Mockito.anyInt()));
    }

    @Test
    void update() {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        when(employeeRepository.findById(5)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.update(5, employeeRequestDto));
    }
}
