package com.internship.tabulaprocessing.service;

import com.internship.tabulacore.entity.Account;
import com.internship.tabulacore.repository.AccountRepository;
import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.Company;
import com.internship.tabulaprocessing.entity.Customer;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.repository.CompanyRepository;
import com.internship.tabulaprocessing.repository.CustomerRepository;
import com.internship.tabulaprocessing.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final AccountRepository accountRepository;
  private final CompanyRepository companyRepository;
  private final OrderRepository orderRepository;

  public Optional<Customer> save(Customer customer) {
    validateCustomer(customer);
    checkAccount(customer);

    return Optional.of(customerRepository.save(customer));
  }

  private void checkAccount(Customer customer) {
    Optional<Customer> userUsingAccount = customerRepository.findByAccountId(customer.getAccountId());

    if(userUsingAccount.isPresent() && customer.getId() != userUsingAccount.get().getId()) {
      throw new EntityAlreadyPresentException("Account is taken by another user");
    }
  }

  public Account getAccount(int accountId) {
    Optional<Account> account = accountRepository.findById(accountId);

    if (account.isEmpty()) {
      throw new EntityNotFoundException("Account not found with id " + accountId);
    }

    return account.get();
  }

  public List<String> getOrdersIds(int customerId) {
    return orderRepository.findOrdersByCustomerId(customerId).stream()
        .map(String::valueOf)
        .collect(Collectors.toList());
  }

  private void validateCustomer(Customer customer) {
    Optional<Company> company = companyRepository.findById(customer.getCompany().getId());

    if (company.isEmpty()) {
      throw new EntityNotFoundException(
          "Company not found with id " + customer.getCompany().getId());
    }

    customer.setCompany(company.get());
    List<Customer> foundCustomer =
        customerRepository.findIfPresent(customer.getAccountId(), customer.getCompany().getId());

    if (!foundCustomer.isEmpty()) {
      throw new EntityAlreadyPresentException("Customer already in database");
    }
  }

  public Customer find(int id) {
    return customerRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
  }

  public Optional<Customer> update(int id, Customer customer) {
    Optional<Customer> oldCustomerDetails = customerRepository.findById(id);

    if (oldCustomerDetails.isEmpty()) {
      throw new EntityNotFoundException("Customer not found with id " + id);
    }
    Optional<Company> foundCompany = companyRepository.findById(customer.getCompany().getId());

    if (foundCompany.isEmpty()) {
      throw new EntityNotFoundException(
          "Company not found with id " + customer.getCompany().getId());
    }
    customer.setCompany(foundCompany.get());
    customer.setOrders(oldCustomerDetails.get().getOrders());
    customer.setId(oldCustomerDetails.get().getId());
    checkAccount(customer);

    return Optional.of(customerRepository.saveAndFlush(customer));
  }

  public Page<Customer> findAll(QueryParameter queryParameter) {
    return customerRepository.findAll(queryParameter.getPageable());
  }

  public void delete(int id) {
    find(id);
    customerRepository.deleteById(id);
  }

  public Customer findByAccountId(int id) {
    return customerRepository
        .findByAccountId(id)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found with account id " + id));
  }
}