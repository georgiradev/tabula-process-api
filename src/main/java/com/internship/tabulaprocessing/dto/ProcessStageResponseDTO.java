package com.internship.tabulaprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessStageResponseDTO {

  @Min(value = 0, message = "Id cannot be less than zero")
  int id;

  @NotBlank
  @Size(
      min = 4,
      max = 30,
      message = "Process Stage name cannot be less than 4 or greater than 30 characters.")
  private String name;

  private String nextStage;

  @NotBlank(message = "Process cannot be blank.")
  private String process;

  @NotBlank(message = "Department cannot be blank.")
  private String department;
}
