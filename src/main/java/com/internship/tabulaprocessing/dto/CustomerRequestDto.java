package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class CustomerRequestDto {

  @Min(value = 1)
  private int accountId;

  @NotBlank private String phone;

  @Min(value = 1)
  private int companyId;

  private List<String> orderIds;
}
