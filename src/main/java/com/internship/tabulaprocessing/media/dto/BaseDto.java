package com.internship.tabulaprocessing.media.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class BaseDto {
    @Min(value = 0)
    private int id;
}
