package com.internship.tabulaprocessing.dto;

import com.internship.tabulaprocessing.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private int id;

    private Timestamp dateTimeCreated;

    private BigDecimal price;

    private String note;

    private Customer customer;

    private String[] orderItemsIds;
}