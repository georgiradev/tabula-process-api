package com.internship.tabulaprocessing.dto;

import com.internship.tabulaprocessing.entity.TimeOffStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOffResponse {
    private int id;
    //TODO: to be changed to enum type
    private String typeOfTimeOff;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int employeeId;
    private int approverId;
    private TimeOffStatus status;
    private String comment;
}
