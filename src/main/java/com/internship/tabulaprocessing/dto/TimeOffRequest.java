package com.internship.tabulaprocessing.dto;

import com.internship.tabulaprocessing.entity.TimeOffType;
import com.internship.tabulaprocessing.entity.TypeName;
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
public class TimeOffRequest {
    @NotNull(message = "Type off time off cannot be null!")
    private TypeName typeOfTimeOff;

    @NotNull(message = "You should provide startDateTime of timeOff")
    private LocalDateTime startDateTime;

    @NotNull(message = "You should provide endDateTime of timeOff")
    private LocalDateTime endDateTime;

    private int employeeId;

    private int approverId;

    private String comment;
}
