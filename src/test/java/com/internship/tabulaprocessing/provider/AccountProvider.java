package com.internship.tabulaprocessing.provider;

import com.internship.tabulacore.entity.Account;

public class AccountProvider {
    public static Account getAccountInstance() {
        Account account = new Account();
        account.setId(6);
        account.setFullName("Some Name");
        account.setEmail("some@email.com");
        account.setDatetimeCreated(null);
        account.setDatetimeUpdated(null);
        return account;
    }
}
