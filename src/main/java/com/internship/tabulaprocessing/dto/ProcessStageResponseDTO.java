package com.internship.tabulaprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessStageResponseDTO {

  int id;
  private String name;
  private Integer nextStageId;
  private boolean firstStage;
  private int processId;
  private int departmentId;
}
