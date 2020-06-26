package com.internship.tabulaprocessing.dto;

import com.internship.tabulacore.dto.AccountDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeResponseDto {

  protected int id;

  private BigDecimal ratePerHour;

  private AccountDto account;

  private DepartmentDTO department;
}
