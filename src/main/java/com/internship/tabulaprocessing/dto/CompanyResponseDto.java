package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyResponseDto {

  private int id;

  private String name;

  private double discountRate;

  private String address;

  private String country;

  private String city;

  private String vatNumber;

  private List<CustomerResponseDto> customers;
}
