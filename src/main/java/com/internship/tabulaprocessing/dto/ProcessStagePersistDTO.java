package com.internship.tabulaprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessStagePersistDTO {

  @NotBlank
  @Size(
      min = 4,
      max = 30,
      message = "Process Stage name cannot be less than 4 or greater than 30 characters.")
  private String name;

  private String nextStage;

  @NotBlank
  private String process;

  @NotBlank
  private String department;

}
