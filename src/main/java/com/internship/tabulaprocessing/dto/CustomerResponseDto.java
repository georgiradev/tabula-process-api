package com.internship.tabulaprocessing.dto;

import com.internship.tabulacore.dto.AccountDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerResponseDto {

  private int id;

  private AccountDto account;

  private int accountId;

  private String phone;

  private CompanyResponseDto company;

  private int companyId;

  private List<String> ordersIds;
}
