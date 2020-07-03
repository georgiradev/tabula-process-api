package com.internship.tabulaprocessing.dto;

import lombok.Data;

@Data
public class WorkLogPatchRequest {
    private double hoursSpent;
    private int employeeId;
    private int trackingHistoryId;
}
