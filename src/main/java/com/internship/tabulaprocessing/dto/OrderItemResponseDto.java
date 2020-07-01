package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponseDto {

  private String id;

  private double width;

  private double height;

  private int count;

  private String note;

  private BigDecimal totalPrice;

  private int mediaId;

  private Integer orderId;
}
