package com.internship.tabulaprocessing.customer;

import com.internship.tabulacore.entity.Account;
import com.internship.tabulacore.repository.AccountRepository;
import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.Company;
import com.internship.tabulaprocessing.entity.Customer;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.provider.AccountProvider;
import com.internship.tabulaprocessing.provider.CompanyProvider;
import com.internship.tabulaprocessing.provider.CustomerProvider;
import com.internship.tabulaprocessing.repository.CompanyRepository;
import com.internship.tabulaprocessing.repository.CustomerRepository;
import com.internship.tabulaprocessing.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

  @InjectMocks private CustomerService customerService;

  @Mock private CustomerRepository customerRepository;

  @Mock private AccountRepository accountRepository;

  @Mock private CompanyRepository companyRepository;

  @Mock private Mapper mapper;

  @Test
  void testCustomerSave() {
    Customer customer = assembleObject();

    when(companyRepository.findById(any(Integer.class)))
        .thenReturn(Optional.of(customer.getCompany()));
    when(customerRepository.save(customer)).thenReturn(customer);

    assertEquals(Optional.of(customer), customerService.save(customer));
  }

  @Test
  void testCustomerDuplicateSaveMustFail() {
    Customer customer = assembleObject();

    when(companyRepository.findById(any(Integer.class)))
        .thenReturn(Optional.of(customer.getCompany()));
    when(customerRepository.findIfPresent(customer.getAccountId(), customer.getCompany().getId()))
        .thenReturn(Collections.singletonList(customer));

    assertThrows(EntityAlreadyPresentException.class, () -> customerService.save(customer));
  }

  @Test
  void testCustomerSaveNonExistingCompanyMustFail() {
    Customer customer = assembleObject();

    when(companyRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> customerService.save(customer));
  }

  @Test
  void testFindCustomer() {
    Customer customer = assembleObject();

    when(customerRepository.findById(any(Integer.class))).thenReturn(Optional.of(customer));

    assertEquals(Optional.of(customer), customerService.find(customer.getId()));
  }

  @Test
  void testFindCustomerThatNotExistShouldFail() {
    Customer customer = assembleObject();

    assertThrows(EntityNotFoundException.class, () -> customerService.find(customer.getId()));
  }

  @Test
  void deleteCustomer() {
    Customer customer = assembleObject();

    when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
    customerService.delete(customer.getId());
    when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> customerService.delete(customer.getId()));
  }

  @Test
  void deleteCustomerByNonExistingIdShouldFail() {
    Customer customer = assembleObject();

    when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> customerService.delete(customer.getId()));
  }

  @Test
  void testFindAllCustomers() {
    List<Customer> customers = Collections.singletonList(assembleObject());
    Page<Customer> paging = new PageImpl<>(customers);
    QueryParameter queryParameter = new QueryParameter();

    when(customerRepository.findAll(any(Pageable.class))).thenReturn(paging);

    assertEquals(paging, customerService.findAll(queryParameter));
  }

  @Test
  void testFindAllCustomersButNoContentFound() {
    List<Customer> customers = Collections.emptyList();
    Page<Customer> paging = new PageImpl<>(customers);
    QueryParameter queryParameter = new QueryParameter();

    when(customerRepository.findAll(any(Pageable.class))).thenReturn(paging);

    assertEquals(paging, customerService.findAll(queryParameter));
  }

  @Test
  void testUpdateCustomer() {
    Customer customer = assembleObject();

    when(customerRepository.findById(any(Integer.class))).thenReturn(Optional.of(customer));
    when(companyRepository.findById(any(Integer.class))).thenReturn(Optional.of(customer.getCompany()));
    when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);

    assertEquals(Optional.of(customer), customerService.update(1, customer));
  }

  @Test
  void testUpdateCustomerByNonExistingIdShouldFail() {
    Customer customer = assembleObject();

    when(customerRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

    assertThrows(
        EntityNotFoundException.class, () -> customerService.update(customer.getId(), customer));
  }

  @Test
  void testUpdateCustomerByNonExistingAccountShouldFail() {
    Customer customer = assembleObject();

    assertThrows(
        EntityNotFoundException.class, () -> customerService.update(customer.getId(), customer));
  }

  Customer assembleObject() {
    Customer customer = CustomerProvider.getCustomerInstance();
    customer.setCompany(CompanyProvider.getCompanyInstance());
    Company company = CompanyProvider.getCompanyInstance();
    customer.setCompany(company);
    company.setCustomers(Collections.singletonList(customer));

    return customer;
  }
}
