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
public class TimeOffRequest {
    //TODO: to be changed to enum type
    String typeOfTimeOff;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
}
