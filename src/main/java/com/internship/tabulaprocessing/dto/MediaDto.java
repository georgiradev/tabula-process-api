package com.internship.tabulaprocessing.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class MediaDto {

  @Min(value = 0)
  protected int id;

  @NotNull
  @NotBlank
  @Size(min = 2, max = 40)
  private String name;

  @NotNull(message = "Price must not be null")
  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 5, fraction = 2)
  private BigDecimal price;
  // Media Extra IDs
  private List<String> mediaExtraIds;
}
