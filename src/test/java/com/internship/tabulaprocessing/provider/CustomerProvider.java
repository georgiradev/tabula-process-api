package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.Customer;

public class CustomerProvider {

    public static Customer getCustomerInstance() {
        Customer customer = new Customer();

        customer.setId(1);
        customer.setAccountId(1);
        customer.setPhone("88888888");

        return customer;
    }
}
