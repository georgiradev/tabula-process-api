package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TrackingHistoryResponseDTO {

    private int id;
    private LocalDateTime dateTimeUpdated;
    private Integer assigneeId;
    private Integer orderId;
    private Integer processStageId;

}
