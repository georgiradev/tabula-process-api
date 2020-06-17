package com.internship.tabulaprocessing.dto;

import com.internship.tabulaprocessing.todelete.AccountDTOView;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
public class EmployeeDto {
    @Min(value = 0)
    protected int id;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=5, fraction=2)
    private BigDecimal ratePerHour;

    private String accountId;
    private String departmentId;

    AccountDTOView accountDTOView;
    DepartmentDTO departmentDTO;
}
