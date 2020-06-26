package com.internship.tabulaprocessing.dto;

import com.internship.tabulaprocessing.entity.TypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOffTypeResponseDto {

  private int id;

  private TypeName name;

  private boolean isPaid;
}
