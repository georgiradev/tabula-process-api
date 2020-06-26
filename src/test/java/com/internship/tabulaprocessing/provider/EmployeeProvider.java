package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.Employee;

import java.math.BigDecimal;

public class EmployeeProvider {

  public static Employee getEmployeeInstance() {
    Employee employee = new Employee();
    employee.setId(1);
    employee.setRatePerHour(BigDecimal.valueOf(5));
    employee.setAccountId(1);
    employee.setAccount(AccountProvider.getAccountInstance());
    return employee;
  }
}
