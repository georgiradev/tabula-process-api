package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CustomerPatchDto {

  @Min(value = 1)
  private Integer accountId;

  @Size(min = 3, message = "Invalid phone")
  private String phone;

  @Min(value = 1)
  private Integer companyId;
}