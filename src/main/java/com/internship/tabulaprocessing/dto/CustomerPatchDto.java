package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class CustomerPatchDto {

  @Min(value = 1)
  private Integer accountId;

  private String phone;

  @Min(value = 1)
  private Integer companyId;
}
