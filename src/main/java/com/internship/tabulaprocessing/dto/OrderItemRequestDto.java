package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemRequestDto {

  @DecimalMin(value = "0.0", inclusive = false)
  private double width;

  @DecimalMin(value = "0.0", inclusive = false)
  private double height;

  @Min(value = 1)
  private int count;

  @NotBlank
  private String note;

  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 7, fraction = 2)
  private BigDecimal pricePerPiece;

  @Min(value = 1)
  private int mediaId;

  @Min(value = 1)
  private int orderId;
}