package com.internship.tabulaprocessing.dto;

import com.internship.tabulaprocessing.entity.TimeOffStatus;
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
public class TimeOffPatchRequest {
    private TypeName typeOfTimeOff;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private int employeeId;

    private int approverId;

    private String comment;
}
