package com.internship.tabulaprocessing.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@Data
public class WorkLogPutRequest {
    @DecimalMin(value = "0.5", message = "hoursSpent cannot be less than 0.5h")
    private double hoursSpent;

    @Min(value=1, message = "EmployeeID cannot be less than 1")
    private int employeeId;

    @Min(value=1, message = "TrackingHistoryID cannot be less than 1")
    private int trackingHistoryId;
}
