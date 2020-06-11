package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponseDto {

  private int id;

  private String name;

  private String discountRate;

  private String address;

  private String country;

  private String city;

  private String vatNumber;

  // private List<Customer> customers;
}
