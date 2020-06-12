package com.internship.tabulaprocessing.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class MediaDto {

    @Min(value = 0)
    protected int id;
    @Size(min = 2, max = 40)
    private String name;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;

}
