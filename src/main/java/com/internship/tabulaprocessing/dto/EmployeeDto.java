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
public class EmployeeDto {
    @Min(value = 0)
    protected int id;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=5, fraction=2)
    private BigDecimal ratePerHour;

    @NotNull
    private String accountId;
    @NotNull
    private String departmentId;

    AccountDto accountDto;
    DepartmentDTO departmentDto;


}
