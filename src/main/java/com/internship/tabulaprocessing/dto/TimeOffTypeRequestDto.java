package com.internship.tabulaprocessing.dto;

import com.internship.tabulaprocessing.entity.TypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOffTypeRequestDto {

  @NotNull(message = "Time off type must be valid")
  private TypeName name;

  @NotNull
  private Boolean isPaid;
}
