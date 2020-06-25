package com.internship.tabulaprocessing.dto;

import com.internship.tabulaprocessing.entity.TimeOffStatus;
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
public class TimeOffPatchRequestForDeleting {
    private TimeOffStatus status;
}
