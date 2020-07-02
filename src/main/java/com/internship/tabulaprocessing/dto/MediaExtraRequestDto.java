package com.internship.tabulaprocessing.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class MediaExtraRequestDto {

    @Size(min = 2, max = 40)
    @NotBlank(message = "Name can not be null")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;
}

