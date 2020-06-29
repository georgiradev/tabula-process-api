package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CustomerRequestDto {

  @Min(value = 1)
  private int accountId;

  @NotBlank
  private String phone;

  @Min(value = 1)
  private int companyId;
}
