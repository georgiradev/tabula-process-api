package com.internship.tabulaprocessing.employee;


import com.internship.tabulacore.entity.Account;
import com.internship.tabulacore.repository.AccountRepository;
import com.internship.tabulaprocessing.dto.EmployeeRequestDto;
import com.internship.tabulaprocessing.dto.EmployeeResponseDto;
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

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

<<<<<<< HEAD

=======
>>>>>>> 80218e8a9e6913c3c3e012dfb9fe8fdb64e0e66e

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
    void deleteById() {
        Employee employee = new Employee();
        employee.setId(1);
        when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> employeeService.deleteById(employee.getId()));
    }

    @Test
    void update() {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        when(employeeRepository.findById(5)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.update(5, employeeRequestDto));
    }
}
