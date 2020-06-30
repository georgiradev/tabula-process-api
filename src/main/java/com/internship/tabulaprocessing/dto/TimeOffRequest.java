package com.internship.tabulaprocessing.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOffRequest {
    @Min(value = 1, message = "Тype of time off ID cannot be less than 1")
    private int typeOfTimeOffId;

    @NotNull(message = "You should provide startDateTime of timeOff")
    private LocalDateTime startDateTime;

    @NotNull(message = "You should provide endDateTime of timeOff")
    private LocalDateTime endDateTime;

    @Min(value = 1, message = "Employee ID cannot be less than 1")
    private int employeeId;

    @Min(value = 1, message = "Approver ID cannot be less than 1")
    private int approverId;

    private String comment;
}
