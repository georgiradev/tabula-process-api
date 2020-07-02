package com.internship.tabulaprocessing.dto;

import lombok.Data;
import javax.validation.constraints.Min;

@Data
public class WorkLogCreateRequest {
    @Min(value=1, message = "EmployeeID cannot be less than 1")
    private int employeeId;

    @Min(value=1, message = "TrackingHistoryID cannot be less than 1")
    private int trackingHistoryId;
}
