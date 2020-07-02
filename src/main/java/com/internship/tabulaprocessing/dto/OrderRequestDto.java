package com.internship.tabulaprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private String note;
    @Min(value = 1,message = "customerId cannot be less than 1")
    private int customerId;
    @Min(value = 1,message = "processId cannot be less than 1")
    private int processId;
    @NotNull
    private List<Integer> orderItemIds;

}
