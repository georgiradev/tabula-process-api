package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderUpdateRequestDTO {


    private String note;
    @Min(value = 1,message = "customerId cannot be less than 1")
    private int customerId;
    @Min(value = 1,message = "processStageId cannot be less than 1")
    private int processStageId;
    @NotNull(message = "Order items cannot be empty")
    private int[] orderItemIds;
}
