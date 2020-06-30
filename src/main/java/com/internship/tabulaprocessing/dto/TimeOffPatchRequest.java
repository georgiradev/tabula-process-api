package com.internship.tabulaprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOffPatchRequest {
    private int typeOfTimeOffId;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private int employeeId;

    private int approverId;

    private String comment;
}
