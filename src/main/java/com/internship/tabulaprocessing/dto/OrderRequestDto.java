package com.internship.tabulaprocessing.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.internship.tabulaprocessing.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

  @FutureOrPresent private LocalDateTime dateTimeCreated;

  @Digits(integer = 5, fraction = 2)
  @Min(value = 1)
  private BigDecimal price;

  @Size(min = 5, max = 250)
  private String note;

  @JsonIgnore private Customer customer;

  @JsonProperty("customer")
  private void unpackNested(int customerId) {
    this.customer = new Customer();
    customer.setId(customerId);
  }

  private String[] orderItemsIds;
}
