package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemPatchDto {

  @DecimalMin(value = "0.0", inclusive = false)
  private Double width;

  @DecimalMin(value = "0.0", inclusive = false)
  private Double height;

  @Min(value = 1)
  private Integer count;

  private String note;

  @Min(value = 1)
  private Integer mediaId;

  private Integer orderId;
}
