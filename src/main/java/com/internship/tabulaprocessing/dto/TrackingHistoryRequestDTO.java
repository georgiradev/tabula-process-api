package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class TrackingHistoryRequestDTO {

    @Min(value = 1,message = "assigneeId cannot be less than 1")
    private Integer assigneeId;
    @Min(value = 1,message = "processStageId cannot be less than 1")
    private Integer processStageId;
    @Min(value = 1,message = "orderId cannot be less than 1")
    private Integer orderId;
}
