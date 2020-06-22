package com.internship.tabulaprocessing.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class MediaExtraDto  {

    @Min(value = 0)
    private int id;
    @Size(min = 2, max = 40)
    @NotNull
    @NotBlank
    private String name;
    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=5, fraction=2)
    private BigDecimal price;
}
