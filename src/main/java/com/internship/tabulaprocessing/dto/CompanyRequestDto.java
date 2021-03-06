package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CompanyRequestDto {

  @NotBlank private String name;

  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 5, fraction = 2)
  private double discountRate;

  @NotBlank private String address;

  @NotBlank private String country;

  @NotBlank private String city;

  @Pattern(
      regexp = "^[A-Za-z]{2,4}(?=.{2,12}$)[-_\\s0-9]*(?:[a-zA-Z][-_\\s0-9]*){0,2}$",
      message = "Not a valid VAT number")
  private String vatNumber;
}
