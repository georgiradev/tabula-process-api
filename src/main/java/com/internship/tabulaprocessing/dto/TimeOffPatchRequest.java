package com.internship.tabulaprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOffPatchRequest {
    //ToDo: to be set to enum type
    private String typeOfTimeOff;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private int employeeId;

    private int approverId;

    private String comment;
}
