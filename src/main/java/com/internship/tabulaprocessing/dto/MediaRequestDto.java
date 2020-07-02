package com.internship.tabulaprocessing.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class MediaRequestDto {

    @NotBlank
    @Size(min = 2, max = 40)
    private String name;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;

    private List<String> mediaExtraIds;
}

