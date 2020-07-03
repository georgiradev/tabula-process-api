package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OrderItemPatchDto {

  @DecimalMin(value = "0.0", inclusive = false)
  private Double width;

  @DecimalMin(value = "0.0", inclusive = false)
  private Double height;

  @Min(value = 1)
  private Integer count;

  @Size(min = 3, message = "Note must be at least 3 characters long")
  private String note;

  @Min(value = 1)
  private Integer mediaId;

  @Min(value = 1)
  private Integer orderId;
}