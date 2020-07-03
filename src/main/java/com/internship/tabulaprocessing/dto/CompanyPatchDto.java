package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CompanyPatchDto {

  @Size(min = 3, message = "Name must be at least 3 characters long")
  private String name;

  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 5, fraction = 2)
  private Double discountRate;

  @Size(min = 3, message = "Address must be at least 3 characters long")
  private String address;

  @Size(min = 3, message = "Country must be at least 3 characters long")
  private String country;

  @Size(min = 3, message = "City must be at least 3 characters long")
  private String city;

  @Pattern(
          regexp = "^[A-Za-z]{2,4}(?=.{2,12}$)[-_\\s0-9]*(?:[a-zA-Z][-_\\s0-9]*){0,2}$",
          message = "Not a valid VAT number")
  private String vatNumber;
}