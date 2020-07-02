package com.internship.tabulaprocessing.dto;

import com.internship.tabulaprocessing.entity.TimeOffStatus;
import com.internship.tabulaprocessing.entity.TimeOffType;
import com.internship.tabulaprocessing.entity.TypeName;
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

    private int typeOfTimeOffId;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private int employeeId;

    private int approverId;

    private TimeOffStatus status;

    private String comment;
}