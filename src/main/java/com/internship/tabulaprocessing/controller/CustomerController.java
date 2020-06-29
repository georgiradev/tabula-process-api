package com.internship.tabulaprocessing.controller;

import com.internship.tabulacore.entity.Account;
import com.internship.tabulaprocessing.dto.CustomerRequestDto;
import com.internship.tabulaprocessing.dto.CustomerResponseDto;
import com.internship.tabulaprocessing.entity.Customer;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;
  private final Mapper mapper;

  @PostMapping
  public ResponseEntity<CustomerResponseDto> createCustomer(
      @Valid @RequestBody CustomerRequestDto customerRequestDto) {

    Customer customer = mapper.customerDtoToEntity(customerRequestDto);
    Account account = customerService.getAccount(customer.getAccountId());
    Optional<Customer> savedCustomer = customerService.save(customer);

    if (savedCustomer.isPresent()) {
      CustomerResponseDto customerResponseDto = mapper.customerEntityToDto(savedCustomer.get());
      customerResponseDto.setAccount(mapper.convertToAccountDto(account));
      customerResponseDto.setOrdersIds(customerService.getOrdersIds(savedCustomer.get().getId()));

      return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDto);
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable("id") @Min(1) int id) {
    Optional<Customer> foundCustomer = customerService.find(id);

    if (foundCustomer.isPresent()) {
      CustomerResponseDto customerResponseDto = mapper.customerEntityToDto(foundCustomer.get());
      Account account = customerService.getAccount(foundCustomer.get().getAccountId());
      customerResponseDto.setAccount(mapper.convertToAccountDto(account));
      customerResponseDto.setOrdersIds(customerService.getOrdersIds(foundCustomer.get().getId()));

      return ResponseEntity.ok(customerResponseDto);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @GetMapping
  public ResponseEntity<PagedResult<CustomerResponseDto>> getAllCustomers(
      @Valid QueryParameter queryParameter) {

    Page<Customer> pagedResult = customerService.findAll(queryParameter);
    Page<CustomerResponseDto> pagedResultDto = pagedResult.map(mapper::customerEntityToDto);

    PagedResult<CustomerResponseDto> customerResponseDtoPagedResult =
        new PagedResult<>(
            pagedResultDto.toList(), queryParameter.getPage(), pagedResultDto.getTotalPages());

    for (CustomerResponseDto customerDto : customerResponseDtoPagedResult.getElements()) {
      Account account = customerService.getAccount(customerDto.getAccountId());
      customerDto.setAccount(mapper.convertToAccountDto(account));
      customerDto.setOrdersIds(customerService.getOrdersIds(customerDto.getId()));
    }

    return ResponseEntity.ok(customerResponseDtoPagedResult);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerResponseDto> updateCustomer(
      @PathVariable("id") @Min(1) int id,
      @Valid @RequestBody CustomerRequestDto customerRequestDto) {

    Customer customer = mapper.customerDtoToEntity(customerRequestDto);
    Account account = customerService.getAccount(customer.getAccountId());
    Optional<Customer> updatedCustomer = customerService.update(id, customer);

    if (updatedCustomer.isPresent()) {
      CustomerResponseDto customerResponseDto = mapper.customerEntityToDto(updatedCustomer.get());
      customerResponseDto.setAccount(mapper.convertToAccountDto(account));
      customerResponseDto.setOrdersIds(customerService.getOrdersIds(updatedCustomer.get().getId()));

      return ResponseEntity.ok(customerResponseDto);
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCustomer(@PathVariable("id") @Min(1) int id) {
    customerService.delete(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
