package com.internship.tabulaprocessing.dto;

import com.internship.tabulacore.dto.AccountDto;
import com.internship.tabulacore.entity.Account;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class EmployeeResponseDto {

    protected int id;

    private BigDecimal ratePerHour;

    AccountDto account;

    DepartmentDTO department;

}
