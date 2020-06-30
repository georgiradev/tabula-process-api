package com.internship.tabulaprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOffPatchRequest {
    @Min(value = 1, message = "TypeOfTimeOffId cannot be less than 1")
    private int typeOfTimeOffId;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    @Min(value = 1, message = "TypeOfTimeOffId cannot be less than 1")
    private int employeeId;

    @Min(value = 1, message = "TypeOfTimeOffId cannot be less than 1")
    private int approverId;

    private String comment;
}
