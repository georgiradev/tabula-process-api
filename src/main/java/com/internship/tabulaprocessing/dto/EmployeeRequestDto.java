package com.internship.tabulaprocessing.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class EmployeeRequestDto {

  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 5, fraction = 2)
  private BigDecimal ratePerHour;

  @Min(value = 0, message = "id cannot be less than zero")
  private int accountId;

  @Min(value = 0, message = "id cannot be less than zero")
  private int departmentId;
}
