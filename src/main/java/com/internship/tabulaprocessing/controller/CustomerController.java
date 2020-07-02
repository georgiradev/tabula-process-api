package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.CustomerPatchDto;
import com.internship.tabulaprocessing.dto.CustomerRequestDto;
import com.internship.tabulaprocessing.dto.CustomerResponseDto;
import com.internship.tabulaprocessing.entity.Customer;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
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
  private final PatchMapper patchMapper;

  @PostMapping
  public ResponseEntity<CustomerResponseDto> createCustomer(
      @Valid @RequestBody CustomerRequestDto customerRequestDto) {

    Customer customer = mapper.customerDtoToEntity(customerRequestDto);
    Optional<Customer> savedCustomer = customerService.save(customer);

    if (savedCustomer.isPresent()) {
      CustomerResponseDto customerResponseDto = mapper.customerEntityToDto(savedCustomer.get());

      return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDto);
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable("id") @Min(1) int id) {
    Customer foundCustomer = customerService.find(id);
    CustomerResponseDto customerResponseDto = mapper.customerEntityToDto(foundCustomer);

    return ResponseEntity.ok(customerResponseDto);
  }

  @GetMapping
  public ResponseEntity<PagedResult<CustomerResponseDto>> getAllCustomers(
      @Valid QueryParameter queryParameter) {

    Page<Customer> pagedResult = customerService.findAll(queryParameter);
    Page<CustomerResponseDto> pagedResultDto = pagedResult.map(mapper::customerEntityToDto);

    PagedResult<CustomerResponseDto> customerResponseDtoPagedResult =
        new PagedResult<>(
            pagedResultDto.toList(),
            queryParameter.getPage(),
            pagedResultDto.getTotalPages(),
            pagedResult.getTotalElements());

    return ResponseEntity.ok(customerResponseDtoPagedResult);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerResponseDto> updateCustomer(
      @PathVariable("id") @Min(1) int id,
      @Valid @RequestBody CustomerRequestDto customerRequestDto) {

    Customer customer = mapper.customerDtoToEntity(customerRequestDto);
    Optional<Customer> updatedCustomer = customerService.update(id, customer);

    if (updatedCustomer.isPresent()) {
      CustomerResponseDto customerResponseDto = mapper.customerEntityToDto(updatedCustomer.get());

      return ResponseEntity.ok(customerResponseDto);
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCustomer(@PathVariable("id") @Min(1) int id) {
    customerService.delete(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping(
      path = "/{id}",
      consumes = {"application/merge-patch+json"})
  public ResponseEntity<CustomerResponseDto> patchCustomer(
      @PathVariable int id, @Valid @RequestBody CustomerPatchDto customerPatchDto) {

    Customer foundCustomer = customerService.find(id);
    Customer patchedCustomer = patchMapper.mapObjectsToCustomer(customerPatchDto, foundCustomer);
    Optional<Customer> updatedCustomer = customerService.update(id, patchedCustomer);

    if (updatedCustomer.isPresent()) {
      CustomerResponseDto customerResponseDto = mapper.customerEntityToDto(updatedCustomer.get());

      return ResponseEntity.ok(customerResponseDto);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @GetMapping("account/{id}")
  public ResponseEntity<CustomerResponseDto> getCustomerByAccountId(
      @PathVariable("id") @Min(1) int id) {
    Customer foundCustomer = customerService.findByAccountId(id);
    CustomerResponseDto customerResponseDto = mapper.customerEntityToDto(foundCustomer);

    return ResponseEntity.ok(customerResponseDto);
  }
}
