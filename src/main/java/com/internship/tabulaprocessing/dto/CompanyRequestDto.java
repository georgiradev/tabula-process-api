package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CompanyRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(
            regexp = "^(?:\\d{0,5}\\.\\d{1,2})$|^\\d{0,5}$",
            message = "Not a valid discount rate. Max rate must be below or equal to 99999.99")
    private String discountRate;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "City is required")
    private String city;

    @Pattern(
            regexp = "^[A-Za-z]{2,4}(?=.{2,12}$)[-_\\s0-9]*(?:[a-zA-Z][-_\\s0-9]*){0,2}$",
            message = "Not a valid VAT number")
    private String vatNumber;

    // private List<Customer> customers;
}
