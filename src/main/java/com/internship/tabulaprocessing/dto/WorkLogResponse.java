package com.internship.tabulaprocessing.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WorkLogResponse {
    private int id;
    private double hoursSpent;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private int employeeId;
    private int trackingHistoryId;
}
