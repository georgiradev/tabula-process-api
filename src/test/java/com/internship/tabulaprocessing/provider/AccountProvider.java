package com.internship.tabulaprocessing.provider;

import com.internship.tabulacore.entity.Account;

import java.time.LocalDateTime;

public class AccountProvider {

  public static Account getAccountInstance() {
    Account account = new Account();

    account.setId(1);
    account.setFullName("John Doe");
    account.setEmail("john@email.com");
    account.setDatetimeCreated(LocalDateTime.now());
    account.setPassword("12345");

    return account;
  }
}
